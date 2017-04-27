package component.crawler.processor.preprocess;

/**
 * Created by ercan on 09.04.2017.
 */
public class PreprocessHandler {
    private Preprocessor headPreprocessor;

    public PreprocessHandler() {
        StemmingPreprocessor stemmingPreprocessor = new StemmingPreprocessor();
        UniqueWordPreprocessor uniqueWordPreprocessor = new UniqueWordPreprocessor();

        stemmingPreprocessor.setNextPreprocessor(uniqueWordPreprocessor);
        headPreprocessor = stemmingPreprocessor;
    }

    public PreprocessedSentence process(String sentence) {
        return headPreprocessor.process(new PreprocessedSentence(sentence));
    }
}
