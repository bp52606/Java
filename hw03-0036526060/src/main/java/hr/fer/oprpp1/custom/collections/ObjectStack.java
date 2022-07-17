package hr.fer.oprpp1.custom.collections;

/**
 * Class that represents a stack
 * 
 * @author 38591
 *
 * @param <T> type of data stored in a stack
 */

public class ObjectStack<T> {
	
	private ArrayIndexedCollection<T> array = new ArrayIndexedCollection<T>();
	
	/**
	 * Method returns true if stack is empty and false if it isn't
	 * 
	 * @return true if stack is empty, false otherwise
	 */
	
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	/**
	 * Method returns amount of data stored in stack
	 * 
	 * @return number of elements in stack
	 */
	
	public int size() {
		return array.size();
	}
	
	/**
	 * Method adds given argument to this stack
	 * 
	 * @param value given to be added to stack
	 */
	
	public void push(T value) {
		array.add(value);
	}
	
	/**
	 * Method returns an element last added to stack and removes it from stack
	 * 
	 * @return an element last added to stack
	 */
	
	public T pop() {
		if(!this.isEmpty()) {
			T ret = array.get(array.size()-1);
			array.remove(ret);
			return ret;
		} else {
			throw new EmptyStackException();
		}
	}
	
	/**
	 * Method returns an element last added to stack and removes it from stack
	 * 
	 * @return an element last added to stack
	 */
	
	public T peek() {
	 if(!this.isEmpty()) {
		T ret = array.get(array.size()-1);
		return ret;
	 } else {
		 throw new EmptyStackException();
	 }
	}
	
	/**
	 * Method removes all data from stack leaving it empty
	 * 
	 */
	
	public void clear() {
		array.clear();
	}
}
