package com.imaginea.crawler.boilerplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerGist {
	static Set<String> visited = new HashSet<String>();

	public static void main(String[] args) throws IOException {
		processPage("http://mail-archives.apache.org/mod_mbox/maven-users/");
	}

	public static void processPage(String URL) throws IOException {
		if (isValid(URL)) {
			// get useful information
			Document doc = Jsoup.connect(URL).get();
			System.out.println("----"+doc.baseUri());
			//System.out.println(doc.getElementsMatchingText(Pattern.compile("Permalink")));
			System.out.println(URL);
			printWriter(URL, doc);
			//System.out.println(doc.title());
			// get all links and recursively call the processPage method
			Elements questions = doc.select("a[href]");
			for (Element link : questions) {
				if (link.attr("href").contains("2014")) 
					processPage(link.attr("abs:href"));
			}
		}
	}
	private static void printWriter(String url, Document doc) throws FileNotFoundException {
		System.out.println("Saving "+doc.id());
		PrintWriter out = new PrintWriter(new File(doc.id()+"html"));
		out.write(doc.outerHtml());
	}
	private static boolean isValid(String url) {
		return visited.add(url) && !(url.endsWith("js") || url.endsWith("css"));
	}
}