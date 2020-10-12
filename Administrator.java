import java.io.*;
import java.util.*;

//A class used to parse arguments and options entered in the command line programs
public class Administrator implements IAdmin{
	//Define the valid options: help, verbose and banner options
    private static final Option help = new Option("-h", "-help", "List a summary of all options and their arguments.");
    private static final Option verbose = new Option("-v", "-verbose", "Enable verbose output.");
    private static final Option banner =  new Option("-b" , "-banner", "Print the application's banner.");
    private HashMap<String, String> proUsage; //used to store the usage of the program(s)
    private HashMap<String, Banner> proBanner; //used to store the banners of the program(s)
	private static File srcFile = null;
	protected String srcFilename;
	private String args[];
	private String programName;
    public List<String> filesList = new ArrayList<String>(); //A list of all valid files entered by the user

	/**
	 * Administrator constructor
	 * @param args[] command line arguments array
	 * @param proUsage usage of the program
	 * @param proBanner banner of the program
	 */
	public Administrator(String[] args, HashMap<String, String> proUsage, HashMap<String, Banner> proBanner) {
		this.args = args;
		this.proUsage = proUsage;
		this.proBanner = proBanner;
	}
	
	/**
	 * A method to parse the command line arguments array
	 * @param args[] command line arguments array
	 * @return boolean, true: valid arguments, false: invalid arguments
	 */
	public boolean parseOptionsArgs(String args[]) throws IOException {
		if(!checkEmptyArgs(args)) { //Check if user has entered arguments
			if(checkProgramName(args[0])) { //Check validity of the program name
				if(isValidArgs(args)) {//Check number of arguments
					for(int i = 1; i < args.length; i++) { //loop all arguments, check if it is an option or a file 
						if (checkOptions(args[i]));
						else if(fileExist(args[i]));
						else
							return false;			
					}
					//print option based on last user option choice
					if(help.isEnaled())
						System.out.println(proUsage.get(programName));
					else if(banner.isEnaled())
						System.out.println(proBanner.get(programName));
					else if(Option.verboseEnabled && getFilesList().length == 0) { //if user didn't specify an option or a file
						System.out.println("Please specify a source file!");
						return false;
					}
					return true;			
				}
				else
					return false;
			}
			else 
				return false;
		}
		else {
//			proUsage.get(programName);
			return false;
		}
	}

	/**
	 * A method to check the validity of a program name
	 * @param programName
	 * @return true if programName is valid, false otherwise
	 */
	public boolean checkProgramName(String programName) {
		if(proBanner.containsKey(programName)) {
			this.programName= programName; 
			return true;
		}
		else {
			System.out.println("invalid program name!");
			return false;
		}
	}
	
	/**
	 * A method to check if the argument is an option then enable it and disable the other options 
	 * @param arg : an argument
	 * @return true if arg is a valid option, false otherwise
	 */
	public boolean checkOptions(String arg) {
		if(help.getOptionVersions().contains(arg)) {
			help.setStatus("enabled");
			Option.verboseEnabled = false;
			banner.setStatus("disabled");
			return true;
		}
		else if(verbose.getOptionVersions().contains(arg)) {
			help.setStatus("disabled");
			Option.verboseEnabled = true;
			banner.setStatus("disabled");
			return true;
		}
		else if(banner.getOptionVersions().contains(arg)) {
			help.setStatus("disabled");
			Option.verboseEnabled = false;
			banner.setStatus("enabled");
			return true;
		}
		return false;
	}
	
	/**
	 * A method to check the validity of the number of arguments
	 * @param args[]
	 * @return true if args length >= 2, false otherwise
	 */
	public boolean isValidArgs(String args[]) {
		if (args.length < 2){
			proUsage.get(programName);
			return false;
		}
		return true;
	}
	
	/**
	 * A method to check if a file exists
	 * @param srcFilename
	 * @return true if file exists, false otherwise
	 * @throws IOException
	 */
	public boolean fileExist(String srcFilename) throws IOException{
		if (srcFilename != null) { // Check <src>
			//System.out.println("Counting Characters in: srcFilename = '" + srcFilename + "'");
			srcFile = new File(srcFilename);
			if (!srcFile.canRead()) {
				System.out.println("Cannot open File '" + srcFilename + "'");
				return false;
			}
			else {
				System.out.println(srcFilename);
				filesList.add(srcFilename);
				System.out.println("[OK] Filename = '" + srcFilename + "'");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * A method to check if arguments array is empty
	 * @param args
	 * @return true if args length = 0, false otherwise
	 */
	public boolean checkEmptyArgs(String [] args) {
		return args.length == 0;
	}
	
	
	/**
	 * A method to set program name
	 * @param programName
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	/**
	 * A method to get program name
	 * @return programName
	 */
	public String getProgramName() {
		return programName;
	}
	
	/**
	 * A method to return the valid list of files entered by user
	 * @return String[] files List
	 */
	public String [] getFilesList() {
		return filesList.toArray(new String[filesList.size()]);
	}
	
}
