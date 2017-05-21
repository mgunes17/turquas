package component.question_generator.factory.itu.type;

import component.question_generator.factory.itu.type.nameEntityQuestionType.*;
import model.Question;
import nlp_tool.itu.ItuNlpService;
import nlp_tool.itu.ParsedWord;

import java.util.*;

/**
 * Created by mustafa on 09.04.2017.
 */
public class NerQuestion implements QuestionType {
    private final ItuNlpService nlpService;
    private final Map<String, NameEntityQuestionType> nameEntityMap;
    private final Set<String> nameEntitySet;

    public NerQuestion() {
        nlpService = new ItuNlpService();
        nameEntityMap = new HashMap<>();
        nameEntityMap.put("B-PERSON", new PersonQuestion());
        nameEntityMap.put("B-LOCATION", new LocationQuestion());
        nameEntityMap.put("B-ORGANIZATION", new OrganizationQuestion());
        nameEntityMap.put("B-DATE", new DateQuestion());
        nameEntityMap.put("B-TIME", new TimeQuestion());
        nameEntityMap.put("B-MONEY", new MoneyQuestion());
        nameEntityMap.put("B-PERCENTAGE", new PercentageQuestion());

        nameEntitySet = new HashSet<>();
        nameEntitySet.add("B-PERSON");
        nameEntitySet.add("B-LOCATION");
        nameEntitySet.add("B-ORGANIZATION");
        nameEntitySet.add("B-DATE");
        nameEntitySet.add("B-TIME");
        nameEntitySet.add("B-MONEY");
        nameEntitySet.add("B-PERCENTAGE");
    }

    public List<Question> reorganize(String sentence) {
        List<ParsedWord> parsedWords = nlpService.getNameEntities(sentence);

        List<Question> questionList = new ArrayList<>();
        Map<Integer, String> neIndex = new HashMap<>();

        //bu yapıdan hangi name entity nerede başlıyor
        Set<NameEntityQuestionType> existNameEntity = new HashSet<>();

        int i = 0;
        for(ParsedWord parsedWord: parsedWords) {
            if(parsedWord.getNer().contains("B-")) {
                neIndex.put(i, parsedWord.getNer());
                existNameEntity.add(nameEntityMap.get(parsedWord.getNer()));
            }

            i++;
        }

        for(NameEntityQuestionType neqt: existNameEntity) {
            if(neqt != null) {
                questionList.addAll(neqt.generateQuestion(parsedWords, neIndex));
            }
        }

        return questionList;
    }
}
