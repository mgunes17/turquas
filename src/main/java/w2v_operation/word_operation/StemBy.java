package w2v_operation.word_operation;

import model.Sentence;
import nlp_tool.zemberek.ZemberekSentenceAnalyzer;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 10.05.2017.
 */
public class StemBy extends WordType {
    private TurkishSentenceAnalyzer analyzer = ZemberekSentenceAnalyzer.getSentenceAnalyzer();

    public StemBy() {
        super();
    }

    public void prepareWord(Map<String, List<String>> convertedSentences) {
        for(Sentence sentence: getSentences()) {
            String sentenceWord = rebuildSentenceByStem(sentence.getOriginalSentence());
            List<String> questions = new ArrayList<String>();

            for (String question : sentence.getQuestions()) {
                questions.add(rebuildSentenceByStem(question));
            }

            convertedSentences.put(sentenceWord, questions);
        }
    }

    public String rebuildSentenceByStem(String sentence) {
        SentenceAnalysis analysis = analyzer.analyze(sentence);
        analyzer.disambiguate(analysis);

        StringBuilder newSentence = new StringBuilder();

        for (SentenceAnalysis.Entry entry : analysis) {
            WordAnalysis wordAnalysis = entry.parses.get(0);
            newSentence.append(wordAnalysis.dictionaryItem.lemma + " ");
        }

        return newSentence.toString();
    }
}
