package deneme;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import db.configuration.ConnectionConfiguration;
import db.configuration.MappingManagerConfiguration;
import db.dao.QuestionDAO;
import model.Question;

/**
 * Created by mustafa on 04.06.2017.
 */
public class DenemeTest {
    public static void main(String[] args) {
        ConnectionConfiguration.getSession();
        MappingManagerConfiguration.getMappingManager();
        Mapper<Question> questionMapper = MappingManagerConfiguration.getQuestionMapper();
        Question question = new Question();
        question.setSourceName("https://tr.wikipedia.org/wiki/Pazar_Ligi");
        question.setQuestion("21914-15 ne zaman lig , takımların çokluğu sebebiyle İstanbul Futbol Birliği Ligi ve İstanbul Şampiyonluğu Ligi olarak adlandırılan iki grup halinde düzenlendi ?");
        question.setNounClause(false);

        Question question1 = new QuestionDAO().getQuestionsByLimit(1).get(0);
        System.out.println("gelen" + question1.getSourceName());
        question1.setSourceName("kafamıza göre");
        questionMapper.save(question1);
    }
}
