package model;

/**
 * Created by mustafa on 11.05.2017.
 */
public class SimilarityValue implements Comparable<SimilarityValue> {
    private double value;
    private QuestionForCompare questionForCompare;

    public int compareTo(SimilarityValue o) {
        double oValue = o.getValue();
        if(this.value > oValue)
            return -1;
        else
            return 1;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public QuestionForCompare getQuestionForCompare() {
        return questionForCompare;
    }

    public void setQuestionForCompare(QuestionForCompare questionForCompare) {
        this.questionForCompare = questionForCompare;
    }
}
