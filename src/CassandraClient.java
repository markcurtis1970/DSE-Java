import com.datastax.driver.core.*;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.exceptions.QueryExecutionException;
import com.datastax.driver.core.exceptions.QueryValidationException;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import java.util.List;

public class CassandraClient {

	private Cluster cluster;
	private Session session;
	private PreparedStatement prep;
		
	/**
	 * Sets up a session to a given node in a cluster
	 * @param node
	 */
	public void connect(String node) {
			cluster = Cluster.builder()
			         .addContactPoint(node)
			         .withCredentials("cassandra", "cassandra")
			         .build();
			session = cluster.connect();
		}
		
	/**
	 * cleans up the session
	 */
	public void close() {	
		session.close();
		cluster.close();
			}
	
	/**
	 * This method takes a table name and a set of keys and values, should work with any type of table... he says..!
	 * Its only good for one row at a time though... but good if you are writing a whole load of rows I think.
	 * 
	 * @param table
	 * @param keys
	 * @param values
	 */
	public void insert(String table, List<String>keys, List<Object>values){
		Insert insert;
		
//		List<String> keysList = new ArrayList(keyvalues.keySet());
//		List<String> valuesList = new ArrayList(keyvalues.values());
	
		String loadkeys[];
		Object loadvalues[];
		loadkeys = keys.toArray(new String[0]);
		loadvalues = values.toArray();
		
		insert = QueryBuilder.insertInto(table).values(loadkeys, loadvalues);
		try {
			session.execute(insert);
		}
		catch (Exception e) {
	         e.printStackTrace();
	         System.out.println(insert.toString());
	      } 
	}

	/**
	 * You need to use this with the makePrepStatement below
	 * 
	 * @param values
	 */
	public void insert(List<String>values){
		try {
			for (String val : values){
				session.execute(prep.bind(val, val + "value2"));
			}
		}
		catch (Exception e) {
	         e.printStackTrace();
	      } 
	}
	
	/**
	 * make a prepared statement object
	 * @param statement
	 */
	public void makePrepStatement(String statement){
		prep = session.prepare(statement);
	}
	
	/**
	 * reads from the DB using a given set size
	 * @param statement
	 * @param size
	 */
	public void fetchStatement(String statement, Integer size){
		SimpleStatement stm = new SimpleStatement(statement);
		stm.setFetchSize(size);
		ResultSet results = session.execute(stm);
		int counter = 0;
		for ( Row row : results ) {
			System.out.println(row.toString() + "\trow " + counter);
			counter ++;
		   }
		
	}
	
	public void runQuery(String query){
		SimpleStatement statement = new SimpleStatement(query);
		try {
			ResultSet results = session.execute(statement);
			for (Row row: results) {
				System.out.println(row.toString());
			}
		}
		catch (NoHostAvailableException e) {
	         e.printStackTrace();
	      } 
		catch (QueryExecutionException e) {
	         e.printStackTrace();
	      } 
		catch (QueryValidationException e) {
	         e.printStackTrace();
	      } 
		catch (IllegalStateException e) {
	         e.printStackTrace();
	      }
	}
	public void quickTest(){
		   Metadata metadata = cluster.getMetadata();
		   System.out.printf("Connected to cluster: %s\n", 
		         metadata.getClusterName());
		   for ( Host host : metadata.getAllHosts() ) {
		      System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
		         host.getDatacenter(), host.getAddress(), host.getRack());
		   }
		
	
	}
	
}
