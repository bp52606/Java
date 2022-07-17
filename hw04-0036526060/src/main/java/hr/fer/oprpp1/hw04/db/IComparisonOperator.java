package hr.fer.oprpp1.hw04.db;
/**
 * Interface that represents functions that operators do
 * 
 * @author 38591
 *
 */
public interface IComparisonOperator {
	
	/**
	 * Method that checks if conditions defined by strategies are satisfied
	 * 
	 * @param value1 first argument of type String being tested
	 * @param value2 second argument of type String being tested
	 * @return true if conditions are satisfied, false otherwise
	 */
	
	public boolean satisfied(String value1, String value2);

}
