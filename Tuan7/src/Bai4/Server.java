package Bai4;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
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
            Random r = new Random();
            int count = 0;
            int low = 1;
            int high = 500;
            int randomValue = r.nextInt((high-low) + 1) + low;
            long timeStart = System.currentTimeMillis();
            
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
				if (!Pattern.matches("^[0-9]*$", tmp)) {
		        	tmp = "Không phải là số nguyên!";
		        	//break;
		        } else {
		        	count++;
		        	tmp = doanSo(tmp, randomValue, count, timeStart);
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
	
	private static String doanSo(String input, int randomValue, int count, long timeStart) {
        int num = Integer.parseInt(input);
        if (randomValue == num) {
        	long timeEnd = System.currentTimeMillis();
        	long minutes = ((timeEnd - timeStart) / 1000) / 60;
        	long seconds = ((timeEnd - timeStart) / 1000) % 60;
        		
        	input = "Bạn đã đoán đúng, đó là số: " + randomValue + ". Số lần đoán: " + count + ". Tổng thời gian là: " + minutes + " phút " + seconds + " giây";
        	//input += Integer.toString(randomValue);
        	//break;
        } else {
        	if (randomValue > num) {
        		input = "Số bạn đoán nhỏ hơn kết quả. Vui lòng đoán số lớn hơn lại ";
//        		input += Integer.toString(randomValue);
//        		input += Integer.toString(count);
        			
        	} else {
        		input = "Số bạn đoán lớn hơn kết quả. Vui lòng đoán số nhỏ hơn lại ";
//        		input += Integer.toString(randomValue);
//        		input += Integer.toString(count);
        	}
        }
        return input;
	}
}