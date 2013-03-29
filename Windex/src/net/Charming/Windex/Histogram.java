package net.Charming.Windex;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * @author ltseng
 *
 */
public class Histogram {

	private HashMap<String, Double> args;
	/**
	 * @param args
	 */
	public Histogram(){
		this.setArgs(new HashMap<String, Double>());
		
	}
	
	/**
	 * @return the args
	 */
	public HashMap<String, Double> getArgs() {
		return args;
	}

	/**
	 * @param the args to set
	 */
	public void setArgs(HashMap<String, Double> args) {
		this.args = args;
	}
	
	
	public String toString(){
		String return_string = "";
		int i;
		ArrayList<String> keys = new ArrayList<String>(this.getArgs().keySet());
		
		for (i=0; i<keys.size(); i++){
			return_string = return_string + keys.get(i)
					+ ": " + this.getArgs().get(keys.get(i)) + "\n";
		}
		return return_string;
	}

	
	public void count(String s){
		if (this.getArgs().containsKey(s)){
			double current_val = this.getArgs().get(s);
			this.getArgs().put(s, current_val + 1);
		}else{
			this.getArgs().put(s,(double) 1);			
		}	
	}
	
	public double freq(String s){
		return this.getArgs().get(s);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Histogram h = new Histogram();
		h.count("hello");
		System.out.println(h.freq("hello"));
		System.out.println(h.toString());
	}

}
