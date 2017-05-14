package component.question_generator.factory;

import model.Question;

import java.util.List;

/**
 * Created by mustafa on 09.04.2017.
 */
public abstract class QuestionFactory {
    public abstract List<Question> getQuestionList();
}
