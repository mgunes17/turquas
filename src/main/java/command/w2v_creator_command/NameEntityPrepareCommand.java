package command.w2v_creator_command;

import command.AbstractCommand;
import command.Command;
import component.w2v_creator.named_entity.PreparingForNamedEntity;
import db.dao.SentenceDAO;
import db.dao.W2VTokenDAO;
import model.W2VToken;
import w2v_operation.vector_operation.AverageBy;
import w2v_operation.word_operation.StemBy;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by mustafa on 21.05.2017.
 */
public class NameEntityPrepareCommand extends AbstractCommand implements Command {
    private List<List<Double>> w2vList;
    private List<Integer> classIndexList;
    private int index = 0;
    private Map<String, Integer> namedEntityMap;

    public NameEntityPrepareCommand() {
        w2vList = new ArrayList<>();
        classIndexList = new ArrayList<>();
        namedEntityMap = new HashMap<>();
    }

    @Override
    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter))
            return false;

        try{
            int limit = Integer.parseInt(parameter[1]);
            SentenceDAO sentenceDAO = new SentenceDAO();
            List<String> sentenceList;
            if(limit == 0){
                sentenceList = sentenceDAO.getAllSentencesOnly();
            } else {
                sentenceList = sentenceDAO.getSentencesOnlyWithLimit(limit);
            }

            Map<String, String> sentenceNePair = new PreparingForNamedEntity().extractNE(sentenceList);

            prepareListForSave(sentenceNePair);
            createTextFiles();
        } catch(NumberFormatException ex){
            System.out.println("lütfen bir sayı giriniz.");
            System.out.println(ex.getMessage());
        }

        return true;
    }

    private void prepareListForSave(Map<String, String> sentenceNePair) {
        W2VTokenDAO w2VTokenDAO = new W2VTokenDAO();
        for(String sentence: sentenceNePair.keySet()) {
            Map<String, W2VToken> w2VTokens = w2VTokenDAO.getW2vTokenForWordsForType("token", getWordList(sentence));
            String stem = new StemBy().rebuildSentence(sentence).toLowerCase();
            List<Double> w2v = new AverageBy().findValue(stem, "token", w2VTokens);
            w2vList.add(w2v);

            if(namedEntityMap.containsKey(sentenceNePair.get(sentence))) {
                classIndexList.add(namedEntityMap.get(sentenceNePair.get(sentence)));
            } else {
                index++;
                namedEntityMap.put(sentenceNePair.get(sentence), index);
                classIndexList.add(index);
            }
        }
    }

    private List<String> getWordList(String sentence){
        String[] words = sentence.split(" ");
        List<String> wordList = new ArrayList<>();
        wordList.addAll(Arrays.asList(words));

        return wordList;
    }

    private void createTextFiles() {
        try {
            PrintWriter pwInput = new PrintWriter("ne_input.txt");
            PrintWriter pwOutput = new PrintWriter("ne_output.txt");
            PrintWriter pwMap = new PrintWriter("ne_map.txt");

            int size = w2vList.size();

            for(int i = 0; i < size; i++) {
                // w2v değeri
                for(Double value: w2vList.get(i)) {
                    pwInput.print(value + " ");
                }
                pwInput.println();

                // cevabın indexi
                pwOutput.print(classIndexList.get(i));
                pwOutput.println();
            }

            for(String ne: namedEntityMap.keySet()){
                pwMap.print(ne + " " + namedEntityMap.get(ne));
                pwMap.println();
            }

            pwInput.close();
            pwOutput.close();
            pwMap.close();
            System.out.println("Dosyalar oluşturuldu.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected boolean validateParameter(String[] parameter) {
        return parameter.length == 2;
    }


}
