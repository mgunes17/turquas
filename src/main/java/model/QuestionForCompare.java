package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 11.05.2017.
 */
public class QuestionForCompare {
    private String question;
    private String answer;
    private double[] vector; // sorunun vektör değeri
    private List<SimilarityValue> similarityList;

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

    public double[] getVector() {
        return vector;
    }

    public void setVector(double[] vector) {
        this.vector = vector;
    }

    public List<SimilarityValue> getSimilarityList() {
        return similarityList;
    }

    public void setSimilarityList(List<SimilarityValue> similarityList) {
        this.similarityList = similarityList;
    }
}
