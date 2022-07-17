package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Class used when "help" command is given.
 * Represents implementation of {@link ShellCommand}
 * Specifies instructions for actions using given arguments 
 * 
 * @author 38591
 *
 */

public class HelpCommand implements ShellCommand {

	/**
	 * String value of command name
	 * 
	 */
	
	private String name = "help";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] elements = {};
		if(!arguments.isEmpty()) {
			elements = Utility.argumentSupport(arguments);
		} 
		if(elements.length>1) {
			env.writeln("Invalid number of arguments for this command");
		} else {
			
			if(elements.length == 0) {
				env.commands().forEach((x,y) -> {
					System.out.println(x);
				});
			} else {
				String comName = elements[0];
				if(env.commands().containsKey(comName)) {
					env.writeln(comName);
					List<String> opis = env.commands().get(comName).getCommandDescription();
					opis.forEach(x -> {
						env.writeln(x);
					});
				} else {
					env.writeln("There is no such command!");
				}
			}
		
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
				"If started with no arguments, this command must list names of all supported commands.",
				"If started with single argument, it must print name and the description of selected command (or print \r\n" + 
				"appropriate error message if no such command exists)."
		}));
	}

}
