import com.datastax.driver.core.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by mark on 17/07/15.
 */
public class ExampleExport {
    Cluster cluster;
    Session session;

    /**
     * Main method:
     *
     * @param args
     */
    public static void main (String args[]) throws IOException {
        ExampleExport export = new ExampleExport();
        export.runExport("192.168.56.23", "markc.test","/Users/mark/javaStuff/test.txt");

    }

    /**
     * runExport:
     * initiate cluster connection and export
     * all records from the given table
     * @param node - IP address / Hostname
     * @param table - name of target table
     */
    private void runExport(String node, String table, String outputFile) throws IOException {

        // setup local containers
        ResultSet results;
        Statement statement;
        Session session = null;
        Cluster cluster = null;
        String line;
        FileWriter fileWriter;
        PrintWriter printWriter;
        Integer count = 0;


        // Create connection to the cluster
        cluster = Cluster.builder()
                .addContactPoint(node)
                .build();
        session = cluster.connect();

        // Create the output file stream
        fileWriter = new FileWriter(outputFile, false);

        // Setup statement for export
        statement = new SimpleStatement("select * from " + table + ";");
        statement.setFetchSize(100);

        // Initiate the export of the records
        results = session.execute(statement);

        // Iterate through results and write out to file
        for (Row row : results) {
            line = String.format("%s, %s, %s\n", row.getString("key"), row.getString("col1"), row.getString("col2"));
            fileWriter.write(line);
            fileWriter.flush();
            count++;
        }

        // Close file once complete
        fileWriter.close();

        // Clean up session etc
        session.close();
        cluster.close();

        // Write summary to std out
        System.out.println("Finished. Exported " + count + " lines.");

    }

}
