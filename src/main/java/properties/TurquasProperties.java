package properties;

import admin.UserInterfaceAdmin;
import admin.W2VCreatorAdmin;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ercan on 21.05.2017.
 */
public class TurquasProperties {

    public boolean loadProperties(){
        try {
            Properties properties = new Properties();
            String fileName = "src/main/resources/turquas.properties";
            InputStream inputStream = new FileInputStream(fileName);
            properties.load(inputStream);
            String pretrained = properties.getProperty("pretrained");
            String python = properties.getProperty("python");
            String script = properties.getProperty("script");

            W2VCreatorAdmin.filenameMap.put("pretrained", pretrained);
            UserInterfaceAdmin.pathMap.put("python", python);
            UserInterfaceAdmin.pathMap.put("script", script);

            return true;
        } catch (IOException ex) {
            System.out.println("properties okunamadı" + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
}
