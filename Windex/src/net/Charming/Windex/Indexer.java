package net.Charming.Windex;

import java.util.ArrayList;
import java.util.PriorityQueue;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import redis.clients.jedis.Jedis;

public class Indexer {
	private String host; // = "spinyfin.redistogo.com";
	private String auth; // = "17e73db046b03d22967f27eee076b2ba";
	private int port; //= 9003;
	

	private Jedis jedis; 
	
	public Indexer(){
		this.setHost("spinyfin.redistogo.com");
		this.setAuth("17e73db046b03d22967f27eee076b2ba");
		this.setPort(9003);
		
		this.setJedis(new Jedis(this.getHost(), this.getPort()));
		this.getJedis().auth(this.getAuth());
		this.getJedis().connect();
		System.out.println("Connected");
	}
	

	
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}



//make sure to pass in URL too
	public void index(String url, Document current_page) {
		// TODO Auto-generated method stub
		//doc filled with html
		try{
			//Extract text from html
			String text = current_page.body().text();
			//split into words
			String[] terms = text.split(" ");
			//create histogram of terms from array of strings
			Histogram html_terms = new Histogram();
			for (String term: terms){
				String stripped_term = term.replaceAll("\\W", "");
				html_terms.count(stripped_term);
			}
			System.out.println(html_terms);
		
			for (String term2: html_terms.getArgs().keySet()){
				//for each word in the histogram
				//make an indexed url object (url, freq)
				IndexedURL url_obj = new IndexedURL(html_terms.freq(term2),url);
				//PriorityQueue pqueue = 	this.getJedis().get(term2);
			}
		}
		catch (Exception err){
			System.out.println(err);
		}

		
			//Get priorityqueue from database
			//Insert object into priorityqueue
			//Save priorityqueue to database
	
		
		//}
				
	}
}
