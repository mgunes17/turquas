package command.db_updater_command;

import command.AbstractCommand;
import command.Command;
import db.dao.TokenDAO;
import model.Token;
import nlp_tool.itu.LabelMorph;

import java.util.Set;

/**
 * Created by mustafa on 26.04.2017.
 */
public class MorphologicCommand extends AbstractCommand implements Command {
    private TokenDAO tokenDAO;
    private LabelMorph label;
    private int count;

    public MorphologicCommand(TokenDAO tokenDAO, LabelMorph label) {
        this.tokenDAO = tokenDAO;
        this.label = label;
    }

    public boolean execute(String[] parameter) {
        if(validateParameter(parameter)) {
            Set<Token> tokenSet = tokenDAO.getUnlabeledToken(count);
            label.labelMorph(tokenSet);
            tokenDAO.saveLabeledToken(tokenSet);
            return true;
        } else {
            return false;
        }
    }

    protected boolean validateParameter(String[] parameter) {
        if(parameter.length == 2) {
            parseCount(parameter[1]);
            return true;
        } else
            System.out.println("Eksik/Fazla parametre");

        return false;
    }

    private void parseCount(String count) {
        try {
            this.count = Integer.parseInt(count);
        } catch (NumberFormatException ex) {
            System.out.println("Lütfen sayısal bir değer giriniz.");
        }
    }
}
