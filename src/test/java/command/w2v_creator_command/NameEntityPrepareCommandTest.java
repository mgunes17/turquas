package command.w2v_creator_command;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mustafa on 21.05.2017.
 */
public class NameEntityPrepareCommandTest {
    private String[] trueCommand1;
    private String[] falseCommand1;

    @Before
    public void setUp() {
        trueCommand1 = new String[1];
        trueCommand1[0] = "ne";

        falseCommand1 = new String[1];
        falseCommand1[0] = "ney";
    }

    @Test
    public void validateParameter() throws Exception {
        NameEntityPrepareCommand command = new NameEntityPrepareCommand();
        assertEquals(true, command.validateParameter(trueCommand1));
        assertEquals(false, command.validateParameter(falseCommand1));
    }

}