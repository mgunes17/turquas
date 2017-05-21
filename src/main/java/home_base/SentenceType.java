package home_base;

import nlp_tool.zemberek.ZemberekSentenceAnalyzer;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;

/**
 * Created by mustafa on 21.05.2017.
 */
public class SentenceType {
    public boolean isNounClause(String sentence) {
        TurkishSentenceAnalyzer sentenceAnalyzer = ZemberekSentenceAnalyzer.getSentenceAnalyzer();
        SentenceAnalysis analysis = sentenceAnalyzer.analyze(sentence);
        sentenceAnalyzer.disambiguate(analysis);
        System.out.println(sentence);

        SentenceAnalysis.Entry entry = analysis.getEntry(analysis.size() - 1);
        String type = entry.parses.get(0).dictionaryItem.primaryPos.name();

        if(type.equals("Punctuation"))
            type = analysis.getEntry(analysis.size() - 2).parses.get(0).dictionaryItem.primaryPos.name();

        return !type.equals("Verb");
    }
}
