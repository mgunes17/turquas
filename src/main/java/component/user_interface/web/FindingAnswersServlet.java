package component.user_interface.web;

import command.user_interface_command.AnswerCommand;
import component.user_interface.answerer.AnswererWithDeepLearning;
import component.user_interface.answerer.AnswererWithVectorSimilarity;
import home_base.Turquas;
import model.QuestionForCompare;
import model.SimilarityValue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

/**
 * Created by mustafa on 22.05.2017.
 */
@WebServlet(name = "FindingAnswersServlet", urlPatterns = {"/findinganswers"})
public class FindingAnswersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String question = request.getParameter("question").toLowerCase();
        AnswerCommand answerCommand = new AnswerCommand();
        System.out.println(question);

        session.setAttribute("question", request.getParameter("question"));

        if(!answerCommand.validateQuestion(question))
            session.setAttribute("state", 2);
        else {
            AnswererWithDeepLearning answererWithDeepLearning = new AnswererWithDeepLearning();
            answererWithDeepLearning.answer(question);
            QuestionForCompare answerDeepLearning = answererWithDeepLearning.getUserQuestion();

            AnswererWithVectorSimilarity answererWithVectorSimilarity = new AnswererWithVectorSimilarity();
            answererWithVectorSimilarity.answer(question);
            QuestionForCompare answerSimilarity = answererWithVectorSimilarity.getUserQuestion();

            Set<String> answersForVecSim = new LinkedHashSet<>();
            List<SimilarityValue> answerListForVecSim = new ArrayList<>();

            for(SimilarityValue sv: answerSimilarity.getSimilarityList()){
                if(!answersForVecSim.contains(sv.getQuestionForCompare().getAnswer())){
                    answerListForVecSim.add(sv);
                }
                answersForVecSim.add(sv.getQuestionForCompare().getAnswer());
            }

            session.setAttribute("similarityList", answerListForVecSim);
            session.setAttribute("learningList", answerDeepLearning.getSimilarityList());
            session.setAttribute("state", 1);
        }

        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
