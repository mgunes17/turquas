package command.w2v_creator_command;

import command.Command;
import component.w2v_creator.sentence_file_creator.SentenceLoader;
import component.w2v_creator.input_creator.AllWordsInputCreator;
import component.w2v_creator.input_creator.VectorLoader;
import component.w2v_creator.input_creator.WordsAverageInputCreator;

/**
 * Created by ercan on 27.04.2017.
 */
public class Input4DLCommand implements Command {

    public boolean execute(String[] parameter) {
        try {
            VectorLoader vectorLoader = new VectorLoader();
            vectorLoader.loadVectorsIntoMapFromFile();
            int sentenceCount = Integer.parseInt(parameter[1]); // veri tabanından sentence count kadar cümle çek

            if(parameter[2].equals("average")){ // kelimelerin direk kendisini al
                WordsAverageInputCreator wordsAverageInputCreator = new WordsAverageInputCreator(new SentenceLoader(sentenceCount));
                wordsAverageInputCreator.createInputs();
            } else if(parameter[2].equals("full")){ // kelimelerin stemlerini al
                AllWordsInputCreator allWordsInputCreator = new AllWordsInputCreator(new SentenceLoader(sentenceCount));
                allWordsInputCreator.createInputs();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
