package db.dao;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import db.configuration.ConnectionConfiguration;
import model.UniqueWord;
import model.W2VToken;

import java.util.List;

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

    protected void prepareForInsert(){
        preparedStatement = session.prepare(
                "INSERT INTO w2v_token (token_name, is_stem, value) values (?, ?, ?)");
    }
}
