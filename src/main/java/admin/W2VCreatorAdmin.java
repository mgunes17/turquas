package admin;

import command.CommandSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ercan on 27.04.2017.
 */
public class W2VCreatorAdmin extends Admin {
    public static Map<String, Integer> w2vParameterMap;
    public static Map<String, String> filenameMap;
    public static Map<String, List<Double>> wordVectorMap;

    static {
        w2vParameterMap = new HashMap<String, Integer>();
        w2vParameterMap.put("epoch", 5);
        w2vParameterMap.put("min_word_freq", 1);
        w2vParameterMap.put("iteration", 1);
        w2vParameterMap.put("layer_size", 100);
        w2vParameterMap.put("window_size", 5);
        filenameMap = new HashMap<String, String>();
        filenameMap.put("target_file", "target.txt");
        filenameMap.put("source_file", "source.txt");
        filenameMap.put("input4dl_file", "input4dl.txt");
        wordVectorMap = new HashMap<String, List<Double>>();

    }

    public W2VCreatorAdmin(CommandSet commandSet){
        super(commandSet);
    }

}
