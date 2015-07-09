import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class JAVA_577 {

	public static void main (String args []){
		Cluster cluster = Cluster.builder().addContactPoint("130.211.158.230").build();
		cluster.getConfiguration().getSocketOptions().setConnectTimeoutMillis(5);
		cluster.getConfiguration().getSocketOptions().setReadTimeoutMillis(20);
		final Session session = cluster.connect("java577");
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(100);
		for (int i=0; i<100; ++i) {
			executorService.execute(new Runnable() {
				int count = 0;
				@Override
				public void run() {
					while (true) {
						try
						{ ResultSet rs = session.execute("select * from testks.record4 limit 1000;"); Host queriedHost = rs.getExecutionInfo().getQueriedHost(); 
						System.out.println("Invoking " + Thread.currentThread().getName() + " : " + count++ + " against the host " + queriedHost); 
						}
						catch (Exception e) {
							String message = e.getMessage();
							if (!message.contains("timeout during read query"))
							{ System.out.println("Exception " + e.getMessage()); }
						}
						Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
						}
					}
				});
			}
			Uninterruptibles.sleepUninterruptibly(100, TimeUnit.HOURS);
		}
	}

