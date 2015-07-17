/**
 * Created by mark on 09/07/15.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.AlreadyExistsException;
public class TestAlreadyExists {


    private static void createKeyspace(Cluster cluster, String keyspace) {
        Session session = cluster.connect();
        StringBuilder expression = new StringBuilder();
        expression.append("create keyspace ");
        expression.append(keyspace);
        expression.append(" with replication = { 'class' : 'SimpleStrategy', 'replication_factor': '1'}");
        session.execute(expression.toString());
        session.close();
    }

    private static void createTable(Session session, String table) throws IOException {
        System.out.println(" - create table");
        session.execute(getContent("/Users/mark/javaStuff/MarkC/TestAlreadyExists/" + table + "/table.txt"));
    }

    private static void waitForSchemaAgreement(Cluster cluster) {
        while (!cluster.getMetadata().checkSchemaAgreement()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getContent(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        //BufferedReader br = new BufferedReader(new InputStreamReader(TestAlreadyExists.class.getResourceAsStream(path)));
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        Builder builder = Cluster.builder();
        builder.addContactPoint("192.168.56.21");

        Cluster cluster = builder.build();

        PoolingOptions pools = cluster.getConfiguration().getPoolingOptions();

        pools.setCoreConnectionsPerHost(HostDistance.REMOTE, 2);
        pools.setMaxConnectionsPerHost(HostDistance.REMOTE, 2);

        for (int i = 1; i < 50; i++) {

            String keyspace = "test" + i;
            if (!cluster.getMetadata().checkSchemaAgreement()) {
                waitForSchemaAgreement(cluster);
            }
            try {
                createKeyspace(cluster, keyspace);
            } catch (AlreadyExistsException ae) {
                System.out.println(">>>WARN: " + ae.getMessage() + " <<<");
            }
            if (!cluster.getMetadata().checkSchemaAgreement()) {
                waitForSchemaAgreement(cluster);
            }

            Session session = cluster.connect(keyspace);
            String[] tables = {"Agent",
                    "Award",
                    "Contribution",
                    "Customer",
                    "EditorialObject",
                    "HistoricalNote",
                    "Location",
                    "Opus",
                    "Profession",
                    "PublicationEvent",
                    "Role"};

            for (String table : tables) {
                System.out.println("table " + table);
                if (!cluster.getMetadata().checkSchemaAgreement()) {
                    waitForSchemaAgreement(cluster);
                }
                try {
                    createTable(session, table);
                } catch (AlreadyExistsException ae) {
                    System.out.println(">>>WARN: " + ae.getMessage() + " <<<");
                }
                if (!cluster.getMetadata().checkSchemaAgreement()) {
                    waitForSchemaAgreement(cluster);
                }

                System.out.println("done " + i);
            }
        }
    }
}

