package modelo;

import java.time.LocalDateTime;

public class Like {
    private int id;
    private int usuarioId;
    private int publicacionId;
    private LocalDateTime fechaLike;

    public Like() {
    }

    public Like(int usuarioId, int publicacionId) {
        this.usuarioId = usuarioId;
        this.publicacionId = publicacionId;
    }

    public Like(int id, int usuarioId, int publicacionId, LocalDateTime fechaLike) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.publicacionId = publicacionId;
        this.fechaLike = fechaLike;
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

    public LocalDateTime getFechaLike() {
        return fechaLike;
    }

    public void setFechaLike(LocalDateTime fechaLike) {
        this.fechaLike = fechaLike;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", publicacionId=" + publicacionId +
                ", fechaLike=" + fechaLike +
                '}';
    }
}
