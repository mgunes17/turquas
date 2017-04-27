package component.question_generator.word;

import component.question_generator.factory.zemberek.type.suffix.Suffix;

/**
 * Created by mustafa on 29.03.2017.
 */
public class Word {
    private final String word;
    private String primaryPos;
    private String secondaryPos;
    private String log;
    private Suffix suffix;
    private boolean isCopQuestion = false;
    private boolean isInstQuestion = false;
    private boolean isGen = false;

    public Word(String word, Suffix suffix) {
        this.word = word;
        this.suffix = suffix;
    }

    public Word(String word) {
        this.word = word;
    }

    //getter-setter

    public String getWord() {
        return word;
    }

    public String getPrimaryPos() {
        return primaryPos;
    }

    public void setPrimaryPos(String primaryPos) {
        this.primaryPos = primaryPos;
    }

    public String getSecondaryPos() {
        return secondaryPos;
    }

    public void setSecondaryPos(String secondaryPos) {
        this.secondaryPos = secondaryPos;
    }

    public Suffix getSuffix() {
        return suffix;
    }

    public void setSuffix(Suffix suffix) {
        this.suffix = suffix;
    }

    public boolean isCopQuestion() {
        return isCopQuestion;
    }

    public void setCopQuestion(boolean copQuestion) {
        isCopQuestion = copQuestion;
    }

    public boolean isInstQuestion() {
        return isInstQuestion;
    }

    public void setInstQuestion(boolean instQuestion) {
        isInstQuestion = instQuestion;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public boolean isGen() {
        return isGen;
    }

    public void setGen(boolean gen) {
        isGen = gen;
    }


}
