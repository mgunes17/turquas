package command.question_generator_command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mustafa on 09.05.2017.
 */
public class ConvertCommandTest {
    ConvertCommand convertCommand;
    String[] commands1;
    String[] commands2;


    @Before
    public void setUp() throws Exception {
        convertCommand = new ConvertCommand();
        commands1 = new String[1];
        commands1[0] = "convert";
        commands2 = new String[2];
        commands2[0] = "convert";
        commands2[1] = "100";
    }

    @After
    public void tearDown() throws Exception {
        //yeni oluşturulanları vt den sil
    }

    @Test
    public void execute() throws Exception {
        assertEquals(false, convertCommand.execute(commands1));
        assertEquals(true, convertCommand.execute(commands2));
        //ayrıca vt den kontrol et
    }

    @Test
    public void validateParameter() throws Exception {
        assertEquals(false, convertCommand.validateParameter(commands1));
        assertEquals(true, convertCommand.validateParameter(commands2));
    }

}