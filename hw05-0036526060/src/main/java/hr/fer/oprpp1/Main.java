package hr.fer.oprpp1;

public class Main {

	public static void main(String[] args) {
		byte[] byteArray = Util.hextobyte("01aE22");
		for(byte b : byteArray) {
			System.out.println(b);
		}
		String hex = Util.bytetohex(new byte[] {(byte)345,67,23});
		System.out.println(hex);
		
		
	}

}
