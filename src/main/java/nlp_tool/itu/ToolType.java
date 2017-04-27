package nlp_tool.itu;

/**
 * Created by mustafa on 09.04.2017.
 */
public enum ToolType {
    NameEntityRecognition("ner"),
    Morphanalyzer("morphanalyzer"),
    IsTurkish("isturkish"),
    Morpgenerator("morphgenerator"),
    Tokenizer("tokenizer"),
    Normalize("normalize"),
    Deasciifier("deasciifier"),
    Vowelizer("Vowelizer"),
    DepParserFormal("DepParserFormal"),
    DepParserNoisy("DepParserNoisy"),
    Spellcheck("spellcheck"),
    Disambiguator("disambiguator"),
    PipelineFormal("pipelineFormal"),
    PipelineNoisy("pipelineNoisy");

    private String toolName;

    ToolType(String toolName) {
        this.toolName = toolName;
    }

    public String getToolName() {
        return toolName;
    }
}
