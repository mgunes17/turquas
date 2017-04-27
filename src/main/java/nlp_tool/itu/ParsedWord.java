package nlp_tool.itu;

/**
 * Created by mustafa on 09.04.2017.
 */
public class ParsedWord {
    private String word;
    private String wordPOS;
    private String ner;

    public ParsedWord() {
        //non-args
    }

    //getter - setter

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordPOS() {
        return wordPOS;
    }

    public void setWordPOS(String wordPOS) {
        this.wordPOS = wordPOS;
    }

    public String getNer() {
        return ner;
    }

    public void setNer(String ner) {
        this.ner = ner;
    }
}
