package command.db_updater_command;

import command.AbstractCommand;
import command.Command;
import component.w2v_creator.converter.Word2Vec;
import component.w2v_creator.sentence_file_creator.DefaultSentenceFileCreator;
import component.w2v_creator.sentence_file_creator.LetterLimitedSentenceFileCreator;
import component.w2v_creator.sentence_file_creator.SentenceLoader;
import component.w2v_creator.sentence_file_creator.StemmedSentenceFileCreator;
import db.dao.W2VTokenDAO;
import model.W2VToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 09.05.2017.
 */
public class W2VTokenCommand extends AbstractCommand implements Command {
    public boolean execute(String[] parameter) {
        //kaydedilecek liste
        W2VTokenDAO w2VTokenDAO = new W2VTokenDAO();
        List<W2VToken> w2VTokenList = new ArrayList<W2VToken>();

        SentenceLoader sentenceLoader = new SentenceLoader(0); //tüm cümleleri çek
        /*StemmedSentenceFileCreator stemmedCreator = new StemmedSentenceFileCreator(sentenceLoader);
        stemmedCreator.createFile(); // stem hallerini al

        Word2Vec word2Vec = new Word2Vec();
        try {
            word2Vec.run(); //w2v değerlerini hesapla
        } catch (Exception e) {
            e.printStackTrace();
        }

        readW2VFromFile(w2VTokenList, "stem");*/

        Word2Vec word2Vec = new Word2Vec();
        LetterLimitedSentenceFileCreator letterLimitedCreator = new LetterLimitedSentenceFileCreator(sentenceLoader, 5);
        letterLimitedCreator.createFile(); //kelimelerin ilk 5 harfini al

        System.out.println("letter başladı.");
        try {
            word2Vec.run(); //w2v değerlerini hesapla
        } catch (Exception e) {
            e.printStackTrace();
        }

        //dosyadan değerleri oku
        readW2VFromFile(w2VTokenList, "letter");
        System.out.println("letter bitti.");

        w2VTokenDAO.insertToTable(w2VTokenList);
        w2VTokenList = new ArrayList<>();
        System.out.println("letter kaydetti");

        DefaultSentenceFileCreator defaultSentenceFileCreator = new DefaultSentenceFileCreator(sentenceLoader);
        defaultSentenceFileCreator.createFile(); // token olarak kaydet

        System.out.println("token başladı");
        try {
            word2Vec.run(); //w2v değerlerini hesapla
        } catch (Exception e) {
            e.printStackTrace();
        }

        //dosyadan değerleri oku
        readW2VFromFile(w2VTokenList, "token");
        System.out.println("token bitti.");
        //kaydet
        w2VTokenDAO.insertToTable(w2VTokenList);
        System.out.println("vtye kaydedildi.");
        return true;
    }

    private void readW2VFromFile(List<W2VToken> w2VTokenList, String type) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("target.txt"));

            String line = bufferedReader.readLine(); //ilk satırda değer yok
            line = bufferedReader.readLine();

            while(line != null) {
                String[] parsedLine = line.split(" ");
                W2VToken w2VToken = new W2VToken();
                w2VToken.setType(type);
                w2VToken.setTokenName(parsedLine[0]);

                List<Double> w2vValues = new ArrayList<Double>();

                for(int i = 1; i < parsedLine.length; i++) {
                    w2vValues.add(Double.parseDouble(parsedLine[i]));
                }
                w2VToken.setValue(w2vValues);
                w2VTokenList.add(w2VToken);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean validateParameter(String[] parameter) {
        return false;
    }
}
