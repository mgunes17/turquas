package component.question_generator.factory.zemberek.type;

import component.question_generator.word.Word;
import model.Question;
import nlp_tool.zemberek.ZemberekSentenceAnalyzer;
import zemberek.morphology.analysis.SentenceAnalysis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 31.03.2017.
 */
public class GenQuestion implements QuestionType {
    private final String NERENIN = "nerenin";
    private final String KIMIN = "kimin";
    private final String NEYIN = "neyin";

    public List<Question> reorganize(List<Word> wordList) {
        StringBuilder builder = new StringBuilder();
        StringBuilder answer = new StringBuilder();

        SentenceAnalysis analysis = ZemberekSentenceAnalyzer.getSentenceAnalyzer().analyze("");
        ZemberekSentenceAnalyzer.getSentenceAnalyzer().disambiguate(analysis);

        for (Word word : wordList) {

            if(!word.isGen()) {
                builder.append(word.getWord() + " ");
            } else {
                builder.append(" " + NEYIN + " ");
                answer.append(word.getWord() + " ");
            }
        }

        List<Question> questions = new ArrayList<Question>();
        questions.add(new Question(builder.toString(), answer.toString(), "gen"));
        return questions;
    }
}
