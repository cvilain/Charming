package net.Charming.Windex;

import org.jsoup.nodes.Document;

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
		//find everything not in brackets
		//split into words
		Histogram html_terms = new Histogram();
		for (String term: html_terms.getArgs().keySet()){
			//for each word in the histogram
			//make an indexed url object (url, freq)
			//Get priorityqueue from database
			//Insert object into priorityqueue
			//Save priorityqueue to database
	
		
		}
				
	}
}
