package com.imaginea.crawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;

import com.imaginea.crawler.config.CrawlerConfig;
import com.imaginea.crawler.config.Filter;

/**
 * 
 * This class is the starting point for executing the crawler. All the config need to be updated here and call the start method from Crawler class.
 * The main method must be passed with min three args 1. URL to crawl 2. Download directory 3. Max number of req (inifinite, if not specified)
 * If user want to apply filters, they can be passed as args above 2.
 * Filters must be in the format of 'name':'value'
 * 
 *  As of now this program supports only three filters
 *  1. URL - URL containing any string e.g, url:2014
 *  2. Title - Title of the page e.g. title:bug
 *  3. Content - search for content e.g content:nar
 * 
 * @author udayd
 *
 */
public class MyCrawler {

	public static void main(String[] s) throws Exception {
		if (s.length < 3) {
			System.out.println("***************Alas!! This program not intelligent enough to read ur brain, just kidding!!"
					+ "\nPlease enter valid URL and Download directory***************");
			return;
		}

		CrawlerConfig config = new CrawlerConfig();
		List<Filter> filters = new ArrayList<Filter>();
		Filter filter = new Filter();

		// config.setDownloadDirectory("/home");
		// config.setMaxReq(1000);
		config.setRequestUrl(s[0]);
		config.setDownloadDirectory(s[1]);
		config.setMaxReq(Integer.parseInt(s[2]));
		
		if (s.length > 3) {
			//Ok, you want to filter the results
			String[] fil = Arrays.copyOfRange(s, 3, s.length);
			for (String f : fil) {
				filter.setName(f.split(":")[0]);
				filter.setValue(f.split(":")[1]);
				filters.add(filter);
			}
			config.setFilters(filters);
		}
		try {

			ICrawler crawler = new CrawlerImpl(config);
			List<Document> documents = crawler.start(true);
			System.out.println("Returned docs - "+documents.size());
			System.out.println("So, here you have all pages in hand. I am not saving them to disk as of now."
					+ "Invoke Crawler.start(true) to save copies on disk in the given download directory");
		} catch (Exception e) {
			System.out.println("Something wrong :( !! Best part is author is good enough to print what is wrong!!");
			e.printStackTrace();
		}
	}
}
