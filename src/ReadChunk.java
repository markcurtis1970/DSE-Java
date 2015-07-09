import java.util.ArrayList;
import java.util.List;


public class ReadChunk {

	List<String>keys = new ArrayList<>();
	List<String>values = new ArrayList<>();
	CassandraClient client = new CassandraClient();

	public ReadChunk() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Main method, you got to have it!
	 * 
	 * @param args
	 */
	public static void main (String [] args) {
		ReadChunk myClass = new ReadChunk();
		myClass.loadData();
		System.exit(0);
	}
	
	/**
	 * load up some data into the table and then read it back
	 */
	private void loadData() {
		generateData(1000, "text1", "cheese"); // generate some data load it into class wide list object
		client.connect("192.168.56.21");
		client.runQuery("use mytest"); // sets the keyspace
		client.makePrepStatement("insert into safi(text1, text2) values (?,?);"); // makes the prep statement
		client.insert(values); // now do the insert
		readData(); // reads the data back
		client.close(); // close and clean up
		}
	
	/**
	 * reads the data and sets a fetch size
	 */
	private void readData(){
		client.fetchStatement("select * from safi;", 5);
	}
	
	private void generateData(Integer count, String keyPrefix, String valuePrefix){
		Integer ptr;
		
		for (ptr=1; ptr<count; ptr++){
			keys.add(keyPrefix);
			values.add(valuePrefix + ptr.toString());
		}
		
	}
	
	private void setupConnection(String nodeip){
		client.connect(nodeip);
	}

	private void cleanupConnection(){
		client.close();
	}
	
}
