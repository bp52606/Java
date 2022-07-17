package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class PrimDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PrimDemo() {
		setLocation(500, 500);
		setSize(300, 200);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		Container c2 = new Container();
		c2.setLayout(new GridLayout(1, 2));
		
		PrimListModel listModel = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(listModel);
		JList<Integer> list2 = new JList<>(listModel);
		
		c2.add(new JScrollPane(list1));
		c2.add(new JScrollPane(list2));
		
		JPanel bottomPanel = new JPanel(new GridLayout(1, 0));
		
		JButton sljedeći = new JButton("sljedeći");
		bottomPanel.add(sljedeći);
		
		sljedeći.addActionListener(e -> listModel.next());
		
		cp.add(c2, BorderLayout.CENTER);
		cp.add(bottomPanel, BorderLayout.PAGE_END);
		
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}

}
