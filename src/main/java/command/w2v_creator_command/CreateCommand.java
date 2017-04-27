package command.w2v_creator_command;

import command.Command;
import component.w2v_creator.creator.DefaultCreator;
import component.w2v_creator.creator.LetterLimitedCreator;
import component.w2v_creator.creator.SentenceLoader;
import component.w2v_creator.creator.StemmedCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ercan on 26.04.2017.
 */
public class CreateCommand implements Command {
    private final static Logger logger = LoggerFactory.getLogger(CreateCommand.class);

    public boolean execute(String[] parameter) {
        try {
            int sentenceCount = Integer.parseInt(parameter[1]); // veri tabanından sentence count kadar cümle çek
            int parameterLength = parameter.length;
            SentenceLoader sentenceLoader = new SentenceLoader(sentenceCount);

            if(parameterLength == 2){ // kelimelerin direk kendisini al
                DefaultCreator defaultCreator = new DefaultCreator(sentenceLoader);
                defaultCreator.createFile();
            } else if(parameterLength == 3 && parameter[2].equals("stem")){ // kelimelerin stemlerini al
                StemmedCreator stemmedCreator = new StemmedCreator(sentenceLoader);
                stemmedCreator.createFile();
            } else if(parameterLength == 4 && parameter[2].equals("first")){ // kelimelerin ilk N harfini al
                int letterCount = Integer.parseInt(parameter[3]);
                LetterLimitedCreator letterLimitedCreator = new LetterLimitedCreator(sentenceLoader, letterCount);
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
