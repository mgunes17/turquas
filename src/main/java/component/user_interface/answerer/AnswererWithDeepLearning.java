package component.user_interface.answerer;

import admin.UserInterfaceAdmin;
import admin.W2VCreatorAdmin;
import model.QuestionForCompare;
import model.SimilarityValue;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by ercan on 17.05.2017.
 */
public class AnswererWithDeepLearning extends QuestionAnswerer{
    private QuestionForCompare userQuestion;

    public AnswererWithDeepLearning(){
        super();
    }

    @Override
    public void answer(String question){
        userQuestion = createUserQuestionForCompare(question);
        writeQuestionVectorToFile(userQuestion.getQuestionVector()); // DL için input

        double[] answerVector = predictWithDeepLearning(); // DL tahminini al
        userQuestion.setAnswerVector(answerVector); // cevaplarla karşılaştırmak için cevap vektörü olarak ata

        long start_time = System.nanoTime();
        List<QuestionForCompare> candidateList = createCandidateList(question);
        candidateList.add(0, userQuestion);
        long end_time = System.nanoTime();
        double difference = (end_time - start_time)/1e6;
        System.out.println("candidate çekilmesi:" + difference);

        start_time = System.nanoTime();
        calculateSimilarityList(candidateList);
        end_time = System.nanoTime();
        difference = (end_time - start_time)/1e6;
        System.out.println("benzerlik hesabı:" + difference);

        int candidateCount = candidateList.size();
        int answerCount = findAnswerCount(candidateCount);
        System.out.println("\n\n...DEEP LEARNING ILE CEVAP VERILIYOR...\n\n");
        printAnswers(userQuestion, answerCount);
    }

    @Override
    public void calculateSimilarityList(List<QuestionForCompare> candidateList){
        int candidateCount = candidateList.size();
        for(int i = 1; i < candidateCount; i++){ // 0, kullanıcı sorusunun kendisi
            double value = super.similarityType.findSimilarity(userQuestion.getAnswerVector(),
                    candidateList.get(i).getAnswerVector());
            SimilarityValue similarityValue = new SimilarityValue();
            similarityValue.setQuestionForCompare(candidateList.get(i));
            similarityValue.setValue(value);
            userQuestion.getSimilarityList().add(similarityValue);
        }
        userQuestion.getSimilarityList().sort(new SimilarityComparator());
    }

    private void writeQuestionVectorToFile(double[] vector){
        try {
            PrintWriter printWriter = new PrintWriter(UserInterfaceAdmin.pathMap.get("question"));
            for(double value: vector) {
                printWriter.print(value + " ");
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private double[] predictWithDeepLearning(){
        try {
            String command = UserInterfaceAdmin.pathMap.get("python") + " " + UserInterfaceAdmin.pathMap.get("script");
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            p.destroy();

            return predict();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String readRawPredictedVector(){
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(UserInterfaceAdmin.pathMap.get("prediction"));

            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private double[] predict(){
        String prediction = readRawPredictedVector();
        double[] vector = new double[W2VCreatorAdmin.w2vParameterMap.get("layer_size")];

        if(prediction != null){
            int length = prediction.length();
            prediction = prediction.substring(2, length-2);
            String[] values = prediction.split(",");

            for(int i = 0; i < W2VCreatorAdmin.w2vParameterMap.get("layer_size"); i++){
                vector[i] = Double.parseDouble(values[i]);
            }
        } else {
            System.out.println("Cevap vektörü dosyadan okunamadı.");
        }

        return vector;
    }


}
