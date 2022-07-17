package hr.fer.oprpp1.custom.collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable;
import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

class SimpleHashtableTester {

	public SimpleHashtable<String,Integer> classExample;

	/**
	 * Initializes new SimpleHashtable and adds elements to it
	 * 
	 * 
	 */
	
	@BeforeEach
	public void Initialize() {
		classExample = new SimpleHashtable<String, Integer>(5);
		classExample.put("knjiga",20);
		classExample.put("gumica", 40);
		classExample.put("oštrilo", 22);
		classExample.put("ravnalo", 34);
	}
	
	/**
	 * Tests if values are being correctly added to or overwritten in this table
	 * 
	 */
	
	@Test
	public void TestPut() {
		
		assertThrows(NullPointerException.class, () -> classExample.put(null, 15));
		assertTrue(classExample.containsKey("knjiga"), "Array should contain this key but doesn't");
		classExample.put("knjiga", 45);
		assertEquals(45,classExample.get("knjiga"));
		assertEquals(4,classExample.size());
	}
	
	/**
	 * Tests if SimpleHashtable searches for keys correctly
	 * 
	 */
	
	@Test
	public void TestContainsKey() {
		assertTrue(classExample.containsKey("oštrilo"),"Array should contain this key but doesn't");
		assertFalse(classExample.containsKey("trokut"),"Array shouldn't contain this key but does");
		assertFalse(classExample.containsKey(null),"Array shouln't contain null key but does");
		classExample.remove("gumica");
		assertFalse(classExample.containsKey("gumica"),"Array shouldn't contain this key but does");		
	}
	
	/**
	 * Tests if SimpleHashtable searches for values correctly
	 * 
	 */
	
	@Test
	public void TestContainsValue() {
		assertTrue(classExample.containsValue(40),"Array should contain this value but doesn't");
		assertFalse(classExample.containsValue(354),"Array shouldn't contain this value but does");
		assertFalse(classExample.containsValue(null),"Array shouln't contain null value but does");
		classExample.remove("gumica");
		assertFalse(classExample.containsValue(40),"Array shouldn't contain this value but does");	
	}
	
	/**
	 * Tests if SimpleHashtable returns correct values of given keys, if they exist in the table
	 * 
	 */
	
	@Test
	public void TestGet() {
		assertEquals(22,classExample.get("oštrilo"));
		assertEquals(null,classExample.get("bilježnica"));
		assertEquals(null, classExample.get(null));
	}
	
	/**
	 * Tests if SimpleHashtable returns correct number of elements the table contains
	 * 
	 */
	
	@Test
	public void TestSize() {
		assertEquals(4, classExample.size());
		classExample.remove("knjiga");
		assertEquals(3, classExample.size());
		classExample.put("korektor", 25);
		classExample.put("pernica", 22);
		assertEquals(5, classExample.size());	
	}
	
	/**
	 * Tests if SimpleHashtable correctly removes elements from table
	 * 
	 */
	
	@Test
	public void TestRemove() {
		classExample.remove("oštrilo");
		assertFalse(classExample.containsKey("oštrilo"),"Array shouldn't contain this key but does");
		assertEquals(null, classExample.remove(null));
		assertEquals(20, classExample.remove("knjiga"));
		classExample.remove("gumica");
		classExample.remove("ravnalo");
		assertTrue(classExample.isEmpty());
	}
	
	/**
	 * Tests if SimpleHashtable returns correct information about whether it contains any elements or not
	 * 
	 */
	
	@Test
	public void TestIsEmpty() {
		classExample.remove("knjiga");
		classExample.remove("gumica");
		classExample.remove("oštrilo");
		classExample.remove(null);
		assertFalse(classExample.isEmpty(),"Array shouldn't be empty but is");
		classExample.remove("ravnalo");
		assertTrue(classExample.isEmpty(),"Array shoul be empty but isn't");
	}
	
	/**
	 * Tests if SimpleHashtable returns correct String representation of elements it's table contains
	 * 
	 */
	
	@Test
	public void TestToString() {
		assertEquals("[knjiga=20, gumica=40, oštrilo=22, ravnalo=34]", classExample.toString());
		classExample.clear();
		assertEquals("[]", classExample.toString());
	}
	
	/**
	 * Tests if SimpleHashtable correctly creates an array containing elements in it's table
	 * 
	 */
	
	@Test
	public void TestToArray() {
		assertEquals("knjiga", classExample.toArray()[0].getKey());
		assertEquals("gumica", classExample.toArray()[1].getKey());
		assertEquals("oštrilo", classExample.toArray()[2].getKey());
		assertEquals("ravnalo", classExample.toArray()[3].getKey());
		classExample.remove("oštrilo");
		assertEquals("ravnalo", classExample.toArray()[2].getKey());
	}
	
	/**
	 * Tests if SimpleHashtable correctly removes all elements from it's table
	 * 
	 */
	
	@Test
	public void TestClear() {
		classExample.clear();
		assertEquals(0, classExample.size());
		assertTrue(classExample.isEmpty(),"Array should be empty but is not");
		assertEquals(0, classExample.toArray().length);
	}
	
	/**
	 * Tests if an iterator of SimpleHashtable works properly
	 * 
	 */
	
	@Test
	public void TestIterator() {
		assertTrue(classExample.iterator().hasNext(), "Iterator should have next value but doesn't");
		Iterator<TableEntry<String,Integer>> iterator = classExample.iterator();
		iterator.next();
		assertTrue(iterator.hasNext(), "Iterator should have next value but doesn't");
		iterator.next();
		assertTrue(iterator.hasNext(), "Iterator should have next value but doesn't");
		assertEquals("oštrilo", iterator.next().getKey());
		assertTrue(iterator.hasNext(), "Iterator should have next value but doesn't");
		iterator.remove();
		iterator.next();
		assertFalse(iterator.hasNext(), "Iterator shouldn't have next value but does");	
		iterator.remove();
		assertThrows(IllegalStateException.class, () -> iterator.remove());
		assertEquals(2, classExample.size());
	}
	
	
	
}
