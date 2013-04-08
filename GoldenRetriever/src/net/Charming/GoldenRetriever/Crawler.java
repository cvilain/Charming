/**
 * 
 */
package net.Charming.GoldenRetriever;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author ltseng
 * Class for crawling the Internet from a given base URL
 */
public class Crawler {
	private String url; 
	private Document doc;
	private Indexer indexer;
	public HashMap<String,String> linkMap;
	//public BufferedWriter writer;
	//public FileWriter fwriter;
	
	/**
	 * Constructor for a Crawler object. File writing objects are created
	 * automatically, given a filename.
	 * @param u
	 * @param fname
	 */
	public Crawler(String u, Indexer i){
		this.setUrl(u);
		this.setIndexer(i);
		this.linkMap = new HashMap <String,String>();
		/*
		try{
			this.fwriter = new FileWriter(fname);
			this.writer = new BufferedWriter(this.fwriter);
		}catch(Exception err){
			System.out.println(err);
		}
		*/
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Recursive method for getting html from a given link and parsing the html
	 * to find links, for which it will then get the html. This only gets the
	 * html if the given recursion depth is equal to the max depth.
	 * @param depth
	 * @param url
	 * @return
	 */
	public void crawl(int depth, String url){
		Document current_page = new Document("");
		try{
			current_page = getHTML(url);
		} catch(Exception err){
			System.out.println(err);
		}
		ArrayList<String> next_links = new ArrayList<String>();
		next_links = parseHTML(url, current_page);
		if (depth==3){
			 //System.out.println("Current url is " + url);
			this.linkMap.put(url, current_page.toString());
			/*
			try{
				this.writer.write ("Link is: " + url);
				this.writer.newLine();
				this.writer.write(current_page.toString());
				this.writer.newLine();
				this.writer.newLine();
			}catch (Exception err){
				System.out.println(err);
			}
			*/
			//Pass data to indexer
			this.getIndexer().index(url, current_page);
			return;
		} else{
			for (String link : next_links){
				if (link != url){
					crawl(depth+1,link);
				}
			}
		}
	}
	
	/**
	 * Uses Jsoup to pull HTML from the private url variable and
	 * sets it to a Document object. 
	 */
	public Document getHTML(String url_to_get) throws IOException{
		Document current_doc = Jsoup.connect(url_to_get).get();
		if (current_doc == null){
			throw new IOException();
		}
		return current_doc;
	}

	/**
	 * Given a document, find all the Elements with "a" and "href," construct
	 * a new url, which may or may not contain the base url, and add links to
	 * an ArrayList of string links.
	 * @param url
	 * @param currentDoc
	 * @return
	 */
	public ArrayList<String> parseHTML(String url, Document currentDoc){
		ArrayList<String> next_links = new ArrayList<String>();
		String new_url="";
		try{
			Element body = currentDoc.body();

			Elements links = body.getElementsByTag("a");
			for (Element link: links){
				
				if (link.hasAttr("href")){
					String linkURL = link.attr("href");
					if (linkURL.length()>0){
						if (linkURL.toCharArray()[0] == '/' || linkURL.toCharArray()[0] == '_'){
							new_url = url+linkURL;
						}
						else if (linkURL.toCharArray()[0] == '.'){
							new_url = url+linkURL.substring(1,linkURL.length());
						}
						else if (linkURL.toCharArray()[0] == '#'){
							break;
						}
						else if (!linkURL.substring(0, 4).equals("http")){
							new_url = url+linkURL;
						}
						else{
							new_url = linkURL;
						}
						if (!this.linkMap.containsKey(new_url)){
							next_links.add(new_url);
						}else{
							System.out.println("Duplicate url");
						}
					}
				}
			}
		}catch(Exception err){
			System.out.println(err.toString());
		}
		return next_links;
	}
	
	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}


	/**
	 * @return the indexer
	 */
	public Indexer getIndexer() {
		return indexer;
	}


	/**
	 * @param indexer the indexer to set
	 */
	public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
	}

	
}
