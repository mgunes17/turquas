package command.w2v_creator_command;

import command.AbstractCommand;
import command.Command;
import db.dao.QuestionDAO;
import file_operation.W2V4Sentence;
import model.Question;
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
    private Map<List<Double>, List<Double>> w2vValues = new HashMap<>();
    private Map<String, WordType> wordTypeMap;
    private Map<String, VectorType> vectorTypeMap;

    public Sentence2W2VCommand() {
        wordTypeMap = new HashMap<>();
        wordTypeMap.put("stem", new StemBy());
        wordTypeMap.put("letter", new LetterBy());

        vectorTypeMap = new HashMap<>();
        vectorTypeMap.put("near", new NearBy());
        vectorTypeMap.put("average", new AverageBy());
    }

    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter)) {
            return false;
        }

        String wordType = parameter[1];
        String vectorType = parameter[2];
        String valueType = wordType + "_" + vectorType;

        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questionList = questionDAO.getAllQuestions();

        for(Question question: questionList) {
            w2vValues.put(question.getQuestionW2vValueMap().get(valueType),
                    question.getAnswerW2vValueMap().get(valueType));
        }

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
