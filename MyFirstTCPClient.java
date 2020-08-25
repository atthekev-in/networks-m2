import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream
import static java.lang.System.*;
public class MyFirstTCPClient {
  public static void main(String[] args) throws IOException {
    if ((args.length < 2) || (args.length > 3))  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");
    String server = args[0];       // Server name or IP address
    // Convert input String to bytes using the default character encoding
    byte[] byteBuffer = args[1].getBytes();
    int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;
    // Create socket that is connected to server on specified port
    Socket socket = new Socket(server, servPort);
    out.println("Connected to server...sending echo string");
    InputStream in = socket.getInputStream();
    OutputStream out = socket.getOutputStream();
    long start_time = currentTimeMillis();
    out.write(byteBuffer);  // Send the encoded string to the server
    // Receive the same string back from the server
    int totalBytesRcvd = 0;  // Total bytes received so far
    int bytesRcvd;           // Bytes received in last read
    while (totalBytesRcvd < byteBuffer.length) {
      if ((bytesRcvd = in.read(byteBuffer, totalBytesRcvd,  
                        byteBuffer.length - totalBytesRcvd)) == -1)
        throw new SocketException("Connection close prematurely");
      totalBytesRcvd += bytesRcvd;
    }
    long end_time = currentTimeMillis();
    long elapsed_time = end_time - start_time;
    System.out.println("Received: " + new String(byteBuffer));
    System.out.println("Elapsed time: " + elapsed_time + " m/s");
    socket.close();  // Close the socket and its streams
  }
}


