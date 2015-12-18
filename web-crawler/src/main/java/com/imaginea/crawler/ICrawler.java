package com.imaginea.crawler;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;

/**
 * @author udayd
 *
 */
public interface ICrawler {

	/**
	 * Invoking point for this class and returns the list of valid documents to
	 * the controller after applying filter
	 * 
	 * @return List<Document>
	 * @throws IOException
	 */
	List<Document> start(boolean toSave) throws IOException;
	
	/**
	 * The method invoked from start. This method uses Jsoup API to read through
	 * the HTML docs and fetch URLs recursively.
	 * 
	 * @param url
	 * @return List<Document>
	 * @throws IOException
	 */
	List<Document> visitPage(String url) throws IOException;

}
