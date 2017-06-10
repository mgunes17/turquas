package model;

import com.datastax.driver.mapping.annotations.*;
import db.configuration.ModelVariables;

import java.util.List;
import java.util.Map;

/**
 * Created by ercan on 13.05.2017.
 */
@Table(keyspace = ModelVariables.KEYSPACE, name = ModelVariables.QUESTION_TABLE_NAME)
public class Question {
    @PartitionKey
    @Column(name = "source_name")
    private String sourceName;

    @ClusteringColumn(2)
    @Column(name = "question")
    private String question;

    @ClusteringColumn(0)
    @Column(name = "noun_clause")
    private boolean nounClause;

    @Column(name = "processed")
    private boolean processed;

    @FrozenValue
    @Column(name = "question_w2v_value_map")
    private Map<String, List<Double>> questionW2vValueMap;

    @Column(name = "answer")
    private String answer;

    @FrozenValue
    @Column(name = "answer_w2v_value_map")
    private Map<String, List<Double>> answerW2vValueMap;

    @ClusteringColumn(1)
    @Column(name = "question_type")
    private String questionType;

    public Question() {
        //non - arg
    }

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Question(String question, String answer, String questionType) {
        this.question = question;
        this.answer = answer;
        this.questionType = questionType;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, List<Double>> getQuestionW2vValueMap() {
        return questionW2vValueMap;
    }

    public void setQuestionW2vValueMap(Map<String, List<Double>> questionW2vValueMap) {
        this.questionW2vValueMap = questionW2vValueMap;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Map<String, List<Double>> getAnswerW2vValueMap() {
        return answerW2vValueMap;
    }

    public void setAnswerW2vValueMap(Map<String, List<Double>> answerW2vValueMap) {
        this.answerW2vValueMap = answerW2vValueMap;
    }

    public boolean isNounClause() {
        return nounClause;
    }

    public void setNounClause(boolean nounClause) {
        this.nounClause = nounClause;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}
