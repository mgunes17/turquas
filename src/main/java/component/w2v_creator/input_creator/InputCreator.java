package component.w2v_creator.input_creator;

import component.w2v_creator.creator.SentenceLoader;

/**
 * Created by ercan on 27.04.2017.
 */
public abstract class InputCreator {
    SentenceLoader sentenceLoader;
    abstract void createInputs();
}
