package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/**
 * Tests {@link QueryParser}
 * 
 * @author 38591
 *
 */
class QueryParserTest {
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
	static QueryParser parser1;
	/**
	 * Second used lexer
	 * 
	 */
	static QueryParser parser2;
	
	/**
	 * Initializes queries and query lexers
	 * 
	 */
	
	@BeforeAll
	public static void ScannInput() {
		line1= "jmbag=\"0000000078\"";
		line2 = "firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ž\" and jmbag>\"0000000002\"";
		parser1 = new QueryParser(line1);
		parser2 = new QueryParser(line2);
	}
	/**
	 * Tests if checker for direct queries is correctly implemented
	 * 
	 */
	@Test
	public void IsDirectQuery() {
		assertTrue(parser1.isDirectQuery());
		assertFalse(parser2.isDirectQuery());
	}
	
	/**
	 * Tests if method returns JMBAGs correctly
	 * 
	 */
	@Test
	public void getQueriedJmbag() {
		assertThrows(IllegalStateException.class,() -> parser2.getQueriedJMBAG());
		assertEquals("0000000078", parser1.getQueriedJMBAG());
	}

	/**
	 * Tests if information on queries is correctly returned
	 * 
	 */
	@Test
	public void getQueryTest() {
		assertTrue(parser1.getQuery().size() == 1);
		assertTrue(parser2.getQuery().size() == 4);
	}
}
