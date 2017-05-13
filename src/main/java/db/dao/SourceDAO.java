package db.dao;

import com.datastax.driver.core.*;
import db.configuration.ConnectionConfiguration;
import db.configuration.ModelVariables;
import model.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 26.04.2017.
 */
public class SourceDAO {
    private final static Logger logger = LoggerFactory.getLogger(SourceDAO.class);
    private String keyspace;
    private String tableName;
    private Session session;
    private PreparedStatement preparedStatement;

    public SourceDAO(){
        this.keyspace = ModelVariables.KEYSPACE;
        this.tableName = ModelVariables.SOURCE_TABLE_NAME;
        session = ConnectionConfiguration.getSession();
    }

    public Map<String, Source> getAllSourceWordCount() {
        String query = "SELECT * FROM source";
        ResultSet resultSet = session.execute(query);
        Map<String, Source> sourceMap = new HashMap<String, Source>();

        for(Row row: resultSet) {
            Source source = new Source(row.getString(0), row.getMap(3, String.class, Integer.class));
            sourceMap.put(source.getSourceName(), source);
        }

        return sourceMap;
    }

    public void insert(Source source){
        try{
            BoundStatement bound = preparedStatement.bind(
                    source.getSourceName(), source.getBestWords(),
                    new Timestamp(System.currentTimeMillis()), source.getWordCountMap());
            session.execute(bound);
            logger.info("SourceDAO insert başarıyla tamamlandı.");
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void update(Source source){
        try{
            BoundStatement bound = preparedStatement.bind(
                    source.getBestWords(), new Timestamp(System.currentTimeMillis()),
                    source.getWordCountMap(), source.getSourceName());
            session.execute(bound);
            logger.info("SourceDAO update başarıyla tamamlandı.");
        } catch(Exception ex){
            logger.warn("SourceDAO update hata verdi.");
        }
    }

    public void delete(Source source){
        try{
            BoundStatement bound = preparedStatement.bind(source.getSourceName());
            session.execute(bound);
            logger.info("SourceDAO delete başarıyla tamamlandı.");
        } catch(Exception ex){
            logger.warn("SourceDAO delete hata verdi.");
        }
    }

    public void insertBatch(List<Source> sourceList){
        try{
            BatchStatement batch = new BatchStatement();
            prepareForInsert();

            for(Source source: sourceList){
                BoundStatement bound = preparedStatement.bind(
                        source.getSourceName(),
                        new Timestamp(System.currentTimeMillis()), source.getWordCountMap());
                batch.add(bound);
            }

            session.execute(batch);
            System.out.println("SourceDAO insertBatch başarıyla tamamlandı.");
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            System.out.println(("SourceDAO insertBatch hata verdi."));
        }

    }

    private void prepareForInsert(){
        preparedStatement = session.prepare(
                "INSERT INTO " + tableName + " (source_name, " +
                        "last_updated_date, word_count_map) values (?, ?, ?)");
    }

    private void prepareForUpdate(){
        preparedStatement = session.prepare("UPDATE " + tableName +" " +
                "SET best_words= ?," + "last_updated_date=?, " + "word_count_map = word_count_map + ?" +
                "WHERE source_name=?");
    }

    private void prepareForDelete(){
        preparedStatement = session.prepare(
                "DELETE FROM " + tableName + " WHERE source_name=?");
    }
}
