package component.w2v_creator.named_entity;

import nlp_tool.itu.ParsedWord;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by mustafa on 21.05.2017.
 */
public class PreparingForNamedEntityTest {
    private PreparingForNamedEntity preparing;
    private List<ParsedWord> parsedWordList;
    private List<String> sentenceList;

    @Before
    public void setUp() throws Exception {
        preparing = new PreparingForNamedEntity();
        parsedWordList = new ArrayList<>();
        parsedWordList.add(new ParsedWord("Mustafa"));
        parsedWordList.add(new ParsedWord("Güneş"));
        parsedWordList.add(new ParsedWord("dün"));
        parsedWordList.add(new ParsedWord("İzmir'den"));
        parsedWordList.add(new ParsedWord("geldi"));

        sentenceList = new ArrayList<>();
        sentenceList.add("Mustafa Güneş dün İzmir'den geldi .");
    }

    @Test
    public void subtractNamedEntity() throws Exception {
        String sentence1 = preparing.subtractNamedEntity(parsedWordList, 0, 1);
        String sentence2 = preparing.subtractNamedEntity(parsedWordList, 3, 3);
        assertEquals("dün İzmir'den geldi", sentence1);
        assertEquals("Mustafa Güneş dün geldi", sentence2);
    }

    @Test
    public void sumNamedEntity() throws Exception {
        String ne1 = preparing.sumNamedEntity(parsedWordList, 0, 1);
        String ne2 = preparing.sumNamedEntity(parsedWordList, 3, 3);
        assertEquals("Mustafa Güneş", ne1);
        assertEquals("İzmir'den", ne2);
    }

    @Test
    public void extractNE() throws Exception {
        Map<String, String> pair = preparing.extractNE(sentenceList);

        assertEquals(true, pair.containsKey("dün İzmir'den geldi"));
        assertEquals(true, pair.containsKey("Mustafa Güneş dün geldi"));
        assertEquals("Mustafa Güneş", pair.get("dün İzmir'den geldi"));
        assertEquals("İzmir'den", pair.get("Mustafa Güneş dün geldi"));
    }
}