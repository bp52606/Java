package hr.fer.oprpp1.custom.collections;

/**
 * Collection of pairs containing keys and their values
 * 
 * @author 38591
 *
 * @param <K> represents key of a dictionary
 * @param <V> represents value of a certain key in a dictionary
 */
public class Dictionary<K,V> {
	
	private ArrayIndexedCollection<Pair> array = new ArrayIndexedCollection<Pair>();
	
	/**
	 * Return true if dictionary has no elements inside, false otherwise
	 * 
	 * @return true if dictionary is empty, false otherwise
	 */
	
	public boolean isEmpty() {
		if (this.size() == 0) return true;
		return false;
	}
	
	/**
	 * Returns integer value of number of elements inside this dictionary
	 * 
	 * @return integer value of a size of dictionary
	 */
	public int size() {
		return this.array.size();
	}
	
	/**
	 * Deletes all elements from this dictionary
	 * 
	 */
	public void clear() {
		this.array.clear();
	}
	
	/**
	 * Adds new key and value to this dictionary or overwrites value of existing key
	 * 
	 * @param key key of a dictionary whose value we change or set
	 * @param value to be added to a given key
	 * @return changed value or null if new key was added
	 */
	
	public V put(K key, V value) {
			V valueRet = null;
			if(this.get(key)==null) {
				this.array.add(new Pair(key,value));
			} else {
				int index = this.array.indexOf(new Pair(key,this.get(key)));
				Pair changePair = this.array.get(index);
				changePair.setValue(value);
				this.array.remove(index);
				this.array.insert(changePair, index);
			}
			return valueRet;
	}
	
	/**
	 * Returns value of given key if the key already exists in dictionary, null otherwise
	 * 
	 * @param key whose value is requested
	 * @return value of given key
	 */
	
	@SuppressWarnings("unchecked")
	public V get(Object key){
		V value = null;
		Object[] array = this.array.toArray();
		for(Object element : array) {
			if((((Pair)element).getKey()).equals(key)){
				value = ((Pair)element).getValue(); 
			}
		}
		return value;
	}
	
	/**
	 * Removes pair from dictionary containing given key and returns value belonging to given key
	 * 
	 * @param key being removed from dictionary 
	 * @return value of given key
	 */
	
	@SuppressWarnings("unchecked")
	public V remove(Object key) {
		V value = null;
		if(this.get(key)!=null) {
			value = this.get(key);
			this.array.remove(this.array.indexOf(new Pair((K)key,value)));
		}
		return value;
	}
	
	/**
	 * Represents a pair containing a key and it's value
	 * 
	 * @author 38591
	 *
	 * @param <K> key of a pair
	 * @param <V> value of a pair
	 */
	
	
	
	private class Pair {
		private K key;
		private V value;
		
		
		/**
		 * Constructor that creates a new Pair of key and it's value
		 * 
		 * @param key variable that represents key
		 * @param value variable that represents value of a certain key
		 */
		
		public Pair (K key, V value){
			this.setKey(key);
			this.setValue(value);
		}

		/**
		 * Method that returns key variable
		 * 
		 * @returns variable that represents key in this pair
		 */
		
		public K getKey() {
			return key;
		}

		/**
		 * Method sets given argument as key of this pair
		 * 
		 * @param key given variable to be set as key of this pair
		 */
		
		public void setKey(K key) {
			if(key!=null) {
				this.key = key;
			} else {
				throw new NullPointerException();
			}
		}

		/**
		 * Method that returns value variable
		 * 
		 * @returns variable that represents value of a key in this pair
		 */
		
		public V getValue() {
			return value;
		}

		/**
		 * Method sets given argument as value of a key in this pair
		 * 
		 * @param key given variable to be set as value of a key in this pair
		 */
		
		public void setValue(V value) {
			this.value = value;
		}
	
		
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object other) {
			if(((Pair)other).getKey().equals(this.getKey()) &&
					((Pair)other).getValue().equals(this.getValue())) {
				return true;
			}
			return false;
		}
		
	}
}
