package component.crawler.content;

import java.io.IOException;

/**
 * Created by ercan on 23.03.2017.
 */
public interface Content {
    String fetchContent(String sourceName) throws  IOException;
}
