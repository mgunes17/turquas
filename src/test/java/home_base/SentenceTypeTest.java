package home_base;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mustafa on 21.05.2017.
 */
public class SentenceTypeTest {
    private List<String> nounSentences;
    private List<String> verbSentences;
    private SentenceType sentenceType;

    @Before
    public void setup() {
        sentenceType = new SentenceType();

        nounSentences = new ArrayList<>();
        nounSentences.add("Bugün hava çok sıcak .");
        nounSentences.add("Bugün hava çok sıcaktır .");
        nounSentences.add("Türkiye'nin başkenti Ankara'dır");
        nounSentences.add("Türkiyenin başkenti neresidir");
        nounSentences.add("En sevdiğin renk hangisi");
        nounSentences.add("En sevdiğin renk hangisidir");

        verbSentences = new ArrayList<>();
        verbSentences.add("Buraya ne zaman geldin");
        verbSentences.add("Hangi kalemi kullanacaksın ?");
        verbSentences.add("Masa daha dün kırıldı");
    }

    @Test
    public void isNounClause() throws Exception {
        for(String sentence: nounSentences) {
            assertEquals(true, sentenceType.isNounClause(sentence));
        }

        for(String sentence: verbSentences) {
            assertEquals(false, sentenceType.isNounClause(sentence));
        }
    }

}