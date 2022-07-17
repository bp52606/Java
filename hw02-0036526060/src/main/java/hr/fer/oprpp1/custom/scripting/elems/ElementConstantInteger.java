package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Class that represents objects of type Integer used as representations of expressions
 * 
 * @author 38591
 *
 */
public class ElementConstantInteger extends Element {
	
	private int value;


	/**
	 * Constructor that initializes new ElementConstantInteger
	 * 
	 * @param value integer value of this Element
	 */
	
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}

	/**
	 * Returns value of this Element
	 * 
	 * @return double value
	 */
	
	public int getValue() {
		return value;
	}

	@Override
	public String asText() {
		return String.valueOf(this.value);
	}
	
}
