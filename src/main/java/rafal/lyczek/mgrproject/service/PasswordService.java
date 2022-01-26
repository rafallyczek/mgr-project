package rafal.lyczek.mgrproject.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordService {

    public List<String> encodePasswords(MultipartFile file) throws IOException {
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

}
