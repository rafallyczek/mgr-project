package rafal.lyczek.mgrproject.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordService {

    //TODO: zaimplementować logikę generującą hasła
    public List<String> generatePasswords(){
        List<String> passwords = new ArrayList<>();
        passwords.add("sTr0nK_p@sswd!");
        passwords.add("haslo");
        passwords.add("alamakota123");
        return passwords;
    }

}
