package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessagesController {


    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String loadMessages() {
        return "messages";
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public String submitForm(@RequestParam String message) {
        
        return "messages";
    }

}
