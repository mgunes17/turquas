package component.user_interface.answerer;

import admin.UserInterfaceAdmin;
import admin.W2VCreatorAdmin;
import component.user_interface.candidate.FindingCandidate;
import home_base.SentenceType;
import model.QuestionForCompare;
import model.SimilarityValue;

import java.util.List;

/**
 * Created by ercan on 17.05.2017.
 */
public class AnswererWithDeepLearning extends QuestionAnswerer{
    public double totalTime = 0.0;
    private QuestionForCompare userQuestion;
    private PythonSocketServer server = new PythonSocketServer();

    public AnswererWithDeepLearning(){
        super();
    }

    @Override
    public void answer(String question){
        long start_time = System.nanoTime(); // soru cevaplama başlangıç

        userQuestion = createUserQuestionForCompare(question);
        double[] answerVector = predictWithDeepLearning(); // DL tahminini al
        userQuestion.setAnswerVector(answerVector); // cevaplarla karşılaştırmak için cevap vektörü olarak ata
        //long start_time = System.nanoTime();
        String w2vType = UserInterfaceAdmin.wordType + "_" + UserInterfaceAdmin.vectorType;

        start_time = System.nanoTime();
        List<QuestionForCompare> candidateList = findCandidates(question, w2vType, new SentenceType().isNounClause(question));
        candidateList.add(0, userQuestion);
        long end_time = System.nanoTime();
        double difference = (end_time - start_time)/1e6;
        System.out.println("candidate çekilmesi:" + difference);


        start_time = System.nanoTime();
        calculateSimilarityList(candidateList);
        end_time = System.nanoTime();
        difference = (end_time - start_time)/1e6;
        System.out.println("benzerliklerin hesaplanması:" + difference);

        //long end_time = System.nanoTime(); // soru cevaplama bitiş
        //double difference = (end_time - start_time)/1e6;
        totalTime = totalTime + difference;

        //long end_time = System.nanoTime();
        //double difference = (end_time - start_time)/1e6;
        //System.out.println("candidate çekilmesi:" + difference);
        //start_time = System.nanoTime();
        //System.out.println("cevap süresi:" + difference);

        int candidateCount = candidateList.size();
        int answerCount = findAnswerCount(candidateCount);
        System.out.println("\n\n...DEEP LEARNING ILE CEVAP VERILIYOR...\n\n");
        //printAnswers(userQuestion, answerCount);
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

    @Override
    public List<QuestionForCompare> findCandidates(String question, String w2vType, boolean nounClause) {

        return new FindingCandidate(w2vType).findCandidatesForDeepLearning(question, nounClause);
    }

    private double[] predictWithDeepLearning(){
        try {
            String prediction = server.askForPrediction(userQuestion.getQuestionVector());

            return predict(prediction);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return null;
    }

    private double[] predict(String prediction){
        double[] vector = new double[W2VCreatorAdmin.w2vParameterMap.get("layer_size")];

        if(prediction != null){
            int length = prediction.length();
            prediction = prediction.substring(2, length-2);
            String[] values = prediction.split(",");

            for(int i = 0; i < W2VCreatorAdmin.w2vParameterMap.get("layer_size"); i++){
                vector[i] = Double.parseDouble(values[i]);
            }
        } else {
            System.out.println("Cevap vektörü yok.");
        }

        return vector;
    }

    public QuestionForCompare getUserQuestion() {
        return userQuestion;
    }
}
