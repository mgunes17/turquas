package db.dao;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Result;
import db.accessor.W2VTokenAccessor;
import db.configuration.ConnectionConfiguration;
import db.configuration.MappingManagerConfiguration;
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
    private final static W2VTokenAccessor w2VTokenAccessor = MappingManagerConfiguration
            .getMappingManager()
            .createAccessor(W2VTokenAccessor.class);;

    public W2VTokenDAO() {
        session = ConnectionConfiguration.getSession();
    }

    public boolean updateTable(List<W2VToken> tokens) {
        String query = "TRUNCATE TABLE w2v_token";
        session.execute(query);

        try{
            BatchStatement batch = new BatchStatement();

            int count = 0;
            for(W2VToken w2VToken: tokens){
                count++;
                batch.add(w2VTokenAccessor.updateTable(w2VToken.getTokenName(), w2VToken.isStem(), w2VToken.getValue()));

                if(count % 10 == 0){
                    session.execute(batch);
                    batch = new BatchStatement();
                }
            }

            session.execute(batch);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public Map<String, W2VToken> getTokens(boolean isStem) {
        Map<String, W2VToken> w2VTokens = new HashMap<String, W2VToken>();
        Result<W2VToken> result = w2VTokenAccessor.getToken(isStem);

        for(W2VToken w2VToken: result) {
            w2VTokens.put(w2VToken.getTokenName(), w2VToken);
        }

        return w2VTokens;
    }
}
