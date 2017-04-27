package component.question_generator.factory;

import component.question_generator.word.Sentence;

/**
 * Created by mustafa on 09.04.2017.
 */
public abstract class QuestionFactory {
    public abstract Sentence getQuestionList();
}
