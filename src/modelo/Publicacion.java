package modelo;

import java.time.LocalDateTime;

public class Publicacion {
    private int id;
    private int usuarioId;
    private int tipoPublicacionId;
    private String urlContenido;
    private String caption;
    private String ubicacion;
    private LocalDateTime fechaPublicacion;

    public Publicacion() {
    }

    public Publicacion(int usuarioId, int tipoPublicacionId, String urlContenido, String caption) {
        this.usuarioId = usuarioId;
        this.tipoPublicacionId = tipoPublicacionId;
        this.urlContenido = urlContenido;
        this.caption = caption;
    }

    public Publicacion(int id, int usuarioId, int tipoPublicacionId, String urlContenido,
                       String caption, String ubicacion, LocalDateTime fechaPublicacion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.tipoPublicacionId = tipoPublicacionId;
        this.urlContenido = urlContenido;
        this.caption = caption;
        this.ubicacion = ubicacion;
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getTipoPublicacionId() {
        return tipoPublicacionId;
    }

    public void setTipoPublicacionId(int tipoPublicacionId) {
        this.tipoPublicacionId = tipoPublicacionId;
    }

    public String getUrlContenido() {
        return urlContenido;
    }

    public void setUrlContenido(String urlContenido) {
        this.urlContenido = urlContenido;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", tipoPublicacionId=" + tipoPublicacionId +
                ", urlContenido='" + urlContenido + '\'' +
                ", caption='" + caption + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", fechaPublicacion=" + fechaPublicacion +
                '}';
    }
}
