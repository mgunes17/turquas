package db.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import db.configuration.ConnectionConfiguration;
import db.configuration.ModelVariables;
import model.Sentence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 11.05.2017.
 */
public class CandidateDAO {
    private Session session;

    public List<Sentence> getSentences() {
        List<Sentence> sentenceList = new ArrayList<Sentence>();
        String query = "select original_sentence, questions from sentence;";
        session = ConnectionConfiguration.getCLuster().connect(ModelVariables.KEYSPACE);
        ResultSet result = session.execute(query);

        for(Row row: result) {
            sentenceList.add(new Sentence(
                    row.get(0, String.class), row.getSet(1, String.class)));
        }

        session.close();
        return sentenceList;
    }
}
