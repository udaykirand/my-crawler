package com.imaginea.crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

import org.jsoup.nodes.Document;

import com.imaginea.crawler.config.CrawlerConfig;

public class Writer implements Runnable {

	private BlockingQueue<Document> docs;
	private String name = null;
	static boolean running = true;
	static String downloadDir = null;

	Writer(CrawlerConfig config, BlockingQueue<Document> docs, String name) {
		System.out.println("Started -- " + name);
		downloadDir = config.getDownloadDirectory()+"/";
		this.docs = docs;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			saveDocuments();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Save documents to disk
	 * 
	 * @param documents
	 * @throws InterruptedException
	 * @throws FileNotFoundException
	 */
	protected void saveDocuments() throws InterruptedException {
		System.out.println("Saving");
		int c = 0;
		PrintWriter out = null;
		Document doc = null;
		while (true) {
			if (!(doc = docs.take()).baseUri().equals("END")) {
				try {
					out = new PrintWriter(downloadDir + new File(name + c++ + ".html"));
					out.write(doc.html());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					if (out != null) {
						out.close();
					}
				}
			} else {
				running = false;
				System.out.println(running + " - " + name);
				break;
			}
		}
		System.out.println("-------- name " + name + " " + c + " --------");
	}

}
