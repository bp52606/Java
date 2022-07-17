package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class DemoFrame1 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();

	
	}
	private void initGUI() {
		Container p = getContentPane();
		p.setLayout(new CalcLayout(3));
		p.add(l("tekst 1"), new RCPosition(1,1));
		p.add(l("tekst 2"), new RCPosition(2,3));
		p.add(l("tekst stvarno najdulji"), new RCPosition(2,7));
		p.add(l("tekst kraći"), new RCPosition(4,2));
		p.add(l("tekst srednji"), new RCPosition(4,5));
		p.add(l("tekst"), new RCPosition(4,7));
		
	}
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new DemoFrame1().setVisible(true);
		});
	}
}
