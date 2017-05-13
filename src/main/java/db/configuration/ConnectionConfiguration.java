package db.configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Created by mustafa on 26.04.2017.
 */
public class ConnectionConfiguration {
    private static Session session = null;

    public static Session getSession() {
        if(session == null)
            session = Cluster.builder().addContactPoint("127.0.0.1").build().connect(ModelVariables.KEYSPACE);

        return session;
    }
}
