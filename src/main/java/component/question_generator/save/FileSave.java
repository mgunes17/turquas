package component.question_generator.save;

import component.question_generator.word.Sentence;
import model.Question;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by mustafa on 31.03.2017.
 */
public class FileSave implements SaveType {
    private File file;
    private FileWriter fileWriter;

    public boolean save(List<Sentence> sentenceList) {
        try {
            file = new File("soruCevap.txt");
            file.createNewFile();
            fileWriter = new FileWriter(file);

            for(Sentence sentence: sentenceList) {
                fileWriter.write(sentence.getSentence() + "\n\n");

                for(Question question: sentence.getQuestionList()) {
                    fileWriter.write(question.getQuestion() + "\n");
                    fileWriter.write(question.getAnswer() + "\n\n");
                }

                fileWriter.write("###############################\n\n");
            }

            fileWriter.flush();
            fileWriter.close();
            return true;
        } catch (IOException exception) {
            System.out.println("IOException :" + exception.getMessage());
            exception.printStackTrace();
            return false;
        }
    }
}
