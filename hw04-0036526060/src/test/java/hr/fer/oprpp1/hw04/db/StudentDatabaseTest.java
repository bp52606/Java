package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/**
 * Tests {@link StudentDatabase}
 * 
 * @author 38591
 *
 */
class StudentDatabaseTest {

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
	 * Tests if student records are being found using JMBAGs correctly
	 * 
	 */
	
	@Test
	public void TestForJMBAG() {
		StudentRecord record1 = database.forJMBAG("0000000006");
		StudentRecord record2 = database.forJMBAG("0000000054");
		StudentRecord record3 = database.forJMBAG(null);
		assertEquals("Ivan", record1.getName());
		assertEquals("Šamija",record2.getSurname());
		assertThrows(NullPointerException.class, () -> record3.getName());
	}
	
	/**
	 * Tests using filters
	 * 
	 */
	
	@Test
	public void TestFilters() {
		IFilter trueFilter = new IFilter() {

			@Override
			public boolean accepts(StudentRecord record) {
				return true;
			}
			
		};
		
		IFilter falseFilter = new IFilter() {

			@Override
			public boolean accepts(StudentRecord record) {
				return false;
			}
			
		};
		
		assertEquals(63, database.filter(trueFilter).size());
		assertEquals(0, database.filter(falseFilter).size());
	}
	
	
	
	
	
	
	

}
