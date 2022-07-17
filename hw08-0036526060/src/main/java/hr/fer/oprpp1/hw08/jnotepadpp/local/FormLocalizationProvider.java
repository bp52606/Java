package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProviderBridge;

/**
 * Class derived from LocalizationProviderBridge.
 * 
 * @author 38591
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{

	/**
	 * Constructor getting {@link ILocalizationProvider} and {@link JFrame} reference 
	 * 
	 * @param provider given {@link ILocalizationProvider} representing a parent
	 * @param jframe a given {@link JFrame} 
	 */
	public FormLocalizationProvider(ILocalizationProvider provider,JFrame jframe) {
		super(provider);
		jframe.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
				
			}
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();			
			}
		});
	}
}
