package component.w2v_creator.sentence_file_creator;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import db.configuration.ConnectionConfiguration;
import db.configuration.ModelVariables;
import model.Sentence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ercan on 26.04.2017.
 */
public class SentenceLoader {
    private Session session;
    private int sentenceCount;

    public SentenceLoader(int sentenceCount){
        buildSession();
        this.sentenceCount = sentenceCount;
    }

    public List<Sentence> getQuestionsForSentences() {
        String questionsQuery;
        if(sentenceCount == 0){
            questionsQuery = "SELECT original_sentence, questions FROM sentence";
        } else {
            questionsQuery = "SELECT original_sentence, questions FROM sentence LIMIT " + sentenceCount;
        }
        session = ConnectionConfiguration.getCLuster().connect(ModelVariables.KEYSPACE);
        ResultSet resultSet = session.execute(questionsQuery);
        List<Sentence> sentenceList = new ArrayList<Sentence>();

        for(Row row: resultSet) {
            Sentence sentence = new Sentence(row.getString(0), row.getSet(1, String.class));
            sentenceList.add(sentence);
        }

        return sentenceList;
    }

    public List<List<String>> getStemListsForSentences(){
        String stemmedWordsQuery;
        if(sentenceCount == 0){
            stemmedWordsQuery = "SELECT stemmed_words_list from sentence";
        } else {
            stemmedWordsQuery = "SELECT stemmed_words_list from sentence LIMIT " + sentenceCount;
        }

        ResultSet result = session.execute(stemmedWordsQuery);
        List<List<String>> sentences = new ArrayList<List<String>>();
        for(Row row: result){
            sentences.add(row.getList(0, String.class));
        }

        return sentences;
    }

    public List<List<String>> getTokenListsForSentences(){
        String tokenListQuery;

        if(sentenceCount == 0){
            tokenListQuery = "SELECT token_list from sentence";
        } else {
            tokenListQuery = "SELECT token_list from sentence LIMIT " + sentenceCount;
        }

        ResultSet result = session.execute(tokenListQuery);
        List<List<String>> sentences = new ArrayList<List<String>>();
        for(Row row: result){
            sentences.add(row.getList(0, String.class));
        }

        return sentences;
    }

    private void buildSession(){
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.connect("turquas");
    }

}
