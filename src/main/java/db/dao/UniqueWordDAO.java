package db.dao;

import com.datastax.driver.core.*;
import db.configuration.ConnectionConfiguration;
import db.configuration.ModelVariables;
import model.UniqueWord;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mustafa on 26.04.2017.
 */
public class UniqueWordDAO {
    private Session session;
    private PreparedStatement preparedStatement;
    private String keyspace;
    private String tableName;

    public UniqueWordDAO(){
        this.keyspace = ModelVariables.KEYSPACE;
        this.tableName = ModelVariables.UNIQUE_WORD_TABLE_NAME;
    }

    public Set<UniqueWord> getAllWords() {
        String query = "SELECT * FROM unique_word";
        session = ConnectionConfiguration.getCLuster().connect("turquas");
        ResultSet result = session.execute(query);
        Set<UniqueWord> uniqueWordSet = new HashSet<UniqueWord>();

        for(Row row: result) {
            UniqueWord uniqueWord = new UniqueWord(row.getString(0));
            uniqueWord.setDocumentSet(row.getSet(1, String.class));
            uniqueWordSet.add(uniqueWord);
        }

        session.close();
        return uniqueWordSet;
    }

    public boolean update(Set<UniqueWord> uniqueWordSet) {
        try{
            BatchStatement batch = new BatchStatement();
            prepareForUpdate();
            session = ConnectionConfiguration.getCLuster().connect("turquas");

            for(UniqueWord uniqueWord: uniqueWordSet){
                BoundStatement bound = preparedStatement.bind(uniqueWord.getValueMap(), uniqueWord.getWord());
                batch.add(bound);
            }

            session.execute(batch);
            session.close();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }

    public void prepareForUpdate(){
        preparedStatement = session.prepare(
                "UPDATE unique_word SET value = ? WHERE word = ?");
    }

    public void insert(UniqueWord uniqueWord){
        try{
            BoundStatement bound = preparedStatement.bind(
                    uniqueWord.getWord(), uniqueWord.getDocumentSet());
            session.execute(bound);
        } catch(Exception ex){
        }
    }

    public void update(UniqueWord uniqueWord){
        try{
            BoundStatement bound = preparedStatement.bind(
                    uniqueWord.getDocumentSet(), uniqueWord.getWord());
            session.execute(bound);
        } catch(Exception ex){
        }
    }

    public void delete(UniqueWord uniqueWord){
        try{
            BoundStatement bound = preparedStatement.bind(uniqueWord.getWord());
            session.execute(bound);
        } catch(Exception ex){
        }
    }

    public void prepareForInsert(){
        preparedStatement = session.prepare(
                "INSERT INTO " + tableName + " (word, documents) values (?, ?)");
    }

    public void prepareForDelete(){
        preparedStatement = session.prepare(
                "DELETE FROM " + tableName + " WHERE word=?");
    }
}
