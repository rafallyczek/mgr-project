package rafal.lyczek.mgrproject.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordService {

    //Wczytaj hasła z pliku
    public List<String> loadPasswords(MultipartFile file) throws IOException {
        List<String> passwords = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = bufferedReader.readLine())!=null){
            passwords.add(line);
        }
        bufferedReader.close();
        return passwords;
    }

    //Hashowanie MD5
    public List<String> encodeWithMD5(List<String> passwords) throws NoSuchAlgorithmException {
        List<String> md5 = new ArrayList<>();
        MessageDigest md5MessageDigest = MessageDigest.getInstance("MD5");
        for(String password : passwords){
            md5MessageDigest.update(password.getBytes());
            byte[] digest = md5MessageDigest.digest();
            md5.add(DatatypeConverter.printHexBinary(digest).toLowerCase());
        }
        return md5;
    }

    //Hashowanie SHA
    public List<String> encodeWithSHA(List<String> passwords) throws NoSuchAlgorithmException {
        List<String> sha = new ArrayList<>();
        MessageDigest shaMessageDigest = MessageDigest.getInstance("SHA-256");
        for(String password : passwords){
            shaMessageDigest.update(password.getBytes());
            byte[] digest = shaMessageDigest.digest();
            sha.add(DatatypeConverter.printHexBinary(digest).toLowerCase());
        }
        return sha;
    }

    //Hashowanie bcrypt
    public List<String> encodeWithBcrypt(List<String> passwords){
        List<String> bcrypt = new ArrayList<>();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        for(String password : passwords){
            bcrypt.add(encoder.encode(password));
        }
        return bcrypt;
    }

}
