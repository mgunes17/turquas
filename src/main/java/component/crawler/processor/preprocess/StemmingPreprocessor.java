package component.crawler.processor.preprocess;
import nlp_tool.zemberek.ZemberekSentenceAnalyzer;
import org.antlr.v4.runtime.Token;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;
import zemberek.tokenization.TurkishTokenizer;
import java.util.ArrayList;
import java.util.List;

/** * Created by ercan on 09.04.2017. */
public class StemmingPreprocessor extends Preprocessor {
    private TurkishTokenizer tokenizer;
    private TurkishSentenceAnalyzer analyzer;

    public StemmingPreprocessor() {
        this.tokenizer = TurkishTokenizer.DEFAULT;
        this.analyzer = ZemberekSentenceAnalyzer.getSentenceAnalyzer();
    }

    public PreprocessedSentence process(PreprocessedSentence preprocessedSentence) {
        findStemsAndTokens(preprocessedSentence);
        removePunctuation(preprocessedSentence);

        return proceedToNext(preprocessedSentence);
    }

    private void removePunctuation(PreprocessedSentence preprocessedSentence) {
        String sentence = preprocessedSentence.getOriginalSentence();
        while (sentence.charAt(sentence.length() - 1) == '.' || sentence.charAt(sentence.length() - 1) == '!') {
            sentence = sentence.substring(0, sentence.length() - 1);
            preprocessedSentence.setOriginalSentence(sentence);
        }
    }

    private void findStemsAndTokens(PreprocessedSentence preprocessedSentence) {
        List<String> stemList = new ArrayList<String>();
        List<String> tokenList = new ArrayList<String>();

        SentenceAnalysis analysis = analyzer.analyze(preprocessedSentence.getOriginalSentence());
        List<Token> tokens = tokenizer.tokenize(preprocessedSentence.getOriginalSentence());
        analyzer.disambiguate(analysis);

        for (SentenceAnalysis.Entry entry : analysis) {
            Token token = tokens.remove(0);
            tokenList.add(token.getText());

            if (isAcceptable(token.getType())) {
                WordAnalysis wordAnalysis = entry.parses.get(0);
                stemList.add(wordAnalysis.dictionaryItem.lemma);
            }
        }

        preprocessedSentence.setStemList(stemList);
        preprocessedSentence.setTokenList(tokenList);
    }

    private boolean isAcceptable(int type) {
        return type == 1 || type == 4 || type == 5 || type == 6 ||
                type == 7 || type == 8 || type == 9 || type == 13 ||
                type == 14 || type == 15 || type == 16;
    } // 1-4-5-6-7-9-8-13-14-15-16 (18-19) ? }

}