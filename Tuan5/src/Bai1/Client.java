package Bai1;


import java.io.*;
import java.net.*;
import java.util.*;


public class Client {
      
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;
    private static BufferedReader stdin = null;

    public Client(String address, int port) throws IOException {
        socket = new Socket(address, port);
        System.out.println("connected");
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        stdin = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        while (true) {
            System.out.print("Client input: ");
            line = stdin.readLine();
            out.write(line);
            out.newLine();
            out.flush();
            
            if (line.equalsIgnoreCase("bye"))
                break;
            line = in.readLine();
            System.out.println(line);
        }
        System.out.println("Hệ thống đã dừng!");
        in.close();
        out.close();
        socket.close();
        stdin.close();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 5000);

    }
}

