package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class representing a bar chart.
 * 
 * @author 38591
 *
 */

public class BarChart {
	
	/**
	 * List of x and y values of dots on a graph
	 */
	private List<XYValue> listXY;
	/**
	 * Description of x values
	 */
	private String opisUzX;
	/**
	 * Description of y values
	 */
	private String opisUzY;
	/**
	 * Minimum value of y
	 */
	private int minY;
	/**
	 * Maximum value of y
	 */
	private int maxY;
	/**
	 * Requested gap between y values on graph
	 */
	private int razmakY;
	
	/**
	 * Constructor used to initialize values for a  graph
	 * 
	 * @param listXY List of x and y values of dots on a graph
	 * @param opisUzX description of x values
	 * @param opisUzY description of y values
	 * @param minY minimum value of y
	 * @param maxY maximum value of x
	 * @param razmakY gap between y values on graph
	 */
	
	public BarChart(List<XYValue> listXY, String opisUzX, String opisUzY, int minY, int maxY, int razmakY) {
		this.listXY = listXY;
		this.opisUzX = opisUzX;
		this.opisUzY = opisUzY;
		if(minY<0) throw new IllegalArgumentException();
		this.minY = minY;
		if(maxY<=minY) throw new IllegalArgumentException();
		this.maxY = maxY;
		this.razmakY = razmakY;
		for(XYValue xy : listXY) {
			if(xy.getY()<this.minY) throw new IllegalArgumentException();
		}
		while(((maxY-minY)/razmakY) != (int) ((maxY-minY)/razmakY)) {
			maxY = maxY+1;
		}
	}

	/**
	 * Getter for list of x and y values of marked dots on a graph
	 * @return list of x and y values
	 */
	public List<XYValue> getListXY() {
		return listXY;
	}

	/**
	 * Setter for list of x and y values of marked dots on a graph
	 * 
	 * @param listXY list of x and y values
	 */
	public void setListXY(List<XYValue> listXY) {
		this.listXY = listXY;
	}

	/**
	 * Getter for minimum value of y
	 * @return minimum value of y
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Setter for minimum value of y
	 * @param minY minimum value of y
	 */
	public void setMinY(int minY) {
		this.minY = minY;
	}

	/**
	 * Getter for maximum value of y
	 * @return maximum value of y
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Setter for maximum value of y
	 * @param maxY maximum value of y
	 */
	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	/**
	 * Getter for a gap between y values on graph
	 * 
	 * @return gap between y values
	 */
	
	public int getRazmakY() {
		return razmakY;
	}

	/**
	 * Setter for a gap between y values on graph
	 * 
	 * @param razmakY gap between y values
	 */
	public void setRazmakY(int razmakY) {
		this.razmakY = razmakY;
	}

	/**
	 * Getter for description of x values
	 * 
	 * @return description of x values
	 */
	public String getOpisUzX() {
		return opisUzX;
	}

	/**
	 * Setter for description of x values
	 * 
	 * @param opisUzX description of x values
	 */
	public void setOpisUzX(String opisUzX) {
		this.opisUzX = opisUzX;
	}

	/**
	 * Getter for description of y values
	 * 
	 * @return description of y values
	 */
	public String getOpisUzY() {
		return opisUzY;
	}

	/**
	 * Setter for description of y values
	 * 
	 * @param opisUzX description of y values
	 */
	public void setOpisUzY(String opisUzY) {
		this.opisUzY = opisUzY;
	}
	
	
	
	
}
