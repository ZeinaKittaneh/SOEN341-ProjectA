// wc.java - Count characters, lines, and words

import java.io.*;
import java.util.*;

public class wcoo {
//	public static final String USAGE = "The wc program is used to count charcters, words and lines in source file"
//			+ "\nUsage: wc <option> <src file> {src file}";
//	public static final Banner BANNER = new Banner("wc", "1.5", "2020-2021", "Zeina Kittaneh");

	public static void main(String args[]) throws IOException {
		//Add all counters usages to proUsage HashMap:
		HashMap<String, String> proUsage = new HashMap<String, String>();
		proUsage.put("charcount", "The charcount program is used to cound characters in a source file"
				+ "\nUsage: charcount <option> <src file> ");
		proUsage.put("wordcount", "The wordcount program is used to cound words in a source file"
				+ "\nUsage: wordcount <option> <src file> ");
		proUsage.put("linecount", "The linecount program is used to cound lines in a source file"
				+ "\nUsage: linecount <option> <src file> ");
		proUsage.put("wc", "The wc program is used to count charcters, words and lines in source file" + 
				"\nUsage: wc <option> <src file> {src file}");
		
		//Add all banners to proBanner HashMap:
		HashMap<String, Banner> proBanner = new HashMap<String, Banner>();
		proBanner.put("charcount", new Banner("charcount", "1.1", "2020-2021", "Zeina Kittaneh"));
		proBanner.put("wordcount", new Banner("wordcount", "1.2", "2020-2021", "Zeina Kittaneh"));
		proBanner.put("linecount", new Banner("linecount", "1.3", "2020-2021", "Zeina Kittaneh"));
		proBanner.put("wc", new Banner("wc", "1.5", "2020-2021", "Zeina Kittaneh"));
		
		String counterName;
		//Take an object of class Administrator and pass command line arguments, usages, banners of counters to  it
		Administrator admin = new Administrator(args, proUsage, proBanner); 
		
		if(admin.parseOptionsArgs(args)) { //If arguments are valid
			CounterFactory cf = new CounterFactory(); //Create the counter using counter factory
			counterName = admin.getProgramName(); //Identify the counter type
			if(admin.getFilesList().length != 0) { //If user has entered at least one file name, continue
				if(counterName.equals("wc")) {
					new WCcomposite().countingAll(admin.getFilesList(), admin, cf); //Call the composed class for WC counter 
				}
				else {
					Counter cn = cf.getCounter(counterName, admin.getFilesList()[0]); //Call the appropriate counter 
					counterStrategy cs = new counterStrategy(cn); //Create a strategy for the counter
					cs.execCounting(admin.getFilesList()[0]); //execute the counter process
					if(admin.getFilesList().length > 1)
						System.out.println("only one file allowed!");
//					cn.readFile(admin.getFilesList()[0]);
//					cn.startCounting();
				}		
			}
		}
	}
}
