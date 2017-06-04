package component.question_generator.factory.zemberek.type.suffix;

import component.question_generator.factory.zemberek.type.QuestionType;
import component.question_generator.word.Word;
import model.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 29.03.2017.
 */
public class DatQuestion extends SuffixQuestion implements QuestionType {
    private final String NEYE = "neye";
    private final String KIME = "kime";
    private final String NEREYE = "nereye";

    public List<Question> reorganize(List<Word> wordList) {
        Map<Integer, String> changeMap = new HashMap<Integer, String>();

        //soru kelimelerini bul
        int i = 0;
        for(Word word: wordList) {
            if(word.getSuffix() == Suffix.DATIVE) {
                changeMap.put(i, chooseQuestionWord(word));
            }
            i++;
        }

        return super.reorganize(wordList, changeMap, "dat");
    }

    protected String chooseQuestionWord(Word word) {
        return NEREYE;
    }
}
