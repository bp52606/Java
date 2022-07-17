package hr.fer.oprpp1.custom.collections;

/**
 * Class that represents a tester which checks if given arguments satisfy certain requests
 * 
 * @author 38591
 *
 * @param <T> type of data being tested
 */

public interface Tester<T> {
	
	/**
	 * Method that returns true if given argument satisfies certain requests and false if it doesn't
	 * 
	 * @param obj given argument to be tested
	 * @return true if given argument satisfies requests, false otherwise
	 */
	
	boolean test(T obj);

}
