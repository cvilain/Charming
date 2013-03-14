/**
 * 
 */
package net.Charming.Nightcrawler;
import org.jsoup.nodes.Document;
/**
 * @author ltseng and sbecht
 * Class for testing the Crawler class, which, given a base url,
 * extracts the links from that html for recursive crawling.
 */
public class TestCrawler {

	/**
	 * Instantiate a new Crawler object and write results to a given file.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Crawler nightCrawler = new Crawler("http://www.olin.edu", "Crawler.txt");
		nightCrawler.crawl(1,nightCrawler.getUrl());
	}

}
