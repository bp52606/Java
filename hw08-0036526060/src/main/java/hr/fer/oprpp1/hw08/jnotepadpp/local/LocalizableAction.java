package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 * This class extends the AbstractAction class and represents an action being performed at certain
 * events
 * 
 * @author 38591
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor accepting a key that needs to be translated using {@link ILocalizationProvider}
	 * reference
	 * 
	 * @param key String value of a word that needs to be translated
	 * @param lp provider that translated
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		
		putValue(key,lp.getString(key));
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(key, lp.getString(key));
				
			}
		});
	}
	

}
