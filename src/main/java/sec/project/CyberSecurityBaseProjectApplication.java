package sec.project;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.h2.tools.RunScript;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;

@SpringBootApplication
public class CyberSecurityBaseProjectApplication implements ServletContextInitializer {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(CyberSecurityBaseProjectApplication.class);
    }
    
    @Override
    public void onStartup(ServletContext sc) {
        String databaseAddress = "jdbc:h2:file:./database";
        
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaseAddress, "sa", "");
        } catch (SQLException ex) {
            Logger.getLogger(CyberSecurityBaseProjectApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // If database has not yet been created, insert content
            RunScript.execute(connection, new FileReader("sql/database-schema.sql"));
            RunScript.execute(connection, new FileReader("sql/database-import.sql"));
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }
    }
}
