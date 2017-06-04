package db.dao;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Result;
import db.accessor.QuestionAccessor;
import db.configuration.ConnectionConfiguration;
import db.configuration.MappingManagerConfiguration;
import model.Question;
import model.UniqueWord;

import java.util.*;

/**
 * Created by mustafa on 11.05.2017.
 */
public class CandidateDAO {
    private Session session;
    private static final QuestionAccessor questionAccessor = MappingManagerConfiguration
            .getMappingManager()
            .createAccessor(QuestionAccessor.class);

    public CandidateDAO() {
        session = ConnectionConfiguration.getSession();
    }

    public List<Question> getQuestions(String[] words, boolean nounClause) {
        List<String> sourceList = findSources(words);

        return findQuestions(sourceList, nounClause);
    }

    private List<Question> findQuestions(List<String> sourceList, boolean nounClause){
        List<Question> questionList = new ArrayList<>();
        List<String> sourceNames = new ArrayList<>();

        sourceNames.addAll(sourceList);

        Result<Question> result = questionAccessor.getQuestionsWithInClause(sourceNames, nounClause);
        for(Question question: result) {
            questionList.add(question);
        }

        return questionList;
    }

    private List<String> findSources(String[] words){
        List<String> sourceList = new ArrayList<>();
        Map<String, Integer> sourceCount = new HashMap<>();
        int i = 0;
        for(String word: words) {
            if(!word.equals("")){
                UniqueWord uniqueWord = MappingManagerConfiguration.getUniqueWordMapper().get(word);
                if(uniqueWord != null) {
                    for(String sourceName: uniqueWord.getValueMap().keySet()) {
                        if(uniqueWord.getValueMap().get(sourceName) > 2) {
                           if(sourceCount.containsKey(sourceName))
                               sourceCount.put(sourceName, sourceCount.get(sourceName) + 1);
                           else
                               sourceCount.put(sourceName, 1);
                        }
                    }
                } else {
                   i++;
                }
            } else {
                i++;
            }
        }

        for(String source: sourceCount.keySet()) {
            //if(sourceCount.get(source) == words.length - i)
            if(sourceCount.get(source) >= words.length / 3)
                sourceList.add(source);
        }

        return sourceList;
    }
}
