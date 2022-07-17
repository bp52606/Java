package hr.fer.zemris.java.gui.layouts;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	
		public static void main(String[] args) {
			JPanel p = new JPanel(new CalcLayout(2));
			JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
			JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
			p.add(l1, new RCPosition(1,1));
			p.add(l2, new RCPosition(3,3));
			Dimension dim = p.getPreferredSize();

			System.out.println(dim.height);

		}
}
