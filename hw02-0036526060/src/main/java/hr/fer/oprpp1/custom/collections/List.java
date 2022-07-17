package hr.fer.oprpp1.custom.collections;

/**
 * This class represents an implementation of a list
 * 
 * @author 38591
 *
 */

public interface List extends Collection {
	/**
	 * Method returns object located at given index
	 * 
	 * @param index where the requested object is located
	 * @return object at given index
	 */
	Object get(int index);
	
	/**
	 * Stores given object at given position in a list
	 * 
	 * @param value Object element stored at given position
	 * @param position index in a list where object is being stored
	 */
	
	void insert(Object value, int position);
	
	/**
	 * Returns index of given value in a list, if list contains it
	 * 
	 * @param value given object whose location in a list this method searches for
	 * @return index at which given value is stored in a list
	 */
	
	int indexOf(Object value);
	
	/**
	 * Removes element at given index from a list
	 * 
	 * @param index position of an element in a list we want to remove
	 */
	
	void remove(int index);
	
}
