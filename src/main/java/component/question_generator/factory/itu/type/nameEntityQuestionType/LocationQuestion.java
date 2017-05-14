package component.question_generator.factory.itu.type.nameEntityQuestionType;

import model.Question;
import nlp_tool.itu.ParsedWord;

import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.04.2017.
 */
public class LocationQuestion extends AbstractNameEntityQuestion implements NameEntityQuestionType {
    private final String NEREDE = "nerede";
    //durum eki veya çoğul eklerine de bak

    public LocationQuestion() {
        super("B-LOCATION", "I-LOCATION");
    }

    public List<Question> generateQuestion(List<ParsedWord> parsedWordList, Map<Integer, String> neIndex) {
        return super.generateQuestion(parsedWordList, neIndex, findQuestionWord());
    }

    private String findQuestionWord() {
        return NEREDE;
    }
}
