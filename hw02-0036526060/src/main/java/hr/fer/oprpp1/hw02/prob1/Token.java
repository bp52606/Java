package hr.fer.oprpp1.hw02.prob1;

/**
 * Class representation of a unit that groups one or more sequential characters from given text
 * 
 * @author 38591
 *
 */

public class Token {
	
	private TokenType type;
	private Object value;
	
	/**
	 * Constructor which initializes this token
	 * 
	 * @param type type of this token
	 * @param value value of this token
	 */
	
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns value of this token
	 * 
	 * @return value of this token
	 */
	
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Returns type of this token
	 * 
	 * @return type of this token
	 */
	
	public TokenType getType() {
		return this.type;
	}

}
