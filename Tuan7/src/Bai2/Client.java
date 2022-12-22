   package Bai2;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	public static int desPort = 1234;
	public static String hostname = "localhost";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatagramSocket socket;
		DatagramPacket dpsend, dpreceive;
		InetAddress add;
		Scanner sc;
		try {
			add = InetAddress.getByName(hostname);
			socket = new DatagramSocket();
			sc = new Scanner(System.in);
			while(true) {
				System.out.println("Client input: ");
				String tmp = sc.nextLine();
				byte[] data = tmp.getBytes();
				dpsend = new DatagramPacket(data, data.length, add, desPort);
				System.out.println("Client sent " + tmp + " to " + add.getHostAddress() + " form host " + socket.getLocalPort());
				socket.send(dpsend);
				if (tmp.equals("bye")) {
					System.out.println("Client socket closed!");
					sc.close();
					socket.close();
					break;
				}
				//Action
				dpreceive = new DatagramPacket(new byte[512], 512);
				socket.receive(dpreceive);
				tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
				System.out.println("Client get: " + tmp + " from server!");
			}
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

}
