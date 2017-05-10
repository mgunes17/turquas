package component.crawler.processor.validation;

import admin.CrawlerAdmin;

/**
 * Created by ercan on 09.04.2017.
 */
public class WordCountValidator extends Validator {
    private final int MIN_WORD_COUNT = 5;

    public boolean validate(String sentence) {
        boolean isValid = false;

        String [] words = sentence.split(" ");
        if(words.length >= MIN_WORD_COUNT && words.length <= CrawlerAdmin.crawlerParameterMap.get("max_word_size")){
            isValid = true;
        }

        return isValid(isValid, sentence);
    }
}
