package com.imaginea.crawler.config;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * Saves the filters given by user
 * @author udayd
 *
 */
public class Filter {
	private String name;
	private String value;
	private static final List<String> VALID_FILTER_NAMES = Arrays.asList(new String[] {"title", "url", "content", "content-type"});

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void validate() throws Exception {
		if(name == null || "".equals(name) || !VALID_FILTER_NAMES.contains(name)) 
			throw new Exception("Invalid filter");
	}

	@Override
	public String toString() {
		return "Filter [name=" + name + ", value=" + value + "]";
	}

}
