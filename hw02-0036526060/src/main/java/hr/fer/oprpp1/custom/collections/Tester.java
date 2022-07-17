package hr.fer.oprpp1.custom.collections;
/**
 * This class represents a model of an object that receives a certain object and tests if it is acceptable or not
 * 
 * @author 38591
 *
 */

public interface Tester {
	
	/**
	 * This function tests if given object is acceptable
	 * 
	 * @param obj given object being tested
	 * @return true if object passed the test, false otherwise
	 */
	
	boolean test(Object obj);

}
