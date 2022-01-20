package rafal.lyczek.mgrproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rafal.lyczek.mgrproject.service.PasswordService;

import java.util.List;

@Controller
public class MainController {

    private final PasswordService passwordService;

    @Autowired
    public MainController(PasswordService passwordService){
        this.passwordService = passwordService;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/passwords")
    public String passwords(Model model){
        List<String> passwords = passwordService.generatePasswords();
        model.addAttribute("passwords",passwords);
        return "passwords";
    }

}
