package command.crawler_command;

import admin.CrawlerAdmin;
import command.AbstractCommand;
import command.Command;
import component.crawler.FileCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by mustafa on 10.05.2017.
 */
public class ReadCommand extends AbstractCommand implements Command {
    private final static Logger logger = LoggerFactory.getLogger(ReadCommand.class);
    public boolean execute(String[] parameter) {
        try {
            String filePath = parameter[1];
            FileCrawler fileCrawler = new FileCrawler(CrawlerAdmin.processor, filePath);
            fileCrawler.crawl();

            return true;
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            logger.warn(ex.getMessage());
            logger.warn(ex.toString());
            return false;
        }
    }

    protected boolean validateParameter(String[] parameter) {
        return parameter.length == 2;
    }
}
