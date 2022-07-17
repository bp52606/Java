package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import java.util.LinkedList;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Class representing an implementation of a model of a document opened in a text editor
 * 
 * @author 38591
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * Path of a document opened in a text editor
	 * 
	 */
	private Path filePath;
	
	/**
	 * Text area where content of a document is written 
	 *
	 */
	
	private JTextArea textComponent;
	
	/**
	 * 
	 * List of listeners keeping track of changes happened in the text area where document's content
	 * is written
	 * 
	 */
	private List<SingleDocumentListener> listeners = new LinkedList<SingleDocumentListener>();
	/**
	 * Boolean value representing information about document being edited and not saved yet
	 */
	private boolean isModified = false;
	

	/**
	 * Constructor accepting path of a file and it's content
	 * 
	 * @param filePath path of a file
	 * @param textContent content of a file
	 */
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		this.filePath = filePath;
		textComponent = new JTextArea(textContent);
		textComponent.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
				
			}
			

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
			
		});
				
	}
	
	/**
	 * Text area where document's content is written
	 * 
	 */
	
	public JTextArea getTextComponent() {
		return this.textComponent;
	}

	/**
	 * Method that lets listeners know about happened changes
	 * 
	 */
	private void tellListeners() {
		for(SingleDocumentListener listener : listeners) {
			listener.documentModifyStatusUpdated(this);
		}	
	}

	@Override
	public Path getFilePath() {
		return this.filePath;
	}

	@Override
	public void setFilePath(Path path) {
		if(path!=null) {
			this.filePath = path;
			tellListeners();
		} else {
			throw new IllegalArgumentException("Path must not be null");
		}
	}

	@Override
	public boolean isModified() {
		return this.isModified;
	}

	@Override
	public void setModified(boolean modified) {
		this.isModified = modified;
		tellListeners();
		
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.add(l);	
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.remove(l);		
	}

	/**
	 * Method that returns listeners of this document
	 * 
	 * @return list of this document's listeners
	 */
	
	public List<SingleDocumentListener> getListeners() {
		return listeners;
	}

	
	
}
