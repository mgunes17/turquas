package command.user_interface_command;

import admin.UserInterfaceAdmin;
import command.AbstractCommand;
import command.Command;
import component.user_interface.candidate.FindingCandidate;
import model.QuestionForCompare;
import model.Sentence;
import w2v_operation.vector_operation.VectorType;
import w2v_operation.word_operation.WordType;

import java.util.List;
import java.util.Scanner;

/**
 * Created by mustafa on 10.05.2017.
 */
public class AnswerCommand extends AbstractCommand implements Command {
    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter)) {
            return false;
        }

        Scanner in = new Scanner(System.in);
        System.out.print("answer=>");
        String question = in.nextLine();

        while(!question.equals("change")) {
            if(validateQuestion(question)) { //girdi cevaplanabilir bir soru ise
                List<QuestionForCompare> candidateList = new FindingCandidate().findCandidateList(question);

                WordType wordType = UserInterfaceAdmin.wordTypeMap.get(UserInterfaceAdmin.wordType);
                wordType.prepareQuestionList(candidateList);

                VectorType vectorType = UserInterfaceAdmin.vectorTypeMap.get(UserInterfaceAdmin.vectorType);
                //vectorType
                //candidateListi StemBy ya da LetterByAy yolla
                //NearBy ya da AverageBy a yolla
                //sırala ilk N cevabıu göster
            }
            /*
                benzerlik thrsholdun altındaysa bildir
             */

            /*
                Kullanıcı sorusunun ve cevap cümlelerinin w2v ye çevrilmesi gerek ya hani
                sentence ile başlayan komutta ki çevirme işlemlerini ayrı bir sınıfa al ordan erişs
             */
            System.out.print("answer=>");
            question = in.nextLine();
        }

        return true;
    }

    private boolean validateQuestion(String question) {
        //neden geçersiz olduğunu ekrana yazdır
        //kontroller -> en az 2 kelime, soru kelimesi içermeli(listeyi hazırla), türkçe, ??
        return false;
    }

    protected boolean validateParameter(String[] parameter) {
        return parameter.length == 1;
    }
}
