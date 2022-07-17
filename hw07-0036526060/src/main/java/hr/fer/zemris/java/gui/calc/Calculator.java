package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
/**
 * Class that represents an implementation of a calculator.
 * 
 */
public class Calculator extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Model used to build a calculator
	 */
	private CalcModel model;
	/**
	 * Boolean value saying if a box in JCheckBox is checked or not
	 */
	private boolean boxChecked = false;
	/**
	 * Stack where we save values from calculator
	 */
	private List<Double> stack = new LinkedList<Double>();
	/**
	 * Boolean value saying if a certain value is an operator or not
	 */
	private boolean operator = false;
	/**
	 * Button for cotangent calculation
	 */
	private JButton ctg;
	/**
	 * Button for sine calculation
	 */
	private JButton sin;
	/**
	 * Button for cosine calculation
	 */
	private JButton cos;
	/**
	 * Button for tangent calculation
	 */
	private JButton tan;
	/**
	 * Button for logarithm calculation
	 */
	private JButton log;
	/**
	 * Button for natural logarithm calculation
	 */
	private JButton ln;
	/**
	 * Button for power calculation
	 */
	private JButton povrh;
	
	
	/**
	 * Constructor setting the size,location and a model and initializing gui
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		model = new CalcModelImpl();
	
		setLocation(500, 500);
		setSize(500,300);
		initGUI();
	}

	/**
	 * Initialization of GUI
	 * 
	 */
	private void initGUI() {
		Container cp = getContentPane();
		//cp.setLayout(new CalcLayout(6));
		
		JLabel p = new JLabel();
		p.setLayout(new CalcLayout(6));
		
		JButton equals = setUp("=");
		equals.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				double operand = model.getActiveOperand();
				double current = model.getValue();
				DoubleBinaryOperator operator = model.getPendingBinaryOperation();
				double result = operator.applyAsDouble(operand, current);
				model.setValue(result);
				model.setPendingBinaryOperation(null);
				model.clearActiveOperand();
				
			}
			
		});
		
		/**
		 * Button used to clear values entered on calculator
		 */
		JButton clr = setUp("clr");
		
		clr.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.clear();
				
			}
		});
		
		/**
		 * Button used to calculate 1/x value
		 */
		JButton reverse = setUp("1/x");
		
		reverse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setValue(1/model.getValue());
				
			}
			
		});
		
		sin = setUp("sin");
		sin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!boxChecked) {
					model.setValue((Math.sin(Math.toRadians(model.getValue()))));
				} else {
					model.setValue(Math.asin(Math.toRadians(model.getValue())));
				}
				
			}
		});
		
		/**
		 * Button used for division
		 */
		JButton dijeli = setUp("/");
		
		dijeli.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getPendingBinaryOperation()!=null) {
					model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
							model.getValue()));
				}
				model.setActiveOperand(model.getValue());
				model.freezeValue(model.toString());
				operator = true;
				model.setPendingBinaryOperation(new DoubleBinaryOperator() {
					
					@Override
					public double applyAsDouble(double left, double right) {
						return left/right;
					}
				});
				
			}
		});
		
		/**
		 * Button that sets value on calculator to 0 and deletes everything previously calculated
		 */
		JButton reset = setUp("reset");
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.clearAll();
				
			}
		});
		
		log = setUp("log");
		log.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!boxChecked) {
					model.setValue(Math.log10(model.getValue()));
				} else {
					model.setValue(Math.pow(10, model.getValue()));
				}
				
			}
		});
		
		cos = setUp("cos");
		cos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!boxChecked) {
					model.setValue(Math.cos(Math.toRadians(model.getValue())));
				} else {
					model.setValue(Math.acos(Math.toRadians(model.getValue())));
				}
				
			}
		});
		
		/**
		 * Button used for multiplication
		 */
		JButton puta = setUp("*");
		puta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getPendingBinaryOperation()!=null) {
					model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
							model.getValue()));
				}
				model.setActiveOperand(model.getValue());
				model.freezeValue(model.toString());
				operator = true;
				model.setPendingBinaryOperation(new DoubleBinaryOperator() {
					
					@Override
					public double applyAsDouble(double left, double right) {
						return left*right;
					}
				});
				
			}
		});
		
		/**
		 * Button used to push value entered on a calculator to stack
		 */
		JButton push = setUp("push");
		push.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				stack.add(model.getValue());
			}
		});
		
		
		ln = setUp("ln");
		ln.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!boxChecked) {
					model.setValue(Math.log(model.getValue()));
				} else {
					model.setValue(Math.pow(Math.E,model.getValue()));
				}
				
			}
		});
		
		tan = setUp("tan");
		tan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!boxChecked) {
					model.setValue(Math.tan(Math.toRadians(model.getValue())));
				} else {
					model.setValue(Math.atan(Math.toRadians(model.getValue())));
				}
				
			}
		});
		/**
		 * Button used for subtraction
		 */
		JButton minus = setUp("-");
		minus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getPendingBinaryOperation()!=null) {
					model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
							model.getValue()));
				}
				model.setActiveOperand(model.getValue());
				model.freezeValue(model.toString());
				operator = true;
				model.setPendingBinaryOperation(new DoubleBinaryOperator() {
					
					@Override
					public double applyAsDouble(double left, double right) {
						return left-right;
					}
				});
				
			}
		});
		/**
		 * Button used to take the most recently added value from the stack and 
		 * add it to calculator screen
		 */
		JButton pop = setUp("pop");
		pop.addActionListener(new ActionListener() {

			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = stack.size()-1;
				if(index>=0) {
					model.clear();
					model.setValue(stack.get(index));
					stack.remove(index);
				} else {
					System.out.println("Stack is empty");
				}
				
			}
		});
		
		povrh = setUp("x^n");
		
		povrh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setActiveOperand(model.getValue());
				operator = true;
				if(!boxChecked) {
					model.setPendingBinaryOperation(new DoubleBinaryOperator() {
						
						@Override
						public double applyAsDouble(double left, double right) {
							return Math.pow(left,right);
						}
					});
				} else {
					model.setPendingBinaryOperation(new DoubleBinaryOperator() {
						
						@Override
						public double applyAsDouble(double left, double right) {
							return Math.pow(left, 1/right);
						}
					});
				}
				
			}
		});
		
		ctg = setUp("ctg");
		
		ctg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!boxChecked) {
					model.setValue(1/Math.tan(Math.toRadians(model.getValue())));
				} else {
					model.setValue(Math.atan(1.0/Math.toRadians(model.getValue())));
				}
				
			}
		});
		/**
		 * Button used to change the sign of a number
		 */
		JButton plusMinus = setUp("+/-");
		plusMinus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.swapSign();
				
			}
		});
		
		/**
		 * Button used to add a decimal dot to a number
		 */
		JButton dot = setUp(".");
		dot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.insertDecimalPoint();
				
			}
		});
		/**
		 * Button used for adding
		 */
		JButton plus = setUp("+");
		plus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getPendingBinaryOperation()!=null) {
					model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
							model.getValue()));
				}
				model.setActiveOperand(model.getValue());
				model.freezeValue(model.toString());
				operator = true;
				model.setPendingBinaryOperation(new DoubleBinaryOperator() {
					
					@Override
					public double applyAsDouble(double left, double right) {
						return left+right;
					}
				});
				
			}
		});
		
		
		JLabel l = l("");
		
		model.addCalcValueListener(new CalcValueListener() {
			
			@Override
			public void valueChanged(CalcModel model) {
				l.setText(model.toString());
			}
			
		});

		p.add(l, new RCPosition(1,1));
		
		p.add(equals, new RCPosition(1,6));
		p.add(clr, new RCPosition(1,7));
		p.add(reverse, new RCPosition(2,1));
		p.add(sin, new RCPosition(2,2));
		p.add(rest2("7"), new RCPosition(2,3));
		p.add(rest2("8"), new RCPosition(2,4));
		p.add(rest2("9"), new RCPosition(2,5));
		p.add(dijeli, new RCPosition(2,6));
		p.add(reset, new RCPosition(2,7));
		p.add(log, new RCPosition(3,1));
		p.add(cos, new RCPosition(3,2));
		p.add(rest2("4"), new RCPosition(3,3));
		p.add(rest2("5"), new RCPosition(3,4));
		p.add(rest2("6"), new RCPosition(3,5));
		p.add(puta, new RCPosition(3,6));
		p.add(push, new RCPosition(3,7));
		p.add(ln, new RCPosition(4,1));
		p.add(tan, new RCPosition(4,2));
		p.add(rest2("1"), new RCPosition(4,3));
		p.add(rest2("2"), new RCPosition(4,4));
		p.add(rest2("3"), new RCPosition(4,5));
		p.add(minus, new RCPosition(4,6));
		p.add(pop, new RCPosition(4,7));
		p.add(povrh, new RCPosition(5,1));
		p.add(ctg, new RCPosition(5,2));
		p.add(rest2("0"), new RCPosition(5,3));
		p.add(plusMinus, new RCPosition(5,4));
		p.add(dot, new RCPosition(5,5));
		p.add(plus, new RCPosition(5,6));
		p.add(box("Inv"), new RCPosition(5,7));

		cp.add(p);
	}
	
	

	/**
	 * Label where entered and calculated numbers are shown 
	 * 
	 * @param text String value shown on the label
	 * @return JLabel where calculated values are shown
	 */
	private JLabel l(String text) {
		JLabel l = new JLabel(text,SwingConstants.RIGHT);
		l.setBackground(Color.YELLOW);
		l.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		l.setOpaque(true);
		l.setText("0");
		l.setFont(l.getFont().deriveFont(30f));
		
		return l;
	}
	/**
	 * Setup for buttons on the calculator with functions on them
	 * 
	 * @param text String value shown on the button
	 * @return created JButton
	 */
	private JButton setUp(String text) {
		JButton current = new JButton(text);
		current.setBorder(BorderFactory.createLineBorder(Color.gray));
		current.setBackground(new Color(200,200,250));
		current.setOpaque(true);
		return current;
	}
	/**
	 * Buttons on the calculator used to enter numbers
	 * 
	 * @param text String value representing function of a button
	 * @return created JButton
	 */
	
	private JButton rest2(String text) {
		JButton rest2 = new JButton(text);
		rest2.setBorder(BorderFactory.createLineBorder(Color.gray));
		rest2.setBackground(new Color(200,200,250));
		rest2.setFont(rest2.getFont().deriveFont(30f));
		rest2.setOpaque(true);
		rest2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					if(operator) {
						model.clear();
						operator = false;
					}
					model.insertDigit(Integer.valueOf(((JButton)e.getSource()).getText()));
				
			}
			
		});
		
		return rest2;
	}
	
	/**
	 * Checkbox used to apply reverse functions
	 * 
	 * @param text String value representing function of a button
	 * @return created JButton
	 */
	private JCheckBox box(String text) {
		JCheckBox box = new JCheckBox(text);
		box.setOpaque(true);
		box.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boxChecked = !boxChecked;
				if(boxChecked) {
					ctg.setText("arcctg");
					tan.setText("arctan");
					sin.setText("arcsin");
					cos.setText("arccos");
					log.setText("10^x");
					ln.setText("e^x");
					povrh.setText("x^(1/n)");
				} else {
					ctg.setText("ctg");
					tan.setText("tan");
					sin.setText("sin");
					cos.setText("cos");
					log.setText("log");
					ln.setText("ln");
					povrh.setText("x^n");
				}
				
				
				
			}
		});
		
		return box;
	}
	
	/**
	 * Function that initializes a calculator
	 * 
	 * @param args given arguments
	 */
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}	

}
