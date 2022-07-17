package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/**
 * Tests {@link ConditionalExpression}
 * 
 * @author 38591
 *
 */
class ConditionalExpressionTest {
	/**
	 * Reference to a conditional expression
	 * 
	 */
	static ConditionalExpression expression;
	/**
	 * Reference to a student record
	 * 
	 */
	static StudentRecord record;
	/**
	 * Initializes an expression and a student record
	 * 
	 */
	@BeforeAll
	public static void InitializeExpression() {
		record = new StudentRecord("0000000040", "Zrinka", "Mišura", 5);
		expression = new ConditionalExpression(FieldValuesGetter.FIRST_NAME, "Zrinka", 
				ComparisonOperators.EQUALS);
		
	}
	
	/**
	 * Checks if a student record satisfies correctly given expression
	 * 
	 */
	
	@Test
	public void CompsarisonTest() {
		assertEquals(true, ComparisonOperators.EQUALS.satisfied(expression.getFieldGetter().get(record), 
				expression.getStringLiteral()));
	}

}
