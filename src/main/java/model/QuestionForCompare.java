package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 11.05.2017.
 */
public class QuestionForCompare {
    private String question;
    private String answer;
    private double[] questionVector; // sorunun vektör değeri
    private double[] answerVector; // cevabın vektör değeri
    private List<SimilarityValue> similarityList;
    private String sourceName;

    public QuestionForCompare() {
        similarityList = new ArrayList<SimilarityValue>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public double[] getQuestionVector() {
        return questionVector;
    }

    public void setQuestionVector(double[] questionVector) {
        this.questionVector = questionVector;
    }

    public double[] getAnswerVector() {
        return answerVector;
    }

    public void setAnswerVector(double[] answerVector) {
        this.answerVector = answerVector;
    }

    public List<SimilarityValue> getSimilarityList() {
        return similarityList;
    }

    public void setSimilarityList(List<SimilarityValue> similarityList) {
        this.similarityList = similarityList;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
