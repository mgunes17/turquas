package command.question_generator_command;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mustafa on 21.05.2017.
 */
public class SaveCommandTest {
    private SaveCommand saveCommand;
    private String[] trueCommand1 = new String[3];
    private String[] trueCommand2 = new String[2];
    private String[] falseCommand1 = new String[1];
    private String[] falseCommand2 = new String[2];

    @Before
    public void setup() {
        saveCommand = new SaveCommand();

        trueCommand1 = new String[3];
        trueCommand1[0] = "save";
        trueCommand1[1] = "4";
        trueCommand1[2] = "w2v";

        trueCommand2 = new String[2];
        trueCommand2[0] = "save";
        trueCommand2[1] = "12";

        falseCommand1[0] = "save";

        falseCommand2[0] = "save";
        falseCommand2[1] = "1a";
    }

    @Test
    public void validateParameter() throws Exception {
        assertEquals(true, saveCommand.validateParameter(trueCommand1));
        assertEquals(true, saveCommand.validateParameter(trueCommand2));
        assertEquals(false, saveCommand.validateParameter(falseCommand1));
        assertEquals(false, saveCommand.validateParameter(falseCommand2));

    }

}