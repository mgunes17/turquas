package db.dao;

import com.datastax.driver.core.*;
import db.CassandraConfiguration;
import model.Token;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mustafa on 26.04.2017.
 */
public class TokenDAO {
    private PreparedStatement preparedStatement;
    private Session session;

    public Set<Token> getUnlabeledToken(int count) {
        String query = "SELECT token_name FROM token_morph_analysis WHERE isanalysisnull = true LIMIT " + count;
        session = CassandraConfiguration.getCLuster().connect("turquas");
        ResultSet result = session.execute(query);
        Set<Token> tokenSet = new HashSet<Token>();

        for(Row row: result) {
            Token token = new Token(row.get(0, String.class));
            tokenSet.add(token);
        }

        session.close();
        return tokenSet;
    }

    public boolean saveLabeledToken(Set<Token> tokenSet) {
        try{
            BatchStatement batch = new BatchStatement();
            prepareForInsert();
            session = CassandraConfiguration.getCLuster().connect("turquas");


            for(Token token: tokenSet){
                BoundStatement bound = preparedStatement.bind( token.getToken(), token.getAnalysisSet());
                batch.add(bound);
            }

            session.execute(batch);
            deleteUnLabeledToken(tokenSet);
            session.close();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }

    public boolean deleteUnLabeledToken(Set<Token> tokenSet) {
        try{
            BatchStatement batch = new BatchStatement();
            prepareForDelete();

            for(Token token: tokenSet){
                BoundStatement bound = preparedStatement.bind(token.getToken());
                batch.add(bound);
            }

            session.execute(batch);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }


    public void prepareForInsert(){
        preparedStatement = session.prepare(
                "INSERT INTO token_morph_analysis (isanalysisnull, token_name, analysis) values (false, ?, ?)");
    }

    public void prepareForDelete(){
        preparedStatement = session.prepare(
                "DELETE FROM token_morph_analysis WHERE isanalysisnull = true and token_name = ?");
    }

}
