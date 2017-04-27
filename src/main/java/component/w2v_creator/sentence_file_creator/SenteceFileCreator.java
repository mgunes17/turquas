package component.w2v_creator.sentence_file_creator;

/**
 * Created by ercan on 26.04.2017.
 */
public abstract class SenteceFileCreator {
    SentenceLoader sentenceLoader;
    abstract void createFile();
}
