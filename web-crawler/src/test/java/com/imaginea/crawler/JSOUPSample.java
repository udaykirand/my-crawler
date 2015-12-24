package com.imaginea.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.imaginea.crawler.util.Stopwatch;

public class JSOUPSample implements Runnable{
	static List<Document> documents = new ArrayList<Document>();
	static int maxReq = -1;
	static String url = "http://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/thread";
	
	@Override
	public void run() {
		Stopwatch timer = new Stopwatch();
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		documents.add(doc);
		Elements allLinks = doc.select("a[href]");
		for (Element link : allLinks) {
			if (maxReq == -1 && url.contains("maven-users/201412")) {
				System.out.println(link);
			}
		}
		System.out.println("thread -- "+timer.elapsedTime());
		
	}

}
