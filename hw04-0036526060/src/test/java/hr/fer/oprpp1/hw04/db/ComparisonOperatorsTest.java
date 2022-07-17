package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import hr.fer.oprpp1.hw04.db.ComparisonOperators;

import org.junit.jupiter.api.Test;
/**
 * Tester for {@link ComparisonOperators}
 * 
 * @author 38591
 *
 */
class ComparisonOperatorsTest {

	/**
	 * Reference to an operator
	 * 
	 */
	IComparisonOperator operator;
	/**
	 * Tests if function that < operator does is correctly implemented
	 *
	 */
	@Test
	public void LessOperator() {
		assertEquals(true, ComparisonOperators.LESS.satisfied("Barbara", "Branimir"));
		assertEquals(false, ComparisonOperators.LESS.satisfied("Šimunić", "Šimić"));
	}
	/**
	 * Tests if function that == operator does is correctly implemented
	 *
	 */
	@Test
	public void EqualsOperator() {
		assertEquals(false, ComparisonOperators.EQUALS.satisfied("Barbara", "Branimir"));
		assertEquals(true, ComparisonOperators.EQUALS.satisfied("Šžđš", "Šžđš"));
	}
	/**
	 * Tests if function that <= operator does is correctly implemented
	 *
	 */
	@Test
	public void LessOrEqualsOperator() {
		assertEquals(true, ComparisonOperators.LESS_OR_EQUALS.satisfied("Barbara", "Branimir"));
		assertEquals(true, ComparisonOperators.EQUALS.satisfied("Šžđš", "Šžđš"));
	}
	/**
	 * Tests if function that > operator does is correctly implemented
	 *
	 */
	@Test
	public void GreaterOperator() {
		assertEquals(true, ComparisonOperators.GREATER.satisfied("Branimir", "Barbara"));
		assertEquals(false, ComparisonOperators.GREATER.satisfied("Dubrovnik", "Split"));
	}
	/**
	 * Tests if function that >= operator does is correctly implemented
	 *
	 */
	@Test
	public void GreaterOrEqualsOperator() {
		assertEquals(true, ComparisonOperators.GREATER_OR_EQUALS.satisfied("Branimir", "Barbara"));
		assertEquals(true, ComparisonOperators.GREATER_OR_EQUALS.satisfied("Dubrovnik", "Dubrovnik"));
	}
	/**
	 * Tests if function that != operator does is correctly implemented
	 *
	 */
	@Test
	public void NotEqualsOperator() {
		assertEquals(true, ComparisonOperators.NOT_EQUALS.satisfied("Branimir", "Barbara"));
		assertEquals(false, ComparisonOperators.NOT_EQUALS.satisfied("Dubrovnik", "Dubrovnik"));
	}
	/**
	 * Tests if function that LIKE operator does is correctly implemented
	 *
	 */
	@Test
	public void LikeOperator() {
		assertEquals(false, ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
		assertEquals(true, ComparisonOperators.LIKE.satisfied("Dubrovik", "Dub*ik"));
	}
	

}
