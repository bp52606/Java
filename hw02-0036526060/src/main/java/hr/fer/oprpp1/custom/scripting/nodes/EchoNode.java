package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
/**
 * Class implementation of a node representing a command which generates some textual output dynamically. 
 * It inherits from Node class.
 * 
 * @author 38591
 *
 */
public class EchoNode extends Node {
	
	
	private Element[] elements;

	/**
	 * Constructor that accepts an array of elements in an echo node
	 * 
	 * @param elements
	 */
	
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<elements.length;++i) {
			if(elements[i]!=null) {
				sb.append(elements[i].asText());
				sb.append(" ");
			}
			
		}
		return sb.toString();
	}
	
	
	
}
