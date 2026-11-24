import db.DBComponent;
import db.PostgresDBComponent;
import service.RedSocialService;

import java.util.HashMap;
import java.util.Map;

public class main {
    public static void main(String[] args) {
        // 1. Instanciar e iniciar el componente
        DBComponent db = new PostgresDBComponent();
        db.conectar();

        // 2. Instanciar el servicio
        RedSocialService service = new RedSocialService(db);

        // 3. PRUEBA 1: Registro exitoso (COMMIT)
        service.registrarUsuario("camila_99", "camila@mail.com", "hash123", 1);

        // 4. PRUEBA 2: Error por duplicado (ROLLBACK)
        // Intentamos registrar el mismo usuario otra vez
        service.registrarUsuario("camila_99", "otro_correo@mail.com", "hash456", 1);

        // 5. PRUEBA 3: Insertar un Like
        // (Asumiendo que el usuario_id 1 existe y publicacion_id 1 existe en tu BD)
        // Si no existen, el like fallará por llave foránea
        // service.darLike(1, 1);

        // 6. PRUEBA 4: Consultar todos los usuarios (Usando Reflexión)
        System.out.println("\n--- CONSULTANDO USUARIOS EN LA BD ---");
        Map<String, Object> paramsVacios = new HashMap<>();
        // Usamos una query que traiga todo (debes agregarla a tu queries.json)
        // List<Usuario> usuarios = db.buscarTodos("buscar_todos_usuarios", paramsVacios, Usuario.class);
        // for(Usuario u : usuarios) {
        //     System.out.println(u);
        // }

        db.desconectar();
    }
}