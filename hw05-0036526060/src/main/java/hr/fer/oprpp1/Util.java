package hr.fer.oprpp1;


/**
 * Class used for turning hexadecimal numbers to byte numbers and byte numbers to hexadecimal
 * 
 * @author 38591
 *
 */

public class Util {

	/**
	 * Method that creates an array of bytes using given text as hex-encoded String
	 * 
	 * @param keyText hex-encoded String
	 * @return array of bytes generated from given hex-encoded String
	 */
	
	public static byte[] hextobyte(String keyText) {
		byte[] byteArray;
		if(stringIsValid(keyText)) {

			byteArray = new byte[keyText.length()/2];
			int i = 0;
			char[] textChars = keyText.toCharArray();
			int brojac = 0;
			int keep =  0;
			for(char c : textChars) {
				++brojac;
				if(brojac==2) {
					int result = Character.getNumericValue(c) + keep;
					byteArray[i] = (byte)result;
					brojac = 0;
					++i;
				} else {
					int k = Character.getNumericValue(c);
					keep = k<<4;
				}
				
			}
		} else if(keyText.length()==0) {
			byteArray = new byte[0];
		} else {
			throw new IllegalArgumentException();
		}
		return byteArray;
		
	}
	
	/**
	 * Method that checks if given hex-encoded String contains correct syntax
	 * 
	 * @param keyText hex-encoded String 
	 * @return true if given argument is valid, false otherwise
	 */
	
	private static boolean stringIsValid(String keyText) {
		char[] array = keyText.toCharArray();
		
		//check if has invalid characters
		for(char c : array) {
			if((!Character.isAlphabetic(c) && !Character.isDigit(c))
					|| (Character.isAlphabetic(c) && (!(c>='A' && c<='F')) && !(c>='a' && c<='f'))) return false;
		}
		
		//check if it is odd-sized
		return array.length%2==0;
	}

	
	/**
	 * Method that creates hex-encoded String generated from given array of bytes
	 * 
	 * @param byteArray given array of bytes
	 * @return hex-encoded String
	 */
	
	public static String bytetohex(byte[] byteArray) {
		
		String result = "";
		
		for(byte b : byteArray) {
			result += String.format("%02x", b);
		}
		
		
		return result;
		
	}	
	
}
