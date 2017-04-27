package component.w2v_creator.input_creator;

import admin.W2VCreatorAdmin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ercan on 27.04.2017.
 */
public class VectorLoader {

    public void loadVectorsIntoMapFromFile(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(W2VCreatorAdmin.filenameMap.get("target_file")));
            String line;
            br.readLine();
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
                System.out.println(W2VCreatorAdmin.wordVectorMap.get(values[0]).get(0) + " " + values[0]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("dosya açılamadı");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("input hatası");
            e.printStackTrace();
        }

    }
}
