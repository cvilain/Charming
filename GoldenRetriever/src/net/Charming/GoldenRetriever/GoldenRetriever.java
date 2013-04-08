package net.Charming.GoldenRetriever;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * Provides methods for retrieving search results for a given search string
 * from a Jedis database.
 * @author ltseng and cvilain
 *
 */

public class GoldenRetriever {

	private String query;
	private Jedis jedis;
	private String host;
	private String auth;
	private int port;
	
	/**
	 * Constructor for the GoldenRetriever class. This sets up the Jedis
	 * instance, private attributes, and executes the search algorithm.
	 * @param q - string of search terms
	 */
	public GoldenRetriever(String q){
		
		this.setHost("spinyfin.redistogo.com");
		this.setAuth("17e73db046b03d22967f27eee076b2ba");;
		this.setPort(9003);
		
		this.setJedis(new Jedis(this.getHost(), this.getPort()));
		this.getJedis().auth(this.getAuth());
		this.getJedis().connect();
		
		this.setQuery(q);
	    String[] words = this.getQuery().split(" ");
	    ArrayList<HashMap<String, Double>> all_TFIDF = new ArrayList<HashMap<String,Double>>();
	    for (String word: words){
			HashMap<String,Double> freq_map = this.retrieveFromDB(word);
			System.out.println(freq_map);
			all_TFIDF.add(calc_TFIDF(freq_map));    	
	    }
    	System.out.println(all_TFIDF);

	    ArrayList<String> final_results = returnResults(all_TFIDF);
		for (String result: final_results){
			System.out.println(result);
		}
	}

	/**
	 * Iterates through an arrayList of HashMaps, where each HashMap represents
	 * the mappings between urls and TF/IDF score for one search term, and calculates
	 * the total TF/IDF score for the search query. It then sorts the results
	 * from least to most relevant.
	 * @param all_TFIDF
	 * @return final_results 
	 */
	private ArrayList<String> returnResults(ArrayList<HashMap<String, Double>> all_TFIDF){
		HashMap<String, Double> total_map = new HashMap<String, Double>();
		
		for (HashMap<String, Double> term_maps: all_TFIDF){
			for (String key: term_maps.keySet()){
				if (total_map.containsKey(key)){
					total_map.put(key, total_map.get(key)+term_maps.get(key));
				}
				else{
					total_map.put(key,term_maps.get(key));
				}
			}
		}
		ArrayList<String> final_results = sortResults(total_map);

		return final_results;
	}
	
	/**
	 * Takes in a HashMap of url to total TF/IDF scores and processes
	 * it into a user-readable ArrayList of urls from most to least
	 * relevant.
	 * @param unsorted
	 * @return
	 */
	
	private ArrayList<String> sortResults(HashMap<String,Double> unsorted){
		ArrayList<String> sorted = new ArrayList<String>();
		for (String key: unsorted.keySet()){
			sorted.add(unsorted.get(key).toString() + " " + key);
		}
		Collections.sort(sorted);
		System.out.println(sorted);
		ArrayList<String> final_sorted = new ArrayList<String>();
		for (String result: sorted){
			final_sorted.add(result.split(" ")[1]);
		}
		Collections.reverse(final_sorted);
		return final_sorted;
	}
	
	/**
	 * Extracts information relative to the given query from the 
	 * associated Jedis database, and returns a HashMap of all the
	 * urls and term frequencies for the given query.
	 * @param key
	 * @return
	 */
	private HashMap <String, Double> retrieveFromDB(String key){
		Set<String> urls = this.getJedis().zrange(key, 0, -1);
		HashMap<String,Double> Freq_Map = new HashMap<String,Double>();
		for (String url: urls){
			Freq_Map.put(url, this.getJedis().zscore(key,url));
		}
		return Freq_Map;
	}
	
	/**
	 * Calculates total IDF score for a given query and then divides
	 * everything in the frequency map by that IDF for the total
	 * TF/IDF score.
	 * @param freq_map
	 * @return TFIDF_Map
	 */
	private HashMap<String,Double> calc_TFIDF(HashMap<String,Double> freq_map){
		HashMap <String, Double> TFIDF_Map = new HashMap <String,Double>();
		double IDF = 0;
		for (String url:freq_map.keySet()){
			IDF = IDF + freq_map.get(url);
		}
		for(String url:freq_map.keySet()){
			TFIDF_Map.put(url, freq_map.get(url)/IDF);
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
