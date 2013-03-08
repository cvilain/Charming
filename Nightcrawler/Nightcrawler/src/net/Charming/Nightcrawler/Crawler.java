/**
 * 
 */
package net.Charming.Nightcrawler;

import java.io.IOException;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author ltseng
 *
 */
public class Crawler {
	private String url; 
	private Document doc;
	public HashMap<String,String> linkMap;
	
	
	public Crawler(String u){
		this.setUrl(u);
		this.linkMap = new HashMap <String,String>();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	/**
	 * Uses Jsoup to pull HTML from the private url variable and
	 * sets it to a Document object.
	 */
	public void getHTML(){
		try {
			this.setDoc(Jsoup.connect(url).get());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void parseHTML(){
		Element body = this.doc.body();
		Elements links = body.getElementsByTag("a");
		for (Element link: links){
			if (link.hasAttr("href")){
				String linkURL = link.attr("href");
				if (linkURL.toCharArray()[0] == '/'){
					try{
						Document currentDoc = Jsoup.connect(this.getUrl() + linkURL).get();
						this.linkMap.put(this.getUrl()+linkURL, currentDoc.toString());

					}catch (IOException err){
						err.getStackTrace();
					}
				}
				else if (linkURL.toCharArray()[0] == '#'){
					continue;
				}
				else{
					try{
						Document currentDoc = Jsoup.connect(linkURL).get();
						this.linkMap.put(linkURL, currentDoc.toString());

					}catch (IOException err){
						err.getStackTrace();
					}
				}
			}
		}
	}
	
	public void printMap(){
		for (String url: this.linkMap.keySet()){
			System.out.println(url);
			System.out.print("\t");
			System.out.print(this.linkMap.get(url).length());
			System.out.println("");
			
		}
	}
	
	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	
}
