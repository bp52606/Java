package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Class that represents a decorator for some other IlocalizationProvider
 * 
 * @author 38591
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{

	/**
	 * Boolean value saying if a bridge is connected
	 * 
	 */
	private boolean connected;
	/**
	 * Instance of {@link ILocalizationProvider} representing a parent of this class
	 * 
	 */
	private ILocalizationProvider parent;
	
	/**
	 * Listener letting other listeners know about the changes made
	 * 
	 */
	private ILocalizationListener listener;
	/**
	 * Currently chosen language in the editor
	 * 
	 */
	private String currentLanguage;
	
	/**
	 * Constructor accepting an instance of {@link ILocalizationProvider}(parent)
	 * 
	 * 
	 * @param provider parent {@link ILocalizationProvider}
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.parent = provider;
		
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
			
		};
		
		this.connected = false;
	}
	
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	/**
	 * Connects this class to a parent
	 * 
	 */
	
	public void connect() {
		if(!connected) {
			if(!parent.getCurrentLanguage().equals(this.getCurrentLanguage())) {
				this.currentLanguage = parent.getCurrentLanguage();
				listener.localizationChanged();
			}
			this.connected = true;
			parent.addLocalizationListener(listener);
		}
		
	}
	
	/**
	 * Disconnects this class from a parent
	 * 
	 */
	
	public void disconnect() {
		this.connected = false;
		parent.removeLocalizationListener(listener);
		this.currentLanguage = parent.getCurrentLanguage();
	}

	@Override
	public String getCurrentLanguage() {
		return this.currentLanguage;
	}


}
