package component.w2v_creator.w2v_loader;

import admin.W2VCreatorAdmin;
import db.dao.W2VTokenDAO;
import model.W2VToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ercan on 17.05.2017.
 */
public class W2VLoader {

    public void loadVectorsFromFile(){
        try {
            W2VTokenDAO w2VTokenDAO = new W2VTokenDAO();
            BufferedReader br = new BufferedReader(new FileReader(W2VCreatorAdmin.filenameMap.get("pretrained_file")));
            String line;
            int lineNumber = 0;
            List<W2VToken> w2VTokenList = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                List<Double> vectorValues = new ArrayList<Double>();
                String [] values = line.split(" ");
                for(int i = 1; i < values.length; i++){
                    double value = -99;
                    try{
                        value = Double.parseDouble(values[i]);
                    } catch(NumberFormatException e){
                        System.out.println("input parse edilemedi");
                        e.printStackTrace();
                    }
                    if(value != -99){
                        vectorValues.add(value);
                    }
                }
                W2VToken w2VToken = createW2VToken(values[0], vectorValues);
                w2VTokenList.add(w2VToken);

                if(++lineNumber % 10000 == 0){
                    if(w2VTokenDAO.insertToTable(w2VTokenList)){
                        System.out.println("başarıyla kaydedildi :) #" + lineNumber);
                    } else {
                        System.out.println("kaydedilemedi !!!!");
                    }
                    w2VTokenList = new ArrayList<>();
                }
            }

            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("dosya açılamadı");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("input hatası");
            e.printStackTrace();
        }
    }

    private W2VToken createW2VToken(String token, List<Double> vector){
        W2VToken w2VToken = new W2VToken();
        w2VToken.setType("token");
        w2VToken.setTokenName(token);
        w2VToken.setValue(vector);

        return w2VToken;
    }


}
