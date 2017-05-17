package command.user_interface_command;

import admin.UserInterfaceAdmin;
import command.Command;

/**
 * Created by mustafa on 11.05.2017.
 */
public class SetParameterCommand implements Command{
    // parametreler->threshold yüzde değeri, gösterilecek max cevap sayısı, kullanılacak benzerlik algoritması,
    // w2v ye çevrilme tipi (near, average) kelime nasıl alınacak(stem, 5 letter)

    @Override
    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter)){
            System.out.println("Eksik/fazla parametre.");
            return false;
        }

        switch (parameter[1]) {
            case "wordtype":
                if(UserInterfaceAdmin.wordTypeMap.get(parameter[2]) != null){
                    UserInterfaceAdmin.wordType = parameter[2];
                    System.out.println(UserInterfaceAdmin.wordType);
                } else {
                    System.out.println("Lütfen geçerli bir parametre giriniz.");
                }
                break;
            case "vectype":
                if(UserInterfaceAdmin.vectorTypeMap.get(parameter[2]) != null){
                    UserInterfaceAdmin.vectorType = parameter[2];
                    System.out.println(UserInterfaceAdmin.vectorType);
                } else {
                    System.out.println("Lütfen geçerli bir parametre giriniz.");
                }
                break;
            case "simtype":
                if(UserInterfaceAdmin.similarityMap.get(parameter[2]) != null) {
                    UserInterfaceAdmin.similarityType = parameter[2];
                    System.out.println(UserInterfaceAdmin.similarityType);
                } else {
                    System.out.println("Lütfen geçerli bir parametre giriniz.");
                }
                break;
            default:
                try {
                    if(UserInterfaceAdmin.parameterMap.get(parameter[1]) != null){ // parametre mapinde mi ?
                        int value = Integer.parseInt(parameter[2]);
                        UserInterfaceAdmin.parameterMap.put(parameter[1], value);
                        System.out.println(UserInterfaceAdmin.parameterMap.get(parameter[1]));
                    } else if(UserInterfaceAdmin.pathMap.get(parameter[1]) != null) { // path mapinde mi ?
                        UserInterfaceAdmin.pathMap.put(parameter[1], parameter[2]);
                        System.out.println(UserInterfaceAdmin.pathMap.get(parameter[1]));
                    } else {
                        System.out.println("Böyle bir parametre bulunamadı.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Lütfen sayısal bir değer giriniz.");
                    return false;
                }
        }
        return true;
    }

    protected boolean validateParameter(String[] parameter) {
        return parameter.length == 3;
    }
}
