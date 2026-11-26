package db;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLServerDBComponent implements DBComponent {
    
    private Connection conexion;
    private QueryManager queryManager;

    public SQLServerDBComponent() {
        this.queryManager = new QueryManager();
    }

    @Override
    public void conectar() {
        try {
            // Cadena de conexión para SQL Server
            String url = "jdbc:sqlserver://localhost:1433;"
           + "databaseName=Instagram;"
           + "encrypt=true;"
           + "trustServerCertificate=true;";
           String user = "sa";
           String password = "13082006";
           this.conexion = DriverManager.getConnection(url, user, password);
           this.conexion.setAutoCommit(false); 
            System.out.println("Conexion exitosa a SQL Server.");
        } catch (SQLException e) {
            System.err.println("Error de conexion a SQL Server: " + e.getMessage());
        }
    }

    @Override
    public void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Desconectado de SQL Server.");
            }
        } catch (SQLException e) {
            System.err.println("Error al desconectar: " + e.getMessage());
        }
    }

    @Override
    public void beginTransaction() {
        try { if (conexion != null) conexion.setAutoCommit(false); } catch (SQLException e) {}
    }

    @Override
    public void commit() {
        try { if (conexion != null) conexion.commit(); } catch (SQLException e) {}
    }

    @Override
    public void rollback() {
        try { if (conexion != null) conexion.rollback(); } catch (SQLException e) {}
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
        if (resultados != null && !resultados.isEmpty()) return resultados.get(0);
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
            System.err.println("Error al ejecutar busqueda (" + queryName + "): " + e.getMessage());
        }
        return listaResultados;
    }
}