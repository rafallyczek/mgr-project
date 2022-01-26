package rafal.lyczek.mgrproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import rafal.lyczek.mgrproject.service.PasswordService;

import java.io.IOException;
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

    @PostMapping("/passwords")
    public String passwords(@RequestParam("passwords_file") MultipartFile file, Model model) throws IOException {
        List<String> passwords = passwordService.loadPasswords(file);
        model.addAttribute("passwords",passwords);
        List<String> md5 = passwordService.encodeWithMD5(passwords);
        model.addAttribute("md5",md5);
        List<String> sha = passwordService.encodeWithSHA(passwords);
        model.addAttribute("sha",sha);
        List<String> bcrypt = passwordService.encodeWithBcrypt(passwords);
        model.addAttribute("bcrypt",bcrypt);
        return "passwords";
    }

}
