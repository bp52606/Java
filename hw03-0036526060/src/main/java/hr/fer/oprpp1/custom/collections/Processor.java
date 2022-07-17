package hr.fer.oprpp1.custom.collections;

/**
 * Class that represents a processor which performs an action on a given value of type T
 * 
 * @author 38591
 *
 * @param <T> type of data this class processes
 */

public interface Processor<T> {
	
	/**
	 * Method performs some kind of an action on given value
	 * 
	 * @param value given variable to be processed
	 */
	
	public void process(T value);
	
}
