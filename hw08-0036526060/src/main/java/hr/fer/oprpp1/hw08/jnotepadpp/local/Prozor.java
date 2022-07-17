package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JButton gumb;
	private FormLocalizationProvider flp;

	
	public Prozor() throws HeadlessException {
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(500, 500);
		setTitle("Demo");
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				gumb.setText(flp.getString("login"));
				
			}
		});
		gumb = new JButton(new LocalizableAction("login", flp) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				//
				
			}
			
		});
		initGUI();
		pack();
	}
	
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		
	    JMenuBar menu = new JMenuBar();
	    
	    JMenu fileMenu = new JMenu("Language");
		menu.add(fileMenu);
		
	    fileMenu.add(new JMenuItem(hrAction));
	    fileMenu.add(new JMenuItem(enAction));
	    fileMenu.add(new JMenuItem(deAction));
	   
		
	    hrAction.putValue(Action.NAME, "hr");
	    enAction.putValue(Action.NAME, "en");
	    deAction.putValue(Action.NAME, "de");
		
		getContentPane().add(gumb, BorderLayout.CENTER);
		getContentPane().add(menu, BorderLayout.NORTH);


	}

	
	private Action hrAction = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}

	};
	
	private Action enAction = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};
	
	private Action deAction = new AbstractAction() {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};

	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Očekivao sam oznaku jezika kao argument!");
			System.err.println("Zadajte kao parametar hr ili en.");
			System.exit(-1);
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Prozor().setVisible(true);
			}
		});
	}
}
