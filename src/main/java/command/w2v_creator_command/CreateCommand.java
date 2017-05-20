package command.w2v_creator_command;

import command.Command;
import component.w2v_creator.sentence_file_creator.DefaultSentenceFileCreator;
import component.w2v_creator.sentence_file_creator.LetterLimitedSentenceFileCreator;
import component.w2v_creator.sentence_file_creator.SentenceLoader;
import component.w2v_creator.sentence_file_creator.StemmedSentenceFileCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ercan on 26.04.2017.
 */
public class CreateCommand implements Command {
    private final static Logger logger = LoggerFactory.getLogger(CreateCommand.class);
    private final int letterCount = 5;

    public boolean execute(String[] parameter) {
        try {
            int sentenceCount = Integer.parseInt(parameter[1]); // veri tabanından sentence count kadar cümle çek
            int parameterLength = parameter.length;
            SentenceLoader sentenceLoader = new SentenceLoader(sentenceCount);

            if(parameterLength == 2){ // kelimelerin direk kendisini al
                DefaultSentenceFileCreator defaultCreator = new DefaultSentenceFileCreator(sentenceLoader);
                defaultCreator.createFile();
            } else if(parameterLength == 3 && parameter[2].equals("stem")){ // kelimelerin stemlerini al
                StemmedSentenceFileCreator stemmedCreator = new StemmedSentenceFileCreator(sentenceLoader);
                stemmedCreator.createFile();
            } else if(parameterLength == 3 && parameter[2].equals("letter")){ // kelimelerin ilk N harfini al
                LetterLimitedSentenceFileCreator letterLimitedCreator = new LetterLimitedSentenceFileCreator(sentenceLoader, letterCount);
                letterLimitedCreator.createFile();
            }

            return true;
        } catch(NumberFormatException ex){
            System.out.println("Lütfen sayısal bir değer girin.");
            logger.warn(ex.getMessage());
            logger.warn(ex.toString());
            return false;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            logger.warn(ex.toString());
            return false;
        }
    }



}
