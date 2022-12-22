package aqi;

//import com.sun.net.httpserver.Authenticator;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

/**
 *
 * @author User
 */
public class Server {

    public static int buffsize = 1024;
    public static int port = 1234;

    public static void main(String[] args) throws JSONException {
        DatagramSocket socket;
        DatagramPacket dpreceive, dpsend = null;
        ArrayList<String> listCountry = new ArrayList<String>();
        ArrayList<String> listState = new ArrayList<String>();
        ArrayList<String> listCity = new ArrayList<String>();
        ArrayList<String> listAQI = new ArrayList<String>();
        HashMap<Integer, String> mapCountry = new HashMap<>();
        try {
            socket = new DatagramSocket(port);
            dpreceive = new DatagramPacket(new byte[buffsize], buffsize);
            while (true) {
                socket.receive(dpreceive);
                String tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
                System.out.println("Server received: " + tmp + " from "
                        + dpreceive.getAddress().getHostAddress() + " at port "
                        + socket.getLocalPort());
                if (tmp.equals("bye")) {
                    System.out.println("Server socket closed");
                    socket.close();
                    break;
                }

                //https://api.airvisual.com/v2/countries?key=0e3fc67f-b0b3-4ab7-9c5a-78d507b13d20 (new API)
                StringTokenizer token = new StringTokenizer(tmp, ";", false);
                switch (token.countTokens()) {
                    //Lấy tên các quốc gia
                	case 1:
                		try{
                        if (tmp.equalsIgnoreCase("Hello")) {
                        	listCountry(tmp, dpsend, dpreceive, socket, listCountry, mapCountry);
                        	listCountry = new ArrayList<String>();
                            break;
                        } 
                        else {
                        	//Lấy tên các tỉnh của một quốc gia
                        	listState(tmp, dpsend, dpreceive, socket, listState);
                        	listState = new ArrayList<String>();
                            break;
                        }}catch(HttpStatusException e){}
                    //Lấy tên các thành phố của một tỉnh
                	case 2:
                		listCity(token, tmp, dpsend, dpreceive, socket, listCity);
                		listCity = new ArrayList<String>();
                		break;
    
                    //Lấy chỉ số AQI của một tỉnh
                	case 3:
                		AQI(token, tmp, dpsend, dpreceive, socket, listAQI);
                		break;
                }

            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    private static void listCountry(String tmp, DatagramPacket dpsend, DatagramPacket dpreceive, DatagramSocket socket, ArrayList<String> listCountry, HashMap<Integer, String> mapCountry) {
    	try {
    		
                Connection.Response response = Jsoup.connect("https://api.airvisual.com/v2/countries?key=0e3fc67f-b0b3-4ab7-9c5a-78d507b13d20")
                        .method(Connection.Method.GET)
                        .ignoreContentType(true)
                        .execute();
                String line = response.body();
                String jsonString = line + "";
                JSONObject json = new JSONObject(jsonString);
                String status = (String) json.get("status");
                String result = status.toString();
                if ("success".equalsIgnoreCase(result)) {
                    JSONArray data = json.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject countryname = (JSONObject) data.get(i);
                        String name = (String) countryname.get("country");
                        listCountry.add(name);
                        mapCountry.put(i, name);
                    }

                    String[] arraycountry = new String[listCountry.size()];
                    // ArrayList to Array Conversion
                    for (int i = 0; i < listCountry.size(); i++) {
                        arraycountry[i] = listCountry.get(i);
                    }
                    tmp = Stream.of(arraycountry).collect(Collectors.joining(","));
                    dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
                    System.out.println("Server sent back " + tmp + " to client");
                    socket.send(dpsend);
                } else {
                    tmp = "Có lỗi xảy ra. Vui lòng nhập lại!";
                    dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                            dpreceive.getAddress(), dpreceive.getPort());
                    System.out.println("Server sent back " + tmp + " to client");
                    socket.send(dpsend);
                }
                //break;
            
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    }
    
    private static void listState(String tmp, DatagramPacket dpsend, DatagramPacket dpreceive, DatagramSocket socket, ArrayList<String> listState) throws IOException {
    	try {
    		Connection.Response response1 = Jsoup.connect("https://api.airvisual.com/v2/states?country=" + tmp + "&key=0e3fc67f-b0b3-4ab7-9c5a-78d507b13d20")
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .execute();
            String line1 = response1.body();
            String jsonString1 = line1 + "";
            JSONObject json1 = new JSONObject(jsonString1);
            String status = (String) json1.get("status");
            if ("success".equals(status)) {
                JSONArray data1 = json1.getJSONArray("data");
                for (int i = 0; i < data1.length(); i++) {
                    JSONObject countryname = (JSONObject) data1.get(i);
                    String name = (String) countryname.get("state");
                    // listCountry.add(name+",");
                    listState.add(name);
                }

                String[] arraystate = new String[listState.size()];
                // ArrayList to Array Conversion
                for (int i = 0; i < listState.size(); i++) {
                    arraystate[i] = listState.get(i);
                }
                tmp = Stream.of(arraystate).collect(Collectors.joining(","));
                dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                        dpreceive.getAddress(), dpreceive.getPort());
                System.out.println("Server sent back " + tmp + " to client");
                socket.send(dpsend);
                //break;
            } else {
                Connection.Response response2 = Jsoup.connect("https://api.airvisual.com/v2/states?country=" + tmp + "&key=0e3fc67f-b0b3-4ab7-9c5a-78d507b13d20")
                        .method(Connection.Method.GET)
                        .ignoreContentType(true)
                        .execute();
                String line2 = response2.body();
                String jsonString2 = line2 + "";
                JSONObject json2 = new JSONObject(jsonString2);
                String status2 = (String) json2.get("status");
                tmp = status2;
                dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                        dpreceive.getAddress(), dpreceive.getPort());
                System.out.println("Server sent back " + tmp + " to client");
                socket.send(dpsend);
                //break;

            }
		} catch (Exception e) {
			// TODO: handle exception
			tmp = "Quốc gia bạn nhập không tồn tại hoặc bạn đã nhập sai định dạng!";
			dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                    dpreceive.getAddress(), dpreceive.getPort());
            System.out.println("Server sent back " + tmp + " to client");
            socket.send(dpsend);
            //break;
		}
    }
    
    private static void listCity(StringTokenizer token, String tmp, DatagramPacket dpsend, DatagramPacket dpreceive, DatagramSocket socket, ArrayList<String> listCity) throws IOException {
    	try {
			String country = token.nextToken();
            String state = token.nextToken();
            Connection.Response response2 = Jsoup.connect("https://api.airvisual.com/v2/cities?state=" + state + "&country=" + country + "&key=0e3fc67f-b0b3-4ab7-9c5a-78d507b13d20")
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .execute();
            String line2 = response2.body();
            String jsonString2 = line2 + "";
            JSONObject json2 = new JSONObject(jsonString2);
            String status2 = (String) json2.get("status");
            if ("success".equals(status2)) {
                JSONArray data1 = json2.getJSONArray("data");
                for (int i = 0; i < data1.length(); i++) {
                    JSONObject countryname = (JSONObject) data1.get(i);
                    String name = (String) countryname.get("city");
                    // listCountry.add(name+",");
                    listCity.add(name);
                }
                String[] arraycity = new String[listCity.size()];
                // ArrayList to Array Conversion
                for (int i = 0; i < listCity.size(); i++) {
                    arraycity[i] = listCity.get(i);
                }
                tmp = Stream.of(arraycity).collect(Collectors.joining(","));
                dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                        dpreceive.getAddress(), dpreceive.getPort());
                System.out.println("Server sent back " + tmp + " to client");
                socket.send(dpsend);
                //break;
            } else {

                tmp = status2;
                dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                        dpreceive.getAddress(), dpreceive.getPort());
                System.out.println("error:" + tmp);
                socket.send(dpsend);
                //break;
            }
		} catch (Exception e) {
			// TODO: handle exception
			tmp = "Bạn nhập sai tên quốc gia hoặc tỉnh. Vui lòng kiểm tra lại!";
			dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                    dpreceive.getAddress(), dpreceive.getPort());
            System.out.println("Server sent back " + tmp + " to client");
            socket.send(dpsend);
            //break;
		}
    }
    
    private static void AQI(StringTokenizer token, String tmp, DatagramPacket dpsend, DatagramPacket dpreceive, DatagramSocket socket, ArrayList<String> listAQI) throws IOException {
    	try {
			String country = token.nextToken();
            String state = token.nextToken();
            String city = token.nextToken();
            Connection.Response response3 = Jsoup.connect("https://api.airvisual.com/v2/city?city=" + city + "&state=" + state + "&country=" + country + "&key=0e3fc67f-b0b3-4ab7-9c5a-78d507b13d20")
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .execute();
            String line3 = response3.body();
            String jsonString3 = line3 + "";
            JSONObject json3 = new JSONObject(jsonString3);
            String status3 = (String) json3.get("status");
            if ("success".equals(status3)) {
                JSONObject data3 = json3.getJSONObject("data");
                JSONObject current = data3.getJSONObject("current");
                JSONObject pollution = current.getJSONObject("pollution");
                String AQI = pollution.get("aqius").toString();
//                listAQI.add(AQI);
//                String[] arrayAQI = new String[listAQI.size()];
//                // ArrayList to Array Conversion
//                for (int i = 0; i < listAQI.size(); i++) {
//                    arrayAQI[i] = listAQI.get(i);
//                }
//                tmp = Stream.of(arrayAQI).collect(Collectors.joining(","));
                tmp = "Chỉ số AQI của " + city + " là: " + AQI;
                dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                        dpreceive.getAddress(), dpreceive.getPort());
                System.out.println("Server sent back " + tmp + " to client");
                socket.send(dpsend);
                //break;
            } else {
                JSONObject data3 = json3.getJSONObject("status");
                String status4 = (String) data3.get("data");
                tmp = status4;
                dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                        dpreceive.getAddress(), dpreceive.getPort());
                System.out.println("error:" + tmp);
                socket.send(dpsend);
                //break;
            }
		} catch (Exception e) {
			// TODO: handle exception
			tmp = "Bạn nhập sai tên quốc gia hoặc tỉnh hoặc thành phố. Vui lòng kiểm tra lại!";
			dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                    dpreceive.getAddress(), dpreceive.getPort());
            System.out.println("Server sent back " + tmp + " to client");
            socket.send(dpsend);
            //break;
		}
    }
}
