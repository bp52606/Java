package hr.fer.oprpp1.hw04.db;

/**
 * Class that represents an expression containing field name, operator symbol and string literal.
 * 
 * @author 38591
 *
 */

public class ConditionalExpression {
	
	/**
	 * FieldValueGetter that this conditional expression contains.
	 * 
	 */
	
	private IFieldValueGetter fieldGetter;
	
	/**
	 * String literal that this conditional expression contains.
	 * FieldValueGetter is being compared with it.
	 * 
	 * 
	 */
	
	private String stringLiteral;
	
	/**
	 * Operator that compares string literal and field value getter in this expression
	 * 
	 * 
	 */
	
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor that creates an expression containing a field value getter, a string literal and a strategy
	 * 
	 * @param getter given attribute in an expression
	 * @param literal given string literal in an expression
	 * @param strategy operator used to compare given attribute and string literal
	 */
	
	public ConditionalExpression(IFieldValueGetter getter,String literal,IComparisonOperator strategy) {
		this.fieldGetter = getter;
		this.stringLiteral = literal;
		this.comparisonOperator = strategy;
	}

	/**
	 * Getter for an attribute of this expression
	 * 
	 * @return IFieldValueGetter attribute
	 */
	
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Getter for a string literal of this expression
	 * 
	 * @return String representation of given literal
	 */
	
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Getter for an operator used in this expression
	 * 
	 * @return given operator in this expression
	 */
	
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	
}
