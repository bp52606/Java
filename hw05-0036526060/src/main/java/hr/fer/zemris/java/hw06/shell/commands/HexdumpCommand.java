package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;
/**
 * Class used when "hexdump" command is given.
 * Represents implementation of {@link ShellCommand}
 * Specifies instructions for actions using given arguments 
 * 
 * @author 38591
 *
 */
public class HexdumpCommand implements ShellCommand {

	private String name = "hexdump";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] elements = {};
		if(!arguments.isEmpty()) {
			elements = Utility.filePathSupport(arguments);
		}
		if(elements.length!=1) {
			env.writeln("Invalid number of arguments for this command");
		} else {
			StringBuilder sb = new StringBuilder();
			Path path = Paths.get(elements[0]);
			try(InputStream is = Files.newInputStream
					(path, StandardOpenOption.READ)) {
					
				byte[] buff = new byte[16];
				
				int i = 0;
				while (true) {		
						int r = is.read(buff);
						if (r < 1) break;
						sb.append(String.format("%08X:", i));
						for(int k=0;k<16;++k) {
							if(k==8) {
								if(k<r) {
									sb.append(String.format("%2X",buff[k]));
								} else {
									sb.append(sb.append("  "));
								}
							} else {
								if(k<r) {
									sb.append(String.format(" %2X",buff[k]));
								} else {
									sb.append("   ");
								}
							}
							if((k==7 || k==15)) {
								if(k==15) sb.append(" ");
								sb.append("|");
							} 
						}
						
						sb.append(" ");
						for(int k=0;k<r;++k) {
							char c = (char) buff[k];
							int in = (int) buff[k];
							if(in > 127 || in <32) {
								sb.append(".");
							} else {
								sb.append(c);
							}
						}
						sb.append("\n");
						
						i+=16;
				}
				
				env.writeln(sb.toString());

			} catch (IOException e) {
				e.printStackTrace();
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
				"The hexdump command expects a single argument: file name, and produces hex-output." ,
				"On the right side of the image only a standard subset of characters is shown; for all other characters a\r\n" + 
				"'.' is printed instead (i.e. replace all bytes whose value is less than 32 or greater than 127 with '.')"
		}));
	}

}
