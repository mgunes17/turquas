package db.dao;

import com.datastax.driver.core.*;
import db.configuration.ConnectionConfiguration;
import model.W2VToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.05.2017.
 */
public class W2VTokenDAO {
    private Session session;
    private PreparedStatement preparedStatement;


    public boolean updateTable(List<W2VToken> tokens) {
        String query = "TRUNCATE TABLE w2v_token";
        session = ConnectionConfiguration.getCLuster().connect("turquas");
        session.execute(query);

        try{
            BatchStatement batch = new BatchStatement();
            prepareForInsert();

            for(W2VToken w2VToken: tokens){
                BoundStatement bound = preparedStatement.bind(
                        w2VToken.getTokenName(), w2VToken.isStem(), w2VToken.getValue());
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

    public Map<String, W2VToken> getTokens(boolean isStem) {
        Map<String, W2VToken> w2VTokens = new HashMap<String, W2VToken>();
        String query = "select token_name, value from w2v_token WHERE is_stem = " + isStem + " ;";
        session = ConnectionConfiguration.getCLuster().connect("turquas");
        ResultSet result = session.execute(query);

        for(Row row: result) {
            w2VTokens.put(row.getString(0), new W2VToken(row.getString(0), row.getList(1, Float.class)));
        }

        return w2VTokens;
    }

    protected void prepareForInsert(){
        preparedStatement = session.prepare(
                "INSERT INTO w2v_token (token_name, is_stem, value) values (?, ?, ?)");
    }
}
