package hr.fer.oprpp1.hw04.db;
/**
 * Defines a function for obtaining a requested field value from given StudentRecord
 * 
 * @author 38591
 *
 */
public interface IFieldValueGetter {
	/**
	 * Returns requested parameter of a student record
	 * 
	 * @param record given student record 
	 * @return String value of requested parameter
	 */
	public String get(StudentRecord record);

}
