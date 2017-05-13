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

    @ClusteringColumn
    @Column(name = "question")
    private String question;

    @Column(name = "noun_clause")
    private boolean nounClause;

    @FrozenValue
    @Column(name = "question_w2v_value_map")
    private Map<String, List<Double>> questionW2vValueMap;

    @Column(name = "question_word")
    private String questionWord;

    @Column(name = "word_type_after_question_word")
    private String wordTypeAfterQuestionWord;

    @Column(name = "answer")
    private String answer;

    @FrozenValue
    @Column(name = "answer_w2v_value_map")
    private Map<String, List<Double>> answerW2vValueMap;

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

    public String getQuestionWord() {
        return questionWord;
    }

    public void setQuestionWord(String questionWord) {
        this.questionWord = questionWord;
    }

    public String getWordTypeAfterQuestionWord() {
        return wordTypeAfterQuestionWord;
    }

    public void setWordTypeAfterQuestionWord(String wordTypeAfterQuestionWord) {
        this.wordTypeAfterQuestionWord = wordTypeAfterQuestionWord;
    }

    public String getAnswer() {
        return answer;
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
}
