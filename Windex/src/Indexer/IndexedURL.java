/**
 * 
 */
package Indexer;

/**
 * @author ltseng and mdunn
 * Special object for tracking occurrences of a term in a given URL
 */
public class IndexedURL {
	private int freq;
	private String url;
	
	public IndexedURL(int freq, String url){
		this.setFreq(freq);
		this.setUrl(url);
	}

	/**
	 * @return the frequency of occurrences
	 */
	public int getFreq() {
		return freq;
	}

	/**
	 * @param freq the freq to set
	 */
	public void setFreq(int freq) {
		this.freq = freq;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
