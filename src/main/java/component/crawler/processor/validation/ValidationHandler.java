package component.crawler.processor.validation;

/**
 * Created by ercan on 08.04.2017.
 */
public class ValidationHandler {
    private Validator headValidator;

    public ValidationHandler() {
        LanguageValidator languageValidator = new LanguageValidator();
        QuestionableValidator questionableValidator = new QuestionableValidator();
        WordCountValidator wordCountValidator = new WordCountValidator();

        languageValidator.setNextValidator(questionableValidator);
        questionableValidator.setNextValidator(wordCountValidator);

        headValidator = languageValidator;
    }

    public boolean validate(String sentence) {
        return headValidator.validate(sentence);
    }
}
