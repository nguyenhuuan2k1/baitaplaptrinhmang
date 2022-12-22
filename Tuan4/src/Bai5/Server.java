package Bai5;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;

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
            System.out.println("Client " + socket.getInetAddress() +   " connected...");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(true) {
                // Server nhận dữ liệu từ client qua stream
                String line = in.readLine();
                if (line.equals("bye"))
                    break;
                System.out.println("Server received: " + line);
                // Server gửi phản hồi ngược lại cho client (chuỗi đảo ngược)
                
                out.write(xulyChuoi(line));
                out.newLine();
                out.flush();
            }
            System.out.println("Server closed connection");
            // Đóng kết nối
            in.close();
            out.close();
            socket.close();
            server.close();
        } catch (IOException e) { System.err.println(e); }
    }
    
    private static String xulyChuoi(String input) {
    	int num1, num2;
		String op = "";
		String data = input;
		//String result;
		StringTokenizer st = new StringTokenizer(data, "+-*/", true);
		num1 = Integer.parseInt(st.nextToken());
		op = st.nextToken();
		num2 = Integer.parseInt(st.nextToken());
		if (op.equals("+"))
		{
			input = num1 + " + " + num2 + " = " + (num1+num2);
//			System.out.print(num1 + " + " + num2 + " = ");
//			System.out.print(num1 + num2);
		}
		else if (op.equals("-")) {
			input = num1 + " - " + num2 + " = " + (num1-num2);
//			System.out.print(num1 + " - " + num2 + " = ");
//			System.out.print(num1 - num2);
		}
		else if (op.equals("*")) {
			input = num1 + " * " + num2 + " = " + (num1*num2);
//			System.out.print(num1 + " * " + num2 + " = ");
//			System.out.print(num1 * num2);
		}
		else if (op.equals("/")) {
			if (num2 != 0) {
				input = num1 + " / " + num2 + " = " + (num1/num2);
//				System.out.print(num1 + " / " + num2 + " = ");
//				System.out.print((float)num1 / num2);
		}
			else {
				input = "Khong the chia cho 0!";
				//System.out.println("Khong the chia cho 0!");
			}	
			}
		else {
			input = "Nhap sai dinh dang!";
			//System.out.println("Nhap sai dinh dang!");
		}
		return input;
    }
}