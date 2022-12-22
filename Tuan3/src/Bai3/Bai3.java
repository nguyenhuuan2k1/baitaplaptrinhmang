package Bai3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Bai3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Scanner read = new Scanner(new File("src/Bai3/data.txt"));
			while (read.hasNextLine()) {
				String ip = read.nextLine();
				try {
					InetAddress add = InetAddress.getByName(ip);
					if (add.isReachable(3000) == true) {
						System.out.println("IP " + ip + " is reachable!");
					}
					else {
						System.out.println("IP " + ip + " is not reachable!");
					}
					
				} catch (IOException e1) {
					// TODO: handle exception
					System.out.println("IP " + ip + " is invalid!");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		}
	}

}
