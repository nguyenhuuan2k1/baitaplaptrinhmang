package Bai2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Bai2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Scanner read = new Scanner(new File("src/Bai2/data.txt"));
			while (read.hasNextLine()) {
				String domain = read.nextLine();
				try {
					InetAddress add = InetAddress.getByName(domain);
					System.out.println("Domain " + domain + " có IP: " + add.getHostAddress());
				} catch (UnknownHostException e1) {
					// TODO: handle exception
					System.out.println("Domain " + domain + " không hợp lệ!");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		}
	}

}
