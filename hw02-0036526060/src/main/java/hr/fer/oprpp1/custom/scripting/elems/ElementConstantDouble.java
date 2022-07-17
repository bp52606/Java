package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Class that represents objects of type Double used as representations of expressions
 * 
 * @author 38591
 *
 */
public class ElementConstantDouble extends Element {
	private double value;

	/**
	 * Constructor that initializes new ElementConstantDouble
	 * 
	 * @param value double value of this Element
	 */
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}

	/**
	 * Returns value of this Element
	 * 
	 * @return double value
	 */
	public double getValue() {
		return value;
	}

	@Override
	public String asText() {
		return String.valueOf(this.value);
	}
}
