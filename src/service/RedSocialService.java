package service;

import db.DBComponent;
import modelo.Usuario;
import java.util.HashMap;
import java.util.Map;

public class RedSocialService {
    private DBComponent db;

    public RedSocialService(DBComponent db) {
        this.db = db;
    }

    /**
     * Simula el registro de un usuario validando que no exista previamente.
     * (Transacción de la pizarra 2)
     */
    public void registrarUsuario(String username, String email, String password, int tipoUsuarioId) {
        System.out.println("\n--- INICIANDO TRANSACCIÓN: REGISTRO DE USUARIO ---");
        db.beginTransaction();
        
        try {
            // 1. Verificar si el usuario ya existe
            Map<String, Object> paramsBusqueda = new HashMap<>();
            paramsBusqueda.put("username", username);
            Usuario usuarioExistente = db.buscarUno("buscar_usuario_por_username", paramsBusqueda, Usuario.class);

            if (usuarioExistente != null) {
                throw new Exception("El username '" + username + "' ya está en uso.");
            }

            // 2. Insertar el nuevo usuario
            Map<String, Object> paramsInsert = new HashMap<>();
            paramsInsert.put("tipo_usuario_id", tipoUsuarioId);
            paramsInsert.put("username", username);
            paramsInsert.put("email", email);
            paramsInsert.put("password_hash", password);

            boolean exito = db.insertar("insertar_usuario", paramsInsert);

            if (!exito) {
                throw new Exception("Error al insertar en la base de datos.");
            }

            // 3. Si todo sale bien, confirmamos la transacción
            db.commit();
            System.out.println(" Transacción EXITOSA: Usuario '" + username + "' registrado.");

        } catch (Exception e) {
            // 4. Si algo falla, deshacemos todo
            db.rollback();
            System.err.println(" Transacción FALLIDA (ROLLBACK): " + e.getMessage());
        }
    }

    /**
     * Simula dar un Like (Transacción simple)
     */
    public void darLike(int usuarioId, int publicacionId) {
        System.out.println("\n--- INICIANDO TRANSACCIÓN: DAR LIKE ---");
        db.beginTransaction();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("usuario_id", usuarioId);
            params.put("publicacion_id", publicacionId);
            
            db.insertar("insertar_like", params);
            db.commit();
            System.out.println(" Like registrado con éxito.");
        } catch (Exception e) {
            db.rollback();
            System.err.println(" Error al dar like: " + e.getMessage());
        }
    }
}