package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Abstraction of operations performed by commands and information about them such as their name and description
 * of their functionality
 * 
 * @author 38591
 *
 */

public interface ShellCommand {
		

	/**
	 * Method that executes operation followed by an input of a certain command
	 * 
	 * @param env given Environment through which command interacts with user
	 * @param arguments user's input after the command
	 * @return status of a shell after performed operation, shell is either terminated or continues it's work
	 */
	
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Method used to access command name
	 * 
	 * @return String value of a name of a command
	 */
	
	String getCommandName();
	
	/**
	 * Method used to access command description
	 * 
	 * @return List of String values of text giving information about a command 
	 */
	
	List<String> getCommandDescription();

}
