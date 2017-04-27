package nlp_tool.zemberek;

import zemberek.morphology.ambiguity.Z3MarkovModelDisambiguator;
import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;

import java.io.IOException;

/**
 * Created by mustafa on 28.03.2017.
 */
public class ZemberekSentenceAnalyzer {
    private static TurkishSentenceAnalyzer sentenceAnalyzer;

    public static TurkishSentenceAnalyzer getSentenceAnalyzer() {
        if(sentenceAnalyzer == null)
            createSentenceAnalyzer();

        return sentenceAnalyzer;
    }


    private static void createSentenceAnalyzer() {
        TurkishMorphology morphology = null;
        try {
            morphology = TurkishMorphology.createWithDefaults();
            Z3MarkovModelDisambiguator disambigutor = new Z3MarkovModelDisambiguator();
            sentenceAnalyzer = new TurkishSentenceAnalyzer(
                    morphology,
                    disambigutor
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
