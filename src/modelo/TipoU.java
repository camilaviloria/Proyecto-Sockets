package modelo;

public class TipoU {
    public static final String USUARIO_PERSONAL = "Usuario_Personal";
    public static final String USUARIO_INFLUENCER = "Usuario_Influencer";
    public static final String USUARIO_EMPRESARIAL = "Usuario_Empresarial";

    private int id;
    private String nombre;

    public TipoU() {
    }

    public TipoU(String nombre) {
        this.nombre = nombre;
    }

    public TipoU(int id, String nombre) {
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
        return "TipoU{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
