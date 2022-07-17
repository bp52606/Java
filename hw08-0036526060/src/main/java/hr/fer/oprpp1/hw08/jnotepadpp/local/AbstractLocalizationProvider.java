package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.LinkedList;

import java.util.List;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

/**
 * Class that implements of {@link ILocalizationProvider}  and adds it the
ability to register, de-register and inform (fire() method) listeners.
 * 
 * @author 38591
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

		/**
		 * List of localization listeners registered to this class
		 */
		List<ILocalizationListener> listeners = new LinkedList<ILocalizationListener>();
	
		public void addLocalizationListener(ILocalizationListener l) {
			listeners.add(l);
		}
		
		public void removeLocalizationListener(ILocalizationListener l) {
			listeners.remove(l);
		}
		
		/**
		 * Letting listeners know about the changes made.
		 */
		public void fire() {
			for(ILocalizationListener listener : listeners) {
				listener.localizationChanged();
			}
		}
}
