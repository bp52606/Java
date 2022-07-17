package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Class used when "charsets" command is given.
 * Represents implementation of {@link ShellCommand}
 * Specifies instructions for actions using given arguments
 * 
 * @author 38591
 *
 */

public class CharsetCommand implements ShellCommand {

	/**
	 * String value of command name
	 * 
	 */
	
	private String name = "charsets";

	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] elements = {};
		if(!arguments.isEmpty()) {
			elements = Utility.argumentSupport(arguments);
		}
		if(elements.length!=0) {
			env.writeln("This command shouldn't take arguments");
		} else {
			Charset.availableCharsets().forEach((x,y) -> {
				env.writeln((x));
			});
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
				"Command charsets takes no arguments and lists names of supported charsets for your Java platform", 
				"Charset.availableCharsets()). A single charset name is written per line."
		}));
	}

}
