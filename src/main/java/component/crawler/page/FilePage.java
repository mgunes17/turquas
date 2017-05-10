package component.crawler.page;

/**
 * Created by mustafa on 10.05.2017.
 */
public class FilePage extends Page {
    private String filePath;

    public FilePage(String filePath){
        this.filePath = filePath;
    }

    public String getAddressName() {
        return filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
