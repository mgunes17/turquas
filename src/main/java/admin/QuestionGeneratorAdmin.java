package admin;

import command.CommandSet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mustafa on 27.04.2017.
 */
public class QuestionGeneratorAdmin extends Admin {
    public static Map<String, Integer> questionGEneratorParameterMap;

    static {
        questionGEneratorParameterMap = new HashMap<String, Integer>();
        questionGEneratorParameterMap.put("session_size", 5);
    }

    public QuestionGeneratorAdmin(CommandSet commandSet) {
        super(commandSet);
    }
}
