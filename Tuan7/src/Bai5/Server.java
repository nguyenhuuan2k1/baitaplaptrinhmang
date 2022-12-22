package Bai5;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.net.*;
import java.security.*;
import java.math.*;

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
				StringTokenizer token = new StringTokenizer(tmp, ":", false);
				String action = token.nextToken();
				String data = token.nextToken();
				if (action.equals("ENC")) {
					tmp = encrypt1(data);
					//tmp = encrypt2(data);
				} else if (action.equals("DEC")) {
					tmp = decrypt(data);
				} else {
					tmp = "Bạn nhập sai định dạng. Vui lòng nhập lại";
				}
				
				dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
				System.out.println("Server sent back: " + tmp + " to client!");
				socket.send(dpsend);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Bạn nhập sai định dạng. Vui lòng nhập lại");
		}
	}
	
	private static String encrypt1(String input) {
	    try {
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      byte[] messageDigest = md.digest(input.getBytes());
	      return convertByteToHex(messageDigest);
	    } catch (NoSuchAlgorithmException e) {
	      throw new RuntimeException(e);
	    }
	  }
	
	 private static String convertByteToHex(byte[] data) {
		    StringBuffer sb = new StringBuffer();
		    for (int i = 0; i < data.length; i++) {
		      sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
		    }
		    return sb.toString();
		  }
	 
	 private static String encrypt2(String s) throws Exception {
	      MessageDigest m=MessageDigest.getInstance("MD5");
	      m.update(s.getBytes(),0,s.length());     
	      return new BigInteger(1,m.digest()).toString(16); 
	   }
	 
	 private static String decrypt(String md5_hash) throws Exception {
         String api_key = "YOUR_VIP_KEY";
         URL md5online = new URL("https://www.md5online.org/api.php?d=1&p="+api_key+"&h="+md5_hash);
         BufferedReader in = new BufferedReader(new InputStreamReader(md5online.openStream()));
                           
         String result = "";
         String inputLine;
         while ((inputLine = in.readLine()) != null)
            result = result+inputLine;
         in.close();
                           
         return result;
   }

}
