import db.DBComponent;
import db.PostgresDBComponent;
import db.MySQLDBComponent;
import service.RedSocialService;

public class main {
    public static void main(String[] args) {
        
        // =================================================================
        // DEMOSTRACION 1: USANDO POSTGRESQL (El motor real)
        // =================================================================
        System.out.println("=== INICIANDO PRUEBA CON POSTGRESQL ===");
        DBComponent dbPostgres = new PostgresDBComponent();
        dbPostgres.conectar();
        
        RedSocialService servicePostgres = new RedSocialService(dbPostgres);
        servicePostgres.registrarUsuario("juan_perez", "juan@mail.com", "hash123", 1); // Exito
        servicePostgres.registrarUsuario("juan_perez", "juan2@mail.com", "hash456", 1); // Rollback
        
        dbPostgres.desconectar();


        // =================================================================
        // DEMOSTRACION 2: USANDO MYSQL (El componente simulado por desacoplamiento)
        // =================================================================
        System.out.println("\n\n=== INICIANDO PRUEBA CON MYSQL (DESACOPLADO) ===");
        DBComponent dbMySQL = new MySQLDBComponent();
        dbMySQL.conectar();
        
        RedSocialService serviceMySQL = new RedSocialService(dbMySQL);
        // El mismo servicio, la misma logica, pero con otro motor
        serviceMySQL.registrarUsuario("maria_gomez", "maria@mail.com", "pass123", 1); 
        
        dbMySQL.desconectar();
    }
}