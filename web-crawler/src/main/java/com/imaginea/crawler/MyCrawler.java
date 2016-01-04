package com.imaginea.crawler;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.imaginea.crawler.config.CrawlerConfig;
import com.imaginea.crawler.util.InputProcessor;
import com.imaginea.crawler.util.Stopwatch;
import com.imaginea.crawler.util.Constants;

public class MyCrawler {

	final static Logger logger = Logger.getLogger(MyCrawler.class);

	public static void main(String[] s) throws Exception {
		logger.info("Started MyCrawler");
		logger.info("Log file location [" + System.getProperty("user.home") + "/crawler.log]");
		Stopwatch timer = new Stopwatch();
		BlockingQueue<Document> queue = new ArrayBlockingQueue<Document>(25000);

		File inputFile = null;
		if (s.length > 0) {
			inputFile = new File(s[0]);
		} else {
			inputFile = new File(Constants.DEFAULT_INPUT_DIR);
		}
		if (!inputFile.exists()) {
			logger.error("No input file location specified");
			return;
		}

		CrawlerConfig config = InputProcessor.prepareConfig(inputFile);
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
