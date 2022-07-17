package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Class used when "ls" command is given.
 * Represents implementation of {@link ShellCommand}
 * Specifies instructions for actions using given arguments 
 * 
 * @author 38591
 *
 */

public class LsCommand implements ShellCommand {

	/**
	 * String value of command name
	 * 
	 */
	
	private String name = "ls";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] elements = {};
		if(!arguments.isEmpty()) {
			elements = Utility.filePathSupport(arguments);
		}
		StringBuilder sb = new StringBuilder();
		if(elements.length!=1) {
			env.writeln("Invalid number of arguments for this command");
		} else {
			try {
				Path path = Paths.get(elements[0]);
				if(path.toFile().isDirectory()) {
					try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(path)) {
							
						for(Path p : dirStream) {
							
								sb.append(firstColumn(p));
								long size = p.toFile().length();
								sb.append(" ");
								sb.append(" ".repeat(10-String.valueOf(size).length()));
								sb.append(size);
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								BasicFileAttributeView faView = Files.getFileAttributeView(
										p, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
										);
								BasicFileAttributes attributes = faView.readAttributes();
								FileTime fileTime = attributes.creationTime();
								String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
								sb.append(" ");
								sb.append(formattedDateTime);
								sb.append(" ");
								sb.append(p.toFile().getName());
								
								env.writeln((sb.toString()));
								sb = new StringBuilder();
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} else {
					env.writeln("Given file is not a directory!");
				}
			} catch(ShellIOException e) {
				return ShellStatus.TERMINATE;
			}
		}
		return ShellStatus.CONTINUE;
	}

	private String firstColumn(Path p) {
		StringBuilder sb = new StringBuilder();
		if(p.toFile().isDirectory()) {
			sb.append("d");
		} else {
			sb.append("-");
		}
		if(p.toFile().canRead()) {
			sb.append("r");
		} else {
			sb.append("-");
		}
		if(p.toFile().canWrite()) {
			sb.append("w");
		} else {
			sb.append("-");
		}
		if(p.toFile().canExecute()) {
			sb.append("x");
		} else {
			sb.append("-");
		}
		return sb.toString();
	}

	@Override
	public String getCommandName() {
		return this.name;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(Arrays.asList(new String[] {
				"Command ls takes a single argument – directory – and writes a directory listing (not recursive).",
				"Output should be formatted as in following example."
		}));
	}

}
