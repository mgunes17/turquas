package nlp_tool.itu;

import component.question_generator.generate.MainGenerator;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by mustafa on 09.04.2017.
 */
public class ItuConfiguration {
    private static String token;
    private static String uri;

    public ItuConfiguration() {
        readProperties();
    }

    private boolean readProperties() {
        try {
            Properties properties = new Properties();
            String fileName = "nlp_itu.properties";
            InputStream inputStream = new MainGenerator().getClass().getClassLoader().getResourceAsStream(fileName);
            properties.load(inputStream);
            token = properties.getProperty("token");
            uri = properties.getProperty("uri");
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
    public void test() {
        String input = "Gülşen Gülşen+Noun+Prop+A3sg+Pnon+Nom\n" +
                "Eryiğit Eryiğit+Noun+Prop+A3sg+Pnon+Nom\n" +
                "Mustafa Mustafa+Noun+Prop+A3sg+Pnon+Nom";

        connect(ToolType.NameEntityRecognition, input);

    }

    public String connect(ToolType tool, String input) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(uri);
        List<NameValuePair> parameters 	= new ArrayList<NameValuePair>(3);

        parameters.add(new BasicNameValuePair("tool", tool.getToolName()));
        parameters.add(new BasicNameValuePair("input", input));
        parameters.add(new BasicNameValuePair("token", token));

        try {
            post.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
            HttpResponse resp = client.execute(post);
            return EntityUtils.toString(resp.getEntity());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return "hata";
        } catch (Exception ex) {
            return "hata";
        }
    }
}
