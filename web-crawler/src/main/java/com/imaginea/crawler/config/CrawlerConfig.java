package com.imaginea.crawler.config;

import java.util.ArrayList;
import java.util.List;

/**
 * This saves the crawler config. 
 * @author udayd
 *
 */
public class CrawlerConfig {

	private String downloadDirectory;
	
	private String requestUrl;
	
	private int maxReq = -1;
	
	private List<Filter> filters = new ArrayList<Filter>();
	

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getDownloadDirectory() {
		return downloadDirectory;
	}

	public void setDownloadDirectory(String downloadDirectory) {
		this.downloadDirectory = downloadDirectory;
	}
	
	public int getMaxReq() {
		return maxReq;
	}

	public void setMaxReq(int maxReq) {
		this.maxReq = maxReq;
	}

	public void validate() throws Exception {
		if(downloadDirectory == null || "".equals(downloadDirectory))
			throw new Exception("Invalid Download Directory");
		if(requestUrl == null || "".equals(requestUrl))
			throw new Exception("Invalid request URL");
		if(!filters.isEmpty()) 
			for(Filter filter : filters)
				filter.validate();
	}
	@Override
	public String toString() {
		return "CrawlerConfig [downloadDirectory=" + downloadDirectory + ", requestUrl=" + requestUrl + ", maxReq="
				+ maxReq + "]";
	}

}
