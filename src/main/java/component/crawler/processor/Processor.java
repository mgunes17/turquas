package component.crawler.processor;

import component.crawler.DBSaver;
import component.crawler.content.ContentHandler;
import component.crawler.page.Page;
import component.crawler.processor.preprocess.PreprocessHandler;
import component.crawler.processor.preprocess.PreprocessedSentence;
import component.crawler.processor.validation.ValidationHandler;
import db.dao.SentenceDAO;
import model.Sentence;
import model.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by mustafa on 23.04.2017.
 */
public class Processor {
    private final static Logger logger = LoggerFactory.getLogger(Processor.class);
    private DBSaver dbSaver;
    private Queue<Source> dataToSaveQueue;
    private ContentHandler contentHandler;
    private ValidationHandler validationHandler;
    private Set<String> uniqueSentences = new HashSet<>();

    public Processor(DBSaver dbSaver) {
        this.dbSaver = dbSaver;
        dataToSaveQueue = new LinkedList<Source>();
        contentHandler = new ContentHandler();
        validationHandler = new ValidationHandler();
    }

    public void process(Queue<Page> pageQueue) {
        Page page;
        logger.trace("Processor running..");

        while((page = pageQueue.poll()) != null) {
            Source source = buildSource(page.getAddressName(), page.getContent());
            logger.trace("Processor " + source.getSourceName());

            if(isSourceWorthy(source)) { //doküman kayda değer mi ona bakılacak
                dataToSaveQueue.offer(source);
                logger.info("Processor kayda değer bulundu " + source.getSourceName());
            }
        }

        System.out.println("Veri işleme tamamlandı.");
        System.out.println("Veriler kaydediliyor.");
        dbSaver.save(dataToSaveQueue);
    }

    private boolean isSourceWorthy(Source source){
        int sentenceCount = source.getSentenceSet().size();

        return sentenceCount > 5;
    }

    private Source buildSource(String sourceName, String content){
        Source source = new Source(sourceName);
        List<String> sentences = contentHandler.getSentencesFromParagraph(content);
        sentences = cleanSentences(sentences);

        for(String sentence: sentences) {
            if(validationHandler.validate(sentence)){
                PreprocessHandler preprocessHandler = new PreprocessHandler();
                PreprocessedSentence preprocessedSentence = preprocessHandler.process(sentence);

                Sentence sentenceObject = buildSentenceObject(preprocessedSentence);
                sentenceObject.setSourceName(sourceName);
                source.getSentenceSet().add(sentenceObject);
                source.updateWordCountMap(preprocessedSentence.getWordCountMap());
            }
        }

        return source;
    }

    private List<String> cleanSentences(List<String> sentences){
        List<String> sentenceList = new ArrayList<>();
        SentenceDAO sentenceDAO = new SentenceDAO();
        for(String sentence: sentences){
            if(!uniqueSentences.contains(sentence) && !sentenceDAO.isExist(sentence)){
                uniqueSentences.add(sentence);
                sentenceList.add(sentence);
            }
        }

        return sentenceList;
    }


    private Sentence buildSentenceObject(PreprocessedSentence preprocessedSentence){
        Sentence newSentence = new Sentence(preprocessedSentence.getOriginalSentence());
        newSentence.setStemmedWordsList(preprocessedSentence.getStemList());
        newSentence.setTokenList(preprocessedSentence.getTokenList());
        // tags ata
        // questions ata

        return newSentence;
    }

    private boolean isSaveable(String content) {
        return false;
    }

    public Queue<Source> getDataToSaveQueue() {
        return dataToSaveQueue;
    }

    public void setDataToSaveQueue(Queue<Source> dataToSaveQueue) {
        this.dataToSaveQueue = dataToSaveQueue;
    }
}
