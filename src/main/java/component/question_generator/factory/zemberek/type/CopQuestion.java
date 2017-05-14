package component.question_generator.factory.zemberek.type;

import component.question_generator.word.Word;
import model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ercan on 29.03.2017.
 */
public class CopQuestion implements QuestionType {
    private final String NEDIR = "nedir";
    private final String NERESIDIR = "neresidir";
    private final String KIMDIR = "kimdir";
    private final String NESIDIR = "nesidir";
    private final String NEREDEDIR = "nerededir";
    private final String NEREDENDIR = "neredendir";
    private final String NEDENDIR = "nedendir";

    public List<Question> reorganize(List<Word> wordList) {
        StringBuilder sentence = new StringBuilder();
        StringBuilder answer = new StringBuilder();

        for (Word word : wordList) {
            if(!word.isCopQuestion()) {
                sentence.append(word.getWord() + " ");
            } else {
                sentence.append(NEDIR + " ");
                answer.append(word.getWord() + " ");
            }
        }

        List<Question> questions = new ArrayList<>();
        questions.add(new Question(sentence.toString() , answer.toString()));

        return questions;
    }
}
