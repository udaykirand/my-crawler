package com.imaginea.crawler.util;

import java.io.File;
import com.imaginea.crawler.util.Constants;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.imaginea.crawler.config.CrawlerConfig;

public class InputProcessor extends Constants {
	final static Logger logger = Logger.getLogger(InputProcessor.class);

	public static CrawlerConfig prepareConfig(File inputFile) {
		logger.info("In prepareConfig");
		CrawlerConfig config = new CrawlerConfig();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(inputFile);
			props.load(input);
			config.setDownloadDirectory(props.getProperty(DOWNLOAD_DIR).trim());
			config.setRequestUrl(props.getProperty(URL).trim());
			config.setYear(props.getProperty(YEAR).trim());
		} catch (IOException ex) {

		}
		logger.info("Prepared config [" + config + "]");
		return config;
	}
}
