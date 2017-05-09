package command.w2v_creator_command;

import command.AbstractCommand;
import command.Command;
import db.dao.SentenceDAO;
import db.dao.W2VTokenDAO;
import model.Sentence;
import model.W2VToken;
import org.antlr.v4.runtime.Token;
import zemberek.tokenization.TurkishTokenizer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.05.2017.
 */
public class Sentence2W2VCommand extends AbstractCommand implements Command {
    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter)) {
            return false;
        }

        Map<String, W2VToken> w2VTokens;
        W2VTokenDAO w2VTokenDAO = new W2VTokenDAO();
        List<Sentence> sentences = new SentenceDAO().getAllSentences();

        if(parameter[1].equals("stem")) {
            w2VTokens = w2VTokenDAO.getTokens(true);

            if(parameter[2].equals("near")) {
                try {
                    PrintWriter pwInput = new PrintWriter("input.txt");
                    PrintWriter pwOutput = new PrintWriter("output.txt");

                    for(Sentence sentence: sentences) {
                        for(String question: sentence.getQuestions()) {
                            //soru cümlesinin stemlerini bul
                            //stemlerin w2v değerlerini bul
                        }
                    }

                    pwInput.close();
                    pwOutput.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if(parameter[2].equals("average")) {

            } else {
                System.out.println("Yanlış komut");
            }

        } else if(parameter[1].equals("letter")){
            w2VTokens = w2VTokenDAO.getTokens(false);

            if(parameter[2].equals("near")) {
                try {
                    PrintWriter pwInput = new PrintWriter("input.txt");
                    PrintWriter pwOutput = new PrintWriter("output.txt");

                    for(Sentence sentence: sentences) {
                        for(String question: sentence.getQuestions()) {
                            TurkishTokenizer tokenizer = TurkishTokenizer.DEFAULT;
                            Iterator<Token> tokenIterator = tokenizer.getTokenIterator(question);

                            while (tokenIterator.hasNext()) {
                                Token token = tokenIterator.next();
                                String word;

                                if(token.getText().length() >= 5)
                                    word = token.getText().substring(0, 5);
                                else
                                    word = token.getText();

                                if(w2VTokens.containsKey(word)) {
                                    for(float value: w2VTokens.get(word).getValue()) {
                                        pwInput.print(value + " ");
                                    }
                                } else {
                                    //tamamlamak için 1 koy
                                }
                            }

                            pwInput.println();
                            pwOutput.println();
                        }
                    }

                    pwInput.close();
                    pwOutput.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if(parameter[2].equals("average")) {

            } else {
                System.out.println("Yanlış komut");
            }
        } else {
            System.out.println("Yanlış komut");
        }


        return true;
    }

    protected boolean validateParameter(String[] parameter) {
        return parameter.length == 3;
    }
}
