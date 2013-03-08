/**
 * 
 */
package net.Charming.Nightcrawler;
import org.jsoup.nodes.Document;
/**
 * @author ltseng and sbecht
 *
 */
public class TestCrawler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Crawler nightCrawler = new Crawler("http://students.olin.edu");
		nightCrawler.getHTML();
		nightCrawler.parseHTML();
		nightCrawler.printMap();
	}

}
