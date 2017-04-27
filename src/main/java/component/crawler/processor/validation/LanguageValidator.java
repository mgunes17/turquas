package component.crawler.processor.validation;

import zemberek.langid.LanguageIdentifier;

import java.io.IOException;

/**
 * Created by ercan on 08.04.2017.
 */
public class LanguageValidator extends Validator {

    public boolean validate(String sentence) {
        boolean isValid = false;

        try {
            LanguageIdentifier languageIdentifier  = LanguageIdentifier.fromInternalModelGroup("tr_group");
            if(languageIdentifier.identify(sentence).equals("tr")){
                isValid = true;
            }
        } catch (IOException e) {
            // couldnt load models error
            e.printStackTrace();
        }

        if(isValid) {
            //System.out.println("language validation passed.");
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
