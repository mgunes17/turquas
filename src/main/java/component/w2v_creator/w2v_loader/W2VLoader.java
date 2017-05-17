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
            BufferedReader br = new BufferedReader(new FileReader(W2VCreatorAdmin.filenameMap.get("pretrained_file")));
            String line;

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
                W2VCreatorAdmin.wordVectorMap.put(values[0], vectorValues);
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

    public void saveToDatabase(){
        List<W2VToken> w2VTokenList = createW2VTokenList();
        W2VTokenDAO w2VTokenDAO = new W2VTokenDAO();
        w2VTokenDAO.updateTable(w2VTokenList);
    }

    private List<W2VToken> createW2VTokenList(){
        Map<String, List<Double>> map = W2VCreatorAdmin.wordVectorMap;
        List<W2VToken> w2VTokenList = new ArrayList<>();

        for(String token: map.keySet()){
            W2VToken w2VToken = new W2VToken();
            w2VToken.setType("token");
            w2VToken.setTokenName(token);
            w2VToken.setValue(map.get(token));
            w2VTokenList.add(w2VToken);
        }

        return w2VTokenList;
    }


}
