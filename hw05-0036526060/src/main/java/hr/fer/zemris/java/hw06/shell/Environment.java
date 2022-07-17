package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;
/**
 * Representation of an abstraction which will be passed to each defined command. The each implemented command 
 * communicates with user (reads user input and writes response) only through this interface
 * 
 * @author 38591
 *
 */
public interface Environment {
	
	/**
	 * Method used to read user's input from console
	 * 
	 * @return String representation of user's input
	 * @throws ShellIOException exception thrown if there were mistakes made during input
	 */
	
	 String readLine() throws ShellIOException;
	 
	 /**
	  * Method used to write to console(user)
	  * 
	  * @param text String value written to console
	  * @throws ShellIOException thrown is there were mistakes made during writing to console
	  */
	 
	 void write(String text) throws ShellIOException;
	 
	 /**
	  * Method used to write to console(user). Everything is written in it's own line.
	  * 
	  * @param text String value written to console in it's line
	  * @throws ShellIOException thrown is there were mistakes made during writing to console
	  */
	 
	 void writeln(String text) throws ShellIOException;
	 
	 /**
	  * Method that creates sorted map containing String values of command names and belonging ShellCommand
	  * 
	  * @return sorted map containing String values of command names as keys and appropriate ShellCommand as values
	  */
	 
	 SortedMap<String, ShellCommand> commands();
	 
	 /**
	  * Method that returns a character used to symbolize the beginning of a new line of commands
	  * 
	  * @return character symbol of the beginning of a new line
	  */
	 
	 Character getMultilineSymbol();
	 
	 /**
	  * Method that sets a character value that represents the beginning of a new line 
	  * 
	  * @param symbol given to represent the new line beginning
	  */
	 
	 void setMultilineSymbol(Character symbol);
	 
	 /**
	  * Method that returns a character used to symbolize waiting for user to enter his first command
	  * 
	  * @return character symbol of the start of user's input of commands
	  */
	 
	 Character getPromptSymbol();
	 
	 /**
	  * Method that sets a character used to symbolize waiting for user to enter his first command
	  * 
	  * @param symbol given to represent the start of user's input of commands
	  */
	 
	 void setPromptSymbol(Character symbol);
	 
	 /**
	  * Method that returns a character used to inform the shell that more lines are expected
	  * 
	  * @return character symbol of the information about more expected lines
	  */
	 
	 Character getMorelinesSymbol();
	 
	 /**
	  * Method that sets a character used to inform the shell that more lines are expected
	  * 
	  * @param symbol  given to represent information about more expected lines
	  */
	 
	 void setMorelinesSymbol(Character symbol);
	 
}
