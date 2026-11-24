package db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class QueryManager {
    
    // Aquí se guardarán las consultas leídas del JSON
    private Map<String, String> queries;

    public QueryManager() {
        this.queries = new HashMap<>();
        cargarQueries();
    }

    /**
     * Lee el archivo queries.json de la carpeta resources
     * y lo convierte en un Mapa de Java usando Gson.
     */
    private void cargarQueries() {
        try {
            // Busca el archivo queries.json en la carpeta resources
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("queries.json");
            
            if (inputStream == null) {
                System.err.println("Error: No se encontró el archivo queries.json en resources.");
                return;
            }

            Reader reader = new InputStreamReader(inputStream); // Creamos un Reader para leer el InputStream que es el archivo JSON
            
            // Gson lee el JSON y lo convierte en un Map<String, String>
            this.queries = new Gson().fromJson(reader, new TypeToken<Map<String, String>>(){}.getType());
            
            System.out.println("QueryManager: queries.json cargado correctamente (" + queries.size() + " consultas).");
            
        } catch (Exception e) {
            System.err.println("Error al cargar las consultas: " + e.getMessage());
        }
    }

    /**
     * Obtiene una consulta SQL por su nombre clave.
     * @param nombreQuery El nombre de la consulta (ej. "insertar_usuario")
     * @return El string con el SQL, o null si no existe.
     */
    public String getQuery(String nombreQuery) {
        if (queries == null || !queries.containsKey(nombreQuery)) {
            System.err.println("Advertencia: La consulta '" + nombreQuery + "' no existe en queries.json");
            return null;
        }
        return queries.get(nombreQuery);
    }

    /**
     * (Opcional) Para saber cuántas consultas se cargaron.
     */
    public int getCantidadQueries() {
        return queries != null ? queries.size() : 0;
    }
}