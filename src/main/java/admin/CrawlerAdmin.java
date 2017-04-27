package admin;

import command.CommandSet;
import component.crawler.DBSaver;
import component.crawler.WebCrawler;
import component.crawler.processor.Processor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mustafa on 27.04.2017.
 */
public class CrawlerAdmin extends Admin {
    public static WebCrawler crawler;
    public static Processor processor;
    public static DBSaver dbSaver;
    public static Map<String, Integer> crawlerParameterMap;

    static {
        dbSaver = new DBSaver();
        processor = new Processor(dbSaver);
        crawler = new WebCrawler(processor);
        crawlerParameterMap = new HashMap<String, Integer>();
        crawlerParameterMap.put("session_size", 5);
        //ilklendirmeleri yap
        //global parameters ı oluştur
    }

    public CrawlerAdmin(CommandSet commandSet) {
        super(commandSet);
    }

}
