/**
 * Created by mark on 26/06/15.
 */

    import java.util.ArrayList;
    import java.util.Collection;

    import com.datastax.driver.core.Cluster;
    import com.datastax.driver.core.Host;
    import com.datastax.driver.core.Metadata;
    import com.datastax.driver.core.SocketOptions;
    import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
    import com.datastax.driver.core.policies.LoadBalancingPolicy;
    import com.datastax.driver.core.policies.RoundRobinPolicy;
    import com.datastax.driver.core.policies.TokenAwarePolicy;
    import com.google.common.base.Optional;
    import com.google.common.collect.Lists;

    public class TestWithLogin {

        // the obligatory main entry point :-)
        public static void main (String args []){
            Collection<String> hosts = Lists.newArrayList("192.168.56.21", "192.168.56.22", "192.168.56.23");

            Cluster myCluster = null;
            myCluster = createCluster(hosts,9042, 600, Optional.<String>absent());

        }


        // method from customer to create cluster object
        public static Cluster createCluster(Collection<String> contactPoints, int port, int readTimeoutInMillis, Optional <String> primaryDcName) {
            final Cluster.Builder clusterBuilder = Cluster.builder();
            for (String contactPoint : contactPoints) {
                clusterBuilder.addContactPoint(contactPoint);
            }

            LoadBalancingPolicy mainPolicy = primaryDcName.isPresent() ?
                    new DCAwareRoundRobinPolicy(primaryDcName.get()) :
                    new RoundRobinPolicy();

            final Cluster cluster =
                    clusterBuilder
                            .withPort(port)
                            .withLoadBalancingPolicy(new TokenAwarePolicy(mainPolicy))
                            .withSocketOptions(new SocketOptions().setReadTimeoutMillis(readTimeoutInMillis))
                            .withCredentials("cassandra", "cassandra")
                            .build();

            final Metadata metadata = cluster.getMetadata();
            System.out.println("Connected to cluster: {}\n"+ metadata.getClusterName());
            for (Host host : metadata.getAllHosts()) {
                System.out.println("Datacenter: {}; Host: {}; Rack: {}\n"+
                        host.getDatacenter() + " " + host.getAddress() + " "+ host.getRack());
            }

            return cluster;
        }
    }
