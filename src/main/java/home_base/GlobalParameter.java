package home_base;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mustafa on 23.04.2017.
 */
public class GlobalParameter {
    private static Set<String> savedTokenSet; //değişikliklerin vt ye yansıtılması gerek
    private static Set<String> newTokenSet;

    static {
        savedTokenSet = new HashSet<String>();
        //savedTokenList i vt den oku
        newTokenSet = new HashSet<String>();
    }

}
