package component.question_generator.factory.itu.type.nameEntityQuestionType;

import component.question_generator.word.Question;
import nlp_tool.itu.ParsedWord;

import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.04.2017.
 */
public class TimeQuestion extends AbstractNameEntityQuestion implements NameEntityQuestionType {
    private final String SAAT_KACTA = "saat kaçta";
    //durum eki veya çoğul eklerine de bak

    public TimeQuestion() {
        super("B-TIME", "I-TIME");
    }

    public List<Question> generateQuestion(List<ParsedWord> parsedWordList, Map<Integer, String> neIndex) {
        return super.generateQuestion(parsedWordList, neIndex, findQuestionWord());
    }

    private String findQuestionWord() {
        return SAAT_KACTA;
    }
}
