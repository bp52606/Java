package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Implementation of elements that represent strings
 * 
 * @author 38591
 *
 */
public class ElementString extends Element {
	private String value;
	
	
	/**
	 * Constructor that initializes new ElementString
	 * 
	 * @param value of this String
	 */
	
	public ElementString(String value) {
		super();
		this.value = value;
	}

	@Override
	public String asText() {
		return this.value;
	}

	/**
	 * Returns value of this String
	 * 
	 * @return value of this String
	 */
	
	public String getName() {
		return this.value;
	}
}
