package component.crawler.processor.validation;

/**
 * Created by ercan on 08.04.2017.
 */
public class QuestionableValidator extends Validator{

    public boolean validate(String sentence) {
        boolean isValid = false;

        if(!sentence.contains("?")){
            isValid = true;
        }

        if(isValid) {
            //System.out.println("questionable validation passed.");
            if(getNextValidator() != null) {
                return getNextValidator().validate(sentence);
            }
            else {
                return true;
            }
        } else {
            return false;
        }
    }
}
