package component.question_generator.factory.zemberek;

import component.question_generator.factory.QuestionFactory;
import component.question_generator.factory.zemberek.type.*;
import component.question_generator.factory.zemberek.type.suffix.*;
import component.question_generator.word.Question;
import component.question_generator.word.Sentence;
import component.question_generator.word.Word;
import nlp_tool.zemberek.ZemberekSentenceAnalyzer;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;

import java.util.*;

/**
 * Created by mustafa on 26.03.2017.
 */
public class ZemberekQuestionFactory extends QuestionFactory {
    private final String sentence;
    private TurkishSentenceAnalyzer analyzer;
    private List<Word> wordList = new ArrayList<Word>();
    private Set<QuestionType> questionTypeSet = new HashSet<QuestionType>();
    private Map<String, QuestionType> singleTypes = new HashMap<String, QuestionType>();

    public ZemberekQuestionFactory(String sentence) {
        this.sentence = sentence;
        analyzer = ZemberekSentenceAnalyzer.getSentenceAnalyzer();
        singleTypes.put("abl", new AblQuestion());
        singleTypes.put("acc", new AccQuestion());
        singleTypes.put("dat", new DatQuestion());
        singleTypes.put("loc", new LocQuestion());
        singleTypes.put("cop", new CopQuestion());
        singleTypes.put("inst", new InstQuestion());
        singleTypes.put("time", new TimeQuestion());
        singleTypes.put("gen", new GenQuestion());
    }

    public Sentence getQuestionList() {
        List<Question> generatedList = new ArrayList<Question>();
        Set<String> secondaryPosTags = new HashSet<String>();
        Map<String, QuestionType> tagMap = new HashMap<String, QuestionType>();
        tagMap.put("Time", new TimeQuestion());
        tagMap.put("Clock", new TimeQuestion());
        tagMap.put("Date", new TimeQuestion());

        SentenceAnalysis analysis = analyzer.analyze(sentence);
        analyzer.disambiguate(analysis);

        Word word;
        //secondary pos ları sete at
        for (SentenceAnalysis.Entry entry : analysis) {
            WordAnalysis wa = entry.parses.get(0);
            secondaryPosTags.add(wa.dictionaryItem.secondaryPos.toString());

            word = new Word(entry.input);
            word.setLog(wa.formatLong());
            word.setSuffix(getSuffix(word));
            word.setPrimaryPos(wa.dictionaryItem.primaryPos.toString());
            word.setSecondaryPos(wa.dictionaryItem.secondaryPos.toString());
            if(wa.dictionaryItem.primaryPos.toString().equals("Noun") && wa.formatLong().contains("Cop")){
                questionTypeSet.add(singleTypes.get("cop"));
                word.setCopQuestion(true);
            }
            if( wa.formatLong().contains("Inst")){
                questionTypeSet.add(singleTypes.get("inst"));
                word.setInstQuestion(true);
            }

            wordList.add(word);
        }

        //secondary posların soru tipini set e at
        for(String tag: secondaryPosTags) {
            questionTypeSet.add(tagMap.get(tag));
        }

        for(QuestionType type: questionTypeSet) {
            if(type != null) {
                generatedList.addAll(generate(type));
            }
        }

        Sentence s = new Sentence(sentence);
        s.setQuestionList(generatedList);
        return s;
    }

    private List<Question> generate(QuestionType type) {
        return type.reorganize(wordList);
    }

    private Suffix getSuffix(Word word) {
        String log = word.getLog() + " " + word.getSecondaryPos() + " " + word.getPrimaryPos();

        if(log.contains("Acc")) {
            questionTypeSet.add(singleTypes.get("acc"));
            return Suffix.ACCUSATIVE;
        }
        else if(log.contains("Dat")) {
            questionTypeSet.add(singleTypes.get("dat"));
            return Suffix.DATIVE;
        }
        else if(log.contains("Loc")) {
            questionTypeSet.add(singleTypes.get("loc"));
            return Suffix.LOCATIVE;
        }
        else if(log.contains("Abl")) {
            questionTypeSet.add(singleTypes.get("abl"));
            return Suffix.ABLATIVE;
        }else if(log.contains("Gen")) {
            questionTypeSet.add(singleTypes.get("gen"));
            word.setGen(true);
            return Suffix.PLAIN;
        }
        else{
            return Suffix.NONE;
        }
    }
}
