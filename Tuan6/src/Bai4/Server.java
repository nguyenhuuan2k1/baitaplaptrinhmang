package Bai4;

import java.io.*;
import java.net.*;
import java.util.*;

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
                
                
                out.write(chuoiPhepTinh(line));
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
    
    private static double NhanChia(String input) {
		StringTokenizer st = new StringTokenizer(input, "*/", true);
		Double rs = Double.parseDouble(st.nextToken());
		while(st.hasMoreTokens()) {
			String op = st.nextToken();
			double num = Double.parseDouble(st.nextToken());
			if (op.equals("*"))
				rs *= num;
			else
				rs /= num;
		}
		return rs;
	}
	
	private static double CongTru(String input) {
		StringTokenizer st = new StringTokenizer(input, "+-", true);
		Double rs = Double.parseDouble(st.nextToken());
		while(st.hasMoreTokens()) {
			String op = st.nextToken();
			double num = Double.parseDouble(st.nextToken());
			if (op.equals("+"))
				rs += num;
			else
				rs -= num;
		}
		return rs;
	}
	
	private static String chuoiPhepTinh(String input) {
		try {
			StringTokenizer st = new StringTokenizer(input, "+-", true);
			String[] data = new String[st.countTokens()];
			StringBuilder output = new StringBuilder();
			int i = 0;
			while(st.hasMoreTokens()) 
				data[i++] = st.nextToken();
			for(i=0; i<data.length; i++) {
				if (data[i].indexOf("*") != -1 || data[i].indexOf("/") != -1)
					output.append(Double.toString(NhanChia(data[i])));
				else
					output.append(data[i]);
			}
			input = output.toString();
			return "Kết quả chuỗi phép tính là: " + CongTru(input);
		} catch (Exception e) {
			// TODO: handle exception
			return "Chuôi nhập bị sai định dạng";
		}
		
	}
}