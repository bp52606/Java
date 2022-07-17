package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Tests implementation of ArrayIndexedCollection class
 * 
 * @author 38591
 *
 */
class ArrayIndexedCollectionTest {

	ArrayIndexedCollection exe =  new ArrayIndexedCollection();
	
	/**
	 * Actions performed before each test
	 * 
	 */
	
	@BeforeEach
	public void TestBeforeEach() {
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
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-15));
	}
	
	/**
	 * Tests third constructor
	 * 
	 */
	
	@Test
	public void TestConstructor3() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}
	
	/**
	 * Test fourth constructor
	 * 
	 */
	
	@Test
	public void TestConstructor4() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null,15));
	}
	
	/**
	 * Tests if implementation adds new elements correctly
	 * 
	 */
	
	@Test
	public void TestAdd() {
		assertThrows(NullPointerException.class,() -> exe.add(null));
		exe.add(Integer.valueOf(3445));
		assertEquals(true, exe.contains(3445));
		assertEquals(5, exe.indexOf(Integer.valueOf(3445)));
	}
	
	/**
	 * Tests if implementation clears the collection correctly
	 * 
	 */
	
	@Test
	public void TestClear() {
		exe.clear();
		assertTrue(exe.isEmpty());
	}
	
	/**
	 * Tests if implementation returns elements at given index correctly
	 * 
	 * 
	 */
	
	@Test
	public void TestGet() {
		assertThrows(IndexOutOfBoundsException.class, () -> exe.get(-67));
		assertEquals(1, exe.get(4));
	}
	
	/**
	 * Tests if implementation inserts new elements correctly
	 * 
	 */
	
	@Test
	public void TestInsert() {	
		assertThrows(IndexOutOfBoundsException.class, () -> exe.insert(null, -5));
		assertEquals(1, exe.get(0));
		assertEquals(2,exe.get(1));
	}
	
	/**
	 * Tests if implementation return index of given value correctly
	 * 
	 */
	
	@Test
	public void TestIndexOf() {
		assertEquals(0, exe.indexOf(1));	
		assertEquals(-1, exe.indexOf(6));
	}
	
	/**
	 * Tests if implementation removes element at given index correctly
	 * 
	 */
	
	@Test
	public void TestRemoveIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> exe.remove(432));
		exe.remove(0);
		assertEquals(45, exe.get(2));
	}
	
	/**
	 * 
	 * Tests if implementation removes given object from collection correctly
	 * 
	 */
	
	
	@Test
	public void TestRemoveObject() {
		exe.remove(Integer.valueOf(45));
		assertEquals(1, exe.get(3));
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
	 * Tests if implementation returns information about whether given element is in this collection correctly
	 * 
	 */
	
	@Test
	public void TestContains() {
		exe.remove(Integer.valueOf(1));
		assertEquals(true, exe.contains(Integer.valueOf(1)));
		assertEquals(true, exe.contains(Integer.valueOf(45)));
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
	 * Tests if method forEach works correctly
	 * 
	 * 
	 */
	
	@Test
	public void TestForEach() {
		LinkedListIndexedCollection n = new LinkedListIndexedCollection();
		exe.remove(0);
		class P extends Processor {
			 public void process(Object o) {
				 n.add(o);
			 }
		};
		
		exe.forEach(new P());
		assertEquals(Integer.valueOf(2),n.get(0));
		assertEquals(n.get(1),exe.get(1));
		assertEquals(n.get(2),exe.get(2));
		assertEquals(n.get(3),exe.get(3));
		assertEquals(n.indexOf(Integer.valueOf(45)), exe.indexOf(Integer.valueOf(45)));
		assertEquals(n.size(), exe.size());
	}
	
	
}
