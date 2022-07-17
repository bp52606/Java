package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/**
 * Tests {@link QueryLexer}
 * 
 * @author 38591
 *
 */
class QueryLexerTest {

	/**
	 * First read line
	 * 
	 */
	static String line1;
	/**
	 * Second read line
	 * 
	 */
	static String line2;
	/**
	 * First used lexer
	 * 
	 */
	static QueryLexer lexer1;
	/**
	 * Second used lexer
	 * 
	 */
	static QueryLexer lexer2;
	
	/**
	 * Initializes queries and query lexers
	 * 
	 */
	@BeforeAll
	public static void ScannInput() {
		line1= "jmbag=\"0000000078\"";
		line2 = "firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ž\" and jmbag>\"0000000002\"";
		lexer1 = new QueryLexer(line1.split(" "));
		lexer2 = new QueryLexer(line2.split(" "));
	}
	
	/**
	 * Tests if lexer returns tokens correctly
	 * 
	 */
	@Test
	public void testGetNext() {
		assertEquals(TokenType.ATTRIBUTE, lexer1.getNext().getType());
		assertEquals("=", lexer1.getNext().getValue());
		lexer2.getNext();
		assertEquals(TokenType.OPERATOR, lexer2.getNext().getType());
		assertEquals("A", lexer2.getNext().getValue());
	}
	/**
	 * Tests if lexer checks for next elements correctly
	 * 
	 */
	@Test
	public void testHasNext() {
		lexer1.getNext();
		assertFalse(lexer1.hasNext());
		assertTrue(lexer2.hasNext());
	}

}
