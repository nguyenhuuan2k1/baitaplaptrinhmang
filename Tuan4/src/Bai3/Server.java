package Bai3;

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
            while(true) {
                // Server nhận dữ liệu từ client qua stream
                String line = in.readLine();
                if (line.equals("bye"))
                    break;
                System.out.println("Server received: " + line);
                // Server gửi phản hồi ngược lại cho client (chuỗi đảo ngược)
                
                
                out.write(PhanTich(line));
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
    
    private static String PhanTich(String input) {
    	int n = Integer.parseInt(input);
    	String result = "";
    	while(n<2)
        {
           result = "Ban nhap so khong hop le. Vui long thu lai!";
         
        }
        for(int i=2; i<=n;i++)
        {
           while(n%i==0)
           {
              n=n/i;
              if(n==1)
                  result = result + i + "";
              else
                  result = result + i + " x ";
           }
           if (n==1)
              break;
        }
        return result;
    }
}