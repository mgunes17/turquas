package component.question_generator.generate;

import component.question_generator.factory.QuestionFactory;
import component.question_generator.factory.itu.ItuQuestionFactory;
import component.question_generator.factory.zemberek.ZemberekQuestionFactory;
import component.question_generator.word.Question;
import component.question_generator.word.Sentence;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by mustafa on 22.03.2017.
 */
public class MainGenerator {
    private QuestionFactory factory;
    private Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        /*MainGenerator generator = new MainGenerator();
        //ZemberekSentenceAnalyzer.getSentenceAnalyzer();
        generator.run();*/
    }

    public Set<String> convertQuestions(String sentence) {
        QuestionFactory factoryZemberek = new ZemberekQuestionFactory(sentence);
        factory = new ItuQuestionFactory(sentence);
        Set<String> questionSentence = new HashSet<String>();

        runFactory(factoryZemberek, questionSentence);
        runFactory(factory, questionSentence);

        return questionSentence;
    }

    protected void runFactory(QuestionFactory factory, Set<String> questionSentence) {
        Sentence sentence1 = factory.getQuestionList();
        Set<Question> questions = sentence1.getQuestionList();

        if(questions.size() == 0)
            System.out.println("Soru üretilemedi :(");

        for(Question question: questions) {
            System.out.println(question.getQuestionSentence());
            System.out.println(question.getAnswer());
            questionSentence.add(question.getQuestionSentence());
        }
    }

    /*private boolean save() {
        List<String> sentenceList = new ArrayList<String>();
        sentenceList.add("Türkiye'nin başkenti Ankara'dır .");
        sentenceList.add("Yarın sabah İstanbul' dan dönecek .");
        sentenceList.add("Elmaların 5te 2sini yedi .");
        sentenceList.add("Kitabı bitireli çok oldu .");
        sentenceList.add("Öğrencilerin 5i okula gelmedi.");
        sentenceList.add("Elmaların 60ını suya düşürdü .");
        sentenceList.add("O elmaların 6da 2sinin yarısını yedi .");
        sentenceList.add("Ali İstanbul'dan Ankara'ya gelen adamdır .");
        sentenceList.add("Ali İstanbul'dan Ankara'ya yarın gelecek olan adamdır. .");
        sentenceList.add("Adam hastanede yatıyor .");
        sentenceList.add("Kitabı bitirince sana vereceğim .");
        sentenceList.add("İstanbul'u fetheden kişi Fatih Sultan Mehmet'tir.");


        List<Sentence> savedSentences = new ArrayList<Sentence>();

        for(String sentence: sentenceList) {
            Sentence sentence1 = new Sentence(sentence);
            sentence1.setQuestionList(convertQuestions(sentence));
            savedSentences.add(sentence1);
        }

        SaveType saveType = new FileSave();
        return saveType.save(savedSentences);
    }*/
}
