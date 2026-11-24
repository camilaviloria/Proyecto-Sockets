package modelo;

import java.time.LocalDateTime;

public class Mensaje {
    private int id;
    private int emisorId;
    private int receptorId;
    private String contenido;
    private LocalDateTime fechaEnvio;
    private String estado;

    public Mensaje() {
    }

    public Mensaje(int emisorId, int receptorId, String contenido, String estado) {
        this.emisorId = emisorId;
        this.receptorId = receptorId;
        this.contenido = contenido;
        this.estado = estado;
    }

    public Mensaje(int id, int emisorId, int receptorId, String contenido,
                   LocalDateTime fechaEnvio, String estado) {
        this.id = id;
        this.emisorId = emisorId;
        this.receptorId = receptorId;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmisorId() {
        return emisorId;
    }

    public void setEmisorId(int emisorId) {
        this.emisorId = emisorId;
    }

    public int getReceptorId() {
        return receptorId;
    }

    public void setReceptorId(int receptorId) {
        this.receptorId = receptorId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id=" + id +
                ", emisorId=" + emisorId +
                ", receptorId=" + receptorId +
                ", contenido='" + contenido + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", estado='" + estado + '\'' +
                '}';
    }
}
