package dictionary;

import java.net.*;
import java.io.*;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class Server {
    
    private static Socket socket = null;
    private static ServerSocket server = null;
    static BufferedReader in = null;
    static BufferedWriter out = null;

    public static void main(String[] args) throws IOException {
        try {
            server = new ServerSocket(5000);
            System.out.println("Server started!");
            socket = server.accept();
            System.out.println("Client connected...");
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "";
            HashMap<String, String> map = new HashMap<>();

            while (!line.equalsIgnoreCase("bye")) {
                String path = "src/dictionary/dictionary.txt";
                File file = new File(path);
                line = in.readLine();
                System.out.println("Server received:" + line);
                Scanner sc = new Scanner(file);//đọc file
                
                //Thực hiện hành động ADD
                if (line.startsWith("ADD") && line.contains(";")) {
                    out.write(actionAdd(line, map, sc, path));
                    out.newLine();
                    out.flush();
                    
                //Thực hiện hành động DEL
                } else if (line.startsWith("DEL") && line.contains(";")) {
                    out.write(actionDel(line, map, sc, path));
                    out.newLine();
                    out.flush();

                //Thực hiện hành động tìm từ có trong file
                } else {
                    out.write(actionFind(line, map, sc));
                    out.newLine();
                    out.flush();
                }
            }
            System.out.println("Server closed connection!");
            in.close();
            out.close();
            socket.close();
            server.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    private static String actionAdd(String line, HashMap<String, String> map, Scanner sc, String path) {
    	
    	try {
    		//Tách dữ liệu người dùng nhập vào theo từng Token
            StringTokenizer dicADD = new StringTokenizer(line, ";", false);
            String action = dicADD.nextToken();
            String first = dicADD.nextToken();
            String second = dicADD.nextToken();
            //Đưa dữ liệu từ file vào Hashmap
        	while (sc.hasNextLine()) {
                String dicLine = sc.nextLine().toLowerCase();
                StringTokenizer st = new StringTokenizer(dicLine, ";", false);
                String english = st.nextToken();
                String vietnam = st.nextToken();
                map.put(english, vietnam);
                if (first.equals(english)) {
                	return line = "Đã có từ này trong từ điển!";
                }
                
        	}
        	if (!action.equals("ADD")) {
            	return line = "Sai cú pháp! Vui lòng nhập ADD;x;y với x là từ tiếng anh và y là từ tiếng việt!";
            }
            map.put(first, second);
                	
                //Đưa từ mới vào file
                for (HashMap.Entry<String, String> set : map.entrySet()) {
                        File file1 = new File(path);
                        BufferedWriter bf = null;
                        try {
                        	// tiến hành ghi file
                            bf = new BufferedWriter(new FileWriter(file1));
                            // đưa các từ có trong Hashmap vào lại file
                            for (Map.Entry<String, String> entry : map.entrySet()) {
                                // Ghi dữ liệu theo đúng cấu trúc
                                bf.write(entry.getKey() + ";" + entry.getValue());
                                // tạo dòng mới
                                bf.newLine();
                            }
                            bf.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {

                            try {
                                // đóng writer
                                bf.close();
                            } catch (IOException e) {
                                line = "Thêm từ mới thất bại!" +e;
                                
                            }
                        }
                    }
                line = "Thêm thành công một từ!";    
            
		} catch (Exception e) {
			// TODO: handle exception
			line = "Sai cú pháp! Vui lòng nhập ADD;x;y với x là từ tiếng anh và y là từ tiếng việt!";
		}
        
        
    	return line;
    }
    
    private static String actionDel(String line, HashMap<String, String> map, Scanner sc, String path) {
    	try {
    		//Tách dữ liệu người dùng nhập vào theo từng Token
        	StringTokenizer dicDEL = new StringTokenizer(line, ";", false);
        	int amountToken = dicDEL.countTokens();
            String action = dicDEL.nextToken();
            String first = dicDEL.nextToken();
            //Đưa dữ liệu từ file vào Hashmap
            while (sc.hasNextLine()) {
                String dicLine = sc.nextLine().toLowerCase();
                StringTokenizer st = new StringTokenizer(dicLine, ";", false);
                String english = st.nextToken();
                String vietnam = st.nextToken();
                map.put(english, vietnam);
            }
            if (!action.equals("DEL")) {
            	return line = "Sai cú pháp! Vui lòng nhập DEL;x với x là từ tiếng anh có trong từ điển!";
            }
            if (map.containsKey(first.toLowerCase()) && amountToken == 2) {
                map.remove(first.toLowerCase());
                line = "Xóa thành công";
                //Xóa từ trong file
                for (HashMap.Entry<String, String> set : map.entrySet()) {
                    File file2 = new File(path);
                    BufferedWriter bf = null;
                    try {
                        // tiến hành ghi file
                        bf = new BufferedWriter(new FileWriter(file2));
                        // đưa các từ có trong Hashmap vào lại file
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                        	// Ghi dữ liệu theo đúng cấu trúc
                            bf.write(entry.getKey() + ";" + entry.getValue());
                            // tạo dòng mới
                            bf.newLine();
                        }
                        bf.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {

                        try {
                            // đóng writer
                            bf.close();
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    }
                }
            } 
            else if (amountToken != 2){
                line = "Sai cú pháp! Vui lòng nhập DEL;x với x là từ tiếng anh có trong từ điển!";
            } 
            else {
            	line = "Xóa thất bại vì từ muốn xóa không có trong từ điển!";
            }
		} catch (Exception e) {
			// TODO: handle exception
			line = "Sai cú pháp! Vui lòng nhập DEL;x với x là từ tiếng anh có trong từ điển!";
		}
    	return line;
    }
    
    private static String actionFind(String line, HashMap<String, String> map, Scanner sc) {
    	while (sc.hasNextLine()) {
            String dicLine = sc.nextLine().toLowerCase();
            StringTokenizer st = new StringTokenizer(dicLine, ";", false);
            String english = st.nextToken();
            String vietnam = st.nextToken();
            map.put(english, vietnam);

        }
        if (map.containsKey(line.toLowerCase()) || map.containsValue(line.toLowerCase())) {
            for (HashMap.Entry<String, String> set : map.entrySet()) {
                if (set.getKey().equalsIgnoreCase(line)) {
                    line = (set.getValue().toLowerCase());
                    break;
                } else if (set.getValue().equalsIgnoreCase(line)) {
                    line = (set.getKey().toLowerCase());
                    break;
                }

            }
        } else {
            line = "Không tìm thấy từ đó trong từ điển!";
        }
    	return line;
    }
}
