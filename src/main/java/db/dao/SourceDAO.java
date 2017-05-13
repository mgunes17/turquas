package db.dao;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import db.accessor.SourceAccessor;
import db.configuration.ConnectionConfiguration;
import db.configuration.MappingManagerConfiguration;
import model.Source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 26.04.2017.
 */
public class SourceDAO {
    private Session session;
    private static final SourceAccessor sourceAccessor = MappingManagerConfiguration
            .getMappingManager()
            .createAccessor(SourceAccessor.class);

    public SourceDAO(){
        session = ConnectionConfiguration.getSession();
    }

    public Map<String, Source> getAllSourceWordCount() {
        Map<String, Source> sourceMap = new HashMap<>();
        Result<Source> result = sourceAccessor.getAll();

        for(Source source: result) {
            sourceMap.put(source.getSourceName(), source);
        }

        return sourceMap;
    }

    public void insertBatch(List<Source> sourceList){
        try{
            BatchStatement batch = new BatchStatement();
            for(Source source: sourceList){
                Statement statement = sourceAccessor.insertBatch(source.getSourceName(),
                        source.getLastUpdatedDate(), source.getWordCountMap());
                batch.add(statement);
            }

            session.execute(batch);
            System.out.println("SourceDAO insertBatch başarıyla tamamlandı.");
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            System.out.println(("SourceDAO insertBatch hata verdi."));
        }

    }
}
