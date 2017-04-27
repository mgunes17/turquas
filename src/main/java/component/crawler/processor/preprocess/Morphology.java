package component.crawler.processor.preprocess;

import zemberek.morphology.analysis.tr.TurkishMorphology;

import java.io.IOException;

/**
 * Created by ercan on 09.04.2017.
 */
public class Morphology {
    private static TurkishMorphology wordAnalyzer;

    public static TurkishMorphology getMorphology() {
        if(wordAnalyzer == null)
            createTurkishMorphology();

        return wordAnalyzer;
    }

    private static void createTurkishMorphology() {
        try {
            wordAnalyzer = TurkishMorphology.createWithDefaults();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
