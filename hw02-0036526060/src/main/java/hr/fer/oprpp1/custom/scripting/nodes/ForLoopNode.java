package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Class represents an implementation of a node representing a single for-loop construct
 * 
 * @author 38591
 *
 */

public class ForLoopNode extends Node{
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	/**
	 * Constructor that accepts all elements of a for loop
	 * 
	 * @param variable a variable in a loop
	 * @param startExpression a start expression in a loop
	 * @param endExpression an end expression in a loop
	 * @param stepExpression step expression in a loop
	 */
	
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Method represents a getter for variable
	 * 
	 * @return variable
	 */
	
	public ElementVariable getVariable() {
		return variable;
	}

	
	/**
	 * Method represents a getter for start expression
	 * 
	 * @return start expression
	 */
	
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Method represents a getter for end expression
	 * 
	 * @return end expression
	 */
	
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Method represents a getter for step expression
	 * 
	 * @return step expression
	 */
	
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {
		if(stepExpression!=null) {
			return this.variable.getName()+ " "+ this.startExpression.asText() + " " +
					this.endExpression.asText() + " " + this.stepExpression.asText();
		}
		return this.variable.getName() + " "+this.startExpression.asText()+ " " +
				this.endExpression.asText();
	}
	
	
	
	
	
	
	
	
}
