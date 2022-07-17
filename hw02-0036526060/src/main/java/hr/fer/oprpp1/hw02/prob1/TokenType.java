package hr.fer.oprpp1.hw02.prob1;

/**
 * Class representation of a type that belongs to a certain Token.
 * 
 * @author 38591
 *
 */

public enum TokenType {
	EOF, WORD, NUMBER, SYMBOL;

	public LexerException exception;
	
	private TokenType() {
		exception = new LexerException();
	}
}
