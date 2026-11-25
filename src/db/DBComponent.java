package db;

import java.util.List;
import java.util.Map;

public interface DBComponent {
    void conectar();
    void desconectar();
    void beginTransaction();
    void commit();
    void rollback();
    
    boolean insertar(String queryName, Map<String, Object> parametros);
    <T> T buscarUno(String queryName, Map<String, Object> parametros, Class<T> clase);
    <T> List<T> buscarTodos(String queryName, Map<String, Object> parametros, Class<T> clase);
}