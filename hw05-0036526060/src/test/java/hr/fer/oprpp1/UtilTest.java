package hr.fer.oprpp1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
/**
 * Class that tests functionality of class Util
 * 
 * @author 38591
 *
 */
class UtilTest {

	
	/**
	 * Tests method hexToByte
	 * 
	 */
	
	@Test
	void TestHexToByte() {
		assertArrayEquals(new byte[] {23,45,67},Util.hextobyte("172D43"));
		assertArrayEquals(new byte[] {103,(byte)166,(byte)239},Util.hextobyte("67a6eF"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("#%gfhs"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("abcdFg4"));
	}
	
	/**
	 *  Tests method byteToHex
	 * 
	 */
	
	@Test
	void TestByteToHex() {
		assertEquals("172d43", Util.bytetohex(new byte[] {23,45,67}));
		assertEquals("67a6ef", Util.bytetohex(new byte[] {103,(byte)166,(byte)239}));		
	}

}
