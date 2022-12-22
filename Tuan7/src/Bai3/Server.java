package Bai3;

import java.io.*;
import java.util.*;
import java.net.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Server {
	
	public static int buffsize = 512;
	public static int port = 1234;

	public static void main(String[] args) throws IOException, JSONException {
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
				StringTokenizer token = new StringTokenizer(tmp, ";", false);
				String x = token.nextToken();
				String y = token.nextToken();
				String number = token.nextToken();
				String z = "";
				StringTokenizer token1 = new StringTokenizer(y, ",", false);
				if(token1.countTokens() > 1) {
				y = token1.nextToken();
				z = token1.nextToken();
				}
				if(!x.equals("2") && !x.equals("8") && !x.equals("10") && !x.equals("16")) {
					tmp = "x không phải là một cơ số!";
				}else if(!y.equals("2") && !y.equals("8") && !y.equals("10") && !y.equals("16")) {
					tmp = "y không phải là một cơ số!";
				}else if(!z.isEmpty()) {
					if(!z.equals("2") && !z.equals("8") && !z.equals("10") && !z.equals("16")) {
						tmp = "z không phải là một cơ số!";
					}
					//tmp = convertBase2(number, x, y, z);
					tmp = convertAPI(number, x, y) + ";";
					tmp += convertAPI(number, x, z);
//					dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
//					System.out.println("Server sent back: " + tmp + " to client!");
//					socket.send(dpsend);
					
				}else {
					//tmp = convertBase2(number, x, y, z);
					tmp = convertAPI(number, x, y);
//					dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
//					System.out.println("Server sent back: " + tmp + " to client!");
//					socket.send(dpsend);
				}
				dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
				System.out.println("Server sent back: " + tmp);
				socket.send(dpsend);
			}
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Bạn nhập sai định dạng. Vui lòng nhập lại");
		}
	}
	
	public static String baseConvert(String n, int x, int y) {
		return Integer.toString(Integer.parseInt(n, x), y);
	}
	
	private static String convertBase1(String n, String x, String y) {
		int a = Integer.parseInt(x);
		int b = Integer.parseInt(y);
		
		return baseConvert(n, a, b);	
	}
	
	private static String convertBase2(String n, String x, String y, String z) {
		int a = Integer.parseInt(x);
		int b = Integer.parseInt(y);

		if(z.isEmpty()) {
			return baseConvert(n, a, b);
		}else {
			int c = Integer.parseInt(z);
			return baseConvert(n, a, b) + ";" + baseConvert(n, a, c);
		}
			
	}
	
	private static String convertAPI(String n, String x, String y) throws IOException, JSONException {
		Document doc = Jsoup.connect("https://networkcalc.com/api/binary/" + n + "?from=" + x + "&to=" + y + "").ignoreContentType(true).get();
		String api = doc.text();
		JSONObject json = new JSONObject(api);
		String result = json.getString("converted");
		return result;
	}

}
