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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.05.2017.
 */
public class SaveCommand extends AbstractCommand implements Command {
    private MainGenerator mainGenerator;
    private int recordCount;

    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter))
            return false;

        int sessionSize = QuestionGeneratorAdmin.questionGEneratorParameterMap.get("session_size");
        mainGenerator = new MainGenerator();
        SentenceDAO dao = new SentenceDAO();
        QuestionDAO questionDAO = new QuestionDAO();
        List<Sentence> sentenceList = dao.readForGenerateQuestions(recordCount);

        //bir cümle seti için factory 1 kere verilsin
        for(int i = 0; i < recordCount; i++) {
            if(sentenceList.size() > i){ // düzeltilecek
                System.out.println((i + 1) + ". cümle için soru üretiliyor..");

                Sentence sentence = sentenceList.get(i);
                //soru listesi gelince tek tek hazırla ve kayıt listesine ekle

                long start_time = System.nanoTime();
                mainGenerator = new MainGenerator();
                List<Question>  questionList = mainGenerator.convertQuestions(sentence.getOriginalSentence());
                long end_time = System.nanoTime();
                double difference = (end_time - start_time)/1e6;
                System.out.println("sorular convert edildi:" + difference);


                Map<String, List<Double>> w2vMapForSentence = new HashMap<>();
                StemBy stemBy = new StemBy();
                LetterBy letterBy = new LetterBy();
                AverageBy averageBy = new AverageBy();
                NearBy nearBy = new NearBy();

                start_time = System.nanoTime();
                String stem = stemBy.rebuildSentence(sentence.getOriginalSentence()).toLowerCase();
                String letter = letterBy.rebuildSentence(sentence.getOriginalSentence()).toLowerCase();
                end_time = System.nanoTime();
                difference = (end_time - start_time)/1e6;
                System.out.println("stem ve letter rebuild sentence başladı bitti:" + difference);

                start_time = System.nanoTime();
                w2vMapForSentence.put("stem_average", averageBy.findValue(stem, "stem"));
                //w2vMapForSentence.put("stem_near", nearBy.findValue(stem, stem));
                w2vMapForSentence.put("letter_average", averageBy.findValue(letter, "letter"));
                //w2vMapForSentence.put("letter_near", nearBy.findValue(letter, "letter"));
                end_time = System.nanoTime();
                difference = (end_time - start_time)/1e6;
                System.out.println("vektör değerleri hesaplama:" + difference);


                for(Question question: questionList) { // her bir soruyu db için hazırla
                    start_time = System.nanoTime();
                    stem = stemBy.rebuildSentence(question.getQuestion());
                    letter = letterBy.rebuildSentence(question.getQuestion());

                    Map<String, List<Double>> w2vMapForQuestion = new HashMap<>();
                    w2vMapForQuestion.put("stem_average", averageBy.findValue(stem, "stem"));
                    //w2vMapForQuestion.put("stem_near", nearBy.findValue(stem, stem));
                    w2vMapForQuestion.put("letter_average", averageBy.findValue(letter, "letter"));
                    //w2vMapForQuestion.put("letter_near", nearBy.findValue(letter, "letter"));

                    question.setQuestionW2vValueMap(w2vMapForQuestion);
                    question.setAnswerW2vValueMap(w2vMapForSentence);
                    question.setSourceName(sentence.getSourceName());
                    question.setAnswer(sentence.getOriginalSentence());
                    end_time = System.nanoTime();
                    difference = (end_time - start_time)/1e6;
                    System.out.println("bir soru için stem-letter rebuild + vektörler :" + difference);
                }

                start_time = System.nanoTime();
                questionDAO.saveQuestionList(questionList);
                end_time = System.nanoTime();
                difference = (end_time - start_time)/1e6;
                System.out.println(questionList.size() + "sorunun kaydedilmesi :" + difference);
                questionList = null;
            }
        }

        return true;
    }

    protected boolean validateParameter(String[] parameter) {
        return !(parameter.length != 2 || !parseRecordCount(parameter[1]));
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
