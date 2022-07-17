package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Implementation of elements that represent variables
 * 
 * @author 38591
 *
 */
public class ElementVariable extends Element {
	
	private String name;
	
	/**
	 * Constructor that initializes new ElementVariable
	 * 
	 * @param name of this variable
	 */
	
	public ElementVariable(String name) {
		super();
		this.name = name;
	}

	@Override
	public String asText() {
		return this.name;
	}

	/**
	 * Returns String value of a name of this variable
	 * 
	 * @return String value of name of this variable
	 */ 
	
	public String getName() {
		return this.name;
	}
	
	
}
