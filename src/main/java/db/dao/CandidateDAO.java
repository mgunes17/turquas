package db.dao;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Result;
import db.accessor.SentenceAccessor;
import db.configuration.ConnectionConfiguration;
import db.configuration.MappingManagerConfiguration;
import model.Sentence;
import model.UniqueWord;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by mustafa on 11.05.2017.
 */
public class CandidateDAO {
    private Session session;
    private static final SentenceAccessor sentenceAccessor = MappingManagerConfiguration
            .getMappingManager()
            .createAccessor(SentenceAccessor.class);

    public CandidateDAO() {
        session = ConnectionConfiguration.getSession();
    }

    public List<Sentence> getSentences(String[] words) {
        List<Set<String>> sourceList = findSources(words);

        return findSentences(sourceList);
    }

    private List<Sentence> findSentences(List<Set<String>> sourceList){
        List<Sentence> sentenceList = new ArrayList<>();
        List<String> sourceNames = new ArrayList<>();

        for(Set<String> sourceSet: sourceList) {
            sourceNames.addAll(sourceSet);
        }

        Result<Sentence> result = sentenceAccessor.getSentencesWithInClause(sourceNames);
        for(Sentence sentence: result) {
            sentenceList.add(sentence);
        }

        return sentenceList;
    }

    private List<Set<String>> findSources(String[] words){
        List<Set<String>> sourceList = new ArrayList<>();

        for(String word: words) {
            UniqueWord uniqueWord = MappingManagerConfiguration.getUniqueWordMapper().get(word);
            sourceList.add(uniqueWord.getDocumentSet());
        }

        return sourceList;
    }
}
