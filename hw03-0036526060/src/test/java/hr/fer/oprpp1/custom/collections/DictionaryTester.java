package hr.fer.oprpp1.custom.collections;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.Dictionary;

class DictionaryTester {

	Dictionary<String,Integer> dictionary;
	
	/**
	 * Initializes new Dictionary and adds elements to it
	 * 
	 */
	
	@BeforeEach
	public void DoBeforeEach() {
		dictionary = new Dictionary<>();
		dictionary.put("knjiga", 3);
		dictionary.put("olovka", 675324);
		dictionary.put("gumica", 23);
		dictionary.put("bilježnica", 9);
	}
	/**
	 * Tests if method isEmpty() in class Dictionary returns correct value
	 * 
	 */
	@Test
	public void EmptyTest() {
		assertEquals(false, dictionary.isEmpty());
	}
	/**
	 * Tests if size() method in class Dictionary returns correct value
	 * 
	 */
	@Test
	public void SizeTest() {
		assertEquals(4, dictionary.size());
	}
	/**
	 * Tests if clear() method in class Dictionary returns correct value
	 * 
	 */
	@Test
	public void ClearTest() {
		dictionary.clear();
		assertEquals(0, dictionary.size());
	}
	
	/**
	 * Tests if values are being added correctly to this dictionary 
	 * 
	 */
	
	@Test
	public void AddTest() {
		dictionary.put("nekiString", 2555);
		assertEquals(2555, dictionary.get("nekiString"));
		assertEquals(5, dictionary.size());
		assertThrows(NullPointerException.class, () -> dictionary.put(null, 40));
	}
	
	/**
	 * Tests if this dictionary returns values of keys correctly
	 * 
	 */
	
	@Test 
	public void GetTest() {
		assertEquals(675324, dictionary.get("olovka"));
		assertEquals(null, dictionary.get("nePostoji"));
	}
	
	/**
	 * Tests if this dictionary removes keys from itself correctly
	 * 
	 */
	
	@Test
	public void RemoveTest() {
		dictionary.remove("olovka");
		dictionary.remove("bilježnica");
		assertEquals(null, dictionary.get("olovka"));
		assertEquals(null, dictionary.get("bilježnica"));
		assertEquals(2, dictionary.size());
	}
}
