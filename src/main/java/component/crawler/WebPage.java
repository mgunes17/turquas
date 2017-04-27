package component.crawler;

import java.util.Set;

/**
 * Created by mustafa on 23.04.2017.
 */
public class WebPage {
    private String url;
    private String content;
    private Set<String> sentences;

    public WebPage(String url){
        this.url = url;
    }

    // getters - setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getSentences() {
        return sentences;
    }

    public void setSentences(Set<String> sentences) {
        this.sentences = sentences;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


