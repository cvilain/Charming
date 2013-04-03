package net.Charming.GoldenRetriever;

import java.util.HashMap;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class GoldenRetriever {

	private String query;
	private Jedis jedis;
	private String host;
	private String auth;
	private int port;
	
	public GoldenRetriever(String q){
		
		this.setHost("spinyfin.redistogo.com");
		this.setAuth("17e73db046b03d22967f27eee076b2ba");;
		this.setPort(9003);
		
		this.setJedis(new Jedis(this.getHost(), this.getPort()));
		this.getJedis().auth(this.getAuth());
		this.getJedis().connect();
		
		this.setQuery(q);
		HashMap<String,Double> freq_map = this.retrieveFromDB(this.getQuery());
		System.out.println(freq_map);
		HashMap<String,Double> calc_map = calc_TFIDF(freq_map);
		System.out.println(calc_map);
	}

	
	private HashMap <String, Double> retrieveFromDB(String key){
		Set<String> urls = this.getJedis().zrange(key, 0, -1);
		HashMap<String,Double> Freq_Map = new HashMap<String,Double>();
		for (String url: urls){
			Freq_Map.put(url, this.getJedis().zscore(key,url));
		}
		return Freq_Map;
	}
	
	private HashMap<String,Double> calc_TFIDF(HashMap<String,Double> freq_map){
		HashMap <String, Double> TFIDF_Map = new HashMap <String,Double>();
		double IDF = 0;
		for (String url:freq_map.keySet()){
			IDF = IDF + freq_map.get(url);
		}
		for(String url:freq_map.keySet()){
			TFIDF_Map.put(url, Math.log(freq_map.get(url))/IDF);
		}
		return TFIDF_Map;
	}
	
	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}


	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}


	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}


	/**
	 * @return the auth
	 */
	public String getAuth() {
		return auth;
	}


	/**
	 * @param auth the auth to set
	 */
	public void setAuth(String auth) {
		this.auth = auth;
	}


	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}


	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}


	/**
	 * @return the jedis
	 */
	public Jedis getJedis() {
		return jedis;
	}


	/**
	 * @param jedis the jedis to set
	 */
	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}
	
}
