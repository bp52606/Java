package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;
/**
 * Class used when "tree" command is given.
 * Represents implementation of {@link ShellCommand}
 * Specifies instructions for actions using given arguments 
 * 
 * @author 38591
 *
 */
public class TreeCommand implements ShellCommand {
	
	/**
	 * String value of command name
	 * 
	 */
	
	private String name = "tree";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] elements = {};
		if(!arguments.isEmpty()) {
			elements = Utility.filePathSupport(arguments);
		}

		if(elements.length!=1) {
			env.writeln("Invalid number of arguments for this command");
		} else {
			try {
				Path path = Paths.get(elements[0]);
				if(path.toFile().isDirectory()) {
					printDirectoryTree(path,0,env);
				} else {
					env.writeln("Given file is not a directory");
				}
			} catch(ShellIOException e) {
				return ShellStatus.TERMINATE;
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	private void printDirectoryTree(Path path,int level,Environment env) {
		env.writeln("  ".repeat(level) + path.toFile().getName());
		try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(path)) {
			for(Path p : dirStream){
				if(Files.isDirectory(p)) {
					printDirectoryTree(p, level+1,env);
				} else {
					env.writeln("  ".repeat(level+1) + p.toFile().getName());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

	@Override
	public String getCommandName() {
		return this.name;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(Arrays.asList(new String[] {
				"The tree command expects a single argument: directory name and prints a tree (each directory level shifts output two charatcers to the right)."
		}));
	}

}
