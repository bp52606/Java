package hr.fer.oprpp1.custom.collections;

/**
 * Class that represents an implementation of a list
 * 
 * @author 38591
 *
 * @param <T> type of elements in this list
 */

public interface List<T> extends Collection<T>{
	
	/**
	 * Method returns an element at position index
	 * 
	 * @param index of requested element
	 * @return element at position index
	 */
	
	T get(int index);
	
	/**
	 * Method inserts given on given position of a list
	 * 
	 * @param value being inserted to a list
	 * @param position where value is being added on
	 */
	
	void insert(T value, int position);
	
	/**
	 * Method returns index that belongs to given value in a list
	 * 
	 * @param value whose index is requested
	 * @return index of given value
	 */
	
	int indexOf(Object value);
	
	/**
	 * Method removes an element from position index
	 * 
	 * @param index of element that is being removed
	 */
	
	void remove(int index);
}
