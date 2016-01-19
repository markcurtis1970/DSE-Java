/**
 * Created by mark on 26/06/15.
 */

import com.datastax.driver.core.*;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;


public class TestSSL {
    public static void main(String[] args) throws Exception
    {
        SSLContext context = getSSLContext("/Users/mark/deploy/datastax.truststore",
                "datastax",
                "/Users/mark/deploy/datastax.keystore",
                "datastax"); // truststore first, keystore second

        Session session = null;

        // Default cipher suites supported by C*
        String[] cipherSuites = { "TLS_RSA_WITH_AES_128_CBC_SHA",
                "TLS_RSA_WITH_AES_256_CBC_SHA" };

        Cluster cluster = Cluster.builder()
                .addContactPoints("54.183.130.240")
                        .withSSL(new SSLOptions(context, cipherSuites))
                                //.withCredentials("cassandra", "cassandra") // comment out for no auth
                        .build();
        try {
            System.out.println("connecting...");
            session = cluster.connect();
            System.out.println(session.getState().toString());
            ResultSet myResults;
            myResults = session.execute("select * from system.peers");
            for (Row myRow : myResults) {
                System.out.println(myRow.toString());
            }
            /**
            myResults = session.execute("select * from markc.testme");
            for (Row myRow : myResults) {
                System.out.println(myRow.toString());
            }**/

        }
        catch (Exception e){
            e.printStackTrace();
        }

        session.close();
        cluster.close();
    }

    private static SSLContext getSSLContext(String truststorePath,
                                            String truststorePassword,
                                            String keystorePath,
                                            String keystorePassword)
            throws Exception
    {
        FileInputStream tsf = new FileInputStream(truststorePath);
        FileInputStream ksf = new FileInputStream(keystorePath);
        SSLContext ctx = SSLContext.getInstance("SSL");

        KeyStore ts = KeyStore.getInstance("JKS");
        ts.load(tsf, truststorePassword.toCharArray());
        TrustManagerFactory tmf =
                TrustManagerFactory.getInstance(
                        TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ts);

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(ksf, keystorePassword.toCharArray());
        KeyManagerFactory kmf =
                KeyManagerFactory.getInstance(
                        KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, keystorePassword.toCharArray());

        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
        return ctx;
    }
}
