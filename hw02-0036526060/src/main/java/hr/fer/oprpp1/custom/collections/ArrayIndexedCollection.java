package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * @author 38591
 *
 *
 * General contract of this collection is: duplicate elements are allowed; storage of null references is not allowed
 */

public class ArrayIndexedCollection implements List {
	
	
	
	private  int size;  //current size of collection (number of elements actually stored in elements array)
	private  Object[] elements; //an array of object references which length determines its current capacity (obviously, at any time size can not be greater than array length
	private long modificationCount = 0;
	
	
	/**
	 * Creates an instance with capacity set to 16 
	 * 
	 */
	
	public ArrayIndexedCollection() {
		elements = new Object[16];
	}
	
	/**
	 * 
	 * Creates an instance with capacity set to initialCapacity
	 * 
	 * @param initialCapacity capacity of created instance
	 */
	
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) throw new IllegalArgumentException();
		elements = new Object[initialCapacity];
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
		return size;
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

		Object[] newObject = new Object[size];
		int i = 0;
		for(Object o : elements) {
			if(o!=null) newObject[i] = o;
			++i;
		}
		return newObject;
		
	}

	

	@Override
	public void addAll(Collection other) {
		this.addAll(other);
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
		for(int i=0; i<size;i++) {
			if(elements[i].equals(value)) {
				look = i;
				found = true;
				break;
			}
		}
		if(found) {
			--size;
			for(int i=look; i<elements.length-1;i++) {
				elements[i] = elements[i+1];
			}
			++modificationCount;
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
			
			++modificationCount;
			
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
		++modificationCount;
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
		if(position >=0 && position <= size) {
			int len = elements.length;
			if(this.size() == len) {
				len +=1;
				doubleArr();
			}
			for(int i = len-1; i>position; --i) {
				elements[i] = elements[i-1];
			}
			elements[position] = value;
			++size;
			
			++modificationCount;
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
		if(index>=0 && index<size) {
			for(int i=index; i<elements.length-1;i++) {
				elements[i] = elements[i+1];
			}
			--size;
			++modificationCount;
		} else {
			throw new IndexOutOfBoundsException();
		}
		
	}
	
	
	private static class ElementsGetterImpl implements ElementsGetter {
		
		private ArrayIndexedCollection arr;
		private int counter;
		private long savedModificationCount;
		
		public ElementsGetterImpl(ArrayIndexedCollection col) {
			this.arr = col;
			this.counter = 0;
			savedModificationCount = col.modificationCount;
		}
		
		public boolean hasNextElement() {
			if(savedModificationCount == arr.modificationCount) {
				return counter < arr.size();
			} else {
				throw new ConcurrentModificationException();
			}
		}
		
		public Object getNextElement() {
			if(savedModificationCount == arr.modificationCount) {
				if(counter<arr.size()) {
					Object o = arr.get(counter);
					++counter;
					return o;
				} else {
					throw new NoSuchElementException();
				}
			} else {
				throw new ConcurrentModificationException();
			}
		}
	}
	
	@Override
	public ElementsGetter createElementsGetter() {
		return new ElementsGetterImpl(this);
	}

}