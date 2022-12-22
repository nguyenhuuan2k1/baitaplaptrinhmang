package Tuan2Bai2;

import java.util.LinkedHashMap;
import java.util.StringTokenizer;
import java.util.Set;
public class Bai2 {

	public static void main(String[] args) {
		String input = "Dai hoc  sai gon la mot  trong nhung truong dai hoc  lau doi nhat sai gon";
		String output ="";
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		
		StringTokenizer st = new StringTokenizer(input, " ");
		while (st.hasMoreTokens()) {
			String temp = st.nextToken();
			if (map.get(temp.toLowerCase()) == null) {
				map.put(temp.toLowerCase(), temp);
			}
		}
		for (String value:map.values()) 
		{
			output += value + " ";	
		}
		System.out.println(output);
		
	}

}
