package component.crawler.page;

/**
 * Created by mustafa on 23.04.2017.
 */
public class WebPage extends Page {
    private String url;

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

    public String getAddressName() {
        return url;
    }
}


