package Bai4;

import java.io.*;
import java.net.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "https://masothue.com/Search/?q=300301302&type=auto&force-search=1";
		try {
			Connection.Response con = Jsoup.connect(url).method(Connection.Method.GET).execute();
			
			String newurl = con.url().toString();
			
			Document doc = Jsoup.connect(newurl).method(Connection.Method.GET).execute().parse();
			
			Elements elementList = doc.getElementsByAttributeValue("class", "copy");
			for(Element e:elementList)
				System.out.println(e.text());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
