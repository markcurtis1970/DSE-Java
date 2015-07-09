import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.policies.ExponentialReconnectionPolicy;
import com.datastax.driver.core.policies.ReconnectionPolicy;


public class t13752 {
	
	public static void main (String args []){
		t13752 myTest = new t13752();
		for (int i=0; i < 1001; i ++){
		String hosts[] = {"192.168.56.21","192.168.56.22","192.168.56.23"};
		myTest.initializeDatastaxClient(hosts, "results",Boolean.FALSE);
		System.out.println("connect attempt: " + i);
		}
	}

	private Session session;

	private void initializeDatastaxClient(String[] cassandraHosts, String cassandraKeySpaceName, final boolean noKeySpace) {

		QueryOptions queryOptions = new QueryOptions();

		queryOptions.setConsistencyLevel(ConsistencyLevel.QUORUM);

		PoolingOptions poolingOptions = new PoolingOptions()

		.setMaxConnectionsPerHost(HostDistance.LOCAL, 30)

		.setCoreConnectionsPerHost(HostDistance.LOCAL, 10);

		SocketOptions socketOptions = new SocketOptions()

		.setConnectTimeoutMillis(6000)

		.setReadTimeoutMillis(6000);

		ReconnectionPolicy reconnectionPolicy = new ExponentialReconnectionPolicy(100, 2000);

		Cluster cluster = Cluster.builder()

				.withClusterName("HawkeyeCluster")

				.withPort(9042)

				.addContactPoints(cassandraHosts)

				.withQueryOptions(queryOptions)

				.withReconnectionPolicy(reconnectionPolicy)

				.withPoolingOptions(poolingOptions)

				.withSocketOptions(socketOptions)

				.build();

		this.session = noKeySpace ? cluster.connect() : cluster.connect(cassandraKeySpaceName);

		System.out.println("initialization complete");
		
		System.out.println(this.session.getLoggedKeyspace());

	}
}
