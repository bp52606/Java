package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
/**
 * Class used as a representation of structured documents
 * 
 * @author 38591
 *
 */

public class Node {
	
	private ArrayIndexedCollection aic = new ArrayIndexedCollection();
	private int counter = 0;
	
	/**
	 * Adds new child node to this node
	 * 
	 * @param child node to be set as a child of this node
	 */
	
	public void addChildNode(Node child) {
		aic.add(child);
		++counter;
	}
	
	/**
	 * Returns number of children of this node
	 * 
	 * @return counter of this node
	 */
	public int numberOfChildren() {
		return this.counter;
	}
	
	/**
	 * Returns child node of given node placed at given index
	 * 
	 * @param index of requested child node
	 * @return node placed at given index
	 */
	public Node getChild(int index) {
		if(index < numberOfChildren()) {
			return (Node)aic.get(index);
		} else {
			throw new SmartScriptParserException();
		}
	}
}
