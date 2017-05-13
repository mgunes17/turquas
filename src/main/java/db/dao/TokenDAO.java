package db.dao;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Result;
import db.accessor.TokenAccessor;
import db.configuration.ConnectionConfiguration;
import db.configuration.MappingManagerConfiguration;
import model.Token;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mustafa on 26.04.2017.
 */
public class TokenDAO {
    private Session session;
    private final static TokenAccessor tokenAccessor = MappingManagerConfiguration
            .getMappingManager()
            .createAccessor(TokenAccessor.class);


    public TokenDAO() {
        session = ConnectionConfiguration.getSession();
    }

    public Set<Token> getUnlabeledToken(int count) {
        Result<Token> result = tokenAccessor.getUnlabeledToken(count);
        Set<Token> tokenSet = new HashSet<>();

        for(Token token: result) {
            tokenSet.add(token);
        }

        return tokenSet;
    }

    public boolean saveLabeledToken(Set<Token> tokenSet) {
        try{
            BatchStatement batch = new BatchStatement();

            for(Token token: tokenSet){
                batch.add(tokenAccessor.saveTMA(token.getToken(), token.getAnalysisSet()));
            }

            session.execute(batch);
            deleteUnLabeledToken(tokenSet);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }

    public boolean deleteUnLabeledToken(Set<Token> tokenSet) {
        try{
            BatchStatement batch = new BatchStatement();

            for(Token token: tokenSet){
                batch.add(tokenAccessor.deleteTMA(token.getToken()));
            }

            session.execute(batch);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }
}
