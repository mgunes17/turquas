package nlp_tool.itu;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mustafa on 21.05.2017.
 */
public class ItuNlpServiceTest {
    private List<ParsedWord> parsedWords;

    @Before
    public void setUp() throws Exception {
        ItuNlpService service = new ItuNlpService();
        String sentence = "Mustafa Güneş dün İzmir'den geldi .";
        parsedWords = service.getNameEntities(sentence);
    }

    @Test
    public void getNameEntities() throws Exception {
        assertEquals("B-PERSON", parsedWords.get(0).getNer());
        assertEquals("I-PERSON", parsedWords.get(1).getNer());
        assertEquals("O", parsedWords.get(2).getNer());
        assertEquals("B-LOCATION", parsedWords.get(3).getNer());
        assertEquals("O", parsedWords.get(4).getNer());
    }

}