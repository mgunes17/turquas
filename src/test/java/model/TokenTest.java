package model;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import db.configuration.ConnectionConfiguration;
import db.configuration.ModelVariables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by mustafa on 13.05.2017.
 */
public class TokenTest {
    private Session session;
    private Mapper<Token> tokenMapper;
    private Token token;
    private Token token2;

    @Before
    public void setup() {
        session = ConnectionConfiguration.getSession();
        MappingManager manager = new MappingManager(session);
        tokenMapper = manager.mapper(Token.class);
    }

    @Test
    public void testColumns() {
        token = new Token();
        token.setToken("deneme token");
        Set<String> analysisSet = new HashSet<>();
        analysisSet.add("deneme 1");
        analysisSet.add("deneme 2");
        token.setAnalysisSet(analysisSet);
        token.setAnalysisNull(false);

        token2 = new Token();
        token2.setToken("testt");
        token2.setAnalysisNull(true);

        tokenMapper.save(token);
        tokenMapper.save(token2);

        ResultSet resultSet = session.execute("SELECT * FROM " + ModelVariables.TOKEN_MORPH_ANALYSIS_TABLE_NAME);
        int listSize = resultSet.all().size();

        assertEquals(2, listSize);
    }

    @After
    public void deleteTestValues() {
        tokenMapper.delete(token);
        tokenMapper.delete(token2);
    }
}