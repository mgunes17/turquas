package component.crawler;

import admin.CrawlerAdmin;
import component.crawler.page.FilePage;
import component.crawler.page.Page;
import component.crawler.processor.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Created by mustafa on 10.05.2017.
 */
public class FileCrawler {
    private final static Logger logger = LoggerFactory.getLogger(FileCrawler.class);
    private Processor processor;
    private Queue<String> unvisitedFilePaths;
    private Queue<Page> pageQueue;
    private String filePath;

    public FileCrawler(Processor processor, String filePath){
        unvisitedFilePaths = new LinkedList<String>();
        pageQueue = new LinkedList<Page>();
        this.processor = processor;
        this.filePath = filePath;
    }

    public void crawl(){
        readFilePaths();
        FilePage filePage;
        String filePath = unvisitedFilePaths.poll();

        int count = 0;

        while (filePath != null) {
            try {
                filePage = new FilePage(filePath);
                createFilePageFromTxt(filePage);
                pageQueue.offer(filePage);

                System.out.println("Succesfully opened: " + filePath);
                count++;

                if(isSessionReady(count)){
                    handleSession(count);
                }

                filePath = unvisitedFilePaths.poll();
            } catch (Exception ex) {
                System.out.println(filePath + " " + ex.getMessage());
                ex.printStackTrace();
                logger.warn(ex.getMessage());
            }
        }

        System.out.println("Kuyrukta bekleyen dosya ismi yok.");
        System.out.println("Eğer okunmuş veri varsa kaydedilme işlemi başlıyor.");
        processor.process(pageQueue);
    }

    private void createFilePageFromTxt(FilePage filePage){
        try{
            String content = readContent(filePage.getAddressName());
            String header = getHeaderName(filePage.getAddressName());

            filePage.setContent(content);
            filePage.setSourceName(header);
        } catch(IOException ex){
            System.out.println("Bilgiler dosyadan okunamadı.");
            logger.warn(ex.getMessage());
            logger.warn(ex.toString());
        }
    }

    private String readContent(String addressName) throws IOException {
        File file = new File(addressName);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        return new String(data, "UTF-8");
    }

    private String getHeaderName(String filePath) throws IOException{
        String[] splitPath = filePath.split("/");

        String header = splitPath[splitPath.length - 1];
        header = header.substring(0, header.length() - 4);

        return header;
    }

    private void readFilePaths(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                unvisitedFilePaths.offer(line);
            }

            br.close();
        } catch(IOException ex){
            System.out.println("Bilgiler dosyadan okunamadı.");
            logger.warn(ex.getMessage());
            logger.warn(ex.toString());
        }
    }

    private void handleSession(int count){
        System.out.println("Seans başlıyor... " + count);
        processor.process(pageQueue);
        pageQueue.clear();
        System.out.println("Seans bitti... " + count);
    }

    private boolean isSessionReady(int count) {
        return count > 0 && count % CrawlerAdmin.crawlerParameterMap.get("session_size") == 0;
    }



}
