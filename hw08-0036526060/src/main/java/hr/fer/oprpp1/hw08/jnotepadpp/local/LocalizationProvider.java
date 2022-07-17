package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;

import java.util.ResourceBundle;

/**
 * Class that is singleton (so it has private constructor, private static 
 * instance reference and public static getter method);
 * 
 * @author 38591
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{

	/**
	 * Instance of this class
	 */
	private static LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * Currently set language
	 * 
	 */
	private String language;
	
	/**
	 * Representation of a specific language
	 */
	
	private Locale locale;
	/**
	 * Translations for the given language
	 */
	private ResourceBundle resourceBundle;

	/**
	 * Constructor without arguments
	 * 
	 */
	public LocalizationProvider() {
		this.language = "en";
		locale = Locale.forLanguageTag(this.language);
		resourceBundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.prijevodi", locale);
	}
	
	/**
	 * Getter for an instance of this class
	 * 
	 * @return an instance of this class
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	@Override
	public String getString(String key) {
		return resourceBundle.getString(key);
	}

	/**
	 * Sets the language of this provider
	 * 
	 * @param language given language
	 */
	public void setLanguage(String language) {
		this.language = language;
		locale = Locale.forLanguageTag(this.language);
		resourceBundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.prijevodi", locale);
		//reci listenerima da je nastala promjena
		fire();
	}

	@Override
	public String getCurrentLanguage() {
		return this.language;
	}

	
	


}
