package db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostgresDBComponent implements DBComponent {
    
    private Connection conexion;
    private QueryManager queryManager;

    public PostgresDBComponent() {
        // Inicializamos el gestor de consultas (que lee el queries.json)
        this.queryManager = new QueryManager();
    }

    @Override
    public void conectar() {
        try {
            // ATENCIÓN: Cambia "postgres", "admin" y "instagram" por tus credenciales reales
            String url = "jdbc:postgresql://localhost:5432/instagram";
            String usuario = "postgres";
            String password = "admin";
            
            this.conexion = DriverManager.getConnection(url, usuario, password);
            // Desactivamos el autocommit para poder usar transacciones (COMMIT / ROLLBACK)
            this.conexion.setAutoCommit(false); 
            System.out.println("✅ Conexión exitosa a PostgreSQL.");
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión a la BD: " + e.getMessage());
        }
    }

    @Override
    public void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("🔌 Desconectado de la BD.");
            }
        } catch (SQLException e) {
            System.err.println("Error al desconectar: " + e.getMessage());
        }
    }

    @Override
    public void beginTransaction() {
        try {
            if (conexion != null) {
                conexion.setAutoCommit(false);
            }
        } catch (SQLException e) {
            System.err.println("Error al iniciar transacción: " + e.getMessage());
        }
    }

    @Override
    public void commit() {
        try {
            if (conexion != null) {
                conexion.commit();
            }
        } catch (SQLException e) {
            System.err.println("Error en COMMIT: " + e.getMessage());
        }
    }

    @Override
    public void rollback() {
        try {
            if (conexion != null) {
                conexion.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Error en ROLLBACK: " + e.getMessage());
        }
    }

    @Override
    public boolean insertar(String queryName, Map<String, Object> parametros) {
        String sql = queryManager.getQuery(queryName);
        if (sql == null) {
            System.err.println("❌ Error: La query '" + queryName + "' no existe en el JSON.");
            return false;
        }

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            int index = 1;
            // Recorremos el mapa de parámetros y los inyectamos en el PreparedStatement
            for (Object valor : parametros.values()) {
                ps.setObject(index++, valor);
            }
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al ejecutar insertar (" + queryName + "): " + e.getMessage());
            return false;
        }
    }

    @Override
    public <T> T buscarUno(String queryName, Map<String, Object> parametros, Class<T> clase) {
        List<T> resultados = buscarTodos(queryName, parametros, clase);
        if (resultados != null && !resultados.isEmpty()) {
            return resultados.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> buscarTodos(String queryName, Map<String, Object> parametros, Class<T> clase) {
        String sql = queryManager.getQuery(queryName);
        if (sql == null) {
            System.err.println("❌ Error: La query '" + queryName + "' no existe en el JSON.");
            return new ArrayList<>();
        }

        List<T> listaResultados = new ArrayList<>();

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            int index = 1;
            for (Object valor : parametros.values()) {
                ps.setObject(index++, valor);
            }

            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int cantidadColumnas = metaData.getColumnCount();

                while (rs.next()) {
                    // Creamos una instancia vacía de la clase (ej. new Usuario())
                    T objeto = clase.getDeclaredConstructor().newInstance();

                    // Mapeamos cada columna del ResultSet al atributo del objeto
                    for (int i = 1; i <= cantidadColumnas; i++) {
                        String nombreColumna = metaData.getColumnLabel(i);
                        Object valorColumna = rs.getObject(i);

                        // Buscamos el campo en la clase que coincida con el nombre de la columna
                        for (Field field : clase.getDeclaredFields()) {
                            // Comparamos ignorando mayúsculas y quitando guiones bajos (ej. "tipo_usuario_id" == "tipoUsuarioId")
                            String nombreCampoNormalizado = field.getName().replace("_", "").toLowerCase();
                            String nombreColumnaNormalizado = nombreColumna.replace("_", "").toLowerCase();

                            if (nombreCampoNormalizado.equals(nombreColumnaNormalizado)) {
                                field.setAccessible(true); // Permite acceder a atributos privados
                                field.set(objeto, valorColumna);
                                break;
                            }
                        }
                    }
                    listaResultados.add(objeto);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error al ejecutar búsqueda (" + queryName + "): " + e.getMessage());
        }

        return listaResultados;
    }
}