package component.crawler.page;

import java.util.Set;

/**
 * Created by mustafa on 10.05.2017.
 */
public abstract class Page {
    private String content;
    private Set<String> sentences;
    private String sourceName;

    public abstract String getAddressName();

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<String> getSentences() {
        return sentences;
    }

    public void setSentences(Set<String> sentences) {
        this.sentences = sentences;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
