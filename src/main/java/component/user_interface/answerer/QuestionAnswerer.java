package component.user_interface.answerer;

import admin.UserInterfaceAdmin;
import component.user_interface.similarity.SimilarityType;
import db.dao.W2VTokenDAO;
import model.QuestionForCompare;
import model.SimilarityValue;
import model.W2VToken;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by ercan on 17.05.2017.
 */
public abstract class QuestionAnswerer {
    final SimilarityType similarityType = UserInterfaceAdmin.similarityMap.get(UserInterfaceAdmin.similarityType);

    public abstract void answer(String question);
    public abstract void calculateSimilarityList(List<QuestionForCompare> candidateList);
    public abstract List<QuestionForCompare> findCandidates(String question, String w2vType, boolean nounClause);

    void printAnswers(QuestionForCompare userQuestion, int answerCount){
        int answerShown = 0;
        int answersBelowThreshold = 0;

        System.out.println(userQuestion.getQuestion());
        for(int i = 0; i < answerCount; i++){
            double value = userQuestion.getSimilarityList().get(i).getValue();
            if(value > UserInterfaceAdmin.parameterMap.get("threshold") / 100.0) {
                printSimilarityInfo(userQuestion.getSimilarityList().get(i));
                answerShown++;
            } else {
                answersBelowThreshold++;
            }
        }
        printInfo(answerShown, answersBelowThreshold);
    }

    QuestionForCompare createUserQuestionForCompare(String question){
        QuestionForCompare userQuestion = new QuestionForCompare();
        userQuestion.setQuestion(question);
        userQuestion.setQuestionVector(getUserQuestionVector(userQuestion.getQuestion()));

        return userQuestion;
    }

    private double[] getUserQuestionVector(String userQuestion){
        String vecType = UserInterfaceAdmin.vectorType;
        String wordType = UserInterfaceAdmin.wordType;
        W2VTokenDAO w2VTokenDAO = new W2VTokenDAO();
        Map<String, W2VToken> w2VTokenMap = w2VTokenDAO.getW2vTokenForWordsForType(wordType, sentenceToList(userQuestion));
        List<Double> vector = UserInterfaceAdmin.vectorTypeMap.get(vecType).findValue(userQuestion, wordType, w2VTokenMap);

        return vector.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private List<String> sentenceToList(String sentence){ // burada alınmayacak kelimeleri alma (stop word vb.)
        String[] words = sentence.split(" ");
        List<String> wordList = new ArrayList<>();
        for(String word: words){
            if(!word.equals("")){
                wordList.add(word);
            }
        }

        return wordList;
    }

    private void printInfo(int answerShown, int answersBelowThreshold){
        if(answerShown == 0){
            if(answersBelowThreshold == 0){
                System.out.println("hiç bir cevap gösterilemedi.");
            } else {
                System.out.println("bulunan cevaplar thresholdun altında, hiçbir cevap gösterilemedi");
            }
        }
    }

    int findAnswerCount(int candidateCount){
        if(candidateCount - 1 > UserInterfaceAdmin.parameterMap.get("max_answer_count")){
            return UserInterfaceAdmin.parameterMap.get("max_answer_count");
        } else {
            return candidateCount - 1;
        }
    }

    private void printSimilarityInfo(SimilarityValue similarityValue){
        System.out.println("DEĞER: " + similarityValue.getValue() + " CEVAP: " +
                similarityValue.getQuestionForCompare().getAnswer() + " SORU: " +
                similarityValue.getQuestionForCompare().getQuestion());
    }

    class SimilarityComparator implements Comparator<SimilarityValue> {
        public int compare(SimilarityValue s1, SimilarityValue s2){
            return s1.compareTo(s2);
        }
    }
}
