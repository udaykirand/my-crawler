package com.imaginea.crawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imaginea.crawler.config.CrawlerConfig;
import com.imaginea.crawler.config.Filter;
import com.imaginea.crawler.util.Stopwatch;

/**
 * 
 * This class is the starting point for executing the crawler. All the config
 * need to be updated here and call the start method from Crawler class. The
 * main method must be passed with min three args 1. URL to crawl 2. Download
 * directory 3. Max number of req (inifinite, if not specified) If user want to
 * apply filters, they can be passed as args above 2. Filters must be in the
 * format of 'name':'value'
 * 
 * As of now this program supports only three filters 1. URL - URL containing
 * any string e.g, url:2014 2. Title - Title of the page e.g. title:bug 3.
 * Content - search for content e.g content:nar
 * 
 * @author udayd
 *
 */
public class MyCrawler {
	static final Logger LOG = LoggerFactory.getLogger(MyCrawler.class);

	public static void main(String[] s) throws Exception {
		long start = System.currentTimeMillis();
		if (s.length < 3) {
			System.out.println(
					"***************Alas!! This program not intelligent enough to read ur brain, just kidding!!"
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
			// Ok, you want to filter the results
			String[] fil = Arrays.copyOfRange(s, 3, s.length);
			for (String f : fil) {
				filter.setName(f.split(":")[0]);
				filter.setValue(f.split(":")[1]);
				filters.add(filter);
			}
			config.setFilters(filters);
		}
		try {
			Runnable crawler = new CrawlerImpl(config);
			ExecutorService exec = Executors.newFixedThreadPool(10);
			for(int i = 0; i < 10; i++)
				exec.execute(crawler);
			/*
			 * Thread thread = new Thread(crawler, "Crawler " + i);
			 * crawler.setThread(thread); thread.start(); System.out.println(
			 * "Crawler "+i+" started");
			 */
			exec.shutdown();
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (Exception e) {
			System.out.println("Something wrong :( !! Best part is, author is good enough to print what is wrong!!");
			e.printStackTrace();
		} finally {
			System.out.println("Elapsed time - " + (System.currentTimeMillis() - start) / 1000);
		}
	}
}
