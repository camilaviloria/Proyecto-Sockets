package db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgresDBComponent implements DBComponent {
    
    private Connection conexion;
    private QueryManager queryManager;

    public PostgresDBComponent() {
        this.queryManager = new QueryManager();
    }

    @Override
    public void conectar() {
        try {
            String url = "jdbc:postgresql://localhost:5432/Instagram";
            String usuario = "postgres";
            String password = "13082006";
            
            this.conexion = DriverManager.getConnection(url, usuario, password);
            this.conexion.setAutoCommit(false); 
            System.out.println("Conexion exitosa a PostgreSQL.");
        } catch (SQLException e) {
            System.err.println("Error de conexion a la BD: " + e.getMessage());
        }
    }

    @Override
    public void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Desconectado de la BD.");
            }
        } catch (SQLException e) {
            System.err.println("Error al desconectar: " + e.getMessage());
        }
    }

    @Override
    public void beginTransaction() {
        try {
            if (conexion != null) conexion.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Error al iniciar transaccion: " + e.getMessage());
        }
    }

    @Override
    public void commit() {
        try {
            if (conexion != null) {
                conexion.commit();
                System.out.println("DEBUG: COMMIT ejecutado en la BD.");
            }
        } catch (SQLException e) {
            System.err.println("Error en COMMIT: " + e.getMessage());
        }
    }
    @Override
    public void rollback() {
        try {
            if (conexion != null) conexion.rollback();
        } catch (SQLException e) {
            System.err.println("Error en ROLLBACK: " + e.getMessage());
        }
    }

    @Override
    public boolean insertar(String queryName, Map<String, Object> parametros) {
        String sql = queryManager.getQuery(queryName);
        if (sql == null) return false;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            int index = 1;
            for (Object valor : parametros.values()) {
                ps.setObject(index++, valor);
            }
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al ejecutar insertar (" + queryName + "): " + e.getMessage());
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
        if (sql == null) return new ArrayList<>();

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
                    if (clase == Map.class) {
                        Map<String, Object> fila = new HashMap<>();
                        for (int i = 1; i <= cantidadColumnas; i++) {
                            fila.put(metaData.getColumnLabel(i).toLowerCase(), rs.getObject(i));
                        }
                        listaResultados.add(clase.cast(fila));
                        continue; 
                    }

                    T objeto = clase.getDeclaredConstructor().newInstance();

                    for (int i = 1; i <= cantidadColumnas; i++) {
                        String nombreColumna = metaData.getColumnLabel(i);
                        Object valorColumna = rs.getObject(i);

                        for (Field field : clase.getDeclaredFields()) {
                            String nombreCampoNormalizado = field.getName().replace("_", "").toLowerCase();
                            String nombreColumnaNormalizado = nombreColumna.replace("_", "").toLowerCase();

                            if (nombreCampoNormalizado.equals(nombreColumnaNormalizado)) {
                                field.setAccessible(true);
                                
                                if (valorColumna != null) {
                                    // Conversión segura para tipos de datos
                                    if (field.getType() == int.class || field.getType() == Integer.class) {
                                        field.set(objeto, ((Number) valorColumna).intValue());
                                    } else if (field.getType() == LocalDateTime.class && valorColumna instanceof Timestamp) {
                                        field.set(objeto, ((Timestamp) valorColumna).toLocalDateTime());
                                    } else {
                                        field.set(objeto, valorColumna);
                                    }
                                }
                                break;
                            }
                        }
                    }
                    listaResultados.add(objeto);
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR REAL DE REFLEXION EN " + queryName + ":");
            e.printStackTrace();
        }

        return listaResultados;
    }
}