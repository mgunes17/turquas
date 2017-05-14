package component.crawler.processor.validation;

/**
 * Created by ercan on 14.05.2017.
 */
public class PunctuationValidator extends Validator {
    @Override
    public boolean validate(String sentence) {
        boolean isValid;
        isValid = isSentenceAcceptable(sentence) && isPunctuationOkay(sentence) && !hasUrl(sentence);

        return isValid(isValid, sentence);
    }

    private boolean isSentenceAcceptable(String sentence){
        return (Character.isUpperCase(sentence.charAt(0)) || Character.isDigit(sentence.charAt(0)))
                && sentence.charAt(sentence.length() - 1) == '.';
    }

    private boolean isPunctuationOkay(String sentence){
        return !(sentence.contains("\"") || sentence.contains("“") || sentence.contains(";")
                || sentence.contains(":") || sentence.contains("`")
                || sentence.contains("´") || sentence.contains("”")
                || sentence.contains("‘") || sentence.contains("'")
                || sentence.contains("\n"));
    }

    private boolean hasUrl(String sentence){
        return sentence.contains("http://") || sentence.contains("https://")
                || sentence.contains(".com") || sentence.contains(".org")
                || sentence.contains(".net") || sentence.contains("www.");
    }
}
