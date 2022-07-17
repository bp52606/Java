package hr.fer.zemris.java.gui.layouts;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.*;

/**
 * Layout used in a component which represents a calculator
 * 
 * @author 38591
 *
 */
public class CalcLayout implements LayoutManager2{
	/**
	 * A gap between components of a calculator
	 */
	public int razmak;
	
	/**
	 * Map of components in a calculator and their position in it
	 */
	public Map<Component,RCPosition> mapCompAndConstraint = new LinkedHashMap<Component, RCPosition>();
	
	/**
	 * RCPosition representing number of columns and rows
	 */
	public RCPosition rcposition = new RCPosition(5,7);  //read-only vrijednosti za row i column
	
	/**
	 * Constructor with no arguments
	 */
	public CalcLayout() {
		this.razmak = 0;
	}
	
	/**
	 * Constructor with requested gap between calculator components
	 * 
	 * @param razmak given gap
	 */
	public CalcLayout(int razmak) {
		this.razmak = razmak;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		this.mapCompAndConstraint.remove(comp);
		
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return layoutSize(parent, "preferred");
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return layoutSize(parent, "minimum");
	}
	
	@Override
	public void layoutContainer(Container parent) {
		double width = ((parent).getWidth() - 6* this.razmak) / this.rcposition.getColumn();
		double height = ((parent).getHeight() - 4*this.razmak) / this.rcposition.getRow();
		
		int w = (int)Math.round(width);
		int h = (int)Math.round(height);
		int wmin = 0;
		int hmin = 0;
		if(w>width) {
			wmin = w-1;
		}
		if(h>height) {
			hmin = h-1;
		}
	
		for(Map.Entry<Component, RCPosition> map : mapCompAndConstraint.entrySet()) {
			RCPosition current = map.getValue();
			int x;
			int y;
			int wi;
			int he;
			if(current.getRow() == 1 && current.getColumn() == 1) {
				x = 0;
				y = 0;
				wi = w*5 + 4*razmak;   				//zauzima 5 mista
				he = h;
			} else {
				
				he = h;
				wi = w;
				if(hmin!=0) {
					if(current.getColumn()%2==0) {
						he = hmin;
					} 
				}
				if(wmin!=0) {
					if(current.getRow()%2==0) {
						wi = wmin;
					}
				}
				
				if(current.getColumn()==7) {
					wi +=parent.getInsets().right;
				}
				
				if(current.getRow()==5) {
					he +=parent.getInsets().bottom;
				}
					
				
				x = (current.getColumn()-1)*(wi+this.razmak);
				y = (current.getRow()-1)*(he+this.razmak);
				
			}
			
			//uzmi eventualno postojece granice u obzir
			
			if(current.getColumn() == 1) {
				x+=parent.getInsets().left;			
			}
			if(current.getRow() == 1) {
				y+=parent.getInsets().top;
			}
			map.getKey().setBounds(x, y, wi , he);
		}
		
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(constraints == null) throw new NullPointerException();
		if(!(constraints instanceof RCPosition) && !(constraints instanceof String)) throw new IllegalArgumentException();
		if(constraints instanceof RCPosition) {
			checkIfOkay(comp,(RCPosition)constraints);
			
		} else {
			checkIfOkay(comp, RCPosition.parse((String)constraints));
		}
		
	}

	/**
	 * Check if given values for components are correct
	 * 
	 * @param comp given component for calculator
	 * @param constraints position of a given component
	 */
	private void checkIfOkay(Component comp,RCPosition constraints) {
		int rows = constraints.getRow();
		int columns = constraints.getColumn();
		if(rows>5 || columns > 7
				|| rows <1 || columns <1 || (rows == 1  && (columns>1 && columns<6)) ||
				mapCompAndConstraint.containsValue(constraints)){
					throw new CalcLayoutException();
				}
		this.mapCompAndConstraint.put(comp,new RCPosition(rows, columns));
		
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return layoutSize(target, "maximum");
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Method that calculates requested dimension of a layout considering given arguments
	 * and their requests
	 * 
	 * @param parent Container representing a parent of a current one
	 * @param condition request for maximum, minimum or preferred dimension of a layout
	 * @return dimension of a layout for calculator
	 */
	public Dimension layoutSize(Container parent,String condition) {
		int width = 0;
		int height = 0;
	
		
		for(Map.Entry<Component, RCPosition> map : mapCompAndConstraint.entrySet()) {
			RCPosition currcp = map.getValue();
			boolean one = false;
			if(currcp.getRow()==1 && currcp.getColumn()==1) {
				one = true;
			}
			Dimension current = new Dimension();
			if(condition.equals("preferred")) {
				current = map.getKey().getPreferredSize();
			} else if(condition.equals("minimum")) {
				current = map.getKey().getMinimumSize();
			} else if(condition.equals("maximum")) {
				current = map.getKey().getMaximumSize();
			} else {
				throw new IllegalArgumentException("Only 'preferred', 'minimum' and 'maximum' are allowed");
			}
				
			if(current!=null) {
				int currWidth;
				if(!one) {
					currWidth = current.width;
				} else {
					currWidth  = (current.width-(4*this.razmak))/5;
				}
				int currHeight = current.height;
				if(currWidth>width) {
					width = currWidth;
				}
				if(currHeight>height) {
					height = currHeight;
				}
			}
		}

		return new Dimension(parent.getInsets().left + parent.getInsets().right+ width*this.rcposition.getColumn() + this.razmak*(this.rcposition.getColumn()-1),
				height*this.rcposition.getRow()+this.razmak*(this.rcposition.getRow()-1) + parent.getInsets().top + parent.getInsets().bottom);
	}
	
}
