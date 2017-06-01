package db.dao;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
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
            deleteUnLabeledToken(tokenSet);
            for(Token token: tokenSet){
                Statement statement = tokenAccessor.saveTMA(token.getToken(), token.getAnalysisSet());
                session.execute(statement);
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }

    public boolean deleteUnLabeledToken(Set<Token> tokenSet) {
        try{
            for(Token token: tokenSet){
                Statement statement = tokenAccessor.deleteTMA(token.getToken());
                session.execute(statement);
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }
}
