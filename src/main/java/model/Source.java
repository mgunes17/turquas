package model;

import java.util.Map;

/**
 * Created by mustafa on 26.04.2017.
 */
public class Source {
    private String name;
    private Map<String, Integer> wordCount;

    public Source(String name, Map<String, Integer> wordCount) {
        this.name = name;
        this.wordCount = wordCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getWordCount() {
        return wordCount;
    }

    public void setWordCount(Map<String, Integer> wordCount) {
        this.wordCount = wordCount;
    }
}
