package com.imaginea.crawler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imaginea.crawler.config.CrawlerConfig;
import com.imaginea.crawler.config.Filter;

/**
 * 
 * Abstract class to be extended by the crawler class. This will hold any generic methods.
 * 
 * @author udayd
 *
 */
public abstract class AbstractCrawler {

	Set<String> visited = null;
	List<Document> documents = null;
	protected CrawlerConfig config;
	static final Logger LOG = LoggerFactory.getLogger(AbstractCrawler.class);

	/**
	 * Verifies of the URL has been visited in this session or not. Ignores if
	 * visited already
	 * 
	 * @param url
	 * @return
	 */
	protected boolean isValid(String url) {
		return visited.add(url) && !(url.endsWith("js") || url.endsWith("css"));
	}

	/**
	 * Removes the list of docs from the fetched documents depending on the
	 * filters
	 */
	protected void filterResults() {
		List<Document> removeList = new ArrayList<Document>();
		LOG.info("Received " + documents.size() + " from visitPage");
		for (Document doc : documents) {
			for (Filter filter : config.getFilters()) {

				switch (filter.getName()) {
				case "title":
					LOG.info("---TITLE---");
					if (!doc.title().equalsIgnoreCase(filter.getValue()))
						removeList.add(doc);
					break;
				case "url":
					LOG.info("---URL---");
					if (!doc.baseUri().contains(filter.getValue()))
						removeList.add(doc);
					break;
				case "content":
					LOG.info("---Content---");
					if (!doc.text().contains(filter.getValue()))
						removeList.add(doc);
					break;
				case "content-type":
					// TODO Implement for various content-types
					break;
				default:
					break;
				}

			}
		}
		LOG.info("Removing " + removeList.size() + " documents");
		documents.removeAll(removeList);
	}

	protected abstract void saveDocuments(List<Document> docs) throws FileNotFoundException;
}
