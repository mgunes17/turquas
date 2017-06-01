package component.crawler;

import admin.CrawlerAdmin;
import component.crawler.content.UrlContent;
import component.crawler.page.Page;
import component.crawler.page.WebPage;
import component.crawler.processor.Processor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by mustafa on 23.04.2017.
 */
public class WebCrawler {
    private final static Logger logger = LoggerFactory.getLogger(WebCrawler.class);
    private Queue<String> unvisitedPageUrls;
    private Queue<Page> pageQueue;
    private UrlContent urlContent;
    private Processor processor;
    private Set<String> visitedUrls;

    public WebCrawler(Processor processor){
        unvisitedPageUrls = new LinkedList<String>();
        pageQueue = new LinkedList<Page>();
        urlContent = new UrlContent();
        visitedUrls = new HashSet<String>();
        this.processor = processor;
    }

    public void crawl(int count) {
        WebPage webPage;
        String url = unvisitedPageUrls.poll();
        visitedUrls.add(url);
        int i = 0;

        while (i < count && url != null) { //count, toplam deneme sayısı? toplam başarılı deneme sayısı?
            try {
                webPage = new WebPage(url);
                webPage.setContent(urlContent.fetchContent(webPage.getUrl()));

                if (checkAcceptanceOfDocument(urlContent.getDocument(), url)) {
                    pageQueue.offer(webPage);
                    updateUnvisitedPageUrls();

                    System.out.println("Succesfully connected to: " + url);
                    //logger.info("Succesfully connected to: " + url);
                    i++;

                    if(isSessionReady(i)){
                        System.out.println("Seans başlıyor... " + i);
                        processor.process(pageQueue);
                        pageQueue.clear();
                        System.out.println("Seans bitti... " + i);
                    }
                }
                url = unvisitedPageUrls.poll();
            } catch (Exception ex) {
                System.out.println(url + " " + ex.getMessage());
                ex.printStackTrace();
                url = unvisitedPageUrls.poll();
                logger.warn(ex.getMessage());
            }
        }

        System.out.println("Kuyrukta bekleyen url yok.");
        System.out.println("Veri indirme işlemi tamamlandı.");
        System.out.println("Veriler kayda hazırlanıyor.");
        processor.process(pageQueue);
    }

    private boolean isSessionReady(int count) {
        return count > 0 && count % CrawlerAdmin.crawlerParameterMap.get("session_size") == 0;
    }

    private boolean checkAcceptanceOfDocument(Document document, String url) {
        Element htmlTag = document.select("html").first();
        String langAttribute = htmlTag.attr("lang");

        return langAttribute != null &&
                (url.contains(".tr") || langAttribute.equals("tr-TR") || langAttribute.equals("tr"));
    }

    private void updateUnvisitedPageUrls() {
        List<String> linksOnPage = urlContent.extractLinks();
        for(String link: linksOnPage){
            if(!visitedUrls.contains(link)){
                visitedUrls.add(link);

                if(isAcceptable(link) && !unvisitedPageUrls.contains(link)) {
                    unvisitedPageUrls.add(link);
                }
            }
        }
    }

    private boolean isAcceptable(String url) {
        return !url.equals("") && !url.contains("#") && !url.contains("action=edit") && !url.contains("action=history")
                && !url.contains("veaction=edit") && !url.contains(".jpg") && !url.contains(".png");
    }

    // getters and setters
    public void addUrl(String url) {
        unvisitedPageUrls.add(url);
    }

    public Queue<String> getUnvisitedPageUrls() {
        return unvisitedPageUrls;
    }
}
