package com.imaginea.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.imaginea.crawler.util.Stopwatch;


public class JsoupSample {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/*Document doc = Jsoup.connect("http://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/browser").get();
		System.out.println(doc.html());
		System.out.println("------------------------------------------------------------------------");
		System.out.println(doc.text());
		PrintWriter out = new PrintWriter("/home/udayd/test/" + new File(doc.title() + ".html"));
		out.write(doc.html());
		out.close();*/
		
		List<Document> documents = new ArrayList<Document>();
		int maxReq = -1;
		String url = "http://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/thread";
		try {
			
			Connection.Response response = Jsoup.connect(url).ignoreHttpErrors(true).execute();
			
			if (response.statusCode() == 200) {
				Stopwatch timer = new Stopwatch();
				Document doc = Jsoup.connect(url).get();
				
				documents.add(doc);
				Elements allLinks = doc.select("a[href]");
				for (Element link : allLinks) {
					if (maxReq == -1 && url.contains("maven-users/201412")) {
						System.out.println(link);
					}
				}
				System.out.println(timer.elapsedTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
