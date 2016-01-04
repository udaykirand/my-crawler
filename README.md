# my-crawler


A simple crawler to fetch and save web pages. The project makes use of jsoup utilities to get web pages and parse them. This has a feature to filter the results if required.
The input file contains, URL to crawl, directory to save downloaded files, number of requests to make, filters if any.

The crawler service searches the for valid files in a recursive DFS.
Crawler makes use of ExecutorService to invoke two thread pools one for parsing the web pages and one for writing the valid pages to disk.

Application saves the log file at `${user.home}/crawler.log`.
During testing with maven-users mailing list for the year 2014, it takes `120-150 seconds` to search through the mailing list and download all 2014 mails. 

## Input file format
url.to.crawl=http://mail-archives.apache.org/mod_mbox/maven-users/

download.directory=/home/udayd/final

num.of.requests=-1 

year=2014

## Steps to execute

1.  mvn install on pom will result in a jar webcrawler.jar (with all required dependencies).
2. Crawler can be started in the following way.
  -  From terminal execute the following command `java -jar webcrawler.jar <input file name>`. The input file is optional. If not specified, application reads the file from `./input/input`.

