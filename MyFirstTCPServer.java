import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream
import java.nio.charset.StandardCharsets;
public class MyFirstTCPServer {
  private static final int BUFSIZE = 32;   // Size of receive buffer
  public static void main(String[] args) throws IOException {
    if (args.length != 1)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");
    int servPort = Integer.parseInt(args[0]);
    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);
    int recvMsgSize;   // Size of received message
    byte[] byteBuffer = new byte[BUFSIZE];  // Receive buffer
    for (;;) { // Run forever, accepting and servicing connections
      Socket clntSock = servSock.accept();     // Get client connection
      System.out.println("Handling client at " +
              clntSock.getInetAddress().getHostAddress() + " on port " +
              clntSock.getPort());
      InputStream in = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();
      // Receive until client closes connection, indicated by -1 return
      while ((recvMsgSize = in.read(byteBuffer)) != -1) {
        String data = new String(byteBuffer, StandardCharsets.UTF_8);
        System.out.println(data);
        String message = data.replaceAll("[AEIOUaeiou]", "");
        System.out.println(message);
        byteBuffer = message.getBytes();
        out.write(byteBuffer, 0, recvMsgSize);
      }
      clntSock.close();  // Close the socket.  We are done with this client!
    }
    /* NOT REACHED */
  }
}
