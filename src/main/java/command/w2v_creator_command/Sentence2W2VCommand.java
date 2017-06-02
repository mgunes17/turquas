package command.w2v_creator_command;

import admin.UserInterfaceAdmin;
import command.AbstractCommand;
import command.Command;
import db.dao.QuestionDAO;
import file_operation.W2V4Sentence;
import model.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.05.2017.
 */
public class Sentence2W2VCommand extends AbstractCommand implements Command {
    private Map<List<Double>, List<Double>> w2vValues = new HashMap<>();

    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter)) {
            System.out.println("Eksik/Fazla parametre");
            return false;
        }
        String wordType = parameter[1];
        String vectorType = parameter[2];
        String valueType = wordType + "_" + vectorType;

        int limit = parseLimitCount(parameter[3]);

        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questionList = new ArrayList<>();

        if(limit == 0){
            questionList = questionDAO.getAllQuestions();
        } else if(limit > 0){
            questionList = questionDAO.getQuestionsByLimit(limit);
        }

        for(Question question: questionList) {
            w2vValues.put(question.getQuestionW2vValueMap().get(valueType),
                    question.getAnswerW2vValueMap().get(valueType));
        }
        //input-output dosyalarını oluştur
        if(w2vValues.size() > 0)
            W2V4Sentence.writeToFileSentence2W2V(w2vValues);

        return true;
    }

    protected boolean validateParameter(String[] parameter) { //kontroller map verilerilerine göre yapılsın !!
        return parameter.length == 4
                && UserInterfaceAdmin.wordTypeMap.get(parameter[1]) != null // öyle bir word type var mı ?
                && UserInterfaceAdmin.vectorTypeMap.get(parameter[2]) != null; // öyle bir vector type var mı ?
    }

    private int parseLimitCount(String parameter){
        try {
            return Integer.parseInt(parameter);
        } catch (NumberFormatException ex){
            System.out.println("Lütfen sayısal bir değer giriniz.");
            ex.printStackTrace();
            return -1;
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return -1;
        }
    }
}
