package com.imaginea.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.imaginea.crawler.util.Stopwatch;

public class Test {

	public static void main(String[] s) throws InterruptedException {
		/*Stopwatch timer = new Stopwatch();
		ExecutorService exec = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 100; i++) {
			Runnable sample = new JSOUPSample();
			exec.execute(sample);
		}
		exec.shutdown();
		exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		System.out.println("TIMER "+timer.elapsedTime());*/
		System.out.println(validLink("http://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/%3c013EC3103718A9498A522D8AFC31F99009DC36@KA01PWIEXM001.AD.portinfolink.com%3e"));
	}
	
	private static boolean validLink(String url) {
		return url.matches("http://mail-archives.apache.org/mod_mbox/maven-users/2014(.*).mbox/%3c(.*)%3e");
	}
}
