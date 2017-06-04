package component.question_generator.factory.zemberek.type.suffix;

import component.question_generator.factory.zemberek.type.QuestionType;
import component.question_generator.word.Word;
import model.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 28.03.2017.
 */
public class AblQuestion extends SuffixQuestion implements QuestionType {
    private final String NEDEN = "neden";
    private final String KIMDEN = "kimden";
    private final String NEREDEN = "nereden";

    public List<Question> reorganize(List<Word> wordList) {
        Map<Integer, String> changeMap = new HashMap<Integer, String>();

        //soru kelimelerini bul
        int i = 0;
        for(Word word: wordList) {
            if(word.getSuffix() == Suffix.ABLATIVE) {
                changeMap.put(i, chooseQuestionWord(word));
            }
            i++;

        }

        return super.reorganize(wordList, changeMap, "abl");    }

    protected String chooseQuestionWord(Word word) {
        return NEREDEN;
    }
}
