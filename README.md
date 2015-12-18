# my-crawler


A simple crawler to fetch and save web pages. This has a feature to filter the results if required.
The input contains, URL to crawl, directory to save downloaded files, number of requests to make, filters if any.
Filters must be in the following format:
*field to be filtered:value*. For example, if user need only the pages whose title is aabbcc, then filter is *title:aabbcc*. If user want a URL filter where all pages whose URL has 2014 in it, filter is *url:2014*. Similarly, page content also can be searched for a pattern.

## Improvements
1.  This can be improvised by using threads and an in-memory database like H2 to keep track of visited pages.
2.  Filters can be changed to use RegEx in place of string functions.
3.  Replace sysout with Log4j/SLF4J


## Steps to execute

1.  mvn install on pom will give a jar with dependencies.
2. Crawler can be started in two ways
  -  From terminal execute the following command java -cp webcrawler-jar-with-dependencies.jar com.imaginea.crawler.MyCrawler *URL dir no. of requests filters*
  -  Writing own class similar to MyCrawler and invoking start method from CrawlerImpl.

