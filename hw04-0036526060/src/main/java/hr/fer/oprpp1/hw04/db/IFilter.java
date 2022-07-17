package hr.fer.oprpp1.hw04.db;
/**
 * Interface representing acceptance of a {@link StudentRecord} if it satisfies given conditions
 * 
 * @author 38591
 *
 */
public interface IFilter {
	
	/**
	 * Accepts a StudentRecord if it satisfies certain conditions defined in a function
	 * 
	 * @param record given StudentRecord being tested
	 * @return true if record is accepted, false otherwise
	 */
	
	public boolean accepts(StudentRecord record);
	
}
