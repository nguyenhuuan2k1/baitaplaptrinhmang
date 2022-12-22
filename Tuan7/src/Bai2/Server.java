package Bai2;

import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
	
	public static int buffsize = 512;
	public static int port = 1234;
	public static final char SPACE = ' ';
    public static final char TAB = '\t';
    public static final char BREAK_LINE = '\n';

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DatagramSocket socket;
		DatagramPacket dpreceive, dpsend;
		try {
			socket = new DatagramSocket(1234);
			dpreceive = new DatagramPacket(new byte[buffsize], buffsize);
			randomSo();
			while(true) {
				socket.receive(dpreceive);
				String tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
				System.out.println("Server received: " + tmp + " from " + dpreceive.getAddress().getHostAddress() + " at port " + socket.getLocalPort());
				
				if(tmp.equals("bye")) {
					System.out.println("Server socket closed!");
					socket.close();
					break;
				}
//				int i = Integer.parseInt(tmp);
//				tmp = String.valueOf(tinhTongCacChuSo(i));
				dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
				System.out.println("Server sent back: " + tmp + " to client!");
				socket.send(dpsend);
			}
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
	private static int tinhTongCacChuSo(int n) {
		if (n<10) {
			return n;
		}
		else return n % 10 + tinhTongCacChuSo(n / 10);
	}
	
	private static void randomSo() {
		try {
			String path = "src/Bai2/data.txt";
	        File file = new File(path);
	        Scanner scan = new Scanner(file);
	        String out = "";
	        while(scan.hasNextLine()) {
	        	out += scan.nextLine();
	        }
	        if (countWords(out) < 1000 || out.isEmpty()) {
        		File file2 = new File(path);
        		BufferedWriter bf = null;
        		try {
					bf = new BufferedWriter(new FileWriter(file2));
					Random r = new Random();
					int low = 0;
					int high = 1000;
					for (int i = low; i <= high; i++) {
						int randomValue = r.nextInt((high-low) + 1) + low; 
						bf.write(randomValue);
						if (i % 20 == 0) {
							bf.newLine();
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
        	}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	private static int countWords(String input) {
        if (input == null) {
            return -1;
        }
        int count = 0;
        int size = input.length();
        boolean notCounted = true;
        for (int i = 0; i < size; i++) {
            if (input.charAt(i) != SPACE && input.charAt(i) != TAB 
                    && input.charAt(i) != BREAK_LINE) {
                if(notCounted) {
                    count++;
                    notCounted = false;
                }
            } else {
                notCounted = true;
            }
        }
        return count;
    }

}
