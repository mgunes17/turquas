package command.w2v_creator_command;

import command.AbstractCommand;
import command.Command;
import db.dao.W2VTokenDAO;
import file_operation.W2V4Sentence;
import model.W2VToken;
import w2v_operation.vector_operation.AverageBy;
import w2v_operation.vector_operation.NearBy;
import w2v_operation.vector_operation.VectorType;
import w2v_operation.word_operation.LetterBy;
import w2v_operation.word_operation.StemBy;
import w2v_operation.word_operation.WordType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.05.2017.
 */
public class Sentence2W2VCommand extends AbstractCommand implements Command {
    private Map<String, W2VToken> w2VTokens;
    private Map<List<Float>, List<List<Float>>> w2vValues = new HashMap<List<Float>, List<List<Float>>>();
    private Map<String, List<String>> convertedSentences = new HashMap<String, List<String>>();
    private WordType wordType;
    private VectorType vectorType;
    private Map<String, WordType> wordTypeMap;
    private Map<String, VectorType> vectorTypeMap;

    public Sentence2W2VCommand() {
        wordTypeMap = new HashMap<String, WordType>();
        wordTypeMap.put("stem", new StemBy());
        wordTypeMap.put("letter", new LetterBy());

        vectorTypeMap = new HashMap<String, VectorType>();
        vectorTypeMap.put("near", new NearBy());
        vectorTypeMap.put("average", new AverageBy());
    }

    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter)) {
            return false;
        }

        W2VTokenDAO w2VTokenDAO = new W2VTokenDAO();

        //convertedSentences' ı oluşturur
        if(parameter[1].equals("stem")) {
            w2VTokens = w2VTokenDAO.getTokens(true);
        }
        else {
            w2VTokens = w2VTokenDAO.getTokens(false);
        }

        wordType = wordTypeMap.get(parameter[1]);
        wordType.prepareWord(convertedSentences);

        vectorType = vectorTypeMap.get(parameter[2]);
        vectorType.prepareVector(convertedSentences, w2vValues,  w2VTokens);

        //input-output dosyalarını oluştur
        W2V4Sentence.writeToFileSentence2W2V(w2vValues);

        return true;
    }

    protected boolean validateParameter(String[] parameter) { //kontroller map verilerilerine göre yapılsın !!
        return (parameter.length == 3 &&
                (parameter[1].equals("stem") || parameter[1].equals("letter")) &&
                (parameter[2].equals("average") || parameter[2].equals("near")));
    }
}
