package hr.fer.zemris.java.hw06.shell;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * Class used when "symbol" command is given.
 * Represents implementation of {@link ShellCommand}
 * Specifies instructions for actions using given arguments 
 * 
 * @author 38591
 *
 */
public class SymbolCommand implements ShellCommand {
	
	/**
	 * String value of command name
	 * 
	 */
	
	private String name = "symbol";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] elements = {};
		if(!arguments.isEmpty()) {
			 elements = Utility.argumentSupport(arguments);
		}
		if(elements.length<1 || elements.length>2) {
			env.writeln("Invalid number of arguments for this command");
		} else {
			String symbolName = elements[0];
			char symbol = '"';
			
			if(symbolName.equals("PROMPT")) {
				symbol = env.getPromptSymbol();
			} else if(symbolName.equals("MORELINES")) {
				symbol = env.getMorelinesSymbol();
			} else if(symbolName.equals("MULTILINE")) {
				symbol = env.getMultilineSymbol();
			} else {
				env.writeln("Invalid symbol requested");
				return ShellStatus.CONTINUE;
			}
			
			if(elements.length==1) {
				env.writeln(String.format("Symbol for %s is '%c'",symbolName,symbol));
				
			} else {
	
				char[] newSymbol = elements[1].toCharArray();
				if(symbolName.equals("PROMPT")) {
					env.setPromptSymbol(newSymbol[0]);
				} else if(symbolName.equals("MORELINES")) {
					env.setMorelinesSymbol(newSymbol[0]);
				} else if(symbolName.equals("MULTILINE")) {
					env.setMultilineSymbol(newSymbol[0]);
				} 
				env.writeln(String.format("Symbol for %s changed from '%c' to '%c'",symbolName,
						symbol,newSymbol[0]));
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
				" The command can span across multiple lines.", 
				"Each line that is not the last line of command must end with a special symbol that is used to inform the shell that more lines as expected. "
				, "We will refer to these symbols as PROMPTSYMBOL and MORELINESSYMBOL." ,
				"For each line that is part of multi-line command (except for the first one) a shell must "
				+ "write MULTILINESYMBOL at the beginning followed by a single whitespace." ,
				"Your shell must provide a command symbol that can be used to change these symbols."
		}));
	}

}
