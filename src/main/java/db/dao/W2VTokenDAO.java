package db.dao;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
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
    private final static W2VTokenAccessor w2VTokenAccessor = MappingManagerConfiguration
            .getMappingManager()
            .createAccessor(W2VTokenAccessor.class);

    public W2VTokenDAO() {
        session = ConnectionConfiguration.getSession();
    }

    public boolean updateTable(List<W2VToken> tokens) {
        /*String query = "DELETE FROM w2v_token WHERE type = '" + tokens.get(0).getType() + "';";
        session.execute(query);

        return insertToTable(tokens);*/
        System.out.println("güncellenmemesi gerek");
        return false;
    }

    public boolean insertToTable(List<W2VToken> tokens){
        try{
            for(W2VToken w2VToken: tokens){
                Statement statement = w2VTokenAccessor.insert(w2VToken.getTokenName(), w2VToken.getType(), w2VToken.getValue());
                session.execute(statement);
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public Map<String, W2VToken> getTokens(String type) {
        Map<String, W2VToken> w2VTokens = new HashMap<String, W2VToken>();
        Result<W2VToken> result = w2VTokenAccessor.getToken(type);

        for(W2VToken w2VToken: result) {
            w2VTokens.put(w2VToken.getTokenName(), w2VToken);
        }

        return w2VTokens;
    }

    public Map<String, W2VToken> getW2vTokenForWordsForType(String type, List<String> words){
        Map<String, W2VToken> w2VTokens = new HashMap<String, W2VToken>();
        Result<W2VToken> result = w2VTokenAccessor.getW2VTokensByType(words, type);

        for(W2VToken w2VToken: result) {
            w2VTokens.put(w2VToken.getTokenName(), w2VToken);
        }

        return w2VTokens;
    }

    public Map<String, Map<String, W2VToken>> getW2vTokenForWordsForAllTypes(List<String> words){
        Map<String, Map<String, W2VToken>> w2VTokens = new HashMap<>();
        Result<W2VToken> result = w2VTokenAccessor.getW2VTokensForAllTypes(words);
        w2VTokens.put("stem", new HashMap<>());
        w2VTokens.put("letter", new HashMap<>());
        w2VTokens.put("token", new HashMap<>());

        for(W2VToken w2VToken: result) {
            w2VTokens.get(w2VToken.getType()).put(w2VToken.getTokenName(), w2VToken);
        }

        return w2VTokens;
    }

}
