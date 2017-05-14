package db.dao;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Result;
import db.accessor.QuestionAccessor;
import db.configuration.ConnectionConfiguration;
import db.configuration.MappingManagerConfiguration;
import model.Question;
import model.UniqueWord;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public List<Question> getQuestions(String[] words) {
        List<Set<String>> sourceList = findSources(words);

        return findQuestions(sourceList);
    }

    private List<Question> findQuestions(List<Set<String>> sourceList){
        List<Question> questionList = new ArrayList<>();
        List<String> sourceNames = new ArrayList<>();

        for(Set<String> sourceSet: sourceList) {
            sourceNames.addAll(sourceSet);
        }

        Result<Question> result = questionAccessor.getQuestionsWithInClause(sourceNames);
        for(Question question: result) {
            questionList.add(question);
        }

        return questionList;
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
