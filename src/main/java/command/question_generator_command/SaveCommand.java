package command.question_generator_command;

import admin.QuestionGeneratorAdmin;
import command.AbstractCommand;
import command.Command;
import component.question_generator.generate.MainGenerator;
import db.dao.SentenceDAO;
import model.Sentence;

import java.util.ArrayList;
import java.util.List;

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
        List<Sentence> sentenceList = dao.readForGenerateQuestions(recordCount);
        List<Sentence> newSentenceList = new ArrayList<Sentence>();

        //bir cümle seti için factory 1 kere verilsin
        for(int i = 0; i < recordCount; i++) {
            Sentence sentence = sentenceList.get(i);

            if(sentence.getQuestions().size() == 0) { //soru üret
                newSentenceList.add(new Sentence(
                        sentence.getSourceName(),
                        sentence.getOriginalSentence(),
                        mainGenerator.convertQuestions(sentence.getOriginalSentence()
                        )));
            }

            if(i % sessionSize == 0){
                System.out.println("Seans başlıyor... " + i);
                dao.updateQuestions(newSentenceList);
                System.out.println("Seans bitti... " + i);
            }
        }

        dao.updateQuestions(newSentenceList);
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
