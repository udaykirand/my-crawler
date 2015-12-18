# my-crawler

Steps to execute
1. mvn install on pom will produce a jar with dependencies.
2. Crawler can be started in two ways
  a. From terminal execute the following command
    java -cp webcrawler-jar-with-dependencies.jar com.imaginea.crawler.MyCrawler http://mail-archives.apache.org/mod_mbox/maven-users/201410.mbox/browser /home 50 url:2014
b. Writing own class similar to MyCrawler and invoking start method from CrawlerImpl.
