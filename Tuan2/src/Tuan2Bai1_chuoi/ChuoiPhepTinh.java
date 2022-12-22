package Tuan2Bai1_chuoi;

import java.io.*;
import java.util.StringTokenizer;

public class ChuoiPhepTinh {
	
	private static double NhanChia(String input) {
		StringTokenizer st = new StringTokenizer(input, "*/", true);
		Double rs = Double.parseDouble(st.nextToken());
		while(st.hasMoreTokens()) {
			String op = st.nextToken();
			double num = Double.parseDouble(st.nextToken());
			if (op.equals("*"))
				rs *= num;
			else
				rs /= num;
		}
		return rs;
	}
	
	private static double CongTru(String input) {
		StringTokenizer st = new StringTokenizer(input, "+-", true);
		Double rs = Double.parseDouble(st.nextToken());
		while(st.hasMoreTokens()) {
			String op = st.nextToken();
			double num = Double.parseDouble(st.nextToken());
			if (op.equals("+"))
				rs += num;
			else
				rs -= num;
		}
		return rs;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input = "2+3+4-5*6-7+5+12/4+3-2*2";
		StringTokenizer st = new StringTokenizer(input, "+-", true);
		String[] data = new String[st.countTokens()];
		StringBuilder output = new StringBuilder();
		int i = 0;
		while(st.hasMoreTokens()) 
			data[i++] = st.nextToken();
		for(i=0; i<data.length; i++) {
			if (data[i].indexOf("*") != -1 || data[i].indexOf("/") != -1)
				output.append(Double.toString(NhanChia(data[i])));
			else
				output.append(data[i]);
		}
		input = output.toString();
		System.out.println(CongTru(input));
	}

}
