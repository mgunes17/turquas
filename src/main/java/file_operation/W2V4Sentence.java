package file_operation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 11.05.2017.
 */
public class W2V4Sentence {
    public static void writeToFileSentence2W2V(Map<List<Double>, List<List<Double>>> w2vValues) {
        try {
            PrintWriter pwInput = new PrintWriter("input.txt");
            PrintWriter pwOutput = new PrintWriter("output.txt");

            for(List<Double> sentenceValue: w2vValues.keySet()) { //her bir cümle için
                for(List<Double> questionValues: w2vValues.get(sentenceValue)) { // her bir soru için
                    //soru - input için dosyaya yaz
                    for(Double value: questionValues) {
                        pwInput.print(value + " ");
                    }
                    pwInput.println();

                    //cevap - output için dosyaya yaz
                    for(Double value: sentenceValue) {
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
