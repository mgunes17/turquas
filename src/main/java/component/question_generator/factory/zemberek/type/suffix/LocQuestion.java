package component.question_generator.factory.zemberek.type.suffix;

import component.question_generator.factory.zemberek.type.QuestionType;
import component.question_generator.word.Question;
import component.question_generator.word.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 29.03.2017.
 */
public class LocQuestion extends SuffixQuestion implements QuestionType {
    private final String NEREDE = "nerede";
    private final String KIMDE = "kimde";
    private final String KACTA = "kaçta";

    public List<Question> reorganize(List<Word> wordList) {
        Map<Integer, String> changeMap = new HashMap<Integer, String>();

        //soru kelimelerini bul
        int i = 0;
        for(Word word: wordList) {
            if(word.getSuffix() == Suffix.LOCATIVE) {
                changeMap.put(i, chooseQuestionWord(word));
            }
            i++;
        }

        return super.reorganize(wordList, changeMap, Suffix.LOCATIVE);
    }

    public String chooseQuestionWord(Word word) {
        if(word.getPrimaryPos().equals("Numeral"))
            return KACTA;
        else
            return NEREDE;
    }
}
