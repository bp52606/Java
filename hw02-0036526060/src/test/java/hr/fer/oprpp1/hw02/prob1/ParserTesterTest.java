package hr.fer.oprpp1.hw02.prob1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
/**
 * Test of an implementation of a parser
 * 
 * @author 38591
 *
 */
class ParserTesterTest {

	String document = "";
	String doc = "";
	SmartScriptParser sst;

	/**
	 * Tests if parser recognizes invalid variable names
	 * 
	 */
	
	@Test
	public void testVariableName() {
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer1.txt");
			doc  = loader("src\\test\\resources\\mojExtra\\primjer9.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		 assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(document));
		 assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(doc));
		
	}
	
	/**
	 * Tests if parser recognizes FOR-tag has too many arguments
	 *
	 */
	
	@Test
	public void testTooManyArguments() {
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer2.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		 assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(document));
		
	}
	
	/**
	 * Tests if parser recognizes FOR-tag has too few arguments
	 * 
	 */
	
	@Test
	public void testTooFewArguments() {
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer3.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		 assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(document));
		
	}
	
	/**
	 * Tests if parser recognizes incorrect argument types in FOR-tag 
	 * 
	 */
	
	@Test
	public void testWrongArgumentType() {
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer4.txt");
			doc = loader("src\\test\\resources\\mojExtra\\primjer14.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		 assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(document));
		 assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(document));
		
	}
	
	/**
	 * Tests if node returned by parser has correct number of children
	 * 
	 */
	
	@Test
	public void testNumberOfChildren() {
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer5.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		
		 sst = new SmartScriptParser(document);
		 assertEquals(4, sst.getDocumentNode().numberOfChildren());
		
	}
	
	/**
	 * Tests if method getChild from node returned by parser correctly returns value of a child node
	 * 
	 */
	
	@Test 
	public void testGetChild() {
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer5.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		
		 sst = new SmartScriptParser(document);
		 assertEquals("This is sample text.\\r\\n", sst.getDocumentNode().getChild(0).toString());
		 assertThrows(SmartScriptParserException.class, () -> sst.getDocumentNode().getChild(8));
		
	}
	
	/**
	 * Tests if method equals() from node returned by parser works correctly 
	 * 
	 */
	
	@Test
	public void testEquals() {
		String doc = "";
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer5.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		
		 sst = new SmartScriptParser(document);
		 String m = sst.getDocumentNode().toString();
		 SmartScriptParser ssp = new SmartScriptParser(m);
		 doc = ssp.getDocumentNode().toString();
		 assertEquals(doc,m);
		 
		
	}
	
	/**
	 * 
	 * 
	 */
	
	@Test
	public void testMultipleLineString() {
		
		try {
			document = loader("src\\test\\resources\\extra\\primjer6.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		sst = new SmartScriptParser(document);
		assertEquals(1, sst.getDocumentNode().numberOfChildren());
		
	}
	
	/**
	 * Tests if parse handles strings correctly
	 * 
	 */
	
	@Test
	public void testStrings() {
		
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer6.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		sst = new SmartScriptParser(document);
		String m = sst.getDocumentNode().toString();
		SmartScriptParser ssp = new SmartScriptParser(m);
		doc = ssp.getDocumentNode().toString();
		
		assertEquals(3, sst.getDocumentNode().numberOfChildren());
		assertTrue(doc.equals(m));
		
	}
	
	/**
	 * Tests if parser uses slashes correctly
	 * 
	 */
	
	@Test
	public void testSlash() {
		
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer7.txt");
			doc = loader("src\\test\\resources\\mojExtra\\primjer8.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		sst = new SmartScriptParser(document);
		SmartScriptParser ssp = new SmartScriptParser(doc);
		assertEquals(1, sst.getDocumentNode().numberOfChildren());
		assertEquals(2, ssp.getDocumentNode().numberOfChildren());
		
	}
	
	/**
	 * Tests if parses ignores spaces
	 * 
	 */
	
	@Test
	public void testSpaces() {
		
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer10.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		sst = new SmartScriptParser(document);
		assertEquals(1, sst.getDocumentNode().numberOfChildren());
		
	}
	
	/**
	 * Tests if parser considers given texts OK
	 * 
	 */
	
	@Test
	public void testGoodExamples() {
		
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer11.txt");
			doc = loader("src\\test\\resources\\mojExtra\\primjer12.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		sst = new SmartScriptParser(document);
		SmartScriptParser ssp = new SmartScriptParser(doc);
		assertEquals(1, sst.getDocumentNode().numberOfChildren());
		assertEquals(1, ssp.getDocumentNode().numberOfChildren());
		
	}
	

	private static String loader(String p) throws IOException {
		String docBody = new String(
				 Files.readAllBytes(Paths.get(p))
		);
		return docBody;
	}

}
