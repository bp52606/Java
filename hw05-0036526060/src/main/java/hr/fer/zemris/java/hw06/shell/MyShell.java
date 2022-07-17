package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;

public class MyShell {
	
	
	public static void main(String[] args) {
		
		
		Environment env = new MyEnvironment();
		System.out.println("Welcome to MyShell v 1.0");
		env.setPromptSymbol('>');
		env.setMultilineSymbol('|');
		env.setMorelinesSymbol('\\');
		Scanner sc = new Scanner(System.in);
		String line;
		ShellCommand current;
		ShellStatus status = ShellStatus.CONTINUE;
		System.out.print(env.getPromptSymbol()+" ");
		
		while(true) {
			try {
				if(status.equals(ShellStatus.TERMINATE)) break;
				line = env.readLine();
				if(!line.isEmpty()) {
					if(line.equals("exit")) break;
						
					String result = "";
					String name;
					if(line.contains(" ")) {
						result = line.substring(line.indexOf(" ")+1);
						name = line.substring(0, line.indexOf(" "));
					} else {
						name = line;
					}
					current = env.commands().get(name);
			
					if(current!=null) {
						status = current.executeCommand(env, result);
					} else {
						System.out.println("That command doesn't exist");
					}
				}
				System.out.print(env.getPromptSymbol()+" ");
				
			} catch(ShellIOException ex) {
				status = ShellStatus.TERMINATE;
			}
		}
		sc.close();
		
	}
}
