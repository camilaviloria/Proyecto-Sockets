import db.DBComponent;
import db.PostgresDBComponent;
import db.SQLServerDBComponent;
import service.RedSocialService;

public class main {
    public static void main(String[] args) {
        
        // =================================================================
        // DEMOSTRACION 1: POSTGRESQL
        // =================================================================
        System.out.println("=== INICIANDO PRUEBA CON POSTGRESQL ===");
        DBComponent dbPostgres = new PostgresDBComponent();
        dbPostgres.conectar();
        
        RedSocialService servicePostgres = new RedSocialService(dbPostgres);
        servicePostgres.registrarUsuario("juan_perez", "juan@mail.com", "hash123", 1); // Exito
        servicePostgres.registrarUsuario("juan_perez", "juan2@mail.com", "hash456", 1); // Rollback
        
        dbPostgres.desconectar();

        // =================================================================
        // DEMOSTRACION 2: SQL SERVER
        // =================================================================
        System.out.println("\n=== INICIANDO PRUEBA CON SQL SERVER ===");
        DBComponent dbSQLServer = new SQLServerDBComponent();
        dbSQLServer.conectar();
        
        RedSocialService serviceSQLServer = new RedSocialService(dbSQLServer);
        serviceSQLServer.registrarUsuario("pedro_gomez", "pedro@mail.com", "pass789", 1); // Exito
        serviceSQLServer.registrarUsuario("pedro_gomez", "pedro2@mail.com", "pass000", 1); // Rollback
        
        dbSQLServer.desconectar();
    }
}