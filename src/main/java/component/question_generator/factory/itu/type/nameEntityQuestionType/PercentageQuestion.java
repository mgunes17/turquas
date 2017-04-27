package component.question_generator.factory.itu.type.nameEntityQuestionType;

import component.question_generator.word.Question;
import nlp_tool.itu.ParsedWord;

import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.04.2017.
 */
public class PercentageQuestion extends AbstractNameEntityQuestion implements NameEntityQuestionType {
    private final String YUZDE_KAC = "yüzde kaç";
    //durum eki veya çoğul eklerine de bak

    public PercentageQuestion() {
        super("B-PERCENTAGE", "I-PERCENTAGE");
    }

    public List<Question> generateQuestion(List<ParsedWord> parsedWordList, Map<Integer, String> neIndex) {
        return super.generateQuestion(parsedWordList, neIndex, findQuestionWord());
    }

    private String findQuestionWord() {
        return YUZDE_KAC;
    }
}
