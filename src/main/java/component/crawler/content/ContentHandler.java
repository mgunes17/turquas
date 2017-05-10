package component.crawler.content;

import zemberek.tokenization.TurkishSentenceExtractor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by ercan on 23.03.2017.
 */
public class ContentHandler {
    private String sourceName;
    private Set<String> sentences;
    private Content content;
    private String contentText;

    public ContentHandler(String sourceName, Content content){
        this.sourceName = sourceName;
        this.content = content;
    }

    public ContentHandler() {

    }

    public String fetchContent(){
        if(contentText == null){
           if(!refreshContent()){
               contentText = null;
           }
        }

        return contentText;
    }

    private boolean refreshContent(){
        try{
            contentText = content.fetchContent(sourceName);
            splitParagraphIntoSentences();
            return true;
        } catch(FileNotFoundException e){
            System.out.println("##ERROR## Couldn't open the file..##ERROR##");
        } catch (IOException e) {
            System.out.println("##ERROR## Couldn't read the text in component.crawler.content. ##ERROR##");
        }
        return false;
    }

    private void splitParagraphIntoSentences(){
        sentences = new HashSet<String>();
        TurkishSentenceExtractor extractor = TurkishSentenceExtractor.DEFAULT;

        List<String> sentencesFromDetector = extractor.fromParagraph(contentText);
        sentences.addAll(sentencesFromDetector);
    }

    public List<String> getSentencesFromParagraph(String paragraph){
        TurkishSentenceExtractor extractor = TurkishSentenceExtractor.DEFAULT;

        return extractor.fromParagraph(paragraph);
    }

    public Map<String, List<String>> getAbstractsWithTheirSentences(){
        Map<String, List<String>> abstractsMap = new HashMap<String, List<String>>();
        String[] abstracts = contentText.split("###");

        int i = 0;
        for(String paragraph: abstracts){
            i++;
            abstractsMap.put(Integer.toString(i), getSentencesFromParagraph(paragraph));
        }

        return abstractsMap;
    }



    //getter - setter
    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Set<String> getSentences() {
        return sentences;
    }

    public void setSentences(Set<String> sentences) {
        this.sentences = sentences;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

}
