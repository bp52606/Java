package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;

import java.util.Iterator;

/**
 * 
 * 
 * Class represents a hash code table that enables storage of pairs of keys and values
 * @author 38591
 *
 * @param <K> types of variables that represent keys
 * @param <V> types of variables that represent values
 */

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{

	private TableEntry<K,V>[] table;
	private int modificationCount = 0;
	
	/**
	 * Constructor that creates a table which has a size of 16 slots 
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K,V>[])new TableEntry[16];
	}
	
	/**
	 * Constructor that creates a hash table whose size is a power of number 2 which is equal to or smallest larger number than given argument
	 * 
	 * @param slotSize variable that represents wanted number of slots in table
	 */
	
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int slotSize) {
			
			int i = 0;
			int powerTwo = 1;
			while(powerTwo<slotSize) {
				powerTwo = (int)(Math.pow(Double.valueOf(2),Double.valueOf(i)));
				++i;
			}
			if(powerTwo >= 1) {
				TableEntry<K,V>[] tableEntries = (TableEntry<K,V>[])new TableEntry[powerTwo];
				table = tableEntries;
			} else {
				throw new IllegalArgumentException();
			}	
			
	}
	
	/**
	 * Method adds key variable with it's value to this table or overwrites value if the key already exists in this table
	 * Duplicates size of this table if more than 75% of it's capacity is taken 
	 * 
	 * @param key variable that represents key
	 * @param value variable that represents value of a certain key
	 */
	
	@SuppressWarnings("unchecked")
	public void put(K key, V value) {
		
		if(key!=null) {
			
			//provjera popunjenosti
			if((double) this.size()/this.table.length >= 0.75) {
				++modificationCount;
				TableEntry<K, V>[] thisArray = this.toArray();
				this.table = (TableEntry<K, V>[])new TableEntry[this.table.length*2];
				
				for(int k=0;k<thisArray.length;++k) {
					int hash = hashCode() % this.table.length;
					if (table[hash]==null) {
						table[hash] = new TableEntry<K,V>(thisArray[k].getKey(), thisArray[k].getValue(), null);
					} else {
						TableEntry<K,V> head = table[hash];
						while(head.next!=null) {
							head = head.next;
						}
						head.next = new TableEntry<K,V>(thisArray[k].getKey(), thisArray[k].getValue(), null);
					}
				}
			}
			
			int hash = hashCode() % this.table.length;
			if(!this.containsKey(key)) {
				++modificationCount;
				if (table[hash]==null) {
					table[hash] = new TableEntry<K,V>(key, value, null);
				} else {
					TableEntry<K,V> head = table[hash];
					while(head.next!=null) {
						head = head.next;
					}
					head.next = new TableEntry<K,V>(key, value, null);
				}
			} else {
				for(int i=0;i<table.length;++i) {
					TableEntry<K, V> current = table[i];
					while(current!=null) {
						if(current.getKey().equals(key)) {
							current.setValue(value);
							break;
						}
						current = current.next;
					}
				}
			}
			
		} else {
			throw new NullPointerException();
		}
	}
	
	/**
	 * Method returns true if this table contains key equal to given argument and false otherwise
	 * 
	 * @param key variable that represents key 
	 * @returns true if table contains given key and false otherwise
	 */
	
	public boolean containsKey(Object key) {
		boolean contains = false;
		for(int i=0;i<table.length;++i) {
			TableEntry<K, V> current = table[i];
			while(current!=null) {
				if(current.getKey().equals(key)) {
					contains = true;
					break;
				}
				current = current.next;
			}
		}
		return contains;
	}
	
	/**
	 * Method returns true if this table contains value equal to given argument and false otherwise
	 * 
	 * @param value variable that represents value of a certain key
	 * @returns true if table contains given value and false otherwise
	 */
	
	public boolean containsValue(Object value) {
		boolean contains = false;
		for(int i=0;i<table.length;++i) {
			TableEntry<K, V> current = table[i];
			while(current!=null) {
				if(current.getValue().equals(value)) {
					contains = true;
					break;
				}
				current = current.next;
			}
		}
		return contains;
	}
	
	/**
	 * Returns value of a key in this table given as an argument
	 * 
	 * @param key variable that represents key 
	 * @returns value that belongs to given key
	 */

	public V get(Object key) {
		V returnValue = null;
		for(int i=0;i<table.length;++i) {
			TableEntry<K, V> current = table[i];
			while(current!=null) {
				if(current.getKey().equals(key)) {
					returnValue = current.getValue();
					break;
				}
				current = current.next;
			}
		}
		return returnValue;
	}
	
	/**
	 * Method returns amount of elements stored in this table
	 * 
	 * @returns number of elements actually stored in table
	 */
	
	public int size() {
		int counter = 0;
		for(int i=0;i<table.length;++i) {
			TableEntry<K, V> current = table[i];
			while(current!=null) {
				++counter;
				current = current.next;
			}
		}
		return counter;
	}
	
	/**
	 * Method returns value that belongs to the given key
	 * 
	 * @param key variable that represents key
	 * @return value of key given as an argument
	 */
	
	public V remove(Object key) {
		V value = null;
		for(int i=0;i<table.length;++i) {
			TableEntry<K, V> current = table[i];
			while(current!=null) {
			  if(current.next!=null && current.next.getKey().equals(key)) {
				if(current.next.getKey().equals(key)) {
					value = current.getValue();
					current.next = current.next.next;
					++modificationCount;
					break;
				}
			  } else if(current.getKey().equals(key)) {
				  ++modificationCount;
				  value = table[i].getValue();
				  table[i] = table[i].next;
				  }
			  current = current.next;
			}
		}
		return value;
	}
	
	/**
	 * Method returns true if table contains no elements, false otherwise
	 * 
	 * @return true if table is empty, false otherwise
	 */
	
	public boolean isEmpty() {
		if(this.size() == 0) return true;
		return false;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i=0;i<table.length;++i) {
			TableEntry<K, V> current = table[i];
			while(current!=null) {
				sb.append(current.getKey() + "=" + current.getValue()+ ", ");
				current = current.next;
			}
		}
		String result = "";
		if(this.size()>0) {
			result = sb.substring(0, sb.length()-2) + "]";
		} else {
			sb.append("]");
			result = sb.toString();
		}
		return result;
	}
	
	/**
	 * Method returns an array that contains all elements from this table
	 * 
	 * @return an array containing elements from table
	 */
	
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray() {
		TableEntry<K,V>[] array = (TableEntry<K, V>[]) new TableEntry[this.size()];
		int counter = 0;
		for(int i=0;i<table.length;++i) {
			TableEntry<K, V> current = table[i];
			while(current!=null) {
				array[counter] = current;
				++counter;
				current = current.next;
			}
		}
		return array;
	}
	
	/**
	 * Method deletes all elements from this table 
	 * 
	 */
	
	public void clear() {
		for(int i=0;i<table.length;++i) {
			table[i] = null;
			++modificationCount;
		}
	}
	

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Class that represents pair of key and it's value
	 * Contains a reference to a following pair in a hash table
	 * 
	 * @author 38591
	 *
	 * @param <K> variable that represents key
	 * @param <V> variable that represents value of a certain key
	 */
	
	public static class TableEntry<K,V> {
			private K key;
			private V value;
			private TableEntry<K,V> next;
			
			public TableEntry(K key, V value, TableEntry<K, V> tableEntry) {
				this.key = (K)key;
				this.setValue(value);
				this.next = tableEntry;
			}
			
			public K getKey() {
				return this.key;
			}

			public V getValue() {
				return this.value;
			}

			public void setValue(V value) {
				this.value = value;
			}
		
	}
	
	/**
	 * Class that represents an implementation of an iterator with which we can traverse each and every element in this hash table
	 * Returns information about whether this table contains following elements, returns element that follows currently remembered one
	 and removes currently remembered element from this table
	 * 
	 * @author 38591
	 *
	 */
	
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		private int count = 0;
		private int iteratorModification = modificationCount;
		private TableEntry<K, V> current = table[0];
		private boolean invoked = false;
	
		public boolean hasNext() {
			boolean found = false;
			int counter = count;
			TableEntry<K,V> temp = current;
			while(counter<table.length) {
				if((temp!=null && temp!=current) || (temp!=null && temp.next!=null)) {
					found = true;
					break;
				} else {
					++counter;
					if(counter<table.length) temp = table[counter];
					continue;
				}
			}
			return found;
		}
		
		@SuppressWarnings("rawtypes")
		public SimpleHashtable.TableEntry next() {
			if(iteratorModification == modificationCount) {
				int counter = count;
				TableEntry<K,V> temp = current;
				while(counter<table.length) {
					if((temp!=null && temp!=current) || (temp!=null && temp.next!=null)) {
						if((temp!=null && temp!=current)) {
							current = temp;
							break;
						} else if((temp!=null && temp.next!=null)) {
							current =  temp.next;
							break;
						}
					} else {
						++counter;
						count = counter;
						if(counter<table.length) temp = table[counter];
						continue;
					}
				}
				invoked = false;
				return current;
			} else {
				throw new ConcurrentModificationException();
			}
			
		}
		
		public void remove() {
			if(!invoked) {
				SimpleHashtable.this.remove(current.getKey());
				++iteratorModification;
				invoked = true;
			} else {
				throw new IllegalStateException();
			}
		}
	}

	
}
