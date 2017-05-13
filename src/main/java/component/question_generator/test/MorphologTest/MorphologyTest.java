package component.question_generator.test.MorphologTest;

import nlp_tool.zemberek.ZemberekSentenceAnalyzer;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 20.03.2017.
 */
public class MorphologyTest {
    private TurkishSentenceAnalyzer sentenceAnalyzer = ZemberekSentenceAnalyzer.getSentenceAnalyzer();

    public static void main(String[] args) throws IOException {
        MorphologyTest test = new MorphologyTest();

        List<String> sentences = new ArrayList<String>();
        sentences.add("O elmaların 6da 2sinin yarısını yedi .");
        sentences.add("Öğrencilerin 5i suya düştü .");
        sentences.add("Elmaların 5ini suya düşürdü .");
        sentences.add("Elmaların 60ını suya düşürdü .");
        sentences.add("Her yıl kurban bayramında koç keserim.");
        sentences.add("Ali \"bu iş böyle olmaz\" dedi.");
        sentences.add("Türkiye'nin en yüksek dağı hangisidir ?");
        sentences.add("Bir flüt kaç paradır");
        sentences.add("Maçı hangi takım kazandı");
        sentences.add("Oynanan maçı kazanan Beşiktaş'tı");

        for(String sentence: sentences) {
            test.print(sentence);
        }
    }

    private void print(String sentence) {
        SentenceAnalysis analysis = sentenceAnalyzer.analyze(sentence);
        sentenceAnalyzer.disambiguate(analysis);
        System.out.println(sentence);
        for (SentenceAnalysis.Entry entry : analysis) {
            WordAnalysis wa = entry.parses.get(0);
            System.out.print(wa.formatLong()  + " ");
            System.out.println(wa.dictionaryItem.primaryPos.toString() + " "
                    + wa.dictionaryItem.secondaryPos.toString());
        }

        System.out.println("--------------------");
    }
}
