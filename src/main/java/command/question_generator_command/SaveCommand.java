package command.question_generator_command;

import admin.QuestionGeneratorAdmin;
import command.AbstractCommand;
import command.Command;
import component.question_generator.generate.MainGenerator;
import db.dao.QuestionDAO;
import db.dao.SentenceDAO;
import model.Question;
import model.Sentence;
import w2v_operation.vector_operation.AverageBy;
import w2v_operation.vector_operation.NearBy;
import w2v_operation.word_operation.LetterBy;
import w2v_operation.word_operation.StemBy;
import w2v_operation.word_operation.TokenBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.05.2017.
 */
public class SaveCommand extends AbstractCommand implements Command {
    private MainGenerator mainGenerator;
    private int recordCount;
    private boolean w2v = false;

    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter))
            return false;

        int sessionSize = QuestionGeneratorAdmin.questionGEneratorParameterMap.get("session_size");
        mainGenerator = new MainGenerator();
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
        Map<String, List<Double>> w2vMapForSentence = new HashMap<>();
        StemBy stemBy = new StemBy();
        LetterBy letterBy = new LetterBy();
        TokenBy tokenBy = new TokenBy();
        AverageBy averageBy = new AverageBy();
        NearBy nearBy = new NearBy();
        String sentenceByStem = stemBy.rebuildSentence(sentence).toLowerCase();
        String sentenceByLetter = letterBy.rebuildSentence(sentence).toLowerCase();
        String sentenceByToken = tokenBy.rebuildSentence(sentence).toLowerCase();
        w2vMapForSentence.put("stem_average",
                averageBy.findValue(sentenceByStem, "stem"));
        w2vMapForSentence.put("letter_average",
                averageBy.findValue(sentenceByLetter, "letter"));
        w2vMapForSentence.put("token_average",
                averageBy.findValue(sentenceByToken, "token"));
        //w2vMapForSentence.put("stem_near", nearBy.findValue(stem, stem));
        //w2vMapForSentence.put("letter_near", nearBy.findValue(letter, "letter"));

        for(Question question: questionList) { // her bir soruyu db için hazırla
            sentenceByStem = stemBy.rebuildSentence(question.getQuestion()).toLowerCase();
            sentenceByLetter = letterBy.rebuildSentence(question.getQuestion()).toLowerCase();
            sentenceByToken = tokenBy.rebuildSentence(question.getQuestion()).toLowerCase();

            Map<String, List<Double>> w2vMapForQuestion = new HashMap<>();
            w2vMapForQuestion.put("stem_average",
                    averageBy.findValue(sentenceByStem, "stem"));
            w2vMapForQuestion.put("letter_average",
                    averageBy.findValue(sentenceByLetter, "letter"));
            w2vMapForQuestion.put("token_average",
                    averageBy.findValue(sentenceByToken, "token"));
            //w2vMapForQuestion.put("stem_near", nearBy.findValue(stem, stem));
            //w2vMapForQuestion.put("letter_near", nearBy.findValue(letter, "letter"));

            question.setQuestionW2vValueMap(w2vMapForQuestion);
            question.setAnswerW2vValueMap(w2vMapForSentence);
        }
    }

    protected boolean validateParameter(String[] parameter) {
        return !(!(parameter.length == 2 || (parameter.length == 3 && (w2v = parameter[2].equals("w2v")))) || !parseRecordCount(parameter[1]));
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
