package service;
import db.DBComponent;
import modelo.Contador;
import java.util.HashMap;
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
            Map<String, Object> paramsBusqueda = new HashMap<>();
            paramsBusqueda.put("username", username);
            
            Contador resultado = db.buscarUno("contar_usuario_por_username", paramsBusqueda, Contador.class);
            
            System.out.println("DEBUG: Resultado de la BD: " + (resultado != null ? resultado.getTotal() : "null"));
            
            if (resultado != null && resultado.getTotal() > 0) {
                throw new Exception("El username '" + username + "' ya esta en uso.");
            }

            Map<String, Object> paramsInsert = new java.util.LinkedHashMap<>();
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
    public void darLike(int usuarioId, int publicacionId) {
        System.out.println("\n--- INICIANDO TRANSACCION: DAR LIKE ---");
        db.beginTransaction();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("usuario_id", usuarioId);
            params.put("publicacion_id", publicacionId);
            
            db.insertar("insertar_like", params);
            db.commit();
            System.out.println("Like registrado con exito.");
        } catch (Exception e) {
            db.rollback();
            System.err.println("Error al dar like: " + e.getMessage());
        }
    }
}