package component.user_interface.w2vtoken;

import db.dao.W2VTokenDAO;
import model.W2VToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ercan on 18.05.2017.
 */
public class W2VTokenMap {
    private static Map<String, Map<String, W2VToken>> w2VTokens = null;
    private static W2VTokenMap w2VTokenMap = null;

    private W2VTokenMap(){
        W2VTokenDAO w2VTokenDAO = new W2VTokenDAO();
        w2VTokens = new HashMap<>();
        w2VTokens.put("stem", w2VTokenDAO.getTokens("stem"));
        w2VTokens.put("token", w2VTokenDAO.getTokens("token"));
        w2VTokens.put("letter", w2VTokenDAO.getTokens("letter"));
    }

    public static Map<String, Map<String, W2VToken>> getW2VTokenMap() {
        if(w2VTokens == null)
            w2VTokenMap = new W2VTokenMap();

        return w2VTokens;
    }

}
