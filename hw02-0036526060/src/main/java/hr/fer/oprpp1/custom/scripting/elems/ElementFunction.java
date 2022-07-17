package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Implementation of elements that represent functions
 * 
 * @author 38591
 *
 */
public class ElementFunction extends Element {
		
	private String name;
	
	/**
	 * Constructor that initializes new ElementFunction
	 * 
	 * @param name name of this function
	 */
	
	public ElementFunction(String name) {
		super();
		this.name = name;
	}

	@Override
	public String asText() {
		return this.name;
	}

	/**
	 * Returns name of this ElementFunction as a String
	 * 
	 * @return String value of name
	 */
	public String getName() {
		return this.name;
	}
	
	
	
}
