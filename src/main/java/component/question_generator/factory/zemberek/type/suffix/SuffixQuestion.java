package component.question_generator.factory.zemberek.type.suffix;

import component.question_generator.word.Word;
import model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 29.03.2017.
 */
public abstract class SuffixQuestion {
    protected abstract String chooseQuestionWord(Word word);

    public List<Question> reorganize(List<Word> wordList, Map<Integer, String> changeMap, Suffix suffix) {
        StringBuilder sentence;
        StringBuilder answer;
        List<Question> questions = new ArrayList<Question>();

        for(Integer index: changeMap.keySet()) {
            sentence = new StringBuilder();
            answer = new StringBuilder();

            int i = 0;

            while(i != index) {
                //i bulunana kadar kelimeleri yerleştir
                sentence.append(wordList.get(i).getWord() + " ");
                i++;
            }

            //i bulunduktan sonra soru kelimesini yerleştir
            sentence.append(changeMap.get(i) + " ");
            answer.append(wordList.get(i).getWord());
            i++;

            //kalan kelimeleri yerleştir
            while(i < wordList.size()) {
                sentence.append(wordList.get(i).getWord() + " ");
                i++;
            }

            questions.add(new Question(sentence.toString(), answer.toString()));
        }

        return questions;
    }
}
