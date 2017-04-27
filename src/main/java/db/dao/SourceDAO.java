package db.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import db.CassandraConfiguration;
import model.Source;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mustafa on 26.04.2017.
 */
public class SourceDAO {
    private Session session;

    public Map<String, Source> getAllSourceWordCount() {
        String query = "SELECT * FROM source";
        session = CassandraConfiguration.getCLuster().connect("turquas");
        ResultSet resultSet = session.execute(query);
        Map<String, Source> sourceMap = new HashMap<String, Source>();

        for(Row row: resultSet) {
            Source source = new Source(row.getString(0), row.getMap(3, String.class, Integer.class));
            sourceMap.put(source.getName(), source);
        }

        return sourceMap;
    }
}
