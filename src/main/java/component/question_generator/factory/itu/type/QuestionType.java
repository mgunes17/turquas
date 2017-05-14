package component.question_generator.factory.itu.type;

import model.Question;

import java.util.List;

/**
 * Created by mustafa on 09.04.2017.
 */
public interface QuestionType {
    List<Question> reorganize(String sentence);
}
