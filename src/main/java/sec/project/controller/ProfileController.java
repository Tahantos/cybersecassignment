package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProfileController {


    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public String loadProfile(@PathVariable String id, Model model, HttpSession ses) throws SQLException {
        if (ses.getAttribute("auth") == null) {
            return "redirect:/login";
        }
        
        String databaseAddress = "jdbc:h2:file:./database";
        Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");
        
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM User WHERE id='" + id + "'");
        resultSet.next();
        model.addAttribute("name", resultSet.getString("name"));
        model.addAttribute("secret", resultSet.getString("secret"));
        model.addAttribute("id", ses.getAttribute("id"));
        
        return "profile";
    }



}
