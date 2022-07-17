package hr.fer.zemris.java.hw06.shell;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that describes how given arguments in the command line should be processed
 * 
 * @author 38591
 *
 */

public class Utility {

	/**
	 * Variable that informs is if quotation sign was detected
	 * 
	 */
	
	public static boolean quotation = false;
	
	/**
	 * Variable that informs is if space sign was detected
	 * 
	 */
	
	public static boolean space = false;
	
	/**
	 * Variable that informs is if slash sign was detected
	 * 
	 */
	
	public static boolean slash = false;
	
	/**
	 * Method that detects arguments from user's input if a file path was expected to be given
	 * 
	 * @param arguments String value of user's input
	 * @return array of String values of processed arguments 
	 */
	
	public static String[] filePathSupport(String arguments) {
		
		String[] elements = {};
		List<String> list = new LinkedList<String>();
		char[] characters = arguments.toCharArray();
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		
		for(char c : characters) {
			if(c=='"' && !quotation) {
				quotation = true;
			} else if(c=='"') {
				quotation = false;
				space = true;
				list.add(sb.toString());
				sb = new StringBuilder();
			} else if(c==' ' && !quotation) {
				if(space) space = false;
				list.add(sb.toString());
				sb = new StringBuilder();
			} else if(space && c!=' ') {
				throw new ShellIOException("Invalid syntax in the arguments was detected");
			} else {
				sb.append(c);
			}
		}
		
		if(sb.toString().length()>0) list.add(sb.toString());
	
		if(quotation) throw new ShellIOException("Unclosed quotation in arguments!");
		
		elements = new String[list.size()];
		for(String s : list) {
			elements[counter] = s;
			++counter;
		}

		return elements;
		
	}
	
	/**
	 * Method that detects arguments from user's input
	 * 
	 * @param arguments String value of user's input
	 * @return array of String values of processed arguments 
	 */
	
	public static String[] argumentSupport(String arguments) {
		String[] elements = {};
		char[] characters = arguments.toCharArray();
		StringBuilder sb = new StringBuilder();
		for(char c : characters) {
			if(c=='\\' && !slash) {
				slash = true;
			} else if(c=='"' && slash) {
				slash = false;
				sb.append(c);
			} else if(c=='\\') {
				sb.append("\\");
			} else {
				if(slash) slash = false;
				sb.append(c);
			}
		}
		arguments = sb.toString();
		elements = arguments.split(" ");
		return elements;
	}

}
