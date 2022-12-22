package Bai1;

import java.io.*;
import java.util.*;
import java.net.*;

public class Client {

	public static int port = 1234;
	public static String host = "localhost";
	public static Socket socket ;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try {
			socket = new Socket(host, port);
			System.out.println("Client connected");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			String input;
			while(true) {
				System.out.println("Client sent: ");
				input = stdin.readLine();
				out.write(input + '\n');
				out.flush();
				if(input.equals("bye")) {
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}finally {
			if(socket!=null) {
				socket.close();
				System.out.println("Client socket closed");
			}
		}
	}

}
