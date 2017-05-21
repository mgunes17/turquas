package db.dao;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import db.accessor.UniqueWordAccessor;
import db.configuration.ConnectionConfiguration;
import db.configuration.MappingManagerConfiguration;
import db.configuration.ModelVariables;
import model.UniqueWord;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mustafa on 26.04.2017.
 */
public class UniqueWordDAO {
    private Session session;
    private final static UniqueWordAccessor uniqueWordAccessor = MappingManagerConfiguration
            .getMappingManager()
            .createAccessor(UniqueWordAccessor.class);

    public UniqueWordDAO(){
        session = ConnectionConfiguration.getSession();
    }

    public Set<UniqueWord> getAllWords() {
        Result<UniqueWord> result = uniqueWordAccessor.getAllWords();
        Set<UniqueWord> uniqueWordSet = new HashSet<UniqueWord>();

        for(UniqueWord uniqueWord: result) {
            uniqueWordSet.add(uniqueWord);
        }

        return uniqueWordSet;
    }

    public boolean update(Set<UniqueWord> uniqueWordSet) {
        try{
            for(UniqueWord uniqueWord: uniqueWordSet){
                Statement statement = uniqueWordAccessor.update(uniqueWord.getValueMap(), uniqueWord.getWord());
                session.execute(statement);
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }

    public boolean updateSources(Set<UniqueWord> uniqueWordSet) {
        try{
            for(UniqueWord uniqueWord: uniqueWordSet){
                Statement statement = uniqueWordAccessor.updateSources(uniqueWord.getDocumentSet(), uniqueWord.getWord());
                session.execute(statement);
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
