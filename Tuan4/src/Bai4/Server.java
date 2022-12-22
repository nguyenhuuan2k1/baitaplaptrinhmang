package Bai4;

import java.io.*;
import java.net.*;

public class Server {
	private static ServerSocket server = null;
	private static Socket socket = null;
	private static BufferedReader in = null;
	private static BufferedWriter out = null;

	public static void main(String[] args) {
		try {
			server = new ServerSocket(5000);
			System.out.println("Server started...");
			socket = server.accept();
			System.out.println("Client " + socket.getInetAddress() + " connected...");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while (true) {
				// Server nhận dữ liệu từ client qua stream
				String line = in.readLine();
				if (line.equals("bye"))
					break;
				System.out.println("Server received: " + line);
				String result;
				result = String.valueOf(findPerfect(line));
				if (findPerfect(line) != 0) {
					out.write(result);
				}
					
					out.newLine();
					out.flush();
				
			}
			System.out.println("Server closed connection");
			// Đóng kết nối
			in.close();
			out.close();
			socket.close();
			server.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private static boolean isPerfect(int num) {
		int sum = 0;
		for (int i = 1; i <= num / 2; i++) {
			if (num % i == 0)
				// tổng các ước số của num
				sum += i;
		}
		if (sum == num)
			return true;
		return false;
	}

	private static int findPerfect(String input) {
		try {
			int num = Integer.parseInt(input);
			if (isPerfect(num))
				return num;
			else {
			for (int i = num + 1;; i++) {
				if (isPerfect(i))
					return i;
			}
			}
		} catch (Exception e) {
			return 0;
		}
	}
}
