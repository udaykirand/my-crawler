package com.imaginea.crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.imaginea.crawler.config.CrawlerConfig;

/**
 * 
 * This is the class which is called from crawler. This class can be invoked with default config or custom config using corresponding constructor
 * When custom config is used, it is validated. In case of error, class throws exception with corresponding message
 * 
 * @author udayd
 *
 */
public class CrawlerImpl extends AbstractCrawler implements ICrawler {
	int count;

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
		documents = new ArrayList<Document>();
		File folder = new File(config.getDownloadDirectory());
		if (!folder.exists()) {
			System.out.println("Folder doesnt exist");
			if (folder.mkdirs()) {
				System.out.println("Created folder: " + folder.getAbsolutePath());
			} else {
				throw new Exception(
						"couldn't create the storage folder: " + folder.getAbsolutePath() + " does it already exist ?");
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see com.imaginea.crawler.ICrawler#visitPage(java.lang.String)
	 */
	public List<Document> visitPage(String url) throws IOException {
		System.out.println("In visitPage");
		if (isValid(url)) {
			Document doc = Jsoup.connect(url).get();
			documents.add(doc);
			Elements allLinks = doc.select("a[href]");
			System.out.println("Fetched all urls "+allLinks.size());
			for (Element link : allLinks) {
				if (config.getMaxReq() == -1 || ++count < config.getMaxReq()) {
					visitPage(link.attr("abs:href"));
				}
					
			}
		}

		// There are user given filters. So, filter the documents
		if (!config.getFilters().isEmpty())
			filterResults();
		
		return documents;
	}

	/* (non-Javadoc)
	 * @see com.imaginea.crawler.ICrawler#start(boolean)
	 */
	@Override
	public List<Document> start(boolean toSave) throws IOException {
		List<Document> docs = visitPage(this.config.getRequestUrl());
		if(toSave)
			saveDocuments(docs);
		return docs;
	}

	/**
	 * Save documents to disk
	 * 
	 * @param documents
	 * @throws FileNotFoundException
	 */
	protected void saveDocuments(List<Document> documents) throws FileNotFoundException {
		for (Document doc : documents) {
			PrintWriter out = new PrintWriter(config.getDownloadDirectory()+"/"+new File(doc.title() + ".html"));
			out.write(doc.outerHtml());
		}

	}

}
