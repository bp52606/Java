package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Implementation of elements that represent operators
 * 
 * @author 38591
 *
 */
public class ElementOperator extends Element {
	private String symbol;
	

	/**
	 * Constructor that initializes new ElementOperator
	 * 
	 * @param symbol String value of this Element
	 */
	
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return this.symbol;
	}

	/**
	 * Returns String value of a symbol that represents this operator
	 * 
	 * @return name of this symbol
	 */
	
	public String getName() {
		return this.symbol;
	}
}
