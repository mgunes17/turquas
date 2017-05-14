package w2v_operation.word_operation;

import nlp_tool.zemberek.ZemberekSentenceAnalyzer;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;

/**
 * Created by mustafa on 10.05.2017.
 */
public class StemBy extends WordType {
    private TurkishSentenceAnalyzer analyzer = ZemberekSentenceAnalyzer.getSentenceAnalyzer();

    public StemBy() {
        super();
    }

    public String rebuildSentence(String sentence) {
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
