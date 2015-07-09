import com.datastax.driver.core.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class PrepStatement {
	private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
	Cluster cluster;
	Session session;

	public static void main(String args []){
		PrepStatement myTest = new PrepStatement();
		String node = "192.168.56.21";
		myTest.runTest(node);
		myTest.close();

	}

	private void runTest(String node){
		
//		// If using strings
//		ArrayList<String> vals = new ArrayList<String>(2);
//		vals.add("one");
//		vals.add("two");
		
		// If using Integers
		ArrayList<Integer> vals = new ArrayList<Integer>(4);
		vals.add(1);
		vals.add(2);
		vals.add(3);
		vals.add(4);

		try {
			cluster = Cluster.builder()
					.addContactPoint(node)
					.withCredentials("cassandra", "cassandra") // only if you need a login
					.build();
			session = cluster.connect();
			PreparedStatement preparedStatement = session.prepare("SELECT id, first_name, last_name FROM spam.names WHERE id IN ?");
//			PreparedStatement preparedStatement = session.prepare("SELECT id, first_name FROM spam.names WHERE id IN ?").enableTracing();
			BoundStatement boundStatement = new BoundStatement(preparedStatement);
			ResultSet results = session.execute(boundStatement.bind(vals));
			ExecutionInfo executionInfo = results.getExecutionInfo();
			for (Object result : results){
				System.out.println(result.toString());				
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
	
	private void printTrace(ExecutionInfo execInf){
		System.out.printf( "Host (queried): %s\n", execInf.getQueriedHost().toString() );
	      for (Host host : execInf.getTriedHosts()) {
	         System.out.printf( "Host (tried): %s\n", host.toString() );
	      }
	      QueryTrace queryTrace = execInf.getQueryTrace();
	      System.out.printf("Trace id: %s\n\n", queryTrace.getTraceId());
	      System.out.println("---------------------------------------+--------------+------------+--------------");
	      for (QueryTrace.Event event : queryTrace.getEvents()) {
	         System.out.printf("%38s | %12s | %10s | %12s\n", event.getDescription(),
	               millis2Date(event.getTimestamp()), 
	               event.getSource(), event.getSourceElapsedMicros());
	      }
	}
	
	   private Object millis2Date(long timestamp) {
			return format.format(timestamp);
		   }

	/**
	 * cleans up the session
	 */
	private void close() {	
		session.close();
		cluster.close();
	}
}


