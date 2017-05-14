package component.question_generator.factory.zemberek.type;

import component.question_generator.word.Word;
import model.Question;

import java.util.List;

/**
 * Created by mustafa on 26.03.2017.
 */
public interface QuestionType {
    List<Question> reorganize(List<Word> wordList);
}
