package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessagesController {

    public static int msgId = 1;

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String loadMessages(HttpSession ses, Model model) throws SQLException {
        if (ses.getAttribute("auth") == null) {
            return "login";
        }

        List<String> msgList = new ArrayList<>();

        String databaseAddress = "jdbc:h2:file:./database";
        Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Messages");

        while (resultSet.next()) {
            String msg = resultSet.getString("message");
            msgList.add(msg);
        }

        model.addAttribute("messages", msgList);
        model.addAttribute("id", ses.getAttribute("id"));
        
        resultSet.close();
        connection.close();
        return "messages";
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public String submitForm(@RequestParam String message, HttpSession ses) throws SQLException {
        String userId = (String) ses.getAttribute("id");
        String databaseAddress = "jdbc:h2:file:./database";
        Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");
        connection.createStatement().executeUpdate("INSERT INTO Messages (id, message, user_id) VALUES ('" + msgId + "', '" + message + "', '" + userId + "')");
        connection.close();
        msgId++;
        return "redirect:/messages";
    }

}
