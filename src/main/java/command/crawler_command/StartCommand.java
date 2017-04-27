package command.crawler_command;

import admin.CrawlerAdmin;
import command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mustafa on 23.04.2017.
 */
public class StartCommand implements Command {
    private final static Logger logger = LoggerFactory.getLogger(CrawlerAdmin.class);

    public boolean execute(String[] parameter) {
        try {
            int urlCount = Integer.parseInt(parameter[1]); // madem limit var, url kuyruğunun boyutunu baştan belirle,
                                                        // ya da bunu array ile yap
                                                        // url sayısı ile birlikte zaman sınırlaması da konabilir

            if(CrawlerAdmin.crawler.getUnvisitedPageUrls().size() == 0) {
                System.out.println("Lütfen önce seed url girin.");
                return false;
            }
            CrawlerAdmin.crawler.crawl(urlCount);
            return true;
        } catch(NumberFormatException ex){
            System.out.println("Lütfen start ile birlikte sayısal bir değer girin."); // parametre kontrolünü ayrı metotta yap
            logger.warn(ex.getMessage());
            logger.warn(ex.toString());
            return false;
        }
        catch (Exception ex) { //string to int exceptionu olarak düzelt
            logger.warn(ex.getMessage());
            logger.warn(ex.toString());
            ex.printStackTrace();
            return false;
        }
    }
}
