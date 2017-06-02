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
            /*Properties properties = new Properties();
            String fileName = "src/main/resources/turquas.properties";
            InputStream inputStream = TurquasProperties.class
                    .getResourceAsStream(fileName);
            properties.load(inputStream);
            String pretrained = properties.getProperty("pretrained");
            String python = properties.getProperty("python");
            String script = properties.getProperty("script");*/

            W2VCreatorAdmin.filenameMap.put("pretrained", "/home/student_1/Documents/proje/wiki.tr.vec");
            UserInterfaceAdmin.pathMap.put("python", "/root/miniconda2/bin/python");
            UserInterfaceAdmin.pathMap.put("script", "/home/student_1/predict.py");

            return true;
        } /*catch (IOException ex) {
            System.out.println("properties okunamadı" + ex.getMessage());
            ex.printStackTrace();
            return false;
        }*/ catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
}
