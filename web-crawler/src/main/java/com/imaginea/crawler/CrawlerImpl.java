package com.imaginea.crawler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.imaginea.crawler.config.CrawlerConfig;

/**
 * 
 * This is the class which is called from crawler. This class can be invoked
 * with default config or custom config using corresponding constructor When
 * custom config is used, it is validated. In case of error, class throws
 * exception with corresponding message
 * 
 * @author udayd
 * 
 */
public class CrawlerImpl implements Runnable {
	private String name;
	private Set<String> visited = null;
	private BlockingQueue<Document> docs;
	CrawlerConfig config = null;
	final static Logger logger = Logger.getLogger(CrawlerImpl.class);
	private static String URL_PATTERN = null;

	/**
	 * @param config
	 * @throws Exception
	 */
	public CrawlerImpl(CrawlerConfig config, String name, BlockingQueue<Document> docs, Set<String> visited)
			throws Exception {
		super();
		this.config = config;
		config.validate();
		this.name = name;
		this.visited = visited;
		this.docs = docs;
		URL_PATTERN = config.getRequestUrl() + "(.*)" + config.getYear() + "(.*).mbox/%3c(.*)%3e";
	}

	/**
	 * @param url
	 * @param start
	 * @throws IOException
	 */
	public void visitPage(String url, boolean start) throws IOException {
		// logger.info("In visitPage [" + url + "]");
		if (isValid(url) || start) {
			Document doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
			try {
				if (validLink(url)) {
					docs.put(doc);
				} else {
					Elements allLinks = doc.select("a[href]");
					for (Element link : allLinks) {
						visitPage(link.attr("abs:href"), false);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean validLink(String url) {
		return url.matches(URL_PATTERN);
	}

	@Override
	public void run() {
		try {
			visitPage(config.getRequestUrl(), true);
			logger.info("Setting END to [" + this.name + "]");
			docs.put(new Document("END"));
			logger.info("Thread [" + name + "] Visited [" + visited.size() + "]");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected boolean isValid(String url) {
		return visited.add(url) && url.contains(config.getRequestUrl()) && url.contains(config.getYear());
	}
}
