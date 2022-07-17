package hr.fer.oprpp1.custom.collections;

/**
 * This class is a representation of a stack where we can add and remove elements
 * 
 * @author 38591
 *
 */

public class ObjectStack {
	
private ArrayIndexedCollection array = new ArrayIndexedCollection();
	
	/**
	 * Returns true if array representing this stack is empty, false otherwise
	 * 
	 * @return true if stack is empty, false otherwise
	 */
	
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	/**
	 * Returns integer value of size of array representing this stack
	 * 
	 * @return integer value of size of this stack
	 */
	public int size() {
		return array.size();
	}
	/**
	 * Adds value to array representing this stack
	 * 
	 * @param value Object being pushed to stack
	 */
	
	public void push(Object value) {
		array.add(value);
	}
	
	/**
	 * Returns object  at a last index of an array representing this stack and removes it from that array
	 * 
	 * @return Object taken from stack
	 */
	
	public Object pop() {
		if(!this.isEmpty()) {
			Object ret = new Object();
			ret = array.get(array.size()-1);
			array.remove(ret);
			return ret;
		} else {
			throw new EmptyStackException();
		}
	}
	
	/**
	 * Returns object at a last index of an array representing this stack
	 * 
	 * @return Object on top of a stack
	 */
	
	public Object peek() {
	 if(!this.isEmpty()) {
		Object ret = new Object();
		ret = array.get(array.size()-1);
		return ret;
	 } else {
		 throw new EmptyStackException();
	 }
	}
	
	/**
	 * Clears array representing this stack
	 * 
	 */
	public void clear() {
		array.clear();
	}
}
