package service;
import db.DBComponent;
import modelo.Contador;
import modelo.Publicacion;
import modelo.Usuario;
import java.util.LinkedHashMap;
import java.util.Map;

public class RedSocialService {
    private DBComponent db;

    public RedSocialService(DBComponent db) {
        this.db = db;
    }

    public void registrarUsuario(String username, String email, String password, int tipoUsuarioId) {
        System.out.println("\n--- INICIANDO TRANSACCION: REGISTRO DE USUARIO ---");
        db.beginTransaction();

        try {
            Map<String, Object> paramsBusqueda = new LinkedHashMap<>();
            paramsBusqueda.put("username", username);

            Contador resultado = db.buscarUno("contar_usuario_por_username", paramsBusqueda, Contador.class);

            System.out.println("DEBUG: Resultado de la BD: " + (resultado != null ? resultado.getTotal() : "null"));

            if (resultado != null && resultado.getTotal() > 0) {
                throw new Exception("El username '" + username + "' ya esta en uso.");
            }

            Map<String, Object> paramsInsert = new LinkedHashMap<>();
            paramsInsert.put("tipo_usuario_id", tipoUsuarioId);
            paramsInsert.put("username", username);
            paramsInsert.put("email", email);
            paramsInsert.put("password_hash", password);

            boolean exito = db.insertar("insertar_usuario", paramsInsert);

            if (!exito) {
                throw new Exception("Error al insertar en la base de datos.");
            }

            db.commit();
            System.out.println("Transaccion EXITOSA: Usuario '" + username + "' registrado.");

        } catch (Exception e) {
            db.rollback();
            System.err.println("Transaccion FALLIDA (ROLLBACK): " + e.getMessage());
        }
    }

    public Usuario obtenerUsuario(String username) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("username", username);
        return db.buscarUno("obtener_usuario_por_username", params, Usuario.class);
    }

    public void crearPublicacion(int usuarioId, int tipoPublicacionId, String url, String caption, String ubicacion) {
        System.out.println("\n--- INICIANDO TRANSACCION: CREAR PUBLICACION ---");
        db.beginTransaction();

        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("usuario_id", usuarioId);
            params.put("tipo_publicacion_id", tipoPublicacionId);
            params.put("url_contenido", url);
            params.put("caption", caption);
            params.put("ubicacion", ubicacion);

            boolean exito = db.insertar("insertar_publicacion", params);

            if (!exito) {
                throw new Exception("No se pudo guardar la publicacion.");
            }

            db.commit();
            System.out.println("Transaccion EXITOSA: Publicacion creada.");

        } catch (Exception e) {
            db.rollback();
            System.err.println("Transaccion FALLIDA (ROLLBACK): " + e.getMessage());
        }
    }

    public Publicacion obtenerPublicacion(int publicacionId) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("id", publicacionId);
        return db.buscarUno("obtener_publicacion_por_id", params, Publicacion.class);
    }

    public void darLike(int usuarioId, int publicacionId) {
        System.out.println("\n--- INICIANDO TRANSACCION: DAR LIKE ---");
        db.beginTransaction();

        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("usuario_id", usuarioId);
            params.put("publicacion_id", publicacionId);

            boolean exito = db.insertar("insertar_like", params);

            if (!exito) {
                throw new Exception("No se pudo registrar el like.");
            }

            db.commit();
            System.out.println("Like registrado con exito.");

        } catch (Exception e) {
            db.rollback();
            System.err.println("Error al dar like (ROLLBACK): " + e.getMessage());
        }
    }

    public int contarLikes(int publicacionId) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("publicacion_id", publicacionId);
        Contador resultado = db.buscarUno("contar_likes_publicacion", params, Contador.class);
        return (resultado != null) ? resultado.getTotal() : 0;
    }

    public void comentarPublicacion(int usuarioId, int publicacionId, String texto) {
        System.out.println("\n--- INICIANDO TRANSACCION: COMENTAR PUBLICACION ---");
        db.beginTransaction();

        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("usuario_id", usuarioId);
            params.put("publicacion_id", publicacionId);
            params.put("texto", texto);

            boolean exito = db.insertar("insertar_comentario", params);

            if (!exito) {
                throw new Exception("No se pudo guardar el comentario.");
            }

            db.commit();
            System.out.println("Comentario agregado con exito.");

        } catch (Exception e) {
            db.rollback();
            System.err.println("Error al comentar (ROLLBACK): " + e.getMessage());
        }
    }

    public void enviarMensaje(int emisorId, int receptorId, String contenido) {
        System.out.println("\n--- INICIANDO TRANSACCION: ENVIAR MENSAJE ---");
        db.beginTransaction();

        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("emisor_id", emisorId);
            params.put("receptor_id", receptorId);
            params.put("contenido", contenido);

            boolean exito = db.insertar("insertar_mensaje", params);

            if (!exito) {
                throw new Exception("No se pudo enviar el mensaje.");
            }

            db.commit();
            System.out.println("Mensaje enviado con exito.");

        } catch (Exception e) {
            db.rollback();
            System.err.println("Error al enviar mensaje (ROLLBACK): " + e.getMessage());
        }
    }
}