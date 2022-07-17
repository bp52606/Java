package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


/**
 * When started, program reads the data from current directory from file database.txt. 
 * If in provided file there are duplicate JMBAGs, or if finalGrade is not a number between 1 and 5, 
 * program should terminate with appropriate message to user. 
 * 
 * @author 38591
 *
 */

public class StudentDB {
	
	
	public static void main(String[] args) {
		try {
			
			List<String> fileData = Files.readAllLines(Path.of("src/main/resources/database.txt"),
					StandardCharsets.UTF_8);
			StudentDatabase database = new StudentDatabase(fileData);	
			Scanner sc = new Scanner(System.in);
			System.out.printf("> ");
			String line = sc.nextLine();
			
			while(!line.equals("exit")) {
				
				if(!line.isEmpty()) {
					QueryParser parser = new QueryParser(line.substring(line.indexOf("query")+5));
					List<ConditionalExpression> listOfExpressions = parser.getQuery();
					List<StudentRecord> result = new LinkedList<StudentRecord>();
					int longestSurname;
					int longestName;
					
					if(parser.isDirectQuery()) {
						
						System.out.println("Using index for record retrieval");
						StudentRecord student = database.forJMBAG(parser.getQueriedJMBAG());
						result.add(student);
						longestName = student.getName().length();
						longestSurname = student.getSurname().length();
						printStringFormat(result,student.getName().length(),
								student.getSurname().length());
						
					} else {
						result = database.filter(new QueryFilter(listOfExpressions));
						longestSurname = findLongestString(result.stream()
								.map(s -> s.getSurname()).toArray());
						longestName = findLongestString(result.stream()
								.map(s -> s.getName()).toArray());
						if(!result.isEmpty()) {
							printStringFormat(result,longestName,longestSurname);
						}
					}
					
					
					System.out.println("Records selected: " + result.size());
				}
				
				System.out.printf("> ");
			    line = sc.nextLine();
			}
			
			System.out.println("Goodbye!");
			sc.close();
		
		} catch(IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
		
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	
	/**
	 * Prints String format of given query
	 * 
	 * @param result List of student records that satisfied query conditions
	 * @param longestName number of letters in a longest name that belongs to one of the students
	 * @param longestSurname number of letters in a longest surname that belongs to one of the students
	 */
	
	private static void printStringFormat(List<StudentRecord> result, int longestName, int longestSurname) {
			int jmbag = result.get(0).getJMBAG().length();
			String format = "+" + "=".repeat(jmbag+2) +
			"+" + "=".repeat(longestSurname+2)+
			"+" + "=".repeat(longestName+2)+
			"+" + "=".repeat(3) + "+";
			System.out.println(format);
			Iterator<StudentRecord> it = result.iterator();
			while(it.hasNext()) {
				StudentRecord current = it.next();
				System.out.println("|" + " " + current.getJMBAG() + " " +
				 "|" + " " + current.getSurname() + 
				 " ".repeat(longestSurname-current.getSurname().length()+1) +
				 "|" + " " + current.getName() + 
				 " ".repeat(longestName - current.getName().length() +1)  +
				 "|" + " " + current.getGrade() + " " + "|");
			}
			System.out.println(format);
	}

	
	/**
	 * Looks for the longest String value in given array
	 * 
	 * @param array Object array of values being compared by length of their String value
	 * @return the longest String value in an array
	 */
	
	private static int findLongestString(Object[] array) {
		int size = 0;
		for(Object o : array) {
			String current = String.valueOf(o);
			if(current.length() > size) {
				size = current.length();
			}
		}
		return size;
	}



	
	
}
