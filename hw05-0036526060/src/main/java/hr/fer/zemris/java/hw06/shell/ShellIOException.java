package hr.fer.zemris.java.hw06.shell;
/**
 * Exception thrown when an error occurred while using using
 * Environment.readLine() / Environment.write() / Environment.writeln()
 * 
 * @author 38591
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * Constructor that receives an error message and prints it to console
	 * 
	 * @param string String value of an error message
	 */
	
	public ShellIOException(String string) {
		System.out.println(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
