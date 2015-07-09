/**
 * Created by mark on 26/06/15.
 */
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class DSEtest {
    Cluster cluster;
    Session session;

    public static void main (String args[]){
        DSEtest test = new DSEtest();
        test.runTest("192.168.56.21");

    }
    private void runTest(String node){
        cluster = Cluster.builder()
                .addContactPoint(node)
                .build();
        session = cluster.connect();

        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
        for ( Host host : metadata.getAllHosts() ) {
            System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(), host.getRack());
        }
    }

}
