package component.question_generator.word;

import model.Question;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mustafa on 29.03.2017.
 */
public class Sentence {
    private final String sentence;
    private Set<Question> questionList;

    public Sentence(String sentence) {
        this.sentence = sentence;
        questionList = new HashSet<>();
    }

    //getter-setter

    public String getSentence() {
        return sentence;
    }

    public Set<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(Set<Question> questionList) {
        this.questionList = questionList;
    }
}
