package command.w2v_creator_command;

import admin.W2VCreatorAdmin;
import command.Command;

/**
 * Created by ercan on 26.04.2017.
 */
public class SetCommand implements Command {
    public boolean execute(String[] parameter) {
        if(W2VCreatorAdmin.w2vParameterMap.get(parameter[1]) == null) {
            if(W2VCreatorAdmin.filenameMap.get(parameter[1]) == null){
                System.out.println(parameter[1] + " isimli parametre yok");
            } else {
                String newFilename = parameter[2];
                W2VCreatorAdmin.filenameMap.put(parameter[1], newFilename);
            }
        } else {
            try {
                int value = Integer.parseInt(parameter[2]);
                W2VCreatorAdmin.w2vParameterMap.put(parameter[1], value);
            } catch (NumberFormatException ex) {
                System.out.println("Lütfen sayısal bir değer giriniz.");
                ex.printStackTrace();
                return false;
            }
        }

        return true;
    }
}
