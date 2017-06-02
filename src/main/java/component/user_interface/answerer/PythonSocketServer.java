package component.user_interface.answerer;

import home_base.Turquas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by ercan on 19.05.2017.
 */
public class PythonSocketServer {
    String askForPrediction(double[] questionVector){
        try{
            Socket socket = Turquas.pythonSocket;
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message = prepareMessage(questionVector);
            printWriter.println(message);
            String prediction = bufferedReader.readLine();
            //System.out.println(prediction);

            return prediction;
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return null;
    }

    private String prepareMessage(double[] questionVector){
        StringBuilder message = new StringBuilder();
        for(double value: questionVector){
            message.append(value).append(",");
        }
        message.deleteCharAt(message.length() - 1);

        return message.toString();
    }


}
