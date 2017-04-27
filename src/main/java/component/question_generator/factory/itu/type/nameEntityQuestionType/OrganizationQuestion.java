package component.question_generator.factory.itu.type.nameEntityQuestionType;

import component.question_generator.word.Question;
import nlp_tool.itu.ParsedWord;

import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.04.2017.
 */
public class OrganizationQuestion extends AbstractNameEntityQuestion implements NameEntityQuestionType {
    private final String HANGI_KURUM = "hangi kurum";
    //durum eki veya çoğul eklerine de bak

    public OrganizationQuestion() {
        super("B-ORGANIZATION", "I-ORGANIZATION");
    }

    public List<Question> generateQuestion(List<ParsedWord> parsedWordList, Map<Integer, String> neIndex) {
        return super.generateQuestion(parsedWordList, neIndex, findQuestionWord());
    }

    private String findQuestionWord() {
        return HANGI_KURUM;
    }
}
