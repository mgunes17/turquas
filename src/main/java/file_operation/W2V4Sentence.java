package file_operation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 11.05.2017.
 */
public class W2V4Sentence {
    public static void writeToFileSentence2W2V(Map<List<Double>, List<Double>> w2vValues) {
        try {
            File inputFile = new File("input.txt");
            File outputFile = new File("output.txt");
            PrintWriter pwInput;
            PrintWriter pwOutput;

            if(inputFile.exists() && outputFile.exists()){
                pwInput = new PrintWriter(new FileOutputStream(new File("input.txt"), true));
                pwOutput = new PrintWriter(new FileOutputStream(new File("output.txt"), true));
            } else {
                pwInput = new PrintWriter("input.txt");
                pwOutput = new PrintWriter("output.txt");
            }

            for(List<Double> questionValue: w2vValues.keySet()) { //her bir soru için
                if(questionValue.get(0) >= -1.0 && questionValue.get(0) <= 1.0
                        && w2vValues.get(questionValue).get(0) >= -1 && w2vValues.get(questionValue).get(0) <= 1.0){
                    //soru - input için dosyaya yaz
                    for(Double value: questionValue) {
                        pwInput.print(value + " ");
                    }
                    pwInput.println();

                    //cevap - output için dosyaya yaz
                    for(Double value: w2vValues.get(questionValue)) {
                        pwOutput.print(value + " ");
                    }
                    pwOutput.println();
                }
            }

            pwInput.close();
            pwOutput.close();
            System.out.println("Dosyalar oluşturuldu.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
