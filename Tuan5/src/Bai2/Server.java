package Bai2;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.apache.commons.validator.routines.InetAddressValidator;

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
                InetAddress add;
                add = InetAddress.getLocalHost();
                if (line.equals("hello")) {
                	line = "Private IP: " + add.getHostAddress() + " Public IP: " + getPublicIP();
                }
                StringTokenizer st = new StringTokenizer(line," ", true);
                InetAddressValidator validator = InetAddressValidator.getInstance();
                String syntax = st.nextToken();
                String ip = st.nextToken();
                if (syntax.equals("req")) {
                	if (validator.isValidInet4Address(ip)) {
                		Document doc = Jsoup.connect("http://ip-api.com/json/"+ ip +"?fields=continent,country,city").ignoreContentType(true).get();
                		String api = doc.text();
                		JSONObject json = new JSONObject(api);
                		line = json.getString("continent,country,city");
                		
                	}
                	else {
                		line = "IP không đúng format/IP private";
                	}
				}
                else {
                	line = "Bạn vui lòng nhập đúng cú pháp: req x với x là IP public. Hoặc nhập hello để xuất ra IP private và IP public của server.";
                }
                out.write(line);
                out.newLine();
                out.flush();
            }
            System.out.println("Server closed connection");
            // Đóng kết nối
            in.close();
            out.close();
            socket.close();
            server.close();
        } catch (IOException e) { System.err.println(e); } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static String getPublicIP() {
    	try {
    		URL whatismyip = new URL("http://checkip.amazonaws.com");
        	BufferedReader in = new BufferedReader(new InputStreamReader(
        	                whatismyip.openStream()));

        	String ip = in.readLine(); //you get the IP as a String
        	return ip;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
    }
}
