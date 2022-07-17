package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Class representing an implementation of a model of a text editor 
 * that can create and edit multiple documents.
 * 
 * @author 38591
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * List of open documents in this editor
	 */
	private List<SingleDocumentModel> singleDocuments = new LinkedList<SingleDocumentModel>();
	/**
	 * A document on whose tab the editor is currently focused
	 */
	private SingleDocumentModel current;
	/**
	 * List of listeners for changes made in a multiple document model of a text editor
	 */
	private List<MultipleDocumentListener> listeners = new LinkedList<MultipleDocumentListener>();

	/**
	 * Constructor without arguments that adds change listener and a mouse motion listener
	 */
	
	public DefaultMultipleDocumentModel() {
		this.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(singleDocuments.size()>0 && getSelectedIndex() < singleDocuments.size()
						&& getSelectedIndex()>-1) {
					SingleDocumentModel temp = singleDocuments.get(getSelectedIndex());
					tellListeners(current, temp);
					current = temp;
				}
				
			}
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
		
			
			@Override
			public void mouseMoved(MouseEvent e) {
				JTabbedPane tp = (JTabbedPane)e.getSource();
				int x = tp.indexAtLocation( e.getX(), e.getY() );
				if(x!=-1 && x < singleDocuments.size() && 
						singleDocuments.size()>0 && getDocument(x)!=null &&
						getDocument(x).getFilePath()!=null) 
					setToolTipTextAt(x, getDocument(x).getFilePath().toString());
			} 

		});
	}
	
	@Override
	public JComponent getVisualComponent() {
		return this;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel create = new DefaultSingleDocumentModel(null, "");
		
		tellListeners(current, create);
		current = create;
		
		this.openTabAndSwitch("unnamed",create.getTextComponent());
		this.singleDocuments.add(current);
		tellListenersAboutAdding();
		create.setModified(true);
		
		setIconAt(getSelectedIndex(), getImage(true));
		return create;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		
		if(path!=null) {
			boolean unnamed = false;
			SingleDocumentModel model = this.findForPath(path);
			if(model!=null  && !(getTitleAt(getIndexOfDocument(model))).equals("unnamed")) {
				tellListeners(current, model);
				current = model;
				this.setSelectedComponent(this.getTabComponentAt(this.getIndexOfDocument(current)));
				
			} else {
				
						byte[] okteti;
						try {
							okteti = Files.readAllBytes(path);
							String text = new String(okteti);
							SingleDocumentModel temp = new DefaultSingleDocumentModel(path, text);
							tellListeners(current,temp);
							current = temp;
							this.openTabAndSwitch(path.toFile().getName() ,temp.getTextComponent());
							this.singleDocuments.add(current);
							tellListenersAboutAdding();
		
						
						} catch(Exception ex) {
							ex.printStackTrace();
						}

					
			}
			
			current.addSingleDocumentListener(new SingleDocumentListener() {
				
				@Override
				public void documentModifyStatusUpdated(SingleDocumentModel model) {
					if(model.isModified()) {
						setIconAt(getSelectedIndex(), getImage(true));
					} else {
						setIconAt(getSelectedIndex(), getImage(false));
					}
					
				}
				
				@Override
				public void documentFilePathUpdated(SingleDocumentModel model) {
					// TODO Auto-generated method stub
					
				}
			});
			
			if(!unnamed) this.setIconAt(this.getIndexOfDocument(current), getImage(false));
			return current;

		} else {
			throw new IllegalArgumentException("No such path exists");
		}
		
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		
			try {
			
				
				if(this.findForPath(newPath)!=null && 
						model.getFilePath()!=null && !newPath.toString().equals(model.getFilePath().toString())) {
					throw new IllegalArgumentException();
				} 
				
				byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
				
				Path result = newPath;
				
				if(newPath==null) {
					result = model.getFilePath();
				}
				
				try {
					Files.write(result, podatci);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				model.setFilePath(result);
				this.setTitleAt(this.getSelectedIndex(), result.getFileName().toString());
				model.setModified(false);
				this.setIconAt(getSelectedIndex(), getImage(false));
				
			} catch(IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(this,
						"Close the other document first and then try saving again.");
			}
	}

	/**
	 * Method used to get an icon for a tab
	 * 
	 * @param modified boolean value saying if current document is still unsaved
	 * @return an Icon for a tab
	 */
	
	private Icon getImage(boolean modified) {
		InputStream is;
		if(!modified) {
			is = this.getClass().getResourceAsStream("icons/zelena.png");
		} else {
			is = this.getClass().getResourceAsStream("icons/crvena.png");
		}
		if(is==null) {
			throw new NullPointerException();
		}
		
		byte[] bytes = {};
		try {
			bytes = is.readAllBytes();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImageIcon ii = new ImageIcon(bytes);
		
		//resize to fit
		Image image = ii.getImage().getScaledInstance(13, 12, Image.SCALE_SMOOTH);
		
		return new ImageIcon(image);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		this.singleDocuments.remove(model);	
		tellListenersAboutClosing();
	}

	private void tellListenersAboutClosing() {
		for(MultipleDocumentListener listener : this.listeners) {
			listener.documentRemoved(this.current);
		}
		
	}
	
	private void tellListenersAboutAdding() {
		for(MultipleDocumentListener listener : this.listeners) {
			listener.documentAdded(this.current);
		}
		
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
			this.listeners.add(l);	
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {	
		this.listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return this.singleDocuments.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return this.singleDocuments.get(index);
	}

	@Override
	public SingleDocumentModel findForPath(Path path){
		if(path!=null && path.toFile().exists()) {
			for(SingleDocumentModel model : this.singleDocuments) {
				if(model.getFilePath()!=null && model.getFilePath().equals(path)) {
					return model;
				}
			}
		} 
		return null;
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		return this.singleDocuments.indexOf(doc);
	}
	
	/**
	 * Method that opens a new tab and switches to it 
	 * 
	 * @param title String value of a title of a tab
	 * @param panel panel with a text of a document added to a scroll pane
	 */
	
	public void openTabAndSwitch(String title, JComponent panel) {
		JScrollPane scrollPane = new JScrollPane(panel);
		this.addTab(title, scrollPane);
		this.setSelectedComponent(scrollPane);
		
	}
	
	/**
	 * Method that lets listeners know about the changes made in the model. It is called
	 * when current document has changed.
	 * 
	 * @param previousModel 
	 * @param currentModel
	 */
	private void tellListeners(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		for(MultipleDocumentListener listener :listeners) {
			listener.currentDocumentChanged(previousModel, currentModel);
		}
	}

	/**
	 * Method that returns list of listeners registered on this model
	 * @return list of listeners registered on this model
	 */
	public List<MultipleDocumentListener> getListeners() {
		return listeners;
	}

	/**
	 * Method that sets list of listeners registered on this model
	 * 
	 * @param listeners list of listeners registered on this model
	 */
	public void setListeners(List<MultipleDocumentListener> listeners) {
		this.listeners = listeners;
	}
	
	

}
