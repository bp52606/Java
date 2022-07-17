package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
/**
 * Class that represents an implementation of a linked list with elements of type T
 * 
 * @author 38591
 *
 * @param <T> type of elements in this linked list
 */
public class LinkedListIndexedCollection<T> implements List<T> {
	
	private int size = 0;
	private ListNode<T> first = new ListNode<T>();
	private ListNode<T> last = new ListNode<T>();
	private long modificationCount = 0;
	
	/**
	 * Class that represents elements of this list as nodes and defines previous and next element of each element in list
	 * 
	 * @author 38591
	 *
	 * @param <T> type of an element which a node represents
	 */
	
	private static class ListNode<T> {
		ListNode<T> prev  = null;
		ListNode<T> next  = null;
		Object value ;
	}
	
	/**
	 * Creates an empty collection with first and last node set to null
	 * 
	 */
	
	public LinkedListIndexedCollection() {
		first = last = null;
	}
	
	/**
	 * Creates collection with elements copied from some other collection
	 * 
	 */
	
	public LinkedListIndexedCollection(Collection<T> other) {
		this.addAll(other);	
	}
	
	
	@Override
	public void add(T value) {
		ListNode<T> newNode = new ListNode<T>();
		newNode.value = value;
		if(value!=null) {
			if(size==0) {
				first = newNode;
				first.prev = null;
				last = first;
			} 
			++size;
			ListNode<T> keep = last;
			keep.next = newNode;
			last = newNode;
			last.prev = keep;
			last.next = null;
			if(first==last) last.prev = null;
		
			++modificationCount;
		} else {
			throw new NullPointerException();
		}		
	}

	
	@SuppressWarnings("unchecked")
	public T get(int index) {
		
		ListNode<T> prv = first;
		
		if(index < this.size) {		
			int i = 0;
			while(i!=index) {
				prv = prv.next;
				++i;
			}
			
		} else {
			throw new IndexOutOfBoundsException();
		}
		
		return (T)prv.value;
	}
	

	
	@Override
	public boolean contains(Object value) {
		ListNode<T> start = first;
		int i = 0;
		while(i<size) {
			if((start.value).equals(value)) return true;
			start = start.next;
			++i;
		}
		return false;
	}
	
	@Override
	public void clear() {
		ListNode<T> iter = first;
		ListNode<T> keep = new ListNode<T>();
		int i = 0;
		while(i<this.size) {
			keep = iter;
			if(i>0) {
				keep.prev = null;
			}
			iter = iter.next;
			++i;
			
		}
		size = 0;
		
		++modificationCount;
		
	}
	
	@Override
	public boolean isEmpty() {
		if(size == 0) return true;
		return false;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean remove(Object value) {
		boolean removed = false;
		if(this.contains(value)) {
			ListNode<T> start = first;
			int i = 0;
			while(i<this.size) {
				if((start.value).equals(value)) {
					removed = true;
					break;
				}
				start = start.next;
				++i;
			}
			if(start!=null && start.next!=null) {
				(start.next).prev = start.prev;
			} else {
				last = start.prev;
			}
			if(start!=null && start.prev!=null) {
				(start.prev).next = start.next;
			} else {
				first = start.next;
			}
			start = null;
			--size;
			
			++modificationCount;
		} 
		return removed;
	}

	@Override
	public Object[] toArray() {
		ListNode<T> iter = first;
		Object[] arr = new Object[this.size];
		int i = 0;
		while(i<this.size) {
			arr[i] = iter.value;
			iter = iter.next;
			++i;		
		}
		return arr;
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in linked-list
	 * 
	 * @param value given value of an Object being inserted at the given position
	 * @param position given position where given value is being inserted at
	 */

	public void insert(Object value, int position) {
		ListNode<T> addIt = new ListNode<T>();
		addIt.value = value;
		if(position >= 0 && position <= size) {
			ListNode<T> start = first;
			int i = 0;
			while(i<position-1) {
				start = start.next;
				++i;
			}
			
			ListNode<T> keep = new ListNode<T>();
			keep = start;
			if(start.next==null) {
				keep.next = addIt;
				last = addIt;
				last.prev = keep;
				last.next = null;
				
			} else {
				if(position>0) {		
					keep = start.next;
					addIt.prev = keep.prev;
					addIt.next = keep;
					(keep.prev).next = addIt;
					keep.prev = addIt;
					
				} else {		
					keep.prev = addIt;
					first = addIt;
					first.prev = null;
					first.next = keep;
				}
			}
			++size;
			
			++modificationCount;
					
		} else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is not found
	 * 
	 * @param value given value whose index of the first occurrence is being returned
	 * @return index of given value in the list
	 */
	
	public int indexOf(Object value) {
		ListNode<T> start = first;
		int i = 0;
		while(i<size) {
			if((start.value).equals(value)) return i;
			++i;
			start = start.next;
		}
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection
	 * 
	 * @param index given position of an object that this method removes from the list
	 */
	
	@SuppressWarnings("unchecked")
	public void remove(int index) {

		if(index >= 0 && index <= size-1) {
			ListNode<T> start = first;
			int i = 0;
			while(i<index) {
				start = start.next;
				++i;
			}

			
			remove((T)start.value);
			
			++modificationCount;
			
			
					
		} else {
			throw new IndexOutOfBoundsException();
		}
		
		
	}
	/**
	 * Class that represents a getter of elements in this linked list
	 * 
	 * @author 38591
	 *
	 * @param <T> type of elements this list contains
	 */
	private static class ElementsGetterImpl<T> implements ElementsGetter<T> {
		
		private int counter;
		private ListNode<T> obj;
		private LinkedListIndexedCollection<T> linklist;
		private long savedModificationCount;
		
		
		/**
		 * Constructor that accepts a reference to this list and initializes first element of this list
		 and a counter to starting value
		 * 
		 * @param linkL a reference to this linked list
		 */
		
		public ElementsGetterImpl(LinkedListIndexedCollection<T> linkL) {
			this.counter = 0;
			linklist = linkL;
			obj = linklist.first;
			savedModificationCount = linkL.modificationCount;
		}
		
		public boolean hasNextElement() {
			if(savedModificationCount == linklist.modificationCount) {
				if(counter < linklist.size()) return true;
				return false;
			} else {
				throw new ConcurrentModificationException();
			}
		}
		
		@SuppressWarnings("unchecked")
		public T getNextElement() {
			if(savedModificationCount == linklist.modificationCount) {
				
				if(counter<linklist.size()) {
					T o = (T)obj.value;
					++counter;
					obj = obj.next;
					return (T)o;
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
