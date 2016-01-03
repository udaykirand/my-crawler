package com.imaginea.crawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.imaginea.crawler.config.CrawlerConfig;
import com.imaginea.crawler.config.Filter;
import com.imaginea.crawler.util.Stopwatch;

public class MyCrawler {
	
	final static Logger logger = Logger.getLogger(MyCrawler.class);
	
	public static void main(String[] s) throws Exception {
		//System.setProperty("logfile.name","F:/crawler.log");
		logger.info("Started");
		Stopwatch timer = new Stopwatch();
		BlockingQueue<Document> queue = new ArrayBlockingQueue<Document>(25000);
		CrawlerConfig config = new CrawlerConfig();
		List<Filter> filters = new ArrayList<Filter>();
		Filter filter = new Filter();

		config.setRequestUrl(s[0]);
		config.setDownloadDirectory(s[1]);
		config.setMaxReq(Integer.parseInt(s[2]));

		/*if (s.length > 3) {
			// Ok, you want to filter the results
			String[] fil = Arrays.copyOfRange(s, 3, s.length);
			for (String f : fil) {
				filter.setName(f.split(":")[0]);
				filter.setValue(f.split(":")[1]);
				filters.add(filter);
			}
			config.setFilters(filters);
		}*/
		Set<String> visited = new HashSet<String>();

		ExecutorService executor = Executors.newFixedThreadPool(20);
		for (int i = 0; i < 10; i++) {
			Runnable writer99 = new Writer(config, queue, "writer99" + i);
			Runnable crawler = new CrawlerImpl(config, "Crawler" + i, queue, visited);
			executor.submit(crawler);
			Thread.sleep(333);
			executor.submit(writer99);
		}
		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		logger.info("Completed in [" + timer.elapsedTime() + "]");
	}

}
