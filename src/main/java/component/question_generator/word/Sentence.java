package component.question_generator.word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 29.03.2017.
 */
public class Sentence {
    private final String sentence;
    private List<Question> questionList;

    public Sentence(String sentence) {
        this.sentence = sentence;
        questionList = new ArrayList<Question>();
    }

    //getter-setter

    public String getSentence() {
        return sentence;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
