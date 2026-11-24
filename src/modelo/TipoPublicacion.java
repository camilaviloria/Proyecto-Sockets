package modelo;

public class TipoPublicacion {
    public static final String PUBLICACIONES_INDIVIDUALES = "Publicaciones_Individuales";
    public static final String REELS = "Reels";
    public static final String HISTORIAS = "Historias";
    public static final String PUBLICACIONES_COLABORATIVAS = "Publicaciones colaborativas";

    private int id;
    private String nombre;

    public TipoPublicacion() {
    }

    public TipoPublicacion(String nombre) {
        this.nombre = nombre;
    }

    public TipoPublicacion(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TipoPublicacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
