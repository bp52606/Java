package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
 * Class used when "copy" command is given.
 * Represents implementation of {@link ShellCommand}
 * Specifies instructions for actions using given arguments
 * 
 * @author 38591
 *
 */

public class CopyCommand implements ShellCommand {

	/**
	 * String value of command name
	 * 
	 */
	
	private String name = "copy";
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] elements = {};
		
		if(!arguments.isEmpty()) {
			elements = Utility.filePathSupport(arguments);
		}
		
		if(elements.length!=2) {
			
			env.writeln("Invalid number of arguments for this command");
			return ShellStatus.CONTINUE;
			
		} else {
		
			Path source = Paths.get(elements[0]);
			Path destination = Paths.get(elements[1]);
			
			if(!source.toFile().exists()) {
				env.writeln("Given source file doesn't exist. Please provide another one");
				return ShellStatus.CONTINUE;
			}
			
			if(destination.toFile().exists() && !destination.toFile().isDirectory()) {
			
				env.writeln("Is is ok to overwrite destination file? ('Yes'/'No') : ");
				env.writeln(String.valueOf(env.getPromptSymbol()));
				String s = env.readLine();
				
				if(s.equals("No")) {
					env.writeln("You don't want to overwrite given file. Please choose an empty file");
					return ShellStatus.CONTINUE;
				}
				
			}
			
			if(destination.toFile().isDirectory()) {
				destination = Paths.get(destination +"/"+ source.toFile().getName());
			} 
				
			try(InputStream is = Files.newInputStream(source); 
					OutputStream os = Files.newOutputStream(destination)){
				
				  byte[] buff = new byte[1024];
				  
				  while(true) {
					  int r = is.read(buff);
					  if(r==-1) break;
					  os.write(buff,0,r);
				  }						         
	
				} catch(IOException ex) {
					env.writeln(ex.getMessage());
					return ShellStatus.CONTINUE;
				}
		}
		env.writeln("Given file is successfully copied");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return this.name;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(Arrays.asList(new String[] {
			"The copy command expects two arguments: source file name and destination file name (i.e. paths and names).", 
			"Is destination file exists, you should ask user is it allowed to overwrite it." ,
			"Your copy command must work only with files (no directories).",
			"If the second argument is directory, you should assume that user wants to copy the original file into that directory using the original file name.", 
			" You must implement copying yourself: you are not allowed to simply call copy methods from Files class."	
		}));
	}


}
