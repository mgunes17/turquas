package db.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import db.configuration.ConnectionConfiguration;
import db.configuration.ModelVariables;
import model.Sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by mustafa on 11.05.2017.
 */
public class CandidateDAO {
    private Session session;

    public CandidateDAO() {
        session = ConnectionConfiguration.getSession();
    }

    public List<Sentence> getSentences(String[] words) {
        List<Sentence> sentenceList = new ArrayList<>();

        List<Set<String>> sourceList = new ArrayList<>();

        for(String word: words) {
            String query = "select source from unique_word where word = '" + word + "' ;";
            ResultSet result = session.execute(query);

            for(Row row: result) {
                sourceList.add(row.getSet(0, String.class));
            }
        }

        StringBuilder candidateSentenceQuery = new StringBuilder();
        candidateSentenceQuery.append("select original_sentence, questions from sentence where source_name in ( ");

        for(Set<String> sourceSet: sourceList) {
            for(String sourceName: sourceSet) {
                candidateSentenceQuery.append(" '" + sourceName + "',");
            }
        }

        candidateSentenceQuery.deleteCharAt(candidateSentenceQuery.toString().length() - 1);
        candidateSentenceQuery.append(");");

        ResultSet result = session.execute(candidateSentenceQuery.toString());
        for(Row row: result) {
            sentenceList.add(new Sentence(
                    row.get(0, String.class), row.getSet(1, String.class)));
        }

        return sentenceList;
    }
}
