package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Class used when "mkdir" command is given.
 * Represents implementation of {@link ShellCommand}
 * Specifies instructions for actions using given arguments 
 * 
 * @author 38591
 *
 */

public class MkdirCommand implements ShellCommand {
	
	/**
	 * String value of command name
	 * 
	 */
	
	private String name = "mkdir";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] elements = {};
		if(!arguments.isEmpty()) {
			elements = Utility.filePathSupport(arguments);
		}
		if(elements.length!=1) {
			env.writeln("Invalid number of arguments for this command");
		}
		try {
			
			String directoryName = elements[0];
			Path directory = Paths.get(directoryName);
			if(directory.toFile().exists()) {
				env.writeln("File already exists");
				return ShellStatus.CONTINUE;
			}
			if (Files.createDirectory(directory).toFile().exists()) {
				env.writeln("Directory created successfully");
			} else { 	
				env.writeln("Directory already exists");
			}
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return this.name;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(Arrays.asList(new String[] {
				"The mkdir command takes a single argument: directory name, and creates the appropriate directory structure."
		}));
	}

}
