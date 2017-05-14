package component.crawler.processor.validation;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ercan on 14.05.2017.
 */
public class PunctuationValidatorTest {
    @Test
    public void validate() throws Exception {
        PunctuationValidator punctuationValidator = new PunctuationValidator();

        String s1 = "Ama Rapunzel’in bu yüksek kuleden kaçmasına imkan yokmuş.\n\nAkıllı kızın parlak bir fikri varmış.";
        String s2 = "Ben seni dünyanın kötülüklerinden korumaya çalışıyordum.";
        String s3 = "O anda sanki büyülenmiş ve o marullardan başka şey düşünemez olmuş.\n\n“Ya bu marullardan yerim ya da ölürüm” demiş kendi kendine.";
        String s4 = "Prens Rapunzel’e evlenme teklif etmiş, Rapunzel’de kabul etmiş, yüzü hafifce kızararak";
        String s5 = "adsfsdf sdfs fds fsd f.";
        String s6 = "Bsdfsdf fdsf sd!";
        String s7 = "Basdfdsf dfd fd";
        String s8 = "Bqwew qewwqe eqw eqw?===?=?.";
        String s9 = "Ercan sfdsf sd fd https://translate.google.com.tr/#tr/en/url%20var%20m%C4%B1 sdfds.";

        assertEquals(false, punctuationValidator.validate(s1));
        assertEquals(true, punctuationValidator.validate(s2));
        assertEquals(false, punctuationValidator.validate(s3));
        assertEquals(false, punctuationValidator.validate(s4));
        assertEquals(false, punctuationValidator.validate(s5));
        assertEquals(false, punctuationValidator.validate(s6));
        assertEquals(false, punctuationValidator.validate(s7));
        assertEquals(true, punctuationValidator.validate(s8));
        assertEquals(false, punctuationValidator.validate(s9));
    }

}