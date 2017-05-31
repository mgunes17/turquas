package component.w2v_creator.named_entity;

import nlp_tool.itu.ItuNlpService;
import nlp_tool.itu.ParsedWord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 21.05.2017.
 */
public class PreparingForNamedEntity {
    private final ItuNlpService nlpService;

    public PreparingForNamedEntity() {
        nlpService = new ItuNlpService();
    }

    public Map<String, String> extractNE(List<String> sentences) {
        Map<String, String> pair = new HashMap<>();

        for(String sentence: sentences) { // her bir cümleden NamedEntity çıkar
            List<ParsedWord> parsedWords = nlpService.getNameEntities(sentence);

            for(int i = 0; i < parsedWords.size(); i++) { //her bir kelime için NE kontrolü yap
                if(parsedWords.get(i).getNer().contains("B-")) {
                    int j = i;
                    while(j < parsedWords.size() && parsedWords.get(j + 1).getNer().contains("I-")) { //NE kaç token ?
                        j++;
                    }

                    String sentenceWithoutNamedEntity = subtractNamedEntity(parsedWords, i ,j);
                    System.out.println("Cümle: " + sentenceWithoutNamedEntity);
                    String namedEntity = sumNamedEntity(parsedWords, i, j);
                    System.out.println("NE: " + namedEntity);
                    System.out.println();
                    pair.put(sentenceWithoutNamedEntity, namedEntity);
                }
            }
        }

        return pair;
    }

    protected String subtractNamedEntity(List<ParsedWord> parsedWords, int i, int j) {
        StringBuilder builder = new StringBuilder();

        for(int k = 0; k < parsedWords.size(); k++) {
            if(k < i || k > j) {
                builder.append(parsedWords.get(k).getWord());
                builder.append(" ");
            }
        }

        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    protected String sumNamedEntity(List<ParsedWord> parsedWords, int i, int j) {
        StringBuilder builder = new StringBuilder();

        for(int k = 0; k < parsedWords.size(); k++) {
            if(k >= i && k <= j) {
                builder.append(parsedWords.get(k).getWord());
                builder.append(" ");
            }
        }

        return builder.deleteCharAt(builder.toString().length() - 1).toString();
    }
}
