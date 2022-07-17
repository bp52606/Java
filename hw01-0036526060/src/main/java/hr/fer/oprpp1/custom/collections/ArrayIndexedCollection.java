package hr.fer.oprpp1.custom.collections;

/**
 * @author 38591
 *
 *
 * General contract of this collection is: duplicate elements are allowed; storage of null references is not allowed
 */

public class ArrayIndexedCollection extends Collection {
	
	private int size;  //current size of collection (number of elements actually stored in elements array)
	private Object[] elements; //an array of object references which length determines its current capacity (obviously, at any time size can not be greater than array length

	/**
	 * Creates an instance with capacity set to 16 
	 * 
	 */
	
	public ArrayIndexedCollection() {
		this.elements = new Object[16];
	}
	
	/**
	 * 
	 * Creates an instance with capacity set to initialCapacity
	 * 
	 * @param initialCapacity capacity of created instance
	 */
	
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) throw new IllegalArgumentException();
		this.elements = new Object[initialCapacity];
	}
	
	/**
	 * Creates an instance with elements copied from some other collection and capacity set to 16
	 * 
	 * @param collection a non-null reference to some other Collection
	 */
	
	public ArrayIndexedCollection(Collection collection) {
		
		this();
		
		if(collection!=null) {
			
			if(16 < collection.size()) {
				elements = new Object[collection.size()];
			}
			
			this.addAll(collection);
			
		} else {
			throw new NullPointerException();
		}
		
	}
	
	/**
	 * Creates an instance with elements copied from some other collection and capacity set to initialCapacity
	 * 
	 * @param collection a non-null reference to some other Collection
	 * @param initialCapacity capacity of created instance
	 */
	
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		
		this(initialCapacity);
		
		if(collection!=null) {
			
			if(initialCapacity < collection.size()) {
				elements = new Object[collection.size()];
			} 
			
			this.addAll(collection);
			
		} else {
			throw new NullPointerException();
		}
		
	}
	
	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean contains(Object value) {
		for(Object o : elements) {
			if(o!=null && o.equals(value)) return true;
		}
		return false;
	}

	@Override
	public Object[] toArray() {

		Object[] newObject = new Object[this.size];
		int i = 0;
		for(Object o : elements) {
			if(o!=null) newObject[i] = o;
			++i;
		}
		return newObject;
		
	}

	@Override
	public void forEach(Processor processor) {
		for(Object o : elements) {
			if(o!=null) {
				processor.process(o);
			}
		}
	}

	@Override
	public void addAll(Collection other) {
		super.addAll(other);
	}

	@Override
	public boolean isEmpty() {
		for(Object o : elements) {
			if(o!=null) {
				return false;
			} 
		}
		return true;
	}

	@Override
	public boolean remove(Object value) {
		int look = 0;
		boolean found = false;
		for(int i=0; i<this.size;i++) {
			if(elements[i].equals(value)) {
				look = i;
				found = true;
				break;
			}
		}
		if(found) {
			--this.size;
			for(int i=look; i<elements.length-1;i++) {
				elements[i] = elements[i+1];
			}
		} 

		return found;
		
	}

	@Override
	public void add(Object value) {

		if(!value.equals(null)) {			
			boolean empty = false;
			int i = 0;
			int index = 0;
			
			for(Object o : elements) {
				if(o == null)  {
					empty = true;
					index = i;
					break;
				}
				++i;
			}
			

			if(!empty) {	
				index = this.size;
				doubleArr();
			}
			
			//adding value to collection			
			elements[index] = value;
			++this.size;
			
		} else {
			throw new NullPointerException();
		}

	}
	
	@Override
	public void clear() {
		int br = this.size();
		for(int i = 0;i<br;i++) {
			elements[i] = null;
		}
		this.size = 0;
	}
	
	/**
	 * Returns the object that is stored in backing array at position index
	 * 
	 * @param index of an array with requested object  
	 * @return an element of this collection with given index
	 */
	
	public Object get(int index) {
		
		if(index<this.size() && index >=0) {
			return elements[index];
		} else {
			throw new IndexOutOfBoundsException();
		}
		
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in array
	 * 
	 * @param value an object we are inserting in our collection at given position
	 * @param position index of an array where value is inserted
	 * 
	 */
	
	public void insert(Object value, int position) {
		if(position >=0 && position <= this.size) {
			int len = elements.length;
			if(this.size() == len) {
				len +=1;
				doubleArr();
			}
			for(int i = len-1; i>position; --i) {
				elements[i] = elements[i-1];
			}
			elements[position] = value;
			++this.size;
		} else {
			throw new IndexOutOfBoundsException();
		}
		
	}
	
	/**
	 * Doubles the size of this.elements
	 * 
	 */
	
	public void doubleArr() {
		Object[] novi = new Object[2*elements.length];
		
		int i = 0;
		for(Object o : elements) {
			novi[i] = o;
			++i;
		}
		
		elements = novi;
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is not found
	 * 
	 * @param value whose index the method is searching for
	 * @return index of given value
	 */

	public int indexOf(Object value) {
		for(int i=0;i<elements.length;++i) {
			if(elements[i]!=null && elements[i].equals(value)) return i;
		}
		return -1;
		
	}
	
	/**
	 * Removes element at specified index from collection
	 * 
	 * @param index of an element requested to be removed from an array
	 */

	public void remove(int index) {
		if(index>=0 && index<this.size) {
			for(int i=index; i<elements.length-1;i++) {
				elements[i] = elements[i+1];
			}
			--this.size;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}
}
