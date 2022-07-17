package hr.fer.zemris.java.gui.calc.model;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of calculator model
 * 
 * @author 38591
 *
 */

public class CalcModelImpl implements CalcModel {

	/**
	 * boolean value saying if calculator is currentyl editable
	 * 
	 */
	private boolean editable = true;
	/**
	 * boolean value saying if entered number is positive
	 * 
	 */
	private boolean positive = true;
	/**
	 * String representation of entered digits
	 */
	private String digits = "";
	/**
	 * Double value of entered value
	 */
	private double value = 0;
	/**
	 * String representation of currently calculated value
	 */
	private String frozenValue = null;
	/**
	 * Double value of a current operand
	 */
	private double activeOperand;
	/**
	 * boolean value saying if active operand exists
	 */
	private boolean activeOperandSet = false;
	/**
	 * DoubleBinaryOperator representing an operation currently being used
	 */
	private DoubleBinaryOperator pendingOperation;
	/**
	 * Set of listeners that keep track of changes of calculated values
	 */
	private Set<CalcValueListener> calcValueListeners = new LinkedHashSet<CalcValueListener>();
	
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		this.calcValueListeners.add(l);	
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		this.calcValueListeners.remove(l);	
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
		this.digits = String.valueOf(value);
		this.editable = false;
		tellListeners();
		
	}

	@Override
	public boolean isEditable() {
		return this.editable;
	}

	@Override
	public void clear() {
		this.value = 0;
		this.digits = "";
		this.editable = true;
		this.freezeValue(null);
		tellListeners();
	}

	@Override
	public void clearAll() {
		this.clearActiveOperand();
		this.setPendingBinaryOperation(null);
		this.clear();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(editable) {
			this.positive = !this.positive;
			if(!this.positive) {
				if(!this.digits.isEmpty()) {
					this.digits = "-" + this.digits;
				}
			} else {
				if(!this.digits.isEmpty()) {
					this.digits = this.digits.substring(1);
				}
			}
			this.value = 0 - this.value;
			this.freezeValue(null);
		} else {
			throw new CalculatorInputException();
		}
		tellListeners();
		
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(editable && !digits.equals("")) {
			if(digits.contains(".")) throw new CalculatorInputException();
			digits = digits.concat(".");
			this.freezeValue(null);
		} else {
			throw new CalculatorInputException();
		}
		tellListeners();
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable()) throw new CalculatorInputException();
		if(digit !=0 || !digits.equals("0")) {
			if(digits.equals("0")) {
				digits = "";
			}
			try {
				String test = digits.concat(String.valueOf(digit));
				value = Double.parseDouble(test);
				digits = test;
				this.freezeValue(null);
			} catch(NumberFormatException ex) {
				throw new CalculatorInputException();
			}
			if(!Double.isFinite(value)) {
				throw new CalculatorInputException();
			}
			tellListeners();
		}
		
		
	}

	/**
	 * Method used to notify listeners about made changes
	 */
	private void tellListeners() {
		for(CalcValueListener listener : calcValueListeners) {
			listener.valueChanged(this);
		}
		
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet()) {
			throw new IllegalStateException();
		} 
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		this.activeOperandSet = true;
		
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperandSet = false;
		
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
		
	}

	@Override
	public String toString() {
		if(hasFrozenValue()) {
			return this.frozenValue;
		} else {
			if(!this.digits.isEmpty() && this.getValue()!=0) {
				return this.digits;
			} else {
				if(positive) {
					return "0";
				} else {
					return "-0";
				}
			}
		}
		
	
	}
	
	public void freezeValue(String value) {
		this.frozenValue = value;
	}

	public boolean hasFrozenValue() {
		return frozenValue!=null;
	}
	
	

}
