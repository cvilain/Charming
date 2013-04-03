package net.Charming.GoldenRetriever;

import java.util.ArrayList;
import java.util.PriorityQueue;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import redis.clients.jedis.Jedis;

/**
 * Accepts search terms from the crawler, connects to an instance of a RedisToGo
 * database, and processes the HTML text to find terms to put into the database
 * in the form of sorted sets.
 * @author ltseng and mdunn
 *
 */
public class Indexer {
	private String host; // = "spinyfin.redistogo.com";
	private String auth; // = "17e73db046b03d22967f27eee076b2ba";
	private int port; //= 9003;
	

	private Jedis jedis; 
	
	/**
	 * Connects to a RedisToGo database and empties it.
	 */
	public Indexer(){
		this.setHost("spinyfin.redistogo.com");
		this.setAuth("17e73db046b03d22967f27eee076b2ba");
		this.setPort(9003);
		
		this.setJedis(new Jedis(this.getHost(), this.getPort()));
		this.getJedis().auth(this.getAuth());
		this.getJedis().connect();
		this.getJedis().flushAll();
		System.out.println("Connected");
	}
	

	
	/**
	 * @return host
	 */
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 
	 * @return auth 
	 */
	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	/**
	 * return port
	 * @return
	 */
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 
	 * @return jedis 
	 */
	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}



	/**
	 * Takes html in a Jsoup document, extracts the text and splits into key terms,
	 * builds a histogram of the terms, and adds each term to the RedisToGo database
	 * in a sorted set where the term is the key, the frequency is the score, and the
	 * url string is the member of the set.
	 * @param url - the url of the current page
	 * @param current_page - Jsoup document representing the current page's HTML
	 */
	public void index(String url, Document current_page) {
		System.out.println("Now indexing " + url);
		try{
			//Extract text from html
			String text = current_page.body().text();
			//split into words
			String[] terms = text.split(" ");
			//create histogram of terms from array of strings
			Histogram html_terms = new Histogram();
			for (String term: terms){
				//gets rid of whitespace characters
				String stripped_term = term.replaceAll("\\W", "");
				html_terms.count(stripped_term);
			}
			
			for (String term2: html_terms.getArgs().keySet()){
				//Add terms to the RedisToGo database
				this.getJedis().zadd(term2, html_terms.getArgs().get(term2), url);
			}
			
			//System.out.println(this.getJedis().zscore("Olin",url));
		}
		catch (Exception err){
			System.out.println(err);
		}
	}
}
