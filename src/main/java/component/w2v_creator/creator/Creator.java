package component.w2v_creator.creator;

/**
 * Created by ercan on 26.04.2017.
 */
public abstract class Creator {
    SentenceLoader sentenceLoader;
    abstract void createFile();
}
