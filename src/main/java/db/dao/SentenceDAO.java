package db.dao;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import db.accessor.SentenceAccessor;
import db.configuration.ConnectionConfiguration;
import db.configuration.MappingManagerConfiguration;
import model.Sentence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ercan on 09.04.2017.
 */
public class SentenceDAO {
    private Session session;
    private static final SentenceAccessor sentenceAccessor = MappingManagerConfiguration
            .getMappingManager()
            .createAccessor(SentenceAccessor.class);

    public SentenceDAO(){
        session = ConnectionConfiguration.getSession();
    }

    public List<Sentence> readForGenerateQuestions(int count) {
        List<Sentence> sentenceList = new ArrayList<>();
        Result<Sentence> result = sentenceAccessor.getSentencesByLimit(count);
        for(Sentence sentence: result) {
            sentenceList.add(sentence);
        }

        return sentenceList;
    }

    public List<Sentence> getAllSentences() {
        Result<Sentence> result = sentenceAccessor.getAll();
        return result.all();
    }

    public List<String> getAllSentencesOnly() {
        Result<Sentence> result = sentenceAccessor.getAllOnly();
        List<String> sentences = new ArrayList<>();
        for(Sentence sentence: result){
            sentences.add(sentence.getOriginalSentence());
        }

        return sentences;
    }

    public List<String> getSentencesOnlyWithLimit(int limit) {
        Result<Sentence> result = sentenceAccessor.getOnlySentenceWithLimit(limit);
        List<String> sentences = new ArrayList<>();
        for(Sentence sentence: result){
            sentences.add(sentence.getOriginalSentence());
        }

        return sentences;
    }

    public boolean isExist(String sentence){
        Result<Sentence> result = sentenceAccessor.getSentence(sentence);

        return result.all().size() != 0;
    }

    /*public void updateQuestions(List<Sentence> sentenceList){
        try {
            BatchStatement batch = new BatchStatement();
            for(Sentence sentence: sentenceList) {
                Statement statement = sentenceAccessor.updateQuestions(
                        sentence.getSourceName(),
                        sentence.getOriginalSentence());
                batch.add(statement);
            }

            session.execute(batch);
            System.out.println(("SentenceDAO updateQuestions başarıyla tamamlandı."));
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }*/

    public void insertList(List<Sentence> sentenceList){
        try{
            for(Sentence sentence: sentenceList){
                Statement statement = sentenceAccessor.insertBatch(sentence.getOriginalSentence(),
                        sentence.getSourceName(), sentence.getStemmedWordsList(), sentence.getTags(), sentence.getTokenList());
                session.execute(statement);
            }
            System.out.println("SentenceDAO insertBatch başarıyla tamamlandı.");
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            System.out.println("SentenceDAO insertBatch hata verdi.");
        }

    }
}
