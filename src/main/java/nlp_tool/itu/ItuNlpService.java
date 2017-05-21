package nlp_tool.itu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.04.2017.
 */
public class ItuNlpService {
    private final ItuConfiguration ituNlp;

    public ItuNlpService() {
        ituNlp = new ItuConfiguration();
    }

    public List<ParsedWord> getNameEntities(String sentence) {
        String[] tokenArr = sentence.split("[ ]+");

        //her bir kelimenin tokenını bul
        Map<String, String[]> tokenMap = new LinkedHashMap<String, String[]>();

        for(String token: tokenArr) {
            String[] ret = ituNlp.connect(ToolType.Morphanalyzer, token).split("\n");
            tokenMap.put(token, ret);
        }

        String disForm = buildDisForm(tokenMap);
        String sentenceDisam = ituNlp.connect(ToolType.Disambiguator, disForm);
        String nerForm = buildNerForm(sentenceDisam);
        String sentenceNer = ituNlp.connect(ToolType.NameEntityRecognition, nerForm);
        return mapNer(sentenceNer);
    }

    private String buildNerForm(String disForm) {
        StringBuilder nerForm = new StringBuilder();
        nerForm.append("<DOC> <DOC>+BDTag\n");
        nerForm.append(disForm);
        nerForm.append("<DOC> <DOC>+EDTag");
        return nerForm.toString();
    }

    private String buildDisForm(Map<String, String[]> tokenMap) {
        StringBuilder disForm = new StringBuilder();
        disForm.append("<S> <S>+BSTag\n");

        for(String token: tokenMap.keySet()) {
            disForm.append(token);

            for(String morpToken: tokenMap.get(token)) {
                disForm.append(" " + morpToken);
            }

            disForm.append("\n");
        }

        disForm.append("</S> </S>+ESTag");

        return disForm.toString();
    }

    private List<ParsedWord> mapNer(String responseEntity) {
        List<ParsedWord> parsedWords = new ArrayList<ParsedWord>();
        String[] lines = responseEntity.split("\n");

        for(int i = 2; i < lines.length - 2; i++) {
            if(lines[i].contains("\t\t")) {
                ParsedWord parsedWord = new ParsedWord();
                String[] word = lines[i].split("(\t\t)");
                parsedWord.setNer(word[1].trim());

                String[] word2 = word[0].split(" ");
                parsedWord.setWord(word2[0].trim());
                parsedWord.setWordPOS(word2[1].trim());
                parsedWords.add(parsedWord);
            }
        }

        return parsedWords;
    }
}
