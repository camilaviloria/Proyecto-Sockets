package modelo;

import java.time.LocalDateTime;

public class Comentario {
    private int id;
    private int usuarioId;
    private int publicacionId;
    private String texto;
    private LocalDateTime fechaComentario;

    public Comentario() {
    }

    public Comentario(int usuarioId, int publicacionId, String texto) {
        this.usuarioId = usuarioId;
        this.publicacionId = publicacionId;
        this.texto = texto;
    }

    public Comentario(int id, int usuarioId, int publicacionId, String texto, LocalDateTime fechaComentario) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.publicacionId = publicacionId;
        this.texto = texto;
        this.fechaComentario = fechaComentario;
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

    public int getPublicacionId() {
        return publicacionId;
    }

    public void setPublicacionId(int publicacionId) {
        this.publicacionId = publicacionId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(LocalDateTime fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", publicacionId=" + publicacionId +
                ", texto='" + texto + '\'' +
                ", fechaComentario=" + fechaComentario +
                '}';
    }
}
