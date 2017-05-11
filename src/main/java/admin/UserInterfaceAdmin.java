package admin;

import command.CommandSet;
import component.user_interface.similarity.CosineSimilarity;
import component.user_interface.similarity.GesdSimilarity;
import component.user_interface.similarity.SimilarityType;
import w2v_operation.vector_operation.AverageBy;
import w2v_operation.vector_operation.NearBy;
import w2v_operation.vector_operation.VectorType;
import w2v_operation.word_operation.LetterBy;
import w2v_operation.word_operation.StemBy;
import w2v_operation.word_operation.WordType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mustafa on 10.05.2017.
 */
public class UserInterfaceAdmin extends Admin {
    public static Map<String, WordType> wordTypeMap;
    public static Map<String, VectorType> vectorTypeMap;
    public static Map<String, SimilarityType> similarityMap;
    public static Map<String, Integer> parameterMap;
    public static String vectorType;
    public static String wordType;
    public static String similarityType;

    static {
        wordTypeMap = new HashMap<String, WordType>();
        wordTypeMap.put("letter", new LetterBy());
        wordTypeMap.put("stem", new StemBy());
        wordType = "letter";

        vectorTypeMap = new HashMap<String, VectorType>();
        vectorTypeMap.put("near", new NearBy());
        vectorTypeMap.put("average", new AverageBy());
        vectorType = "average";

        similarityMap = new HashMap<>();
        similarityMap.put("cosine", new CosineSimilarity());
        similarityMap.put("gesd", new GesdSimilarity());
        similarityType = "cosine";

        parameterMap = new HashMap<>();
        parameterMap.put("max_answer_count", 10);
        parameterMap.put("threshold", 30);
    }

    public UserInterfaceAdmin(CommandSet commandSet) {
        super(commandSet);
    }
}
