package component.question_generator.factory.itu.type.nameEntityQuestionType;

import model.Question;
import nlp_tool.itu.ParsedWord;

import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.04.2017.
 */
public interface NameEntityQuestionType {
    List<Question> generateQuestion(List<ParsedWord> parsedWordList, Map<Integer, String> neIndex);
}
