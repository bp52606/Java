package hr.fer.oprpp1.custom.collections;


/**
 * 
 * @author 38591
 *
 * Class Collection represents general collection of objects
 */

public interface Collection<T>{

	/**
	 * 
	 * 
	 * @return true if the collection is empty, false otherwise
	 */
	
	default boolean isEmpty() {
		if(this.size() == 0) return true;
		return false;
	}


	/**
	 * Returns size of this collection
	 * 
	 * @return integer value of this size
	 */
	
	public int size();
	
	/**
	 * Adds value to this collection
	 * 
	 * @param value being added to this collection
	 */
	
	public void add(T value);
	
	/**
	 * 
	 * 
	 * @param value asked for if is in collection
	 * @return true if value is in this collection, false otherwise
	 */
	
	public boolean contains(Object value);
	
	/**
	 * @param value object which the method removes from this collection
	 * @return true only if the collection contains given value as determined by equals method and removes
			   one occurrence of it 
	 */
	
	public boolean remove(T value);
	
	/**
	 * Allocates new array with size equals to the size of this collection, fills it with collection content and
	   returns the array
	   
	 * @return array created from the content of this collection
	 */
	
	public  Object[] toArray();
	
	/**
	 * Method calls processor.process(.) for each element of this collection. The order in which elements will be sent is undefined in this class
	 * 
	 * @param processor instance of Processor which is used to process all elements of this collection
	 */
	
	default void forEach(Processor<T> processor) {
		ElementsGetter<T> eg = this.createElementsGetter();
		while(eg.hasNextElement()) {
			processor.process(eg.getNextElement());
		}
	}
	
	/**
	 * Method adds into the current collection all elements from the given collection. This other collection remains unchanged
	 * 
	 * @param other
	 */
	
	default void addAll(Collection<T> other) {
		
		class LocalProcessor implements Processor<T> {
			
			public void process(T value) {
				add(value);
				
			}

		}
		
		LocalProcessor local = new LocalProcessor();
		other.forEach(local);
	}
	
	/**
	 * Clears this collection
	 * 
	 */
	
	public  void clear();
	
	/**
	 * Returns created ElementsGetter
	 * 
	 * @return ElementsGetter created ElementsGetter
	 */
	public abstract ElementsGetter<T> createElementsGetter();
	
	/**
	 * Tests if we can add given collection to this one
	 * 
	 * @param col given collection to test
	 * @param tester used to test given collection
	 */
	
	default void addAllSatisfying(Collection<T> col, Tester<T> tester) {
		ElementsGetter<T> eg = col.createElementsGetter();
		while(eg.hasNextElement()) {
			T keep = eg.getNextElement();
			if(tester.test(keep)) {
				this.add(keep);
			}
		}
	}

	
	
}
