/**
 * 
 */
package net.Charming.Windex;
import redis.clients.jedis.Jedis;


/**
 * @author ltseng and mdunn
 *
 */
public class WindexTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Indexer windex = new Indexer();
		Crawler crawler = new Crawler("http://www.olin.edu", windex);
		
		crawler.crawl(1, "http://www.olin.edu");
		

/*		
		// Testing Jedis
		jedis.set("foo", "bar");
		System.out.println(jedis.get("foo"));

		// Connect to Jedis
		jedis.connect();
		System.out.println("Connected");
	*/	
		/*
		IndexedURL i = new IndexedURL(5, "www.yahoo.com");
		System.out.println(i.getFreq() + " " + i.getUrl());
		Crawler crawler = new Crawler("http://www.olin.edu");
		crawler.crawl(1, "http://www.olin.edu");
		*/
	}

}
