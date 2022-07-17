package hr.fer.zemris.java.gui.charts;

/**
 * Class that represents a pair of x and y value
 * 
 * @author 38591
 *
 */

public class XYValue {

	/**
	 * Integer representation of x value 
	 */
	
	private int x;
	
	/**
	 * Integer representation of y value 
	 */
	private int y;
	
	/**
	 * Constructor that initializes an XYValue
	 * 
	 * @param x int representation of x value
	 * @param y int representation of y value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter for x value
	 * 
	 * @return x value
	 */
	public int getX() {
		return x;
	}
	/**
	 * Getter for y value
	 * 
	 * @return y value
	 */
	public int getY() {
		return y;
	}
}
