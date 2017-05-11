package command.db_updater_command;

import command.AbstractCommand;
import command.Command;
import db.dao.SourceDAO;
import db.dao.UniqueWordDAO;
import model.Source;
import model.UniqueWord;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by mustafa on 26.04.2017.
 */
public class TfidfCommand extends AbstractCommand implements Command {
    private UniqueWordDAO uniqueWordDAO = new UniqueWordDAO();
    private SourceDAO sourceDAO = new SourceDAO();
    private Set<UniqueWord> uniqueWordSet;
    private Map<String, Source> sourceMap;

    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter)) {
            return false;
        }

        uniqueWordSet = uniqueWordDAO.getAllWords();
        sourceMap = sourceDAO.getAllSourceWordCount();
        compute();
        uniqueWordDAO.update(uniqueWordSet);

        return true;
    }

    private void compute() {
        for(UniqueWord word: uniqueWordSet) {
            Map<String, Double> valueMap = new HashMap<String, Double>();
            for(String sourceName: word.getDocumentSet()) {
                Source source = sourceMap.get(sourceName);
                int tf = source.getWordCountMap().get(word.getWord()); // kelimenin o dokümanda bulunma sayısı
                double idf = Math.log10(sourceMap.size() + 1) / Math.log10(word.getDocumentSet().size() + 1);
                valueMap.put(source.getSourceName(), tf*idf);
            }

            word.setValueMap(valueMap);
        }
    }

    protected boolean validateParameter(String[] parameter) {
        if(parameter.length == 1)
            return true;
        else {
            System.out.println("Eksik/Fazla parametre");
            return false;
        }
    }
}
