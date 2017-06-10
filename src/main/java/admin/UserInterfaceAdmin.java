package admin;

import command.CommandSet;
import component.user_interface.similarity.CosineSimilarity;
import component.user_interface.similarity.EuclidianSimilarity;
import component.user_interface.similarity.GesdSimilarity;
import component.user_interface.similarity.SimilarityType;
import w2v_operation.vector_operation.AverageBy;
import w2v_operation.vector_operation.NearBy;
import w2v_operation.vector_operation.VectorType;
import w2v_operation.word_operation.LetterBy;
import w2v_operation.word_operation.StemBy;
import w2v_operation.word_operation.TokenBy;
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
    public static Map<String, Integer> parameterMap;    // set threshold 40, set max_answer_count 19
    public static String vectorType;                    // set vectype xxx
    public static String wordType;                      // set wordtype xxx
    public static String similarityType;                // set simtype xxx
    public static Map<String, String> pathMap;          // set python xxx, set script xxx, set prediction xxx, set question xxx

    static {
        wordTypeMap = new HashMap<>();
        wordTypeMap.put("letter", new LetterBy());
        wordTypeMap.put("stem", new StemBy());
        wordTypeMap.put("token", new TokenBy());
        wordType = "token";

        vectorTypeMap = new HashMap<>();
        vectorTypeMap.put("near", new NearBy());
        vectorTypeMap.put("average", new AverageBy());
        vectorType = "average";

        similarityMap = new HashMap<>();
        similarityMap.put("cosine", new CosineSimilarity());
        similarityMap.put("gesd", new GesdSimilarity());
        similarityMap.put("euclid", new EuclidianSimilarity());
        similarityType = "gesd";

        parameterMap = new HashMap<>();
        parameterMap.put("max_answer_count", 10);
        parameterMap.put("threshold", 30);
        parameterMap.put("evaluate_count", 5000);

        pathMap = new HashMap<>();
    }

    public UserInterfaceAdmin(CommandSet commandSet) {
        super(commandSet);
    }
}
