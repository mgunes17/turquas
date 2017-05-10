package component.crawler.processor.validation;

/**
 * Created by ercan on 09.04.2017.
 */
public class WordCountValidator extends Validator {
    private final int MIN_WORD_COUNT = 5;
    private final int MAX_WORD_COUNT = 20;

    public boolean validate(String sentence) {
        boolean isValid = false;

        String [] words = sentence.split(" ");
        if(words.length >= MIN_WORD_COUNT && words.length <= MAX_WORD_COUNT){
            isValid = true;
        }

        return isValid(isValid, sentence);
    }
}
