package component.question_generator.factory.itu;

import component.question_generator.factory.QuestionFactory;
import component.question_generator.factory.itu.type.NerQuestion;
import component.question_generator.word.Sentence;
import model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 09.04.2017.
 */
public class ItuQuestionFactory extends QuestionFactory {
    private final String sentence;

    public ItuQuestionFactory(String sentence) {
        this.sentence = sentence;
    }

    public List<Question> getQuestionList() {
        List<Question> generatedList = new ArrayList<Question>();

        //ner sorularını üret
        NerQuestion nerQuestion = new NerQuestion();
        generatedList.addAll(nerQuestion.reorganize(sentence));

        Sentence returnedSentence = new Sentence(sentence);
        System.out.println(generatedList.size() + " soru üretildi. - ITU");
        return generatedList;
    }
}
