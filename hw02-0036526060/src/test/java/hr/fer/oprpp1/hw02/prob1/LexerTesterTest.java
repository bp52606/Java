package hr.fer.oprpp1.hw02.prob1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
/**
 * Test of an implementation of a lexer
 * 
 * @author 38591
 *
 */
class LexerTesterTest {

String document = "";
	
	
	/**
	 * Tests if lexer returns correct token
	 * 
	 */
	
	@Test
	public void testNextToken() {
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		SmartScriptLexer ssl = new SmartScriptLexer(document);
		assertEquals("{$", String.valueOf(ssl.nextToken().getValue()));
	}
	
	/**
	 * Tests if lexer correctly returns information about next token existing 
	 * 
	 */
	
	@Test
	public void testHasNextToken() {
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer5.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		SmartScriptLexer ssl = new SmartScriptLexer(document);
		assertEquals(true, ssl.hasNextToken());
	
	}
	
	/**
	 * Tests if lexer correctly returns state it is in
	 * 
	 */
	
	@Test
	public void testLexerState() {
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer5.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		SmartScriptLexer ssl = new SmartScriptLexer(document);
		assertEquals(true, ssl.hasNextToken());
	
	}
	
	/**
	 * Tests if lexer handles escapes correctly
	 * 
	 */
	
	@Test
	public void testEscaping() {
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer13.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		SmartScriptLexer ssl = new SmartScriptLexer(document);
		assertThrows(SmartScriptParserException.class, () -> ssl.nextToken());
	
	}
	
	/**
	 * Test if incorrectly placed escape is properly handled
	 * 
	 */
	
	@Test
	public void testIllegalEscape() {
		String doc ="";
		try {
			document = loader("src\\test\\resources\\extra\\primjer5.txt");
			doc = loader("src\\test\\resources\\extra\\primjer4.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		SmartScriptLexer ssl = new SmartScriptLexer(document);
		SmartScriptLexer ssl2 = new SmartScriptLexer(doc);
		assertThrows(SmartScriptParserException.class, () -> ssl.nextToken());
		assertThrows(SmartScriptParserException.class, () -> ssl2.nextToken());
	}
	
	private static String loader(String p) throws IOException {
		String docBody = new String(
				 Files.readAllBytes(Paths.get(p))
		);
		return docBody;
	}
}
