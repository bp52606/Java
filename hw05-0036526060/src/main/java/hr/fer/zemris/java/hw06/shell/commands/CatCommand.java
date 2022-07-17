package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;

import java.io.InputStream;
import java.nio.charset.Charset;
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
 * Class used when "cat" command is given.
 * Represents implementation of {@link ShellCommand}
 * Specifies instructions for actions using given arguments 
 * 
 * @author 38591
 *
 */

public class CatCommand implements ShellCommand {

	/**
	 * String value of command name
	 * 
	 */
	
	private String name = "cat";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		
		String[] elements = {};
		if(!arguments.isEmpty()) {
			elements = Utility.filePathSupport(arguments);
		}
		
		Charset charset = Charset.defaultCharset();
		
		if(elements.length == 2) {
			
			if(Charset.isSupported(elements[1])) {
				charset = Charset.forName(elements[1]);
			} else {
				env.writeln("Given charset is not supported");
				return ShellStatus.CONTINUE;
			}
			
		}
		
		if(elements.length<1 || elements.length>2) {
			env.writeln("Invalid number of arguments for this command");
			return ShellStatus.CONTINUE;
			
		} 
		
		Path path = Paths.get(elements[0]);
		if(path.toFile().exists()) {
			try(InputStream is = Files.newInputStream(path)) {
					
				byte[] buff = new byte[1024];
				while (true) {		
						int r = is.read(buff);
						if(r==-1) break;
						env.writeln(new String(buff, 0, r, charset));
						
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
				
		} else {
			env.writeln("Given file does not exist!");
			return ShellStatus.CONTINUE;
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
				"Command cat takes one or two arguments. The first argument is path to some file and is mandatory.", 
				"The \r\nsecond argument is charset name that should be used to interpret chars from bytes." ,
				"If not provided, a default platform charset should be used (see java.nio.charset.Charset class for details).",
				"This command opens given file and writes its content to console.\r\n"
		}));
	}

}
