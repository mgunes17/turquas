package command.w2v_creator_command;

import admin.W2VCreatorAdmin;
import command.AbstractCommand;
import command.Command;
import db.dao.SentenceDAO;
import db.dao.W2VTokenDAO;
import model.Sentence;
import model.W2VToken;
import nlp_tool.zemberek.ZemberekSentenceAnalyzer;
import org.antlr.v4.runtime.Token;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;
import zemberek.tokenization.TurkishTokenizer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by mustafa on 09.05.2017.
 */
public class Sentence2W2VCommand extends AbstractCommand implements Command {
    private Map<String, W2VToken> w2VTokens;
    private Map<List<Float>, List<List<Float>>> w2vValues = new HashMap<List<Float>, List<List<Float>>>();
    private Map<String, List<String>> convertedSentences = new HashMap<String, List<String>>();
    private List<Sentence> sentences = new ArrayList<Sentence>();
    TurkishTokenizer tokenizer = TurkishTokenizer.DEFAULT;
    private TurkishSentenceAnalyzer analyzer = ZemberekSentenceAnalyzer.getSentenceAnalyzer();


    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter)) {
            return false;
        }

        W2VTokenDAO w2VTokenDAO = new W2VTokenDAO();
        sentences = new SentenceDAO().getAllSentences();

        //convertedSentences' ı oluşturur
        if(parameter[1].equals("stem")) {
            w2VTokens = w2VTokenDAO.getTokens(true);
            prepareByStem();
        }
        else {
            w2VTokens = w2VTokenDAO.getTokens(false);
            prepareByLetter();
        }

        //w2vValuesSentences' ı oluşturur
        if(parameter[2].equals("near"))
            prepareByNear();
        else
            prepareByAverage();

        //input-output dosyalarını oluştur
        writeToFileSentence2W2V(w2vValues);

        return true;
    }

    public void prepareByNear() {

    }

    public void prepareByAverage() {
        for(String sentence: convertedSentences.keySet()) { //her bir cümle için
            List<Float> sentenceValue = findAverageValue(sentence);
            List<List<Float>> questionsValues = new ArrayList<List<Float>>();

            for(String question: convertedSentences.get(sentence)) { // her bir soru için
                List<Float> value = findAverageValue(question);
                questionsValues.add(value);
            }

            w2vValues.put(sentenceValue, questionsValues);
        }
    }

    protected List<Float> findAverageValue(String sentence) {
        List<Float> values = new ArrayList<Float>();
        String[] words = sentence.split(" ");
        List<List<Float>> wordValues = new ArrayList<List<Float>>();
        int count = 0;

        for(int i = 0; i < words.length; i++) {
            if( w2VTokens.containsKey(words[i])) {
                count++;
                wordValues.add(w2VTokens.get(words[i]).getValue());
            }
        }

        for(int i = 0; i < W2VCreatorAdmin.w2vParameterMap.get("layer_size"); i++) {
            float sum = 0.0f;

            for(List<Float> xx: wordValues) {
                sum += xx.get(i);
            }

            values.add(sum / wordValues.size());
        }

        return values;
    }

    public void prepareByStem() {
        for(Sentence sentence: sentences) {
            String sentenceWord = rebuildSentenceByStem(sentence.getOriginalSentence());
            List<String> questions = new ArrayList<String>();

            for (String question : sentence.getQuestions()) {
                questions.add(rebuildSentenceByStem(question));
            }

            convertedSentences.put(sentenceWord, questions);
        }
    }

    public String rebuildSentenceByStem(String sentence) {
        SentenceAnalysis analysis = analyzer.analyze(sentence);
        analyzer.disambiguate(analysis);

        StringBuilder newSentence = new StringBuilder();

        for (SentenceAnalysis.Entry entry : analysis) {
            WordAnalysis wordAnalysis = entry.parses.get(0);
            newSentence.append(wordAnalysis.dictionaryItem.lemma + " ");
        }

        return newSentence.toString();
    }

    public void prepareByLetter() {
        for(Sentence sentence: sentences) {
            String sentenceWord = rebuildSentenceByLetter(sentence.getOriginalSentence());
            List<String> questions = new ArrayList<String>();

            for (String question : sentence.getQuestions()) {
                questions.add(rebuildSentenceByLetter(question));
            }

            convertedSentences.put(sentenceWord, questions);
        }
    }

    public String rebuildSentenceByLetter(String sentence) {
        Iterator<Token> tokenIterator = tokenizer.getTokenIterator(sentence);
        StringBuilder newSentence = new StringBuilder();

        while (tokenIterator.hasNext()) {
            Token token = tokenIterator.next();
            newSentence.append(getFirst5Letter(token.getText()) + " ");
        }

        return newSentence.toString();
    }

    public String getFirst5Letter(String text) {
        if (text.length() >= 5)
           return text.substring(0, 5);
        else
            return text;
    }

    protected boolean writeToFileSentence2W2V(Map<List<Float>, List<List<Float>>> w2vValues) {
        try {
            PrintWriter pwInput = new PrintWriter("input.txt");
            PrintWriter pwOutput = new PrintWriter("output.txt");

            for(List<Float> sentenceValue: w2vValues.keySet()) { //her bir cümle için
                for(List<Float> questionValues: w2vValues.get(sentenceValue)) { // her bir soru için
                    //soru - input için dosyaya yaz
                    for(Float value: questionValues) {
                        pwInput.print(value + " ");
                    }
                    pwInput.println();

                    //cevap - output için dosyaya yaz
                    for(Float value: sentenceValue) {
                        pwOutput.print(value + " ");
                    }
                    pwOutput.println();
                }
            }

            pwInput.close();
            pwOutput.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean validateParameter(String[] parameter) {
        return (parameter.length == 3 &&
                (parameter[1].equals("stem") || parameter[1].equals("letter")) &&
                (parameter[2].equals("average") || parameter[2].equals("near")));
    }
}
