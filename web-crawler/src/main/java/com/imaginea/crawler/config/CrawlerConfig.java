package com.imaginea.crawler.config;

/**
 * This saves the crawler config. 
 * @author udayd
 *
 */
public class CrawlerConfig {

	private String downloadDirectory;
	
	private String requestUrl;
	
	private int maxReq = -1;
	
	private int crawlerId;
	
	private boolean isShuttingDown = false;
	
	private Filter filter = new Filter();
	
	private String year;
	

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
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

	public int getCrawlerId() {
		return crawlerId;
	}

	public void setCrawlerId(int crawlerId) {
		this.crawlerId = crawlerId;
	}

	public void validate() throws Exception {
		if(downloadDirectory == null || "".equals(downloadDirectory))
			throw new Exception("Invalid Download Directory");
		if(requestUrl == null || "".equals(requestUrl))
			throw new Exception("Invalid request URL");
		/*if(filters.isEmpty()) 
			for(Filter filter : filters)
				filter.validate();*/
	}
	
	public boolean isShuttingDown() {
		return isShuttingDown;
	}

	public void setShuttingDown(boolean isShuttingDown) {
		this.isShuttingDown = isShuttingDown;
	}

	@Override
	public String toString() {
		return "CrawlerConfig [downloadDirectory=" + downloadDirectory + ", requestUrl=" + requestUrl + ", maxReq="
				+ maxReq + "]";
	}

}
