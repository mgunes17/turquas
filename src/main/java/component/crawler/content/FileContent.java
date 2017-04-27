package component.crawler.content;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ercan on 23.03.2017.
 */
public class FileContent implements Content {

    //boşlukları silme?
    public String fetchContent(String sourceName) throws  IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceName));
            StringBuilder content = new StringBuilder();
            String line = bufferedReader.readLine();

            int i = 0;
            while (i < 10000 && line != null) {
                content.append(line);
                content.append(" ");
                content.append("###");
                line = bufferedReader.readLine();
                i++;
            }

            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw  new IOException();
        }
    }

}
