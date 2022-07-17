package hr.fer.oprpp1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * Class that will allow the user to encrypt/decrypt given file using the AES crypto-algorithm and 
 * the 128-bit encryption key or calculate and check the SHA-256 file digest.
 * 
 * @author 38591
 *
 */
public class Crypto {
	
		/**
		 * Variable that tells us whether encryption was requested
		 * 
		 */
		private static boolean encrypt = false;
	
		public static void main(String[] args) throws IOException {
			if(args.length<2) {
				throw new IllegalArgumentException("There are too few given arguments");
			} 
			if(args[0].equals("checksha") && args.length == 2) {
				digest(args[1]);
			} else if(args[0].equals("encrypt") && args.length == 3) {
					encrypt = true;
					encrypt(args[1],args[2]);
					encrypt = false;
			} else if(args[0].equals("decrypt") && args.length == 3) {
				decrypt(args[1],args[2]);
			} else {
				throw new IllegalArgumentException("Your arguments are invalid");
			}
		}

		/**
		 * Method used to implement decryption operation
		 * 
		 * @param pdf String value of a file name that needs to be decrypted
		 * @param cryptedPdf String value of a decrypted file name
		 */
		
		private static void decrypt(String pdf, String cryptedPdf) {
			Cipher cipher = requestInput();
			Path path = Paths.get(pdf);
			try(InputStream is = Files.newInputStream
					(path, StandardOpenOption.READ); OutputStream os = Files.newOutputStream(Paths.get(cryptedPdf))) {
					
				byte[] buff = new byte[4096];
					
				while (true) {		
						int r = is.read(buff);
						if (r < 1) break;
						byte[] result = cipher.update(buff, 0, r);
						os.write(result);
				}
				
				byte[] result = cipher.doFinal();
				os.write(result);
				System.out.format("Decryption completed. Generated file %s based on file %s.",cryptedPdf,pdf);
					
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
			
		}

		/**
		 * Method used to implement encryption operation
		 * 
		 * @param pdf String value of a file name that needs to be encrypted
		 * @param cryptedPdf String value of a encrypted file name
		 */
		
		private static void encrypt(String pdf, String cryptedPdf) {
			
			Cipher cipher = requestInput();
			Path path = Paths.get(pdf);
			try(InputStream is = Files.newInputStream
					(path, StandardOpenOption.READ); OutputStream os = Files.newOutputStream(Paths.get(cryptedPdf))) {
					
				byte[] buff = new byte[4096];
					
				while (true) {		
						int r = is.read(buff);
						if (r < 1) break;
						byte[] result = cipher.update(buff, 0, r);
						os.write(result);
				}
				
				byte[] result = cipher.doFinal();
				os.write(result);
				System.out.format("Encryption completed. Generated file %s based on file %s.",cryptedPdf,pdf);
					
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
		}

		/**
		 * Method that requests pass and initialization vector from user
		 * 
		 * @return created Cipher using given pass and initialization vector through command line
		 */

		private static Cipher requestInput() {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			Scanner sc = new Scanner(System.in);
			System.out.printf("> ");
			String pass = sc.nextLine();
			if(!pass.isEmpty()) {
				System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			}
			System.out.printf("> ");
			String initVector = sc.nextLine();
			sc.close();
			
			Cipher cipher = initializeCipher(pass,initVector);
			return cipher;
		}

		/**
		 * Method that creates a Cipher using given pass and initialization vector
		 * 
		 * @param pass String value of a password used to create a Cipher
		 * @param initVector String value of an initialization vector used to create a Cipher
		 * @return Cipher created using given password and initialization vector
		 */
		
		private static Cipher initializeCipher(String pass, String initVector)  {
			
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(pass), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(initVector));
			Cipher cipher;
			try {
				cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
				return cipher;
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
			return null;
			
		}

		/**
		 * Method that receives data necessary to create a message digest
		 * 
		 * @param string String representation of a file name whose digest is being created
		 */
		
		private static void digest(String string) {
			
			System.out.format("Please provide expected sha-256 digest for %s:", string);
			Scanner sc = new Scanner(System.in);
			System.out.printf("> ");
			String shaDigest = sc.nextLine();
			if(!shaDigest.isEmpty()) {
				if(shaDigest.equals(countDigest(string))) {
					System.out.format("Digesting completed. Digest of %s matches expected digest.",string);
				} else {
					System.out.format("Digesting completed. Digest of %s does not match the expected digest. Digest\r\n" + 
							"was: " + countDigest(string),string);
				}
			}
			sc.close();
			
		}

		/**
		 * Method calculates digest of a given file
		 * 
		 * @param string String value of a file name
		 * @return calculated sha-256 digest of a given file
		 */
		
		private static String countDigest(String string) {
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
				Path path = Paths.get(string);
				try(InputStream is = Files.newInputStream
						(path, StandardOpenOption.READ)) {
					
					byte[] buff = new byte[4096];
					
					while (true) {
						
							int r = is.read(buff);
							if (r < 1) break;
							messageDigest.update(buff,0,r);
					}
					
					byte[] computedDigest = messageDigest.digest();
					return Util.bytetohex(computedDigest);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return null;
		}

}
