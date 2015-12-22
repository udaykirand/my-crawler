package com.imaginea.crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imaginea.crawler.config.CrawlerConfig;
import com.imaginea.crawler.config.Filter;
import com.imaginea.crawler.util.Stopwatch;

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
public class CrawlerImpl extends AbstractCrawler implements Runnable {
	static final Logger LOG = LoggerFactory.getLogger(CrawlerImpl.class);
	int count;
	private Thread myThread;

	/**
	 * Default Constructor. Creates Crawler with default config
	 */
	public CrawlerImpl() {
		super();
		count = 0;
		visited = new HashSet<String>();
		config = new CrawlerConfig();
		documents = new ArrayList<Document>();
	}

	/**
	 * @param config
	 * @throws Exception
	 */
	public CrawlerImpl(CrawlerConfig config) throws Exception {
		super();
		this.config = config;
		config.validate();
		visited = new HashSet<String>();
		saved = new HashSet<String>();
		documents = new ArrayList<Document>();
		File folder = new File(config.getDownloadDirectory());
		if (!folder.exists()) {
			// System.out.println("Folder doesnt exist");
			if (folder.mkdirs()) {
				// System.out.println("Created folder: " +
				// folder.getAbsolutePath());
			} else {
				throw new Exception(
						"couldn't create the storage folder: " + folder.getAbsolutePath() + " does it already exist ?");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.imaginea.crawler.ICrawler#visitPage(java.lang.String)
	 */
	public List<Document> visitPage(String url) throws IOException {
		System.out.println("In visitPage [" + url + "]");
		if (isValid(url)) {
			try {
				/*Connection.Response response = Jsoup.connect(url).ignoreHttpErrors(true).execute();
				if (response.statusCode() == 200) {*/
					Document doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
					documents.add(doc);
					Elements allLinks = doc.select("a[href]");
					for (Element link : allLinks) {
						if (config.getMaxReq() == -1
								|| count < config.getMaxReq() && url.contains("maven-users/201412")) {
							count++;
							visitPage(link.attr("abs:href"));
						}
					//}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return documents;
	}

	private boolean validLink(String url) {
		for (Filter filter : config.getFilters()) {
			if (filter.isUrlFilter() && url.contains(filter.getValue())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.imaginea.crawler.ICrawler#start(boolean)
	 */
	public List<Document> startNew() throws IOException {
		List<Document> docs = visitPage(this.config.getRequestUrl());
		/*if (!config.getFilters().isEmpty())
			filterResults();*/
		if (documents.size() > 0) {
			saveDocuments();
		}
		return docs;
	}

	/**
	 * Save documents to disk
	 * 
	 * @param documents
	 * @throws FileNotFoundException
	 */
	protected void saveDocuments() {
		// System.out.println("Saving");
		int c = 0;
		PrintWriter out = null;
		try {
			for (Document doc : documents) {
				if (saved.add(doc.baseUri())) {
					c++;
					out = new PrintWriter(config.getDownloadDirectory() + "/" + new File(c + ".html"));
					out.write(doc.html());
					out.close();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// System.out.println("-------- " + c + " --------");
	}

	@Override
	public void run() {
		try {
			startNew();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Thread getThread() {
		return myThread;
	}

	public void setThread(Thread myThread) {
		this.myThread = myThread;

	}

}
