package command.w2v_creator_command;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mustafa on 21.05.2017.
 */
public class NameEntityPrepareCommandTest {
    private String[] trueCommand1;
    private String[] trueCommand2;
    private String[] falseCommand1;
    private String[] falseCommand2;

    @Before
    public void setUp() {
        trueCommand1 = new String[3];
        trueCommand1[0] = "ne";
        trueCommand1[1] = "stem";
        trueCommand1[2] = "near";

        trueCommand2 = new String[3];
        trueCommand2[0] = "ne";
        trueCommand2[1] = "letter";
        trueCommand2[2] = "average";

        falseCommand1 = new String[3];
        falseCommand1[0] = "ne";
        falseCommand1[1] = "stem";
        falseCommand1[2] = "stem";

        falseCommand2 = new String[2];
        falseCommand2[0] = "ne";
        falseCommand2[1] = "letter";
    }

    @Test
    public void validateParameter() throws Exception {
        NameEntityPrepareCommand command = new NameEntityPrepareCommand();
        assertEquals(true, command.validateParameter(trueCommand1));
        assertEquals(true, command.validateParameter(trueCommand2));
        assertEquals(false, command.validateParameter(falseCommand1));
        assertEquals(false, command.validateParameter(falseCommand2));
    }

}