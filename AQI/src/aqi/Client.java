package aqi;

import java.io.*;
import java.util.*;
import java.net.*;

public class Client {

    public static int destPort = 1234;
    public static String hostname = "localhost";

    public static void main(String[] args) throws ClassNotFoundException {
        DatagramSocket socket;
        DatagramPacket dpsend, dpreceive;
        InetAddress add;
        Scanner stdIn;
        try {
            add = InetAddress.getByName(hostname);	
            socket = new DatagramSocket();			
            stdIn = new Scanner(System.in);
            while (true) {
                System.out.print("Client input: ");
                String tmp = stdIn.nextLine();
                byte[] data = tmp.getBytes();
                dpsend = new DatagramPacket(data, data.length, add, destPort);
                System.out.println("Client sent " + tmp + " to " + add.getHostAddress()
                        + " from port " + socket.getLocalPort());
                socket.send(dpsend);				//IOExeption
                if (tmp.equals("bye")) {
                    System.out.println("Client socket closed");
                    stdIn.close();
                    socket.close();
                    break;
                }
                // Get response from server
                dpreceive = new DatagramPacket(new byte[2048], 2048);
                socket.receive(dpreceive);
                tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
                String[] arraycountry = tmp.split(",");
                for (int i = 0; i < arraycountry.length; i++) {

                    // accessing each element of array
                    String x = arraycountry[i];
                    System.out.print(x + "\n");
                }

               // System.out.println("Client get: " + tmp + " from server");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
