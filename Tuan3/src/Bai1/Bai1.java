package Bai1;

import java.net.InetAddress;
import java.util.Scanner;

public class Bai1 {

	public static void main(String[] args) {
		InetAddress add;
		Scanner input = new Scanner(System.in);
		System.out.println("Nhập tên domain: ");
		String domain = input.nextLine();
		do {
			try {
				add = InetAddress.getByName(domain);
				System.out.println("Doamin " + domain + " có địa chỉ IP: " + add.getHostAddress());
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Domain " + domain + " không hợp lệ!");
			}
			System.out.println("Nhập tên domain: ");
			domain = input.nextLine();
		} while (!domain.equals("exit"));
	}
}
