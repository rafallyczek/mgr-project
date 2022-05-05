package rafal.lyczek.mgrproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import rafal.lyczek.mgrproject.service.FileService;
import rafal.lyczek.mgrproject.service.PasswordService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private final PasswordService passwordService;
    private final FileService fileService;

    @Autowired
    public MainController(PasswordService passwordService,
                          FileService fileService){
        this.passwordService = passwordService;
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @PostMapping("/passwords")
    public String passwords(@RequestParam("passwords_file") MultipartFile file, Model model) throws IOException, NoSuchAlgorithmException {
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

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(@RequestParam("md5") List<String> md5,
                                                        @RequestParam("sha") List<String> sha,
                                                        @RequestParam("bcrypt") List<String> bcrypt) throws IOException {
        List<List<String>> lists = new ArrayList<>();
        lists.add(md5);
        lists.add(sha);
        lists.add(bcrypt);
        ByteArrayInputStream in = fileService.buildZip(lists);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment; filename=hashes.zip");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/zip")).body(new InputStreamResource(in));
    }

}
