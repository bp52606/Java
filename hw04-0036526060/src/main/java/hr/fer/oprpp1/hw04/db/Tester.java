package hr.fer.oprpp1.hw04.db;
/**
 * Class that tests written code
 * 
 * @author 38591
 *
 */
public class Tester {

	public static void main(String[] args) {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		System.out.println(oper.satisfied("Zagreb", "Aba*")); // false
		System.out.println(oper.satisfied("AAA", "AA*AA")); // false
		System.out.println(oper.satisfied("AAAA", "AA*AA")); // true
	}

}
