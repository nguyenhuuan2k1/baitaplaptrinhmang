package Bai3;

import java.io.*;
import java.net.*;

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
                
                out.write(findPi(line));
//                out.write("abc");
                out.newLine();
                //out.flush();
                
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
    
    private static String findPi(String input) {
    	double num = Double.parseDouble(input);
    	double rand_x, rand_y, origin_dist, pi=0;
        int circle_points = 0, square_points = 0;
        
        double x,y;
        int k=0;
        
        
        
        // Total Random numbers generated = possible x
        // values * possible y values
        if (num >= 1000000) {
        	for(int i=1; i<=num; i++){
                x=Math.random();
                y=Math.random();
                if(x*x+y*y<=1) 
                	k++;
              }
              double p=4.*k/num;
              
//        for (int i = 0; i < (num * num); i++) {
//      
//            // Randomly generated x and y values in the range [-1,1]
//            rand_x = Math.random()*2-1;
//            rand_y = Math.random()*2-1;
//      
//            // Distance between (x, y) from the origin
//            origin_dist = rand_x * rand_x + rand_y * rand_y;
//      
//            // Checking if (x, y) lies inside the define
//            // circle with R=1
//            if (origin_dist <= 1)
//                circle_points++;
//      
//            // Total number of points generated
//            square_points++;
//      
//            // estimated pi after this iteration
//            pi = ((4.0 * circle_points) / square_points);
//      
//            // For visual understanding (Optional)
//            //System.out.println(rand_x+" "+rand_y+" "+circle_points+" "+square_points+" - "+pi);
//        }
      
        // Final Estimated Value
        input = "Final Estimation of Pi = " + p;
        } else {
        	input = "Bạn phải nhập số lớn hơn 1000000";
        }
        return input;
    }
}