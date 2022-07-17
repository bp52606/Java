package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * @author 38591
 *
 *
 * General contract of this collection is: duplicate elements are allowed; storage of null references is not allowed
 */

public class ArrayIndexedCollection<T> implements List<T> {
	
	
	
	private  int size;  //current size of collection (number of elements actually stored in elements array)
	private  T[] elements; //an array of object references which length determines its current capacity (obviously, at any time size can not be greater than array length
	private long modificationCount = 0;
	
	
	/**
	 * Creates an instance with capacity set to 16 
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection() {
		elements = (T[])new Object[16];
	}
	
	/**
	 * 
	 * Creates an instance with capacity set to initialCapacity
	 * 
	 * @param initialCapacity capacity of created instance
	 */
	
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) throw new IllegalArgumentException();
		elements = (T[])new Object[initialCapacity];
	}
	
	/**
	 * Creates an instance with elements copied from some other collection and capacity set to 16
	 * 
	 * @param collection a non-null reference to some other Collection
	 */
	
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<T> collection) {
		
		this();
		
		if(collection!=null) {
			
			if(16 < collection.size()) {
				size = collection.size();
			} 
			
			elements = (T[])collection.toArray();
			
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
	
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<T> collection, int initialCapacity) {
		
		this(initialCapacity);
		
		if(collection!=null) {
			
			if(initialCapacity < collection.size()) {
				size = collection.size();
			} 
			
			elements = (T[])collection.toArray();
			
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
	public void addAll(Collection<T> other) {
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
		if(this.contains(value)) {
			for(int i=0; i<size;i++) {
				if(((T)elements[i]).equals(value)) {
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
		}
	
		return found;
		
	}

	@Override
	public void add(T value) {

		if(value != null) {			
			boolean empty = false;
			for(Object o : elements) {
				if(o == null) empty = true;
			}
			

			if(!empty) {				
				doubleArr();
			}
			
			//adding value to collection
			int i = 0;
			int ind = 0;
			for(Object o : elements) {						
				if(o == null) {
					ind = i;
					break;
				}
				++i;
			}
			
			elements[ind] = value;
			++size;
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
		size = 0;
		++modificationCount;
	}
	
	/**
	 * Returns the object that is stored in backing array at position index
	 * 
	 * @param index of an array with requested object  
	 * @return an element of this collection with given index
	 */
	
	public T get(int index) {
		
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
	
	public void insert(T value, int position) {
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
	 * Doubles the size of an array of elements in this collection
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public void doubleArr() {
		Object[] novi = (T[])new Object[2*elements.length];
		int i = 0;
		for(Object o : elements) {
			novi[i] = o;
			++i;
		}
		i=0;
		elements = (T[]) new Object[novi.length];
		for(Object o : novi) {
			elements[i] = (T)o;
			++i;
		}	
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is not found
	 * 
	 * @param value whose index the method is searching for
	 * @return index of given value
	 */

	public int indexOf(Object value) {
		for(int i=0;i<elements.length;++i) {
			if(((T)elements[i])!=null && ((T)elements[i]).equals(value)) return i;
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
	
	/**
	 * Class that represents a getter of elements in this array
	 * 
	 * @author 38591
	 *
	 * @param <T> type of elements in this array
	 */
	
	private static class ElementsGetterImpl<T> implements ElementsGetter<T> {
		
		private ArrayIndexedCollection<T> arr;
		private int counter;
		private long savedModificationCount;
		
		/**
		 * Constructor which initializes an array whose elements are being returned and sets counter to a starting value
		 * 
		 * @param col reference to a collection whose elements this getter returns
		 */
		
		public ElementsGetterImpl(ArrayIndexedCollection<T> col) {
			this.arr = col;
			this.counter = 0;
			savedModificationCount = col.modificationCount;
		}
		
		public boolean hasNextElement() {
			if(savedModificationCount == arr.modificationCount) {
				if(counter < arr.size()) {
					return true;
				} else {
					return false;
				}
			} else {
				throw new ConcurrentModificationException();
			}
		}
		
		public T getNextElement() {
			if(savedModificationCount == arr.modificationCount) {
				//Object o;
				if(counter<arr.size()) {
					T o = arr.get(counter);
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
	public ElementsGetter<T> createElementsGetter() {
		return new ElementsGetterImpl<T>(this);
	}

}
