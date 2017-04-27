package component.w2v_creator.input_creator;

import admin.W2VCreatorAdmin;
import component.w2v_creator.creator.SentenceLoader;
import model.Sentence;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
