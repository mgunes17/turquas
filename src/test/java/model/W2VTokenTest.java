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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by mustafa on 13.05.2017.
 */
public class W2VTokenTest {
    private Session session;
    private Mapper<W2VToken> tokenMapper;
    private W2VToken token;
    private W2VToken token2;

    @Before
    public void setup() {
        session = ConnectionConfiguration.getSession();
        MappingManager manager = new MappingManager(
                session);
        tokenMapper = manager.mapper(W2VToken.class);
    }

    @Test
    public void testColumns() {
        token = new W2VToken();
        token.setTokenName("deneme token");
        token.setStem(true);
        List<Double> list = new ArrayList<>();
        list.add(17.0);
        token.setValue(list);

        token2 = new W2VToken();
        token2.setTokenName("testt");
        token2.setStem(false);

        tokenMapper.save(token);
        tokenMapper.save(token2);

        ResultSet resultSet = session.execute("SELECT * FROM " + ModelVariables.W2V_TOKEN_TABLE_NAME);
        int listSize = resultSet.all().size();

        assertEquals(2, listSize);
    }

    @After
    public void deleteTestValues() {
        tokenMapper.delete(token);
        tokenMapper.delete(token2);
    }
}