package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
	
	public static void main(String[] args) {
		ObjectStack stack = new ObjectStack();
		if(args.length == 1 ) {
			String arg = args[0];
			String[] arguments = arg.split(" ");
			
			for(String s : arguments) {
				char[] turn = s.toCharArray();
				char p = turn[0];
				if(p == '-' && turn.length > 1) {
					stack.push(Integer.valueOf("-" + turn[1]));
				}
				else if(Character.isDigit(turn[0])) {
					stack.push(Integer.valueOf(s));
				} else {
					int one = (int) stack.pop();
					int two = (int) stack.pop();
					if(s.equals("+")) {
						stack.push(Integer.valueOf(one+two));
					} else if(s.equals("-")) {
						stack.push(Integer.valueOf(two-one));
					} else if(s.equals("/")) {
						if(one == 0) {
							System.out.println("You cannot divide by 0");
						}
						stack.push(Integer.valueOf(two/one));
					} else if(s.equals("*")) {
						stack.push(Integer.valueOf(two*one));
					} else {
						stack.push(Integer.valueOf(two%one));
					}
				}
			}
			if(stack.size()!=1) {
				System.out.println("Error");
			} else {
				System.out.println("Expression evaluates to " + stack.pop());
			}
		} else {
			System.out.println("There are no arguments");
		}
		
	}
	
	
}
