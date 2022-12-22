package Tuan2Bai1;

import java.util.StringTokenizer;
import java.util.Scanner;
public class Bai1 {

	public static void XuLyChuoi()
	{
		int num1, num2;
		String op = "";
		Scanner input = new Scanner(System.in);
		System.out.println("Input: ");
		String data = input.nextLine();
		StringTokenizer st = new StringTokenizer(data, "+-*/", true);
		num1 = Integer.parseInt(st.nextToken());
		op = st.nextToken();
		num2 = Integer.parseInt(st.nextToken());
		if (op.equals("+"))
		{
			System.out.print(num1 + " + " + num2 + " = ");
			System.out.print(num1 + num2);
		}
		else if (op.equals("-")) {
			System.out.print(num1 + " - " + num2 + " = ");
			System.out.print(num1 - num2);
		}
		else if (op.equals("*")) {
			System.out.print(num1 + " * " + num2 + " = ");
			System.out.print(num1 * num2);
		}
		else if (op.equals("/")) {
			if (num2 != 0) {
			System.out.print(num1 + " / " + num2 + " = ");
			System.out.print((float)num1 / num2);
		}
			else {
				System.out.println("Khong the chia cho 0!");
			}	
			}
		else {
			System.out.println("Nhap sai dinh dang!");
		}
	}
	
	public static void main(String[] args) {
		XuLyChuoi();
	}
}

