import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class SecureServer {
    public static void main(String[] args) throws Exception {
        KeyStore serverKeyStore = KeyStore.getInstance("JKS");
        serverKeyStore.load(new FileInputStream("serverkey.jks"), "stpass123".toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(serverKeyStore, "stpass123".toCharArray());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);

        SSLServerSocketFactory ssf = sslContext.getServerSocketFactory();
        SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(4001);
        System.out.println("Server started on port 4001");

        SSLSocket socket = (SSLSocket) serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println("Hello, client!");
        System.out.println("Client says: " + in.readLine());

        socket.close();
        serverSocket.close();
    }
}
