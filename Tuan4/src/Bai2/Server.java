package Bai2;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Pattern;

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
            
            int count = 0;
            Random r = new Random();
            int low = 1;
            int high = 100;
            int randomValue = r.nextInt((high-low) + 1) + low;
            long timeStart = System.currentTimeMillis();
            
            while(true) {
                // Server nhận dữ liệu từ client qua stream
                String line = in.readLine();
                System.out.println("Server received: " + line);
//                System.out.println(randomValue);
                
                if (!Pattern.matches("^[0-9]*$", line)) {
                	out.write("Không phải là số nguyên!");
                	out.newLine();
                	out.flush();
                	break;
                } else {
                	count = count + 1;
                	int num = Integer.parseInt(line);
                	if (randomValue == num) {
                		long timeEnd = System.currentTimeMillis();
                		long minutes = ((timeEnd - timeStart) / 1000) / 60;
                		long seconds = ((timeEnd - timeStart) / 1000) % 60;
                		
                		out.write("Bạn đã đoán đúng, đó là số: " + randomValue + ". Số lần đoán: " + count + ". Tổng thời gian là: " + minutes + " phút " + seconds + " giây");
                		out.newLine();
                		//out.flush();
                		out.write(Integer.toString(randomValue));
                		out.newLine();
                		out.flush();
                		
                		break;
                	} else {
                		if (randomValue > num) {
                			out.write("Số bạn đoán nhỏ hơn kết quả. Vui lòng đoán số lớn hơn lại");
                			out.newLine();
                			//out.flush();
                			out.write(Integer.toString(randomValue));
                    		out.newLine();
                    		out.flush();
                			
                		} else {
                			out.write("Số bạn đoán lớn hơn kết quả. Vui lòng đoán số nhỏ hơn lại");
                			out.newLine();
                			//out.flush();
                			out.write(Integer.toString(randomValue));
                    		out.newLine();
                    		out.flush();
                			
                		}
                	}
                }
               
            }
            System.out.println("Server closed connection");
            // Đóng kết nối
            in.close();
            out.close();
            socket.close();
            server.close();
        } catch (IOException e) { System.err.println(e); }
    }
}
