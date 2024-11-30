import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class SecureClient {
    public static void main(String[] args) throws Exception {
        KeyStore clientTrustStore = KeyStore.getInstance("JKS");
        clientTrustStore.load(new FileInputStream("clientTruststore.jks"), "passserver123".toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(clientTrustStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        SSLSocketFactory ssf = sslContext.getSocketFactory();
        SSLSocket socket = (SSLSocket) ssf.createSocket("localhost", 4001);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Server says: " + in.readLine());
        out.println("Hello, server!");

        socket.close();
    }
}