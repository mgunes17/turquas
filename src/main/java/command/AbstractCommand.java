package command;

/**
 * Created by mustafa on 26.04.2017.
 */
public abstract class AbstractCommand {
    protected abstract boolean validateParameter(String[] parameter);
}
