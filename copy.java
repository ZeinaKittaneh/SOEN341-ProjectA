// copy.java - Copy sourceFile to destinationFile

import java.io.*;
import java.util.*;

public class copy {
    private static int EOF = -1;
	public static final String USAGE = "The copy program is used to copy sourceFile to destinationFile \nUsage:"
			+ " copy <src file> <dist file>";
	public static final Banner BANNER = new Banner("copy", "1.0", "2020-2021", "Zeina Kittaneh");

    public static void main(String args[]) throws IOException {
    	HashMap<String, String> proUsage = new HashMap<String, String>();
		proUsage.put("copy", USAGE);		
		
		HashMap<String, Banner> proBanner = new HashMap<String, Banner>();
		proBanner.put("copy", BANNER);
    	String programName;
		//Take an object of class Administrator and pass command line arguments, usages, banners of counters to  it
		Administrator admin = new Administrator(args, proUsage, proBanner);

		if(admin.parseOptionsArgs(args)) {
			programName = admin.getProgramName();
			if(admin.getFilesList().length == 1 || admin.getFilesList().length > 2) {//if not valid number of files print usage
				System.out.println(USAGE);
			}
			else if(admin.getFilesList().length == 2){//if valid number files, continue in the copy process
				FileInputStream srcStream = new FileInputStream(admin.getFilesList()[0]);
				FileOutputStream dstStream = new FileOutputStream(admin.getFilesList()[1]);
				
				// Execute the copy.
				int c;
				while ( (c = srcStream.read()) != EOF ) {
					dstStream.write(c);
					if(Option.verboseEnabled)
						System.out.print('.');
				}
				
				// Close and flush all the files.
				srcStream.close();
				dstStream.close();
				
				System.out.println("copy: done.");	
			}
		}
    }
}
