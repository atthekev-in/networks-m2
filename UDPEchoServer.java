import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException

public class UDPEchoServer {

  private static final int ECHOMAX = 255;  // Maximum size of echo datagram

  public static void main(String[] args) throws IOException {

    if (args.length != 1)  // Test for correct argument list
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]);

    DatagramSocket socket = new DatagramSocket(servPort);
    DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

    for (;;) {  // Run forever, receiving and echoing datagrams
      socket.receive(packet);     // Receive packet from client
      System.out.println("Handling client at " +
        packet.getAddress().getHostAddress() + " on port " + packet.getPort());
      String data = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
      System.out.println(data);
      String message = data.replaceAll("[AEIOUaeiou]", "");
      System.out.println(message);
      packet.setData(message.getBytes("UTF-8"));      
      socket.send(packet);       // Send the same packet back to client
      packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

    }
    /* NOT REACHED */
  }
}
