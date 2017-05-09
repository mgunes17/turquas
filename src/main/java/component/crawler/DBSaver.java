package component.crawler;

import db.dao.SentenceDAO;
import db.dao.SourceDAO;
import model.Sentence;
import model.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by mustafa on 23.04.2017.
 */
public class DBSaver {
    private final static Logger logger = LoggerFactory.getLogger(DBSaver.class);
    private final static int batchSize = 20;
    private SentenceDAO sentenceDAO;
    private SourceDAO sourceDAO;

    public DBSaver() {
        sentenceDAO = new SentenceDAO();
        sourceDAO = new SourceDAO();
    }

    public void save(Queue<Source> dataToSaveQueue) {
        logger.trace("Running");

        List<Source> sourceBatchList = new ArrayList<Source>();
        for(int i = 0; i < batchSize; i++) {
            Source source = dataToSaveQueue.poll();
            if(source != null){
                List<Sentence> sentenceBatchList = new ArrayList<Sentence>();
                sentenceBatchList.addAll(source.getSentenceSet());
                sentenceDAO.insertBatch(sentenceBatchList);
                sourceBatchList.add(source);
            }
        }
        sourceDAO.insertBatch(sourceBatchList);

        //Sentence sentence = (Sentence) dataToSaveQueue.poll().getSentenceSet().toArray()[0];
        //sentenceDAO.insert(sentence);
        //logger.trace("execute batch çağırıldı");
        //sentenceDAO.executeBatch(batch);
    }
}
