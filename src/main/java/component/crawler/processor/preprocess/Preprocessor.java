package component.crawler.processor.preprocess;

/**
 * Created by ercan on 09.04.2017.
 */
public abstract class Preprocessor {
    private Preprocessor nextPreprocessor;
    abstract public PreprocessedSentence process(PreprocessedSentence preprocessedSentence);

    public PreprocessedSentence proceedToNext(PreprocessedSentence preprocessedSentence){
        if(getNextPreprocessor() != null){
            return getNextPreprocessor().process(preprocessedSentence);
        } else {
            return preprocessedSentence;
        }
    }

    public Preprocessor getNextPreprocessor() {
        return nextPreprocessor;
    }

    public void setNextPreprocessor(Preprocessor nextPreprocessor) {
        this.nextPreprocessor = nextPreprocessor;
    }
}
