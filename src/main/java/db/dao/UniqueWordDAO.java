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
            session = ConnectionConfiguration.getCLuster().connect("turquas");
            //BatchStatement batch = new BatchStatement();
            prepareForUpdate();

            for(UniqueWord uniqueWord: uniqueWordSet){
                BoundStatement bound = preparedStatement.bind(uniqueWord.getValueMap(), uniqueWord.getWord());
                //batch.add(bound);
                session.execute(bound);
            }

            //session.execute(batch);
            session.close();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }

    public boolean updateSources(Set<UniqueWord> uniqueWordSet) {
        try{
            session = ConnectionConfiguration.getCLuster().connect("turquas");
            //BatchStatement batch = new BatchStatement();
            prepareForSourceUpdate();

            for(UniqueWord uniqueWord: uniqueWordSet){
                BoundStatement bound = preparedStatement.bind(uniqueWord.getDocumentSet(), uniqueWord.getWord());
                //batch.add(bound);
                session.execute(bound);
            }

            //session.execute(batch);
            session.close();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        return true;
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


    private void prepareForSourceUpdate(){
        preparedStatement = session.prepare(
                "UPDATE unique_word SET source = source + ? WHERE word = ?");
    }

    private void prepareForUpdate(){
        preparedStatement = session.prepare(
                "UPDATE unique_word SET value = ? WHERE word = ?");
    }

    private void prepareForInsert(){
        preparedStatement = session.prepare(
                "INSERT INTO " + tableName + " (word, documents) values (?, ?)");
    }

    private void prepareForDelete(){
        preparedStatement = session.prepare(
                "DELETE FROM " + tableName + " WHERE word=?");
    }
}
