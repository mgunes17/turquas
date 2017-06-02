package component.user_interface.candidate;

import db.dao.CandidateDAO;
import db.dao.W2VTokenDAO;
import model.Question;
import model.QuestionForCompare;
import nlp_tool.zemberek.ZemberekSentenceAnalyzer;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mustafa on 11.05.2017.
 */
public class FindingCandidate {
    private CandidateDAO candidateDAO;
    private String w2vType;

    public FindingCandidate(String type) {
        W2VTokenDAO w2VTokenDAO = new W2VTokenDAO();
        candidateDAO = new CandidateDAO();
        w2vType = type;
    }

    public List<QuestionForCompare> findCandidatesForVecSim(String userQuestion, boolean nounClause) {
        List<Question> questionList = getQuestionsFromDatabase(userQuestion, nounClause);

        return prepareListForVecSim(questionList); // sentence ve source işlemleri ortak olarak candidate daoda olacak
    }

    public List<QuestionForCompare> findCandidatesForDeepLearning(String userQuestion, boolean nounClause) {
        List<Question> questionList = getQuestionsFromDatabase(userQuestion, nounClause);

        return prepareListDeepLearning(questionList); // sentence ve source işlemleri ortak olarak candidate daoda olacak
    }

    private List<Question> getQuestionsFromDatabase(String userQuestion, boolean nounClause){
        TurkishSentenceAnalyzer analyzer = ZemberekSentenceAnalyzer.getSentenceAnalyzer();
        SentenceAnalysis analysis = analyzer.analyze(userQuestion);
        analyzer.disambiguate(analysis);

        List<String> wordList = new ArrayList<>();
        for (SentenceAnalysis.Entry entry : analysis) {
            WordAnalysis wordAnalysis = entry.parses.get(0);
            wordList.add(wordAnalysis.dictionaryItem.lemma);
        }
        String[] words = wordList.toArray(new String[wordList.size()]);

        return candidateDAO.getQuestions(words, nounClause);
    }

    private List<QuestionForCompare> prepareListForVecSim(List<Question> questionList){
        List<QuestionForCompare> questionForCompareList = new ArrayList<>();
        for(Question question: questionList){
            QuestionForCompare qfc = createQuestionForCompare(question);
            questionForCompareList.add(qfc);
        }

        return questionForCompareList;
    }

    private List<QuestionForCompare> prepareListDeepLearning(List<Question> questionList){
        List<QuestionForCompare> questionForCompareList = new ArrayList<>();
        Set<String> uniqueAnswers = new HashSet<>();

        for(Question question: questionList){
            if(!uniqueAnswers.contains(question.getAnswer())){
                QuestionForCompare qfc = createQuestionForCompare(question);
                questionForCompareList.add(qfc);
                uniqueAnswers.add(question.getAnswer());
            }
        }

        return questionForCompareList;
    }

    private QuestionForCompare createQuestionForCompare(Question question){
        QuestionForCompare qfc = new QuestionForCompare();
        qfc.setQuestion(question.getQuestion());
        qfc.setQuestionVector(question.getQuestionW2vValueMap().get(w2vType).
                stream().mapToDouble(Double::doubleValue).toArray());
        qfc.setAnswer(question.getAnswer());
        qfc.setAnswerVector(question.getAnswerW2vValueMap().get(w2vType).
                stream().mapToDouble(Double::doubleValue).toArray());

        return qfc;
    }


}
