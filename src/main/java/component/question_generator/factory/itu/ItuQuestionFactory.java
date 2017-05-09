package component.question_generator.factory.itu;

import component.question_generator.factory.QuestionFactory;
import component.question_generator.factory.itu.type.NerQuestion;
import component.question_generator.word.Question;
import component.question_generator.word.Sentence;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mustafa on 09.04.2017.
 */
public class ItuQuestionFactory extends QuestionFactory {
    private final String sentence;

    public ItuQuestionFactory(String sentence) {
        this.sentence = sentence;
    }

    public Sentence getQuestionList() {
        Set<Question> generatedList = new HashSet<Question>();

        //ner sorularını üret
        NerQuestion nerQuestion = new NerQuestion();
        generatedList.addAll(nerQuestion.reorganize(sentence));


        Sentence returnedSentence = new Sentence(sentence);
        returnedSentence.setQuestionList(generatedList);
        return returnedSentence;
    }
}
