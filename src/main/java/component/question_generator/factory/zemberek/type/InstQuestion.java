package component.question_generator.factory.zemberek.type;

import component.question_generator.factory.zemberek.type.suffix.Suffix;
import component.question_generator.word.Word;
import model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ercan on 29.03.2017.
 */
public class InstQuestion implements QuestionType {
    private final String NASIL = "nasıl";
    private final String NE = "ne";
    private final String KIM = "kim";
    private final String NEYI = "neyi";
    private final String KIMI = "kimi";


    public List<Question> reorganize(List<Word> wordList) {

        StringBuilder sentence = new StringBuilder();
        StringBuilder answer = new StringBuilder();

        int size = wordList.size();
        for (int i = 0; i < size - 2; i++) {
            Word word = wordList.get(i);

            if(!word.isInstQuestion()) {
                sentence.append(word.getWord() + " ");
            } else if(wordList.get(i+1).getPrimaryPos().equals("Verb")){
                sentence.append(NASIL + " ");
                answer.append(word.getWord() + " ");
            } else if(wordList.get(i+1).getSecondaryPos().equals("ProperNoun")){
                if(wordList.get(i+1).getSuffix() == Suffix.ACCUSATIVE){
                    sentence.append(word.getWord() + " " + KIM +"i ");
                } else {
                    sentence.append(word.getWord() + " " + KIM +" ");
                }
                answer.append(wordList.get(i+1).getWord() + " ");
                i++;
            } else if(wordList.get(i+1).getPrimaryPos().equals("Noun") &&
                    wordList.get(i+1).getSecondaryPos().equals("None")){
                if(wordList.get(i+1).getSuffix() == Suffix.ACCUSATIVE){
                    sentence.append(word.getWord() + " " + NE +"yi ");
                } else {
                    sentence.append(word.getWord() + " " + NE +" ");
                }
                answer.append(wordList.get(i+1).getWord() + " ");
                i++;
            }
        }

        List<Question> questions = new ArrayList<Question>();
        questions.add(new Question(sentence.toString() + " ?", answer.toString()));

        return questions;
    }
}
