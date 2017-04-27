package component.w2v_creator.input_creator;

import component.w2v_creator.sentence_file_creator.SentenceLoader;

/**
 * Created by ercan on 27.04.2017.
 */
public class WordsAverageInputCreator extends InputCreator {
    public WordsAverageInputCreator(SentenceLoader sentenceLoader){
        this.sentenceLoader = sentenceLoader;
    }

    public void createInputs() {
    }
}
