package command.user_interface_command;

import admin.UserInterfaceAdmin;
import command.AbstractCommand;
import command.Command;
import component.user_interface.candidate.FindingCandidate;
import component.user_interface.similarity.SimilarityType;
import model.QuestionForCompare;
import model.SimilarityValue;
import w2v_operation.vector_operation.VectorType;
import w2v_operation.word_operation.WordType;
import zemberek.langid.LanguageIdentifier;

import java.io.IOException;
import java.util.Comparator;
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
                String w2vType = UserInterfaceAdmin.wordType + " " + UserInterfaceAdmin.vectorType;
                QuestionForCompare userQuestion = new QuestionForCompare();
                userQuestion.setQuestion(question);

                long start_time = System.nanoTime();
                List<QuestionForCompare> candidateList = new FindingCandidate().findCandidateList(question, w2vType);
                candidateList.add(0, userQuestion);

                long end_time = System.nanoTime();
                double difference = (end_time - start_time)/1e6;

                System.out.println("candidate çekilmesi:" + difference);

                start_time = System.nanoTime();

                ///// Amaç W2V değerlerini elde etmeki zaten kayıtlı, kullanıcınınkini bul yeter

                end_time = System.nanoTime();
                difference = (end_time - start_time)/1e6;
                System.out.println("word + vector çekilmesi:" + difference);


                start_time = System.nanoTime();
                SimilarityType similarityType = UserInterfaceAdmin.similarityMap.get(UserInterfaceAdmin.similarityType);

                int size = candidateList.size();
                for(int i = 1; i < size; i++){ // 0, kullanıcı sorusunun kendisi
                    double value = similarityType.findSimilarity(userQuestion.getVector(),
                                                                 candidateList.get(i).getVector());
                    SimilarityValue similarityValue = new SimilarityValue();
                    similarityValue.setQuestionForCompare(candidateList.get(i));
                    similarityValue.setValue(value);
                    userQuestion.getSimilarityList().add(similarityValue);
                }

                userQuestion.getSimilarityList().sort(new SimilarityComparator());

                System.out.println(userQuestion.getQuestion());
                int answerShown = 0;
                for(int i = 0; i < UserInterfaceAdmin.parameterMap.get("max_answer_count"); i++){
                    double value = userQuestion.getSimilarityList().get(i).getValue();

                    if(value > UserInterfaceAdmin.parameterMap.get("threshold") / 100.0) {
                        System.out.println("DEĞER: " + userQuestion.getSimilarityList().get(i).getValue() +
                                " CEVAP: " + userQuestion.getSimilarityList().get(i).
                                getQuestionForCompare().getAnswer() +
                                " SORU: " + userQuestion.getSimilarityList().get(i).
                                getQuestionForCompare().getQuestion());
                        answerShown++;
                    }
                }

                end_time = System.nanoTime();
                difference = (end_time - start_time)/1e6;
                System.out.println("benzerlik hesabı:" + difference);

                if(answerShown == 0){
                    System.out.println("cevapların benzerlik değerleri threshold " +
                            "değerinin altında olduğu için hiç bir cevap gösterilemedi.");
                }
            }
            System.out.print("answer=>");
            question = in.nextLine();
        }

        return true;
    }

    private boolean validateQuestion(String question){
        try{
            LanguageIdentifier languageIdentifier = LanguageIdentifier.fromInternalModelGroup("tr_group");
            String[] words = question.split(" ");

            return words.length >= 2 && languageIdentifier.identify(question).equals("tr");
        } catch(IOException ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return false;
        }
    }

    protected boolean validateParameter(String[] parameter) {
        return parameter.length == 1;
    }

    public static class SimilarityComparator implements Comparator<SimilarityValue>{
        public int compare(SimilarityValue s1, SimilarityValue s2){
            return s1.compareTo(s2);
        }
    }
}
