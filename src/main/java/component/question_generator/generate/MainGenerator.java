package component.question_generator.generate;

import component.question_generator.factory.QuestionFactory;
import component.question_generator.factory.itu.ItuQuestionFactory;
import component.question_generator.factory.zemberek.ZemberekQuestionFactory;
import home_base.SentenceType;
import model.Question;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.WordAnalysis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 22.03.2017.
 */
public class MainGenerator {
    private List<Question> questionList = new ArrayList<>();
    private SentenceType sentenceType = new SentenceType();

    public List<Question> convertQuestions(String sentence) {
        /*QuestionFactory factory = new ItuQuestionFactory(sentence);
        questionList.addAll(factory.getQuestionList());*/

        QuestionFactory factory = new ZemberekQuestionFactory(sentence);
        questionList.addAll(factory.getQuestionList());

        boolean nounClause = sentenceType.isNounClause(sentence);

        for(Question question: questionList)
            question.setNounClause(nounClause);

        return questionList;
    }
}
