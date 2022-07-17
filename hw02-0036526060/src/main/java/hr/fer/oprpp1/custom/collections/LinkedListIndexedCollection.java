package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class represents an implementation of linked list-backed collection of objects
 * 
 * @author 38591
 *
 */

public class LinkedListIndexedCollection implements List {
	
	private int size = 0;
	private ListNode first = new ListNode();
	private ListNode last = new ListNode();
	private long modificationCount = 0;
	
	/**
	 * Class used as a representation of elements in a list
	 * 
	 * @author 38591
	 *
	 */
	
	private static class ListNode {
		ListNode prev  = null;
		ListNode next  = null;
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
	
	public LinkedListIndexedCollection(Collection other) {
		this.addAll(other);	
	}
	
	
	@Override
	public void add(Object value) {
		ListNode newNode = new ListNode();
		newNode.value = value;
		if(value!=null) {
			if(size==0) {
				first = newNode;
				first.prev = null;
				last = first;
			} else {
			
				ListNode keep = new ListNode();
				keep = last;
				keep.next = newNode;
				last = newNode;
				last.prev = keep;
				last.next = null;
				if(first==last) last.prev = null;
				
			}
			++size;
			++modificationCount;
		} else {
			throw new NullPointerException();
		}		
	}


	/**
	 * Returns the object that is stored in linked list at position index
	 * 
	 * @param index of requested Object
	 * @return Object stored at position index
	 */
	
	public Object get(int index) {
		
		ListNode request = first;
		
		if(index < this.size) {		
			int i = 0;
			while(i!=index) {
				request = request.next;
				++i;
			}
			
		} else {
			throw new IndexOutOfBoundsException();
		}
		
		return request.value;
	}
	
	@Override
	public boolean contains(Object value) {
		ListNode start = new ListNode();
		start = first;
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
		first = null;
		last = null;

		size = 0;
		
		++modificationCount;
		
	}
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean remove(Object value) {
			ListNode start = first;
			boolean removed = false;
			int i = 0;
			while(i<this.size) {
				if((start.value).equals(value)) {
					removed = true;
					break;
				}
				start = start.next;
				++i;
			}
			if(start.next!=null) {
				(start.next).prev = start.prev;
			} else {
				last = start.prev;
			}
			if(start.prev!=null) {
				(start.prev).next = start.next;
			} else {
				first = start.next;
			}
			start = null;
			--size;
			
			++modificationCount;
			
			return removed;
	}

	@Override
	public Object[] toArray() {
		ListNode iter = first;
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
		ListNode addIt = new ListNode();
		addIt.value = value;
		if(position >= 0 && position <= size) {
			ListNode start = first;
			int i = 0;
			while(i<position-1) {
				start = start.next;
				++i;
			}
			
			ListNode keep = start;
			
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
		ListNode start = first;
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
	
	public void remove(int index) {

		if(index >= 0 && index <= size-1) {
			ListNode start = first;
			int i = 0;
			while(i<index) {
				start = start.next;
				++i;
			}

			
			remove(start.value);
			
			++modificationCount;
			
			
					
		} else {
			throw new IndexOutOfBoundsException();
		}
		
		
	}
	
	private static class ElementsGetterImpl implements ElementsGetter {
		
		private int counter;
		private ListNode obj;
		private LinkedListIndexedCollection linklist;
		private long savedModificationCount;
		
		public ElementsGetterImpl(LinkedListIndexedCollection linkL) {
			this.counter = 0;
			linklist = linkL;
			obj = linklist.first;
			savedModificationCount = linkL.modificationCount;
		}
		
		public boolean hasNextElement() {
			if(savedModificationCount == linklist.modificationCount) {
				return (counter < linklist.size());
			} else {
				throw new ConcurrentModificationException();
			}
		}
		
		public Object getNextElement() {
			if(savedModificationCount == linklist.modificationCount) {
				if(counter<linklist.size()) {
					Object o = obj.value;
					++counter;
					obj = obj.next;
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
