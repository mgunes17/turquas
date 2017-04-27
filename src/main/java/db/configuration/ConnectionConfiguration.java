package db.configuration;

import com.datastax.driver.core.Cluster;

/**
 * Created by mustafa on 26.04.2017.
 */
public class ConnectionConfiguration {
    private static Cluster cluster = null;

    public static Cluster getCLuster() {
        if(cluster == null)
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        return cluster;
    }

}
