package component.crawler.processor.validation;

/**
 * Created by ercan on 09.04.2017.
 */
public abstract class Validator {
    private Validator nextValidator;

    abstract public boolean validate(String sentence);

    boolean isValid(boolean valid, String sentence) {
        //System.out.println("word count validation passed.");
        return valid && (getNextValidator() == null || getNextValidator().validate(sentence));
    }

    Validator() {
        //non-arg
    }

    private Validator getNextValidator() {
        return nextValidator;
    }

    void setNextValidator(Validator nextValidator) {
        this.nextValidator = nextValidator;
    }
}
