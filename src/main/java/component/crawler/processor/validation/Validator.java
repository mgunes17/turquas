package component.crawler.processor.validation;

/**
 * Created by ercan on 09.04.2017.
 */
public abstract class Validator {
    private Validator nextValidator;
    abstract public boolean validate(String sentence);

    public Validator(Validator nextValidator) {
        this.nextValidator = nextValidator;
    }

    public Validator() {
        //non-arg
    }

    public Validator getNextValidator() {
        return nextValidator;
    }

    public void setNextValidator(Validator nextValidator) {
        this.nextValidator = nextValidator;
    }
}
