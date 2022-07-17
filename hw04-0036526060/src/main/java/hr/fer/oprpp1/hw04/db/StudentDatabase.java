package hr.fer.oprpp1.hw04.db;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Class that represents a database for students
 * 
 * @author 38591
 *
 */
public class StudentDatabase {

	/**
	 * List of student records 
	 * 
	 */
	
	private List<StudentRecord> listOfRecords;
	/**
	 * Map containing student's JMBAG as a key, and his record as value
	 * 
	 */
	private Map<String, StudentRecord> mapOfRecords;	
	
	/**
	 * Constructor that receives list of lines read from a file that contains information about students
	 * 
	 * @param fileData List of String values of information about students
	 */
	
	public StudentDatabase(List<String> fileData) {
		listOfRecords = new LinkedList<StudentRecord>();
		Iterator<String> it = fileData.iterator();
		Set<String> setJMBAG = new LinkedHashSet<String>();
		mapOfRecords = new LinkedHashMap<String, StudentRecord>();
		
		while(it.hasNext()) {
			String current = it.next();
			String[] currentArray = current.split("\t");
			
			String currJmbag = currentArray[0];
			String name = currentArray[1];
			String surname = currentArray[2];
			int grade = Integer.valueOf(currentArray[3]);
			
			if(!setJMBAG.add(currJmbag)) {
				throw new IllegalArgumentException("JMBAG duplicate spotted!");
			}
			
			if(!(grade>=1 && grade<=5)) {
				throw new IllegalArgumentException("Grade value should be in range from 1 to 5!");
			}
			
			StudentRecord record = new StudentRecord(currJmbag, surname, name, grade);
			
			mapOfRecords.put(currJmbag, record);
			
			listOfRecords.add(record);
		}
	}
	
	/**
	 * Returns a student record using given JMBAG
	 * 
	 * @param jmbag String value of a student identification number
	 * @return student record with given JMBAG
	 */
	
	public StudentRecord forJMBAG(String jmbag) {
		
		return this.mapOfRecords.get(jmbag);
	
	}
	
	/**
	 * Filter used to get only certain student records
	 * 
	 * @param filter that defines conditions for choosing student records
	 * @return List of student records that satisfy conditions given by a filter
	 */
	
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temporary = new LinkedList<StudentRecord>();
		
		Iterator<StudentRecord> iterator = listOfRecords.iterator();
		while(iterator.hasNext()) {
			StudentRecord current = iterator.next();
			if(filter.accepts(current)) temporary.add(current);
		}
			
		return temporary;
	}
	
}
