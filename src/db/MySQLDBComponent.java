package db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySQLDBComponent implements DBComponent {
    
    @Override
    public void conectar() {
        System.out.println("Conectado a MySQL (Simulado para demostracion).");
    }

    @Override
    public void desconectar() {
        System.out.println("Desconectado de MySQL.");
    }

    @Override
    public void beginTransaction() { System.out.println("BEGIN en MySQL..."); }

    @Override
    public void commit() { System.out.println("COMMIT en MySQL..."); }

    @Override
    public void rollback() { System.out.println("ROLLBACK en MySQL..."); }

    @Override
    public boolean insertar(String queryName, Map<String, Object> parametros) {
        System.out.println("INSERT simulado en MySQL: " + queryName + " con datos: " + parametros);
        return true;
    }

    @Override
    public <T> T buscarUno(String queryName, Map<String, Object> parametros, Class<T> clase) {
        System.out.println("SELECT (buscarUno) simulado en MySQL: " + queryName);
        return null;
    }

    @Override
    public <T> List<T> buscarTodos(String queryName, Map<String, Object> parametros, Class<T> clase) {
        System.out.println("SELECT (buscarTodos) simulado en MySQL: " + queryName);
        return new ArrayList<>();
    }
}