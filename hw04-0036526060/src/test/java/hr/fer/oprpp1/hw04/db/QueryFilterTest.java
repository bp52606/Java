package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Tests {@link QueryFilter}
 * 
 * @author 38591
 *
 */
class QueryFilterTest {

	/**
	 * Created list of conditional expressions
	 * 
	 */
	List<ConditionalExpression> list = new LinkedList<ConditionalExpression>();
	/**
	 * Initializing list of conditional expressions
	 * 
	 */
	@BeforeEach
	public void initialize() {
		ConditionalExpression expression1 = new ConditionalExpression(FieldValuesGetter.JMBAG, 
				"00000078", ComparisonOperators.EQUALS);
		ConditionalExpression expression2 = new ConditionalExpression(FieldValuesGetter.FIRST_NAME, 
				"Vito", ComparisonOperators.LESS);
		ConditionalExpression expression3 = new ConditionalExpression(FieldValuesGetter.LAST_NAME, 
				"Školjić", ComparisonOperators.GREATER);
		
		list.add(expression1);
		list.add(expression2);
		list.add(expression3);
	}
	
	/**
	 * Checks if filter is correctly applied to expressions
	 * 
	 */
	
	@Test
	public void AcceptsTest() {
		assertTrue(new QueryFilter(list).accepts(new StudentRecord("00000078", "Ante", "Žurić", 5)));
	}
	

}
