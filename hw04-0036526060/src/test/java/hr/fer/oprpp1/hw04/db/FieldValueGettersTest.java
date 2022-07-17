package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/**
 * Tests {@link FieldValuesGetter}
 * 
 * @author 38591
 *
 */
class FieldValueGettersTest {

	/**
	 * Reference to a list of Strings that a file contains
	 * 
	 */
	static List<String> fileData;
	
	/**
	 * Reference to a student database
	 * 
	 */
	static StudentDatabase database;
	
	/**
	 * Creates student database
	 * 
	 */
	
	@BeforeAll
	public static void Initialize() {
		try {
			fileData = Files.readAllLines(Path.of("src/main/resources/database.txt"),
					StandardCharsets.UTF_8);
			database = new StudentDatabase(fileData);	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests getter for a first name
	 * 
	 */
	@Test
	public void FirstNameTest() {
		assertEquals("Ivan", FieldValuesGetter.FIRST_NAME.get(database.forJMBAG("0000000006")));
		assertThrows(NullPointerException.class, 
				() -> FieldValuesGetter.FIRST_NAME.get(database.forJMBAG("00000000465")));
	}
	/**
	 * Tests getter for a last name
	 * 
	 */
	@Test
	public void LastNameTest() {
		assertEquals("Cvrlje", FieldValuesGetter.LAST_NAME.get(database.forJMBAG("0000000006")));
		assertThrows(NullPointerException.class, 
				() -> FieldValuesGetter.LAST_NAME.get(database.forJMBAG("00000000465")));
	}
	/**
	 * Tests getter for JMBAG
	 * 
	 */
	@Test
	public void JmbagTest() {
		assertEquals("0000000006", FieldValuesGetter.JMBAG.get(database.forJMBAG("0000000006")));
		assertThrows(NullPointerException.class, 
				() -> FieldValuesGetter.JMBAG.get(database.forJMBAG("00000000465")));
	}

}
