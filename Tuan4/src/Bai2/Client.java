package Bai2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
	private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;
    private static BufferedReader stdIn = null;
    private static BufferedReader in1 = null;
    
    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 5000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            
            while(true) {
                // Client nhận dữ liệu từ keyboard và gửi vào stream -> server
                System.out.print("Số bạn đoán là số: ");
                String line = stdIn.readLine(); 
                String number = line;
                out.write(line);
                out.newLine();
                out.flush();
                
                String result = "";
                
                line = in.readLine();
                System.out.println("Client received: " + line);
                result = in.readLine();
//                System.out.println("Client received 2: " + result);
                if(number.equals(result))
                	break;
                // Client nhận phản hồi từ server
                
            }
            System.out.println("Client closed connection");
            in.close();
            out.close();
            stdIn.close();
            socket.close();
        } catch (IOException e) { System.err.println(e); }
    }

}
