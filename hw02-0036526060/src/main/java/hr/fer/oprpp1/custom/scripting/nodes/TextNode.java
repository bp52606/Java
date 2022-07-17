package hr.fer.oprpp1.custom.scripting.nodes;
/**
 * Class represents an implementation of a node representing a piece of textual data. It inherits from Node class
 * 
 * @author 38591
 *
 */
public class TextNode extends Node {

	private String text;

	/**
	 * Constructor that accepts text in a text node
	 * 
	 * @param text
	 */
	
	public TextNode(String text) {
		super();
		this.text = text;
	}

	/**
	 * Method represents a getter for text
	 * 
	 * @return text in a String format
	 */
	
	public String getText() {
		return this.text;
	}
	
	public String toString() {
		return this.getText();
	}

}
