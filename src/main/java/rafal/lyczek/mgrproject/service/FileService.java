package rafal.lyczek.mgrproject.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileService {

    public ByteArrayInputStream buildZip(List<List<String>> lists) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOut = new BufferedOutputStream(out);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOut);

        List<File> files = new ArrayList<>();
        files.add(new File("md5.txt"));
        files.add(new File("sha.txt"));
        files.add(new File("bcrypt.txt"));

        for(int i=0;i<lists.size();i++){

            File file = files.get(i);
            List<String> hashes = lists.get(i);
            hashes.set(0,hashes.get(0).substring(1));
            hashes.set(hashes.size()-1,hashes.get(hashes.size()-1).substring(0,hashes.get(hashes.size()-1).length()-1));

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            for(String hash : hashes){
                fileOutputStream.write(hash.getBytes());
                fileOutputStream.write("\n".getBytes());
            }

            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
            fileOutputStream.close();

        }

        zipOutputStream.close();
        bufferedOut.close();

        return new ByteArrayInputStream(out.toByteArray());

    }

}
