package command.w2v_creator_command;

import command.Command;
import component.w2v_creator.w2v_loader.W2VLoader;

/**
 * Created by ercan on 17.05.2017.
 */
public class LoadCommand implements Command {

    @Override
    public boolean execute(String[] parameter) {
        try {
            W2VLoader w2VLoader = new W2VLoader();
            w2VLoader.loadVectorsFromFile();
            w2VLoader.saveToDatabase();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
