package sec.project;

import java.io.FileNotFoundException;
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
            connection.createStatement().execute("DELETE FROM Messages");
            connection.createStatement().execute("DELETE FROM User");
        } catch (SQLException ex) {
            Logger.getLogger(CyberSecurityBaseProjectApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            connection.createStatement().execute("CREATE TABLE User (\n" +
                    "    id varchar(6) PRIMARY KEY,\n" +
                    "    name varchar(200), secret varchar(200), password varchar(200));");
        } catch (SQLException ex) {
            Logger.getLogger(CyberSecurityBaseProjectApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            connection.createStatement().execute("CREATE TABLE Messages (\n" + 
                    " id varchar(6) PRIMARY KEY,\n" +
                    "message varchar(140),\n" +
                    " user_id varchar(6), FOREIGN KEY (user_id) REFERENCES public.user(id));");
        } catch (SQLException ex) {
            Logger.getLogger(CyberSecurityBaseProjectApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            connection.createStatement().execute("INSERT INTO User (id, name, secret, password) VALUES ('012345', 'Jomo', 'I want to be a dancer', 'passw0rd');\n" +
                    " INSERT INTO User (id, name, secret, password) VALUES ('543210', 'Taiou', 'I want to be rich', 'w0rdpass');INSERT INTO User (id, name, secret, password) VALUES ('012321', 'Deppy', 'This is top secret, do not tell anyone Im actually', '123123123');\n" +
                    "INSERT INTO Messages (id, message, user_id) VALUES ('0', 'Test message', '012345');");
        } catch (SQLException ex) {
            Logger.getLogger(CyberSecurityBaseProjectApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
