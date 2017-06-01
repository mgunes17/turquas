package command.question_generator_command;

import command.AbstractCommand;
import command.Command;
import component.question_generator.generate.MainGenerator;
import db.dao.QuestionDAO;
import db.dao.SentenceDAO;
import db.dao.W2VTokenDAO;
import model.Question;
import model.Sentence;
import model.W2VToken;
import w2v_operation.vector_operation.AverageBy;
import w2v_operation.vector_operation.NearBy;
import w2v_operation.word_operation.LetterBy;
import w2v_operation.word_operation.StemBy;
import w2v_operation.word_operation.TokenBy;

import java.util.*;

/**
 * Created by mustafa on 09.05.2017.
 */
public class SaveCommand extends AbstractCommand implements Command {
    private int recordCount;
    private boolean w2v = false;
    private StemBy stemBy = new StemBy();
    private LetterBy letterBy = new LetterBy();
    private TokenBy tokenBy = new TokenBy();
    private AverageBy averageBy = new AverageBy();
    private NearBy nearBy = new NearBy();
    private W2VTokenDAO w2VTokenDAO = new W2VTokenDAO();

    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter))
            return false;

        MainGenerator mainGenerator;
        SentenceDAO dao = new SentenceDAO();
        QuestionDAO questionDAO = new QuestionDAO();
        List<Sentence> sentenceList = dao.readForGenerateQuestions(recordCount);

        for(int i = 0; i < recordCount; i++) {
            if(sentenceList.size() > i){ // düzeltilecek
                System.out.println((i + 1) + ". cümle için soru üretiliyor..");
                Sentence sentence = sentenceList.get(i);
                //soru listesi gelince tek tek hazırla ve kayıt listesine ekle

                mainGenerator = new MainGenerator();
                List<Question>  questionList = mainGenerator.convertQuestions(sentence.getOriginalSentence());

                if(w2v) {
                    saveWithW2V(sentence.getOriginalSentence(), questionList);
                }

                for(Question question: questionList) { // her bir soruyu db için hazırla
                    question.setSourceName(sentence.getSourceName());
                    question.setAnswer(sentence.getOriginalSentence());
                }

                questionDAO.saveQuestionList(questionList);
                questionList = null;
            }
        }

        return true;
    }

    private void saveWithW2V(String sentence, List<Question> questionList) {
        Map<String, String> answerMapByType = createSentencesByWordTypes(sentence);
        List<String> allWordsOfTypes = getWordsForAllTypes(answerMapByType);
        Map<String, Map<String, W2VToken>> w2vTokens = w2VTokenDAO.getW2vTokenForWordsForAllTypes(allWordsOfTypes);
        Map<String, List<Double>> answerW2vValueMap = createW2vMapForSentence(w2vTokens, answerMapByType);

        for(Question question: questionList) { // her sorunun database için hazırlanması
            Map<String, String> questionMapByWordType = createSentencesByWordTypes(question.getQuestion());
            Map<String, List<Double>> questionW2vValueMap = createW2vMapForSentence(w2vTokens, questionMapByWordType);
            question.setQuestionW2vValueMap(questionW2vValueMap);
            question.setAnswerW2vValueMap(answerW2vValueMap);
        }
    }

    // verilen cümlenin farklı kelime tipleri(stem-letter-token) ile tekrardan oluşturulması
    private Map<String, String> createSentencesByWordTypes(String sentence){
        String sentenceByStem = stemBy.rebuildSentence(sentence).toLowerCase();
        String sentenceByLetter = letterBy.rebuildSentence(sentence).toLowerCase();
        String sentenceByToken = tokenBy.rebuildSentence(sentence).toLowerCase();

        Map<String, String> sentenceMapByType = new HashMap<>();
        sentenceMapByType.put("stem", sentenceByStem);
        sentenceMapByType.put("letter", sentenceByLetter);
        sentenceMapByType.put("token", sentenceByToken);

        return sentenceMapByType;
    }

    // verilen cümlenin farklı tiplerdeki(stem-letter-token --> near-avg) w2v değerlerinin bulunması
    private Map<String, List<Double>> createW2vMapForSentence(Map<String, Map<String, W2VToken>> w2vTokens,
    Map<String, String> sentenceMapByType){
        Map<String, List<Double>> w2vMapForSentence = new HashMap<>();

        w2vMapForSentence.put("stem_average",
                averageBy.findValue(sentenceMapByType.get("stem"), "stem", w2vTokens.get("stem")));
        w2vMapForSentence.put("letter_average",
                averageBy.findValue(sentenceMapByType.get("letter"), "letter", w2vTokens.get("letter")));
        w2vMapForSentence.put("token_average",
                averageBy.findValue(sentenceMapByType.get("token"), "token", w2vTokens.get("token")));

        /*w2vMapForSentence.put("stem_near",
                nearBy.findValue(sentenceMapByType.get("stem"), "stem", w2vTokens.get("stem")));
        w2vMapForSentence.put("letter_near",
                nearBy.findValue(sentenceMapByType.get("letter"), "letter", w2vTokens.get("letter")));
        w2vMapForSentence.put("letter_near",
                nearBy.findValue(sentenceMapByType.get("token"), "token", w2vTokens.get("token")));*/

        return w2vMapForSentence;
    }

    private List<String> getWordsForAllTypes(Map<String, String> sentences){
        List<String> allWords = new ArrayList<>();
        for(String sentence: sentences.values()){
            allWords.addAll(sentenceToList(sentence));
        }

        return allWords;
    }

    private List<String> sentenceToList(String sentence){
        String[] words = sentence.split(" ");
        List<String> wordList = new ArrayList<>();
        Collections.addAll(wordList, words);

        return wordList;
    }


    protected boolean validateParameter(String[] parameter) {
        return !(!(parameter.length == 2 || (parameter.length == 3 && (w2v = parameter[2].equals("w2v"))))
                || !parseRecordCount(parameter[1]));
    }

    private boolean parseRecordCount(String count) {
        try {
            recordCount = Integer.parseInt(count);

            if(recordCount < 1) {
                System.out.println("Lütfen 0 dan büyük bir değer girin.");
                return false;
            }

            return true;
        } catch (NumberFormatException ex) {
            System.out.println("Lütfen save ile birlikte sayılsal bir değer girin .");
            return false;
        }
    }
}
