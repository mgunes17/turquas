package component.user_interface.answerer;

import model.QuestionForCompare;
import model.SimilarityValue;

import java.util.List;

/**
 * Created by ercan on 17.05.2017.
 */
public class AnswererWithVectorSimilarity extends QuestionAnswerer{
    private QuestionForCompare userQuestion;

    public AnswererWithVectorSimilarity(){
        super();
    }

    @Override
    public void answer(String question){
        userQuestion = createUserQuestionForCompare(question);

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
        System.out.println("\n\n...BASİT VEKTÖR BENZERLİĞİ ILE CEVAP VERILIYOR...\n\n");
        printAnswers(userQuestion, answerCount);
    }

    @Override
    public void calculateSimilarityList(List<QuestionForCompare> candidateList){
        int candidateCount = candidateList.size();
        for(int i = 1; i < candidateCount; i++){ // 0, kullanıcı sorusunun kendisi
            double value = super.similarityType.findSimilarity(userQuestion.getQuestionVector(),
                                                               candidateList.get(i).getQuestionVector());
            SimilarityValue similarityValue = new SimilarityValue();
            similarityValue.setQuestionForCompare(candidateList.get(i));
            similarityValue.setValue(value);
            userQuestion.getSimilarityList().add(similarityValue);
        }
        userQuestion.getSimilarityList().sort(new SimilarityComparator());
    }
}
