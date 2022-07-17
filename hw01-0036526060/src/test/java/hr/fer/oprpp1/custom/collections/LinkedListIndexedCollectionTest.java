package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Tests implementation of LinkedListIndexedCollection class
 * 
 * @author 38591
 *
 */
class LinkedListIndexedCollectionTest {

	LinkedListIndexedCollection exe = new LinkedListIndexedCollection();
	
	/**
	 * Actions performed before each test
	 * 
	 */
	
	@BeforeEach
	public void BeforeTests() {
		exe.add(Integer.valueOf(1));
		exe.add(Integer.valueOf(2));
		exe.add(Integer.valueOf(13));
		exe.add(Integer.valueOf(45));
		exe.add(Integer.valueOf(1));	 //adding element with value already in a list, should be allowed
	}
	
	/**
	 * Tests second constructor
	 * 
	 */

	@Test
	public void TestConstructor2() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection(3);
		arr.add(1);
		arr.add(2);
		arr.add(3);
		exe = new LinkedListIndexedCollection(arr);
		assertEquals(3, exe.size());
	}
	
	/**
	 * Tests if implementation adds new elements correctly
	 * 
	 */
	
	@Test
	public void TestAddIntoLinkedList() {
		assertThrows(NullPointerException.class, () -> exe.add(null));
	}
	
	/**
	 * Tests if implementation returns elements at given index correctly
	 * 
	 */
	
	@Test
	public void TestGetObjectWithIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> exe.get(7));
	}
	
	/**
	 * Tests if implementation clears the collection correctly
	 * 
	 */
	
	@Test
	public void TestClear() {
		exe.clear();
		assertEquals(0, exe.size());
	}
	
	/**
	 * Tests if implementation inserts new elements correctly
	 * 
	 */
	
	@Test
	public void TestInsert() {
		assertThrows(IndexOutOfBoundsException.class, () -> exe.insert("string", 15));
		exe.insert(25, 0);
		assertEquals(1, exe.indexOf(1));
		exe.insert(67, 6);
		assertEquals(6, exe.indexOf(67));
		exe.insert(456, 3);
		assertEquals(4, exe.indexOf(13));
		assertEquals(7, exe.indexOf(67));
	}

	/**
	 * Tests if implementation returns information about whether given element is in this collection correctly
	 * 
	 */
	
	@Test
	public void TestContains() {
		assertEquals(true, exe.contains(2));
	}
	
	/**
	 * 
	 * Tests if implementation returns information if collection is empty correctly
	 * 
	 */
	
	@Test
	public void TestIsEmpty() {
		exe.clear();
		assertEquals(true, exe.isEmpty());
	}
	
	/**
	 * Tests if implementation returns number of elements stored in collection correctly
	 * 
	 */
	
	@Test
	public void TestSize() {
		assertEquals(5, exe.size());	
	}
	
	/**
	 * 
	 * Tests if implementation removes given object from collection correctly
	 * 
	 */
	
	@Test
	public void TestRemoveObject() {
		exe.remove(Integer.valueOf(1));
		assertEquals(4, exe.size());
	}
	
	/**
	 * Tests if implementation return an array of elements collection contains correctly
	 * 
	 */
	
	@Test
	public void TestToArray() {
		Object[] arr = exe.toArray();
		assertArrayEquals(new Object[]{1,2,13,45,1}, arr);		
	}
	
	/**
	 * Tests if implementation return index of given value correctly
	 * 
	 */
	
	@Test
	public void TestIndexOf() {
		assertEquals(0, exe.indexOf(1));
	}
	
	/**
	 * Tests if implementation removes element at given index correctly
	 * 
	 */
	
	@Test
	public void TestRemoveIndex() {
		exe.remove(1);
		assertEquals(1, exe.indexOf(13));
	}
	
	/**
	 * Tests if method forEach works correctly
	 * 
	 * 
	 */
	
	@Test
	public void TestForEach() {
		ArrayIndexedCollection n = new ArrayIndexedCollection();
		exe.remove(0);
		class P extends Processor {
			 public void process(Object o) {
				 n.add(o);
			 }
		};
		
		exe.forEach(new P());
		assertEquals(n.get(0),exe.get(0));
		assertEquals(n.get(1),exe.get(1));
		assertEquals(n.get(2),exe.get(2));
		assertEquals(n.get(3),exe.get(3));
		assertEquals(n.indexOf(Integer.valueOf(13)), exe.indexOf(Integer.valueOf(13)));
		assertEquals(n.size(), exe.size());
	}

}
