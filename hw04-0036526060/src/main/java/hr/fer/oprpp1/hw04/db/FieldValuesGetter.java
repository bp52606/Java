package hr.fer.oprpp1.hw04.db;
/**
 * An implementation of {@link IFieldValueGetter}
 * Responsible for obtaining a requested field value from given StudentRecord
 * 
 * @author 38591
 *
 */
public class FieldValuesGetter {
	/**
	 * Getter for first name of a student
	 * 
	 */
	public static final IFieldValueGetter FIRST_NAME = s1 -> s1.getName();
	
	/**
	 * Getter for last name of a student 
	 * 
	 */
	
	public static final IFieldValueGetter LAST_NAME = s1 -> s1.getSurname();
	
	/**
	 * Getter for jmbag of a student
	 * 
	 */
	 
	public static final IFieldValueGetter JMBAG = s1 -> s1.getJMBAG();

}
