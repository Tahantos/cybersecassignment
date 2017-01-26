package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loadLogin(HttpSession ses) {
        if (ses.getAttribute("auth") != null) {
            return "redirect:/messages";
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String password, HttpSession ses) throws SQLException {
        String databaseAddress = "jdbc:h2:file:./database";
        Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");

        ResultSet res = connection.createStatement().executeQuery("SELECT * FROM User");
        while (res.next()) {
            if (res.getString("name").equals(name)) {
                if (res.getString("password").equals(password)) {
                    ses.setAttribute("name", name);
                    ses.setAttribute("id", res.getString("id"));
                    ses.setAttribute("auth", true);
                    connection.close();
                    res.close();
                    return "redirect:messages";
                }
            }
        }
        res.close();
        connection.close();
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String loadLogout(HttpSession ses) {
        ses.invalidate();
        return "login";
    }
}
