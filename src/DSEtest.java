import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class DSEtest {
    Cluster cluster;
    Session session;

    public static void main (String args[]){
        DSEtest test = new DSEtest();
        test.runTest(args[0]);

    }
    private void runTest(String node) {
        cluster = Cluster.builder()
                .addContactPoint(node)
                .build();
        session = cluster.connect();

        Metadata metadata = cluster.getMetadata();
        try {

            System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
            for (Host host : metadata.getAllHosts()) {
                System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(), host.getRack());
            }
            System.out.printf("Schema agrement: %s\n", metadata.checkSchemaAgreement());
            System.out.printf("Keyspaces: %s\n", metadata.getKeyspaces());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        cluster.close();
    }

}
