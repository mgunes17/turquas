package nlp_tool.itu;

import model.Token;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mustafa on 26.04.2017.
 */
public class LabelMorph {
    private ItuConfiguration ituConfiguration;

    public LabelMorph() {
        ituConfiguration = new ItuConfiguration();
    }

    public void labelMorph(Set<Token> tokenSet) {
        for(Token token: tokenSet) {
            String result = ituConfiguration.connect(ToolType.Morphanalyzer, token.getToken());
            Set<String> analysisSet = new HashSet<String>();

            for(String analysis: result.split("\n")) {
                analysisSet.add(analysis);
            }

            token.setAnalysisSet(analysisSet);
        }
    }
}
