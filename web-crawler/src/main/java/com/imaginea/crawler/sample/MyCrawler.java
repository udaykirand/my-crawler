package com.imaginea.crawler.sample;

import java.util.ArrayList;
import java.util.List;

import com.imaginea.crawler.CrawlerImpl;
import com.imaginea.crawler.config.CrawlerConfig;
import com.imaginea.crawler.config.Filter;

public class MyCrawler {
	public static void main(String[] s) throws Exception {
		CrawlerConfig config = new CrawlerConfig();
		config.setDownloadDirectory("/home/Documents");
		config.setMaxReq(20);
		config.setRequestUrl("http://mail-archives.apache.org/mod_mbox/maven-users/");
		Filter filter = new Filter();
		filter.setName("url");
		filter.setValue("2014");
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(filter);
		config.setFilters(filters);
		CrawlerImpl crawler = new CrawlerImpl(config);
		
	}
}
