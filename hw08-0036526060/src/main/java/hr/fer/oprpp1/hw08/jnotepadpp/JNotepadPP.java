package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.JFileChooser;

/**
 * Class representing a text editor able to open and edit multiple documents and 
 * show information about them.
 * 
 * @author 38591
 *
 */
public class JNotepadPP extends JFrame{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Tabbed pane used for adding tabs
	 */
	DefaultMultipleDocumentModel tabbedPane;
	/**
	 * Document our editor is currently focused on
	 */
	SingleDocumentModel current;
	/**
	 * Scroll pane where text components of documents are added
	 */
	JScrollPane scrollPane = new JScrollPane();
	
	/**
	 * Localization provider derived from LocalizationProviderBridge;
	 * 
	 */
	private ILocalizationProvider flp;
	/**
	 * Action performed when open option is chosen
	 */
	private Action openDocumentAction;
	/**
	 * Action performed when save option is chosen
	 */
	private Action saveDocumentAction;
	/**
	 * Action performed when save as option is chosen
	 */
	private Action saveAsDocumentAction;
	/**
	 * Action performed when exit option is chosen
	 */
	private Action exitAction;
	/**
	 * Action performed when close tab option is chosen
	 */
	private Action closeTabAction;
	/**
	 * Action performed when new document option is chosen
	 */
	private Action newDocumentAction;
	/**
	 * Action performed when cut document option is chosen
	 */
	private Action cutDocumentAction;
	/**
	 * Action performed when copy document option is chosen
	 */
	private Action copyDocumentAction;
	/**
	 * Action performed when paste document option is chosen
	 */
	private Action pasteDocumentAction;
	/**
	 * Action performed when statistics option is chosen
	 */
	private Action statisticsDocumentAction;
	
	/**
	 * Action performed when croatian language option is chosen
	 *
	 */
	private Action hrAction;
	/**
	 * Action performed when english language option is chosen
	 *
	 */
	private Action enAction;
	/**
	 * Action performed when german language option is chosen
	 *
	 */
	private Action deAction;
	/**
	 * Action performed when upper case option is chosen
	 *
	 */
	private Action uppercase;
	/**
	 * Action performed when lower case option is chosen
	 *
	 */
	private Action lowercase;
	/**
	 * Action performed when invert case option is chosen
	 *
	 */
	private Action invertcase;
	/**
	 * Action performed when ascending option is chosen
	 *
	 */
	private Action ascending;
	/**
	 * Action performed when descending option is chosen
	 *
	 */
	private Action descending;
	/**
	 * A menu with available actions
	 */
	private JMenu fileMenu;
	/**
	 * A menu with available languages
	 */
	private JMenu language;
	/**
	 * A menu with available options for editing text
	 *
	 */
	private JMenu submenu;
	/**
	 * A menu with available options for editing lines in a text
	 *
	 */
	private JMenu sort;
	
	/**
	 * Label for length of a document
	 */
	JLabel label1 = new JLabel();
	/**
	 * Label for a line, column and a selection information
	 */
	JLabel label2 = new JLabel();
	/**
	 * Label for date and time information
	 */
	JLabel label3 = new JLabel();
	
	/**
	 * Constructor initializing text editor
	 * 
	 */

	public JNotepadPP() {
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		setLocation(500, 500);
		setSize(500, 500);
		this.flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				createActionNames();
				createMenus();
			}
		});

		new javax.swing.Timer(0, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	            label3.setText(date.format(Calendar.getInstance().getTime()));
				
			}
		}).start();
		
		
		initGUI();
		
		this.addWindowListener(new WindowAdapter() {
			
			
			@Override
			public void windowClosing(WindowEvent e) {
				exitDialog();
			}

		});
	}
	
	/**
	 * Method for initialization of GUI
	 * 
	 */
	private void initGUI() {
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		tabbedPane = new DefaultMultipleDocumentModel();
		
		if(tabbedPane.getTabCount()>0) {
			setTitle(tabbedPane.getCurrentDocument().getFilePath().toString() +  " - JNotepad++");
		} else {
			setTitle("JNotepad++");
		}
		
		tabbedPane.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				
				if(previousModel == null && currentModel == null) {
					throw new IllegalArgumentException();
				}
				
				if(previousModel!=null) {
					for(SingleDocumentListener listener : ((DefaultSingleDocumentModel)previousModel).getListeners()) {
						previousModel.removeSingleDocumentListener(listener);
						currentModel.addSingleDocumentListener(listener);
					}
				}
				
				if(currentModel.getFilePath()!=null) {
					setTitle(currentModel.getFilePath().toString() + " - JNotepad++");
				} else if(tabbedPane.getTabCount()>=0) {
					setTitle("unnamed - JNotepad++");
				}
				
				JTextArea area = currentModel.getTextComponent(); 
				label1.setText("length: " + 
						currentModel.getTextComponent().getText().getBytes().length+"        	");
				area.addCaretListener(new CaretListener() {
					
					@Override
					public void caretUpdate(CaretEvent e) {
						String labelString = "";
						String labelString2 = "";
						String labelString3 = "";
						
						label1.setText("length: " + 
								currentModel.getTextComponent().getText().getBytes().length+"        	");
						
						JTextComponent c = area;
						int pos = c.getCaretPosition();
						Document doc = c.getDocument(); 
						Element root = doc.getDefaultRootElement();
						int row = root.getElementIndex(pos); 
							
						labelString = "Ln: "+ String.valueOf(root.getElementIndex(pos)+1);
						labelString2 = "Col: "+  String.valueOf(pos - root.getElement(row).getStartOffset()+1);
						labelString3 = "Sel: " + String.valueOf(area.getSelectionEnd() - area.getSelectionStart());

						label2.setText("	" +labelString+"    "+labelString2+"    " + labelString3);
						
					}
				});
				
				
				
			}
		});

		
	
		defineActions();
		
		createMenus();
		//createActionNames();
		createActions();
		createToolbars();
		
		tabbedPane.add(scrollPane);
		
		//add status bar
		/**
		 * panel for a status bar
		 */
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		panel.setLayout(new BorderLayout());
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label1.setHorizontalAlignment(SwingConstants.LEFT);
		label2.setHorizontalAlignment(SwingConstants.LEFT);
		label3.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(label1, BorderLayout.WEST);
		panel.add(label2,BorderLayout.CENTER);
		panel.add(label3,BorderLayout.EAST);
		tabbedPane.remove(0);
		cp.add(tabbedPane);
		cp.add(panel, BorderLayout.SOUTH);
	
		
	}
	
	/**
	 * Method for adding names to components
	 * 
	 */
	
	private void createActionNames() {
		openDocumentAction.putValue(
				Action.NAME, 
				flp.getString("open"));
		newDocumentAction.putValue(
				Action.NAME, 
				flp.getString("new"));
		saveDocumentAction.putValue(
				Action.NAME, 
				flp.getString("save"));
		saveAsDocumentAction.putValue(
				Action.NAME, 
				flp.getString("saveAs"));
		cutDocumentAction.putValue(
				Action.NAME,
				flp.getString("cut"));
		copyDocumentAction.putValue(
				Action.NAME,
				flp.getString("copy"));
		pasteDocumentAction.putValue(
				Action.NAME,
				flp.getString("paste"));
		closeTabAction.putValue(
				Action.NAME, 
				flp.getString("close"));
		statisticsDocumentAction.putValue(
				Action.NAME, 
				flp.getString("statistics"));
		exitAction.putValue(
				Action.NAME,
				flp.getString("exit"));
		
		uppercase.putValue(Action.NAME, flp.getString("uppercase"));
		lowercase.putValue(Action.NAME, flp.getString("lowercase"));
		invertcase.putValue(Action.NAME, flp.getString("invertcase"));
		ascending.putValue(Action.NAME, flp.getString("ascending"));
		descending.putValue(Action.NAME, flp.getString("descending"));
	}

	/**
	 * Method that defines what certain actions should do
	 * 
	 */
	private void defineActions() {
		
			openDocumentAction = new LocalizableAction("open", flp) {
			
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Open file");
					
					if(fc.showOpenDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
						return;
					}
					
					File fileName = fc.getSelectedFile();
					Path filePath = fileName.toPath();
					
					if(!Files.isReadable(filePath)) {
						JOptionPane.showMessageDialog(
								JNotepadPP.this, 
								"Datoteka "+fileName.getAbsolutePath()+" ne postoji!", 
								"Pogreška", 
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					current = tabbedPane.loadDocument(filePath);
					
					
			}
		};
		
			saveDocumentAction = new LocalizableAction("save",flp) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex())).equals("unnamed")) {
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Save document");
					
					if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(
								JNotepadPP.this, 
								"Ništa nije snimljeno.", 
								"Upozorenje", 
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					tabbedPane.saveDocument(tabbedPane.getCurrentDocument(),jfc.getSelectedFile().toPath());
				} else {
					JFileChooser jfc = new JFileChooser();
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					jfc.showSaveDialog(null);

					if(jfc.getSelectedFile() !=null) {
						String path=jfc.getSelectedFile().getAbsolutePath();
						tabbedPane.saveDocument(tabbedPane.getCurrentDocument(),Paths.get(path));
						//tabbedPane.removeTabAt(tabbedPane.getSelectedIndex()-1);
					}
				}
					
			}
			
			
			
		};
		
			saveAsDocumentAction = new LocalizableAction("saveAs",flp) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.showSaveDialog(null);

				String path=jfc.getSelectedFile().getAbsolutePath();

				
				tabbedPane.saveDocument(tabbedPane.getCurrentDocument(),Paths.get(path));
				
			}
				
		};
		
			exitAction = new LocalizableAction("exit",flp){
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				exitDialog();
			
			}
		};
		
		 	closeTabAction = new LocalizableAction("close",flp) {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					JFileChooser jfc = new JFileChooser();
					if(tabbedPane.getCurrentDocument().isModified()) {
						int input = JOptionPane.showConfirmDialog(JNotepadPP.this, 
								"Do you want to save or discard changes?");
						if(input == 0) {
							if(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("unnamed")) {
								jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
								jfc.showSaveDialog(null);
								
								if(jfc.getSelectedFile()!=null) {
									String path=jfc.getSelectedFile().getAbsolutePath();
									tabbedPane.saveDocument(tabbedPane.getCurrentDocument(),Paths.get(path));
								}
	
							} else {
								jfc = new JFileChooser();
								jfc.setDialogTitle("Save document");
									
								if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
									JOptionPane.showMessageDialog(
											JNotepadPP.this, 
											"Ništa nije snimljeno.", 
											"Upozorenje", 
											JOptionPane.WARNING_MESSAGE);
									return;
								}
								tabbedPane.saveDocument(tabbedPane.getCurrentDocument(), jfc.getSelectedFile().toPath());
							}
	
	
						} 
						if(input==0 || input == 1) 
							tabbedPane.closeDocument(tabbedPane.getCurrentDocument());
					} else {
							tabbedPane.closeDocument(tabbedPane.getCurrentDocument());
					}
				}
		 };
		 
		 	newDocumentAction = new LocalizableAction("new",flp) {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					tabbedPane.createNewDocument();
					current = tabbedPane.getDocument(tabbedPane.getTabCount()-1);
				}
			};
			cutDocumentAction = new LocalizableAction("cut",flp) {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				
				@Override
				public void actionPerformed(ActionEvent e) {
					Action a = new DefaultEditorKit.CutAction();
					a.actionPerformed(e);
				}
			};
			
			copyDocumentAction = new LocalizableAction("copy",flp) {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Action a = new DefaultEditorKit.CopyAction();
					a.actionPerformed(e);
				}
			};
			
			pasteDocumentAction = new LocalizableAction("paste",flp) {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Action a = new DefaultEditorKit.PasteAction();
					a.actionPerformed(e);
				}
			};
			
			statisticsDocumentAction = new LocalizableAction("statistics",flp) {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int characters = 0;
					int nonBlank = 0;
					int lines = 0;
					
					char[] chars = {};
					
					if(tabbedPane.getCurrentDocument()!=null && 
							!tabbedPane.getCurrentDocument().getTextComponent().getText().isEmpty()) {
						chars = tabbedPane.getCurrentDocument().getTextComponent().getText().toCharArray();
						characters = chars.length;
						
						for(char c : chars) {
							if(!String.valueOf(c).isBlank()) {
								++nonBlank;
							}
						}
						
						lines = tabbedPane.getCurrentDocument().getTextComponent().getLineCount();
					} 
					
					if(tabbedPane.getTabCount()>0) {
						JOptionPane.showMessageDialog(JNotepadPP.this,
							    String.format("Your document has %d characters"
							    		+ ", %d non-blank characters and %d lines ",
							    		characters,nonBlank,lines));
					} else {
						JOptionPane.showMessageDialog(JNotepadPP.this,
							    "No files are loaded");
					}
				}
			};
			
			hrAction = new AbstractAction() {
				
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					LocalizationProvider.getInstance().setLanguage("hr");
					
				}
			};
			
			enAction = new AbstractAction() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					LocalizationProvider.getInstance().setLanguage("en");
					
				}
			};
		
			deAction = new AbstractAction() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					LocalizationProvider.getInstance().setLanguage("de");
					
				}
			};
			
			uppercase = new AbstractAction() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(tabbedPane.getCurrentDocument()!=null) {
						JTextArea area = tabbedPane.getCurrentDocument().getTextComponent();
						String text = area.getText().toUpperCase();
						try {
							area.getDocument().remove(area.getDocument().getStartPosition().getOffset(),
									area.getText().length());
							area.getDocument().insertString(area.getCaretPosition(), text, null);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					
				}
			};
			
			lowercase = new LocalizableAction("lowercase",flp) {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(tabbedPane.getCurrentDocument()!=null) {
						if(tabbedPane.getCurrentDocument()!=null) {
							JTextArea area = tabbedPane.getCurrentDocument().getTextComponent();
							String text = area.getText().toLowerCase();
							try {
								area.getDocument().remove(area.getDocument().getStartPosition().getOffset(),
										area.getText().length());
								area.getDocument().insertString(area.getCaretPosition(), text, null);
							} catch (BadLocationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
					}
					
				}
			};
			
			invertcase = new LocalizableAction("invertcase",flp) {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(tabbedPane.getCurrentDocument()!=null) {
						JTextArea area = tabbedPane.getCurrentDocument().getTextComponent();
						char[] letters = area.getText().toCharArray();
						StringBuilder sb = new StringBuilder();
						for(char c : letters) {
							if(Character.isLowerCase(c)) {
								sb.append(Character.toUpperCase(c));
							} else {
								sb.append(Character.toLowerCase(c));
							}
						}
						try {
							area.getDocument().remove(area.getDocument().getStartPosition().getOffset(),
									area.getText().length());
							area.getDocument().insertString(area.getCaretPosition(), sb.toString(), null);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				}
			};
			
			ascending = new AbstractAction() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					JTextArea area = tabbedPane.getCurrentDocument().getTextComponent();
					String[] split = {};
					try {
						split = area.getText(area.getLineStartOffset(area.getLineOfOffset(area.getSelectionStart())), 
								area.getLineEndOffset(area.getLineOfOffset(area.getSelectionEnd())) -
								area.getLineStartOffset(area.getLineOfOffset(area.getSelectionStart()))).split("\n");
					} catch (BadLocationException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					Arrays.sort(split,new Comparator<String>() {

						@Override
						public int compare(String o1, String o2) {
							Locale locale = new Locale(LocalizationProvider.getInstance().getCurrentLanguage());
							Collator collator = Collator.getInstance(locale);
							int r = collator.compare(o1, o2);
							return r;
						}
						
					});
					
					StringBuilder sb = new StringBuilder();
					for(String s : split) {
						sb.append(s+"\n");
					}
					
					try {
						int start = area.getLineStartOffset(area.getLineOfOffset(area.getSelectionStart()));
						area.getDocument().remove(area.getLineStartOffset(area.getLineOfOffset(area.getSelectionStart())), 
								area.getLineEndOffset(area.getLineOfOffset(area.getSelectionEnd())) -
								area.getLineStartOffset(area.getLineOfOffset(area.getSelectionStart())));
						area.getDocument().insertString(start, sb.toString(), null);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			};
			
			descending = new AbstractAction() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					JTextArea area = tabbedPane.getCurrentDocument().getTextComponent();
					String[] split = {};
					try {
						split = area.getText(area.getLineStartOffset(area.getLineOfOffset(area.getSelectionStart())), 
								area.getLineEndOffset(area.getLineOfOffset(area.getSelectionEnd())) -
								area.getLineStartOffset(area.getLineOfOffset(area.getSelectionStart()))).split("\n");
					} catch (BadLocationException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					Arrays.sort(split,new Comparator<String>() {

						@Override
						public int compare(String o1, String o2) {
							Locale locale = new Locale(LocalizationProvider.getInstance().getCurrentLanguage());
							Collator collator = Collator.getInstance(locale);
							int r = collator.compare(o1, o2);
							return -r;
						}
						
					});
					
					StringBuilder sb = new StringBuilder();
					for(String s : split) {
						sb.append(s+"\n");
					}
					
					try {
						int start = area.getLineStartOffset(area.getLineOfOffset(area.getSelectionStart()));
						area.getDocument().remove(area.getLineStartOffset(area.getLineOfOffset(area.getSelectionStart())), 
								area.getLineEndOffset(area.getLineOfOffset(area.getSelectionEnd())) -
								area.getLineStartOffset(area.getLineOfOffset(area.getSelectionStart())));
						area.getDocument().insertString(start, sb.toString(), null);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			};
		
		
		
		
	}

	/**
	 * Method for creating menus and it's components
	 * 
	 */
	
	private void createMenus() {
		
		JMenuBar menuBar = new JMenuBar();
		
		fileMenu = new JMenu(flp.getString("file"));
		menuBar.add(fileMenu);
		
		language = new JMenu(flp.getString("language"));
		menuBar.add(language);
		
		submenu = new JMenu(flp.getString("changecase"));
		
		sort = new JMenu(flp.getString("sort"));
		
		sort.add(new JMenuItem(ascending));
		sort.add(new JMenuItem(descending));
		
		submenu.add(new JMenuItem(uppercase));
		submenu.add(new JMenuItem(lowercase));
		submenu.add(new JMenuItem(invertcase));
	
		
		fileMenu.add(submenu);
		fileMenu.add(sort);
		
		language.add(new JMenuItem(hrAction));
	    language.add(new JMenuItem(enAction));
	    language.add(new JMenuItem(deAction));
	    
	    hrAction.putValue(Action.NAME, "Hrvatski");
	    enAction.putValue(Action.NAME, "English");
	    deAction.putValue(Action.NAME, "Deutsch");

		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeTabAction));
		fileMenu.add(new JMenuItem(cutDocumentAction));
		fileMenu.add(new JMenuItem(copyDocumentAction));
		fileMenu.add(new JMenuItem(pasteDocumentAction));
		fileMenu.add(new JMenuItem(statisticsDocumentAction));
		fileMenu.add(new JMenuItem(exitAction));
	
		this.setJMenuBar(menuBar);
		
	}
	
	/**
	 * Method that creates options available in a toolbar
	 * 
	 */

	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(newDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.add(new JButton(closeTabAction));
		toolBar.add(new JButton(cutDocumentAction));
		toolBar.add(new JButton(copyDocumentAction));
		toolBar.add(new JButton(pasteDocumentAction));
		toolBar.add(new JButton(statisticsDocumentAction));
		toolBar.add(new JButton(exitAction));
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * Setting up of actions, their accelerator and mnemonic keys
	 * 
	 */

	private void createActions() {
		
		openDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_O); 
		saveDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_S); 
		saveAsDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control Y"));
		saveAsDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_Y); 
		closeTabAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control C"));
		closeTabAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_C); 
		cutDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control Z"));
		cutDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_Z); 
		copyDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control X"));
		copyDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_X); 
		pasteDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control P"));
		pasteDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_P); 
		statisticsDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control T"));
		statisticsDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_T); 
		exitAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control E"));
		exitAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_E); 
		newDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_N); 
	
	}

	/**
	 * Main function that starts the program
	 * 
	 * @param args given arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
			
		});
	}
	
	public void exitDialog() {
		int counter = tabbedPane.getNumberOfDocuments();
		List<SingleDocumentModel> unsaved = new LinkedList<SingleDocumentModel>();
		for(int i=0;i<counter;++i) {
			SingleDocumentModel model = tabbedPane.getDocument(i);
			if(model.isModified()) {
				unsaved.add(model);
			}
		}
		boolean stop = false;
		for(SingleDocumentModel model : unsaved) {
				int input = 0;
				if(!tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("unnamed")) {
					input = JOptionPane.showConfirmDialog(JNotepadPP.this, 
							"Do you want to save or discard changes for "+ tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
				} else {
					input = JOptionPane.showConfirmDialog(JNotepadPP.this, 
							"Do you want to save or discard changes?");
				}
				if(input==0) {
					JFileChooser jfc = new JFileChooser();
					if(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("unnamed")) {
						jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						jfc.showSaveDialog(null);

						Path path=jfc.getSelectedFile().toPath();

						tabbedPane.saveDocument(tabbedPane.getCurrentDocument(),path);
						tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
						//tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
						
						
					} else {
						jfc = new JFileChooser();
						jfc.setDialogTitle("Save document");
							
						if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
							JOptionPane.showMessageDialog(
									JNotepadPP.this, 
									"Ništa nije snimljeno.", 
									"Upozorenje", 
									JOptionPane.WARNING_MESSAGE);
							return;
						}

						tabbedPane.saveDocument(model, jfc.getSelectedFile().toPath());
						tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
					}
				} else if(input==1) {
					tabbedPane.removeTabAt(tabbedPane.getIndexOfDocument(tabbedPane.getCurrentDocument()));
				} else {
					stop = true;
					break;
				}
			}
		if(!stop) {
			System.exit(0);
		}
	}
	
}
