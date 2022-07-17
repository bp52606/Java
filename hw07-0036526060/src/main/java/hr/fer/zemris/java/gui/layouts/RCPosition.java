package hr.fer.zemris.java.gui.layouts;

/**
 * Class representing a position of a component in it's container
 * 
 * @author 38591
 *
 */
public class RCPosition {
		
	/**
	 * Row of a component's location
	 */
	public int row;
	/**
	 * Column of a component's location
	 */
	public int column;

	/**
	 * Constructor initializing a position in a calculator
	 * 
	 * @param row given index of a row
	 * @param column given index of a column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Parser for given text representing a position
	 * 
	 * @param text String value containing information about a position
	 * @return parsed RCPosition
	 */
	public static RCPosition parse(String text) {
		String[] positions = text.split(",");
		if(positions.length!=2) throw new IllegalArgumentException();
		int row;
		int column;
		try {
			
			row = Integer.valueOf(positions[0]);
			column = Integer.valueOf(positions[1]);
			
		} catch(Exception ex) {
			throw new IllegalArgumentException();
		}
		return new RCPosition(row, column);
		
	}


	/**
	 * Getter for an index of a row of this position
	 * @return index of a row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter for an index of a column of this position
	 * @return index of a column
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RCPosition) {
			return this.getRow() == ((RCPosition)obj).getRow() && 
					this.getColumn() == ((RCPosition)obj).getColumn();
		}
		return false;
	}
	

	

	
}
