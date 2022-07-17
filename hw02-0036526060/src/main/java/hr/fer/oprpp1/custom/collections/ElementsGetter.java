package hr.fer.oprpp1.custom.collections;

/**
 * Returns element by element on user's request
 * 
 * @author 38591
 *
 */

public interface ElementsGetter {	
	
	/**
	 * Returns true if next element exist, false otherwise
	 * 
	 * @returns true if next element exist, false otherwise
	 */
	
	public boolean hasNextElement();
	
	/**
	 * Returns Object value of next element
	 * 
	 * @returns Object value of next element
	 */
	
	public Object getNextElement();
	
	/**
	 * Processes remaining elements of this iterator
	 * 
	 * @param p given process
	 */
	
	default void processRemaining(Processor p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
