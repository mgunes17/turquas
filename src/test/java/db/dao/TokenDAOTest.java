package db.dao;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mustafa on 13.05.2017.
 */
public class TokenDAOTest {
    @Test
    public void getUnlabeledToken() throws Exception {
        TokenDAO tokenDAO = new TokenDAO();
        assertEquals(1, tokenDAO.getUnlabeledToken(1).size());
    }

}