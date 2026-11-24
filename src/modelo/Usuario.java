package modelo;

import java.time.LocalDateTime;

public class Usuario {
    private int id;
    private int tipoUsuarioId;
    private String username;
    private String email;
    private String passwordHash;
    private String fotoPerfil;
    private String biografia;
    private LocalDateTime fechaRegistro;

    public Usuario() {
    }

    public Usuario(int tipoUsuarioId, String username, String email, String passwordHash) {
        this.tipoUsuarioId = tipoUsuarioId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Usuario(int id, int tipoUsuarioId, String username, String email, String passwordHash,
                   String fotoPerfil, String biografia, LocalDateTime fechaRegistro) {
        this.id = id;
        this.tipoUsuarioId = tipoUsuarioId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fotoPerfil = fotoPerfil;
        this.biografia = biografia;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(int tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", tipoUsuarioId=" + tipoUsuarioId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                ", biografia='" + biografia + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
