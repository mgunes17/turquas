package home_base;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mustafa on 05.06.2017.
 */
public class TurquasTest {
    @Test
    public void findQuestionType() throws Exception {
        String s1 = "nereden geldiniz";
        String s2 = "toplantı ne zaman olacak";
        String s3 = "saat 4 olmuş";

        assertEquals("abl", Turquas.findQuestionType(s1));
        assertEquals("time", Turquas.findQuestionType(s2));
        assertEquals("inst", Turquas.findQuestionType(s3));
    }

}