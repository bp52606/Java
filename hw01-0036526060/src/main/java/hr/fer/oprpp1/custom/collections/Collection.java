package hr.fer.oprpp1.custom.collections;


/**
 * 
 * @author 38591
 *
 * Class Collection represents general collection of objects
 */

public class Collection {
	
	protected Collection() {}
		
	/**
	 * @return true if collection contains no objects and false otherwise
	 */
	
	public boolean isEmpty() {
		if(size() == 0) return true;
		return false;
	}

	/**
	 * @return the number of currently stored objects in this collections
	 */
	
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given object into this collection 
	 * 
	 * @param value the given object that is being added to this collection
	 */
	public void add(Object value) {}
	
	
	/**
	 * @param value
	 * @return true only if the collection contains given value, as determined by equals method
	 */
	
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * @param value object which the method removes from this collection
	 * @return true only if the collection contains given value as determined by equals method and removes
			   one occurrence of it 
	 */
	
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this collection, fills it with collection content and
	   returns the array
	   
	 * @return array created from the content of this collection
	 */
	
	public  Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method calls processor.process(.) for each element of this collection. The order in which elements will be sent is undefined in this class
	 * 
	 * @param processor instance of Processor which is used to process all elements of this collection
	 */
	
	public void forEach(Processor processor) {}
	
	/**
	 * Method adds into the current collection all elements from the given collection. This other collection remains unchanged
	 * 
	 * @param other
	 */
	
	public  void addAll(Collection other) {
		
		class LocalProcessor extends Processor {
			
			public void process(Object value) {
				add(value);
				
			}
		}
		LocalProcessor local = new LocalProcessor();
		other.forEach(local);
	}
	
	/**
	 * Removes all elements from this collection
	 * 
	 */
	
	public  void clear() {}
	
}
