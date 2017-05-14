package component.question_generator.factory.itu.type.nameEntityQuestionType;

import model.Question;
import nlp_tool.itu.ParsedWord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.04.2017.
 */
public class AbstractNameEntityQuestion {
    private final String NE_LABEL_1;
    private final String NE_LABEL_2;

    public AbstractNameEntityQuestion(String label_1, String label_2) {
        NE_LABEL_1 = label_1;
        NE_LABEL_2 = label_2;
    }

    public List<Question> generateQuestion(List<ParsedWord> parsedWordList, Map<Integer, String> neIndex, String questionWord) {
        List<Question> questions = new ArrayList<Question>();
        StringBuilder question = new StringBuilder();
        StringBuilder answer = new StringBuilder();

        for(Integer index: neIndex.keySet()) {
            int i = 0;

            if(parsedWordList.get(index).getNer().equals(NE_LABEL_1)) {
                //ne ye kadar kelimeleri ekle
                while(i != index) {
                    question.append(parsedWordList.get(i).getWord() + " ");
                    i++;
                }

                answer.append(parsedWordList.get(i).getWord() + " ");
                question.append(questionWord + " ");
                i++;

                //I ile başladığı sürece cevaba ekle
                while(parsedWordList.get(i).getNer().contains(NE_LABEL_2)) {
                    answer.append(parsedWordList.get(i).getWord() + " ");
                    i++;
                }

                //kalan kısmı soruya ekle
                while(i < parsedWordList.size()) {
                    question.append(parsedWordList.get(i).getWord() + " ");
                    i++;
                }

                Question question1 = new Question(question.toString(), answer.toString());
                questions.add(question1);
            }
        }

        return questions;
    }
}
