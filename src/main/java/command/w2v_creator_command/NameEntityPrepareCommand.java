package command.w2v_creator_command;

import command.AbstractCommand;
import command.Command;
import component.w2v_creator.named_entity.PreparingForNamedEntity;
import db.dao.SentenceDAO;
import w2v_operation.vector_operation.AverageBy;
import w2v_operation.word_operation.StemBy;

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

        SentenceDAO sentenceDAO = new SentenceDAO();
        List<String> sentenceList = sentenceDAO.getAllSentencesOnly();
        Map<String, String> sentenceNePair = new PreparingForNamedEntity().extractNE(sentenceList);

        prepareListForSave(sentenceNePair);
        createTextFiles();

        return true;
    }

    private void prepareListForSave(Map<String, String> sentenceNePair) {
        for(String sentence: sentenceNePair.keySet()) {
            String stem = new StemBy().rebuildSentence(sentence);
            List<Double> w2v = new AverageBy().findValue(stem, "stem");
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

    private void createTextFiles() {

    }

    @Override
    protected boolean validateParameter(String[] parameter) {
        return parameter.length == 3
                && (parameter[1].equals("stem") || parameter[1].equals("letter") || parameter[1].equals("token"))
                && (parameter[2].equals("near") || parameter[2].equals("average"));
    }

}
