package component.question_generator.factory.zemberek.type;

import component.question_generator.word.Word;
import model.Question;
import nlp_tool.zemberek.ZemberekSentenceAnalyzer;
import zemberek.morphology.analysis.SentenceAnalysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mustafa on 26.03.2017.
 */
public class TimeQuestion implements QuestionType {
    private final String QUESTION_WORD = "ne zaman";
    private final Set<String> keywords = new HashSet<String>();

    public List<Question> reorganize(List<Word> wordList) {
        keywords.add("Time");
        keywords.add("Clock");
        keywords.add("Date");

        StringBuilder builder = new StringBuilder();
        StringBuilder answer = new StringBuilder();

        SentenceAnalysis analysis = ZemberekSentenceAnalyzer.getSentenceAnalyzer().analyze("");
        ZemberekSentenceAnalyzer.getSentenceAnalyzer().disambiguate(analysis);

        for (Word word : wordList) {

            if(!keywords.contains(word.getSecondaryPos())) {
                builder.append(word.getWord() + " ");
            } else {
                builder.append(QUESTION_WORD + " ");
                answer.append(word.getWord() + " ");
            }
        }

        List<Question> questions = new ArrayList<Question>();
        questions.add(new Question(builder.toString(), answer.toString()));
        return questions;
    }
}
