package admin;

import command.CommandSet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ercan on 27.04.2017.
 */
public class W2VCreatorAdmin extends Admin {
    private CommandSet commandSet;
    public static Map<String, Integer> w2vParameterMap;
    public static Map<String, String> filenameMap;

    static {
        w2vParameterMap = new HashMap<String, Integer>();
        w2vParameterMap.put("epoch", 5);
        w2vParameterMap.put("min_word_freq", 1);
        w2vParameterMap.put("iteration", 1);
        w2vParameterMap.put("layer_size", 5);
        w2vParameterMap.put("window_size", 5);
        filenameMap = new HashMap<String, String>();
        filenameMap.put("target_file", "target.txt");
        filenameMap.put("source_file", "source.txt");
    }

    public W2VCreatorAdmin(CommandSet commandSet){
        this.commandSet = commandSet;
    }

    public boolean run(String inputCommand) {
        return commandSet.run(inputCommand);
    }
}
