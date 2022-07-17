package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;

public class MyEnvironment implements Environment {

	private Character multipleLine;
	private Character prompt;
	private Character moreLines;
	private Scanner sc = new Scanner(System.in);
	private SortedMap<String,ShellCommand> map;
	
	public MyEnvironment() {
		map = new TreeMap<String, ShellCommand>();
		map.put("charsets", new CharsetCommand());
		map.put("cat", new CatCommand());
		map.put("ls", new LsCommand());
		map.put("tree", new TreeCommand());
		map.put("copy", new CopyCommand());
		map.put("mkdir", new MkdirCommand());
		map.put("hexdump", new HexdumpCommand());
		map.put("help", new HelpCommand());
		map.put("symbol", new SymbolCommand());
	}
	
	@Override
	public String readLine() throws ShellIOException {
		String result = sc.nextLine();
		StringBuilder sb = new StringBuilder();
		if(!result.isEmpty()) {
			while(result.endsWith(String.valueOf(this.getMorelinesSymbol()))) {
				result = result.substring(0, result.length()-1);
				System.out.print(this.getMultilineSymbol()+ " ");
				sb.append(result);
				result = sc.nextLine();
			}
			sb.append(result);
		} else {
			this.writeln("No command is entered");
		}
		return sb.toString();
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.printf(text);
		
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands()  {
		return Collections.unmodifiableSortedMap(map);
		
	}

	@Override
	public Character getMultilineSymbol() {
		return this.multipleLine;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multipleLine = symbol;
		
	}

	@Override
	public Character getPromptSymbol() {
		return this.prompt;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.prompt = symbol;
		
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.moreLines;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.moreLines = symbol;
	}

}
