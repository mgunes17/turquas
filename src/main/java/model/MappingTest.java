package model;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import db.configuration.ConnectionConfiguration;
import db.configuration.ModelVariables;

/**
 * Created by ercan on 13.05.2017.
 */
public class MappingTest {

    public static void main(String[] args){
        Session session = ConnectionConfiguration.getSession();
        MappingManager manager = new MappingManager(session);
        Mapper<Question> questionMapper = manager.mapper(Question.class);
        Mapper<Sentence> sentenceMapper = manager.mapper(Sentence.class);
        Mapper<UniqueWord> uniqueWordMapper = manager.mapper(UniqueWord.class);
        Mapper<Source> sourceMapper = manager.mapper(Source.class);

        /*Question question = questionMapper.get("ercan", "yildiz");
        System.out.println(question.getQuestion() + " " + question.getSourceName());

        Sentence sentence = sentenceMapper.get("a", "b");
        System.out.println(sentence.getOriginalSentence() + " " + sentence.getSourceName());

        UniqueWord uniqueWord = uniqueWordMapper.get("baş");
        System.out.println(uniqueWord.getWord());

        Source source = sourceMapper.get("Kibritçi Kız.txt");
        System.out.println(source.getSourceName() + " " + source.getLastUpdatedDate());*/




    }
}
