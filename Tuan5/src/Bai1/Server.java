package Bai1;

import java.util.*;
import java.io.*;
import java.net.*;
//import org.apache.commons.lang3.StringUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Server {
    
    private static Socket socket = null;
    private static ServerSocket server = null;
    static BufferedReader in = null;
    static BufferedWriter out = null;

    public static void main(String[] args) throws IOException {
        try {
            server = new ServerSocket(5000);
            System.out.println("Server is starting, waiting for client.... ");
            socket = server.accept();
            System.out.println("Client accept");
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "";
            HashMap<String, String> map = new HashMap<>();

            while (!line.equalsIgnoreCase("bye")) {
                //String path = "\\Users\\Public\\Desktop\\dictionary.txt";
                String path = "src/Bai1/dictionary.txt";
                File file = new File(path);
                line = in.readLine().toUpperCase();
                System.out.println("Server receive:" + line);
                Scanner scan = new Scanner(file);

//                if (StringUtils.containsIgnoreCase(line, "ADD")) {
//                    while (scan.hasNextLine()) {
//                        String Dicline = scan.nextLine().toUpperCase();
//                        StringTokenizer st = new StringTokenizer(Dicline, ";", false);
//                        String eng = st.nextToken();
//                        String vn = st.nextToken();
//                        map.put(eng, vn);
//                    }
//                    StringTokenizer dictionaryADD = new StringTokenizer(line, ";", false);
//                    String op = dictionaryADD.nextToken();
//                    String first = dictionaryADD.nextToken();
//                    String second = dictionaryADD.nextToken();
//                    map.put(first,second);
//                    line = "Thêm thành công!";
//                    for (HashMap.Entry<String, String> set : map.entrySet()) {
//                            File file2 = new File(path);
//                            BufferedWriter bf = null;
//                            try {
//                                // create new BufferedWriter for the output file
//                                bf = new BufferedWriter(new FileWriter(file2));
//                                // iterate map entries
//                                for (Map.Entry<String, String> entry : map.entrySet()) {
//                                    // put key and value separated by a colon
//                                    bf.write(entry.getKey() + ";" + entry.getValue());
//                                    // new line
//                                    bf.newLine();
//                                }
//                                bf.flush();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } finally {
//
//                                try {
//                                    // always close the writer
//                                    bf.close();
//                                } catch (IOException e) {
//                                    line = "Thêm thất bại!" +e;
//                                    
//                                }
//                            }
//                        }
//                     
//                    out.write(line);
//                    out.newLine();
//                    out.flush();
//                } else if (StringUtils.containsIgnoreCase(line, "DEL")) {
//
//                    StringTokenizer dictionaryADD = new StringTokenizer(line, ";", false);
//                    String op = dictionaryADD.nextToken();
//                    String first = dictionaryADD.nextToken();
//                    while (scan.hasNextLine()) {
//                        String Dicline = scan.nextLine().toUpperCase();
//                        StringTokenizer st = new StringTokenizer(Dicline, ";", false);
//                        String eng = st.nextToken();
//                        String vn = st.nextToken();
//                        map.put(eng, vn);
//                    }
//                    if (map.containsKey(first.toUpperCase())) {
//                        map.remove(first.toUpperCase());
//                        line = "Xóa thành công";
//                        for (HashMap.Entry<String, String> set : map.entrySet()) {
//                            File file1 = new File(path);
//                            BufferedWriter bf = null;
//                            try {
//                                // create new BufferedWriter for the output file
//                                bf = new BufferedWriter(new FileWriter(file1));
//                                // iterate map entries
//                                for (Map.Entry<String, String> entry : map.entrySet()) {
//                                    // put key and value separated by a colon
//                                    bf.write(entry.getKey() + ";" + entry.getValue());
//                                    // new line
//                                    bf.newLine();
//                                }
//                                bf.flush();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } finally {
//
//                                try {
//                                    // always close the writer
//                                    bf.close();
//                                } catch (IOException ex) {
//                                    System.out.println(ex);
//                                }
//                            }
//                        }
//                    } else {
//                        line = "Xóa thất bại!";
//                    }
//
//                    out.write(line);
//                    out.newLine();
//                    out.flush();
//
//                } else {
                    while (scan.hasNextLine()) {
                        String Dicline = scan.nextLine().toUpperCase();
                        StringTokenizer st = new StringTokenizer(Dicline, ";", false);
                        String eng = st.nextToken();
                        String vn = st.nextToken();
                        map.put(eng, vn);

                    }
                    if (map.containsKey(line.toUpperCase()) || map.containsValue(line.toUpperCase())) {
                        for (HashMap.Entry<String, String> set : map.entrySet()) {
                            if (set.getKey().equalsIgnoreCase(line)) {
                                line = (set.getValue().toUpperCase());
                                break;
                            } else if (set.getValue().equalsIgnoreCase(line)) {
                                line = (set.getKey().toUpperCase());
                                break;
                            }

                        }
                    } else {
                        line = "Không tìm thấy từ đó trong từ điển!";
                    }
                    out.write(line);
                    out.newLine();
                    out.flush();
                }
            //}
            System.out.println("Server closed connection");
            in.close();
            out.close();
            socket.close();
            server.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

