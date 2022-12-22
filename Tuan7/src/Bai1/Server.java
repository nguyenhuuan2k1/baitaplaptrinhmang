package Bai1;

import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
	
	public static int buffsize = 512;
	public static int port = 1234;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DatagramSocket socket;
		DatagramPacket dpreceive, dpsend;
		try {
			socket = new DatagramSocket(1234);
			dpreceive = new DatagramPacket(new byte[buffsize], buffsize);
			while(true) {
				socket.receive(dpreceive);
				String tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
				System.out.println("Server received:: " + tmp + " from " + dpreceive.getAddress().getHostAddress() + " at port " + socket.getLocalPort());
				if(tmp.equals("bye")) {
					System.out.println("Server socket closed!");
					socket.close();
					break;
				}
				//Action
				StringTokenizer token = new StringTokenizer(tmp, " ", false);
				tmp = "";
				while(token.hasMoreTokens()) {
					String line = token.nextToken();
					StringBuilder data = new StringBuilder();
					data.append(line);
					line = data.reverse().toString();
					tmp += line + " ";
				}
				dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
				System.out.println("Server sent back: " + tmp + " to client!");
				socket.send(dpsend);
			}
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Bạn nhập sai định dạng. Vui lòng nhập lại");
		}
	}
}
