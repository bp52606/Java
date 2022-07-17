package hr.fer.oprpp1.hw02.prob1;

/**
 * Class that represents a lexer which reads a text and analyzes it.
 * It returns token which certain part of given text represents.
 * 
 * @author 38591
 *
 */

public class Lexer {
	
	private char[] data; // ulazni tekst 
	private Token token; // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private LexerState state;
	
	// konstruktor prima ulazni tekst koji se tokenizira
	
	/**
	 * Constructor which initializes this lexer
	 * 
	 * @param text being analyzed by this lexer
	 */
	
	public Lexer(String text) { 
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.token = new Token(TokenType.EOF,null);
		state = LexerState.BASIC;
	}
	
	// generira i vraća sljedeći token
	// baca LexerException ako dođe do pogreške
	
	/**
	 * Returns next token that this lexer creates
	 * 
	 * @return token that is created next 
	 */
	
	public Token nextToken() {
	
		
		//trenutni zapis
		StringBuilder sb = new StringBuilder();
		
		//ako je prethodno vraćen EOF, baci LexerException
		if(token.getType().equals(TokenType.EOF) && currentIndex > 0) {
			throw new LexerException();
		}
		
		this.token = new Token(TokenType.EOF,null);
		
		//zapis o tome što je pronađeno
		boolean slova = false;
		boolean brojevi = false;
		boolean simboli = false;
		boolean kosaCrta = false;
		boolean empty = false;
		Long max = Long.MAX_VALUE;
		String maxx = max.toString();
		char[] maks = maxx.toCharArray();
		
		
		//provjeravamo simbole samo ako tekst nije prazan
		if(data.length > 0 && currentIndex < data.length) {
			char c = data[currentIndex];
			String s = String.valueOf(c);
			if(Character.isLetter(c)) {
				slova = true;
			} else if(Character.isDigit(c)) {
				brojevi = true;
			} else if(s.equals("\\")) {
				kosaCrta = true;
			} else if(s.equals(" ")){
				empty = true;
			} else if(!s.equals("\n") && !s.equals("\r") && !s.equals("\t") && !s.equals("#")){
				simboli = true;
			} else if(s.equals("#")) {
				if(this.state == LexerState.BASIC) {
					this.setState(LexerState.EXTENDED);
				} else {
					this.setState(LexerState.BASIC);
				}
				token = new Token(TokenType.SYMBOL, Character.valueOf('#'));
				++currentIndex;
				return token;
			}
			
			++currentIndex;
			if(!empty && !s.equals("\n") && !s.equals("\r") && !s.equals("\t")) sb.append(s);
			int p = currentIndex;
			for(int j = p; j < data.length; ++j) {
				char ch = data[j];
				String sh = String.valueOf(ch);
				 
				if(this.state == LexerState.BASIC) {
					if(slova && !Character.isLetter(ch) && !sh.equals("\\") && !kosaCrta) {
						token = new Token(TokenType.WORD, sb.toString());
						kosaCrta = false;
						break;
					} else if(brojevi && !Character.isDigit(ch) && !sh.equals("\\") && !kosaCrta) {
						token = new Token(TokenType.NUMBER, Long.valueOf(sb.toString()));
						kosaCrta = false;
						break;
					 } else if(simboli && !sh.equals("\\") && !kosaCrta) {
						token = new Token(TokenType.SYMBOL, (sb.toString()).toCharArray()[0]);
						return token;
					 } else if(kosaCrta && !Character.isLetter(ch) ) {
							sb.append(sh);
							kosaCrta = false;
							slova = true;
							if(j==data.length-1) token = new Token(TokenType.WORD, sb.toString());
					 } else if (sh.equals("#")) {
								token = new Token(TokenType.SYMBOL, ch);
								this.setState(LexerState.EXTENDED);
								++currentIndex;
								break;
						
					 } else if(Character.isLetter(ch)) {
					 
						slova = true;
						empty = false;
						if(!kosaCrta) {
							sb.append(sh);
						} else {
							throw new LexerException();
						}
						kosaCrta = false;
						if(j==data.length-1) token = new Token(TokenType.WORD, sb.toString());
					 } else if(Character.isDigit(ch)) {
						brojevi = true;
						empty = false;
						kosaCrta = false;
						sb.append(sh);
						
						//provjera prevelikog broja
						char[] moj = (sb.toString()).toCharArray();
						if(moj.length>maks.length) throw new LexerException();
						for(int k = 0; k<moj.length;++k) {
							if(moj.length == maks.length && moj[k] > maks[k]) throw new LexerException();
						}
						
						if(j==data.length-1) token = new Token(TokenType.NUMBER, Long.valueOf(sb.toString()));
						
						
					 } else if(sh.equals("\\")) {
						kosaCrta = true;
					 } else if(!sh.equals(" ") && !sh.equals("\n") && !sh.equals("\r") && !sh.equals("\t") ){
						simboli = true;
						sb.append(sh);
						if(j==data.length-1) token = new Token(TokenType.SYMBOL, (sb.toString()).toCharArray()[0]);
					}
					//System.out.println(sb.toString());
			 } else {
				 if(sh.equals("#")) {
					 this.state = LexerState.BASIC;
					 return token;
				 } else {
					 if(sh.equals(" ")) {
						 break;
					 } else {
						 sb.append(sh);
					 }
					 token = new Token(TokenType.WORD, sb.toString());
				 }
				 
			 } 
			
			 ++currentIndex;
			
			}
			
		}
		if(token.getType().equals(TokenType.EOF)) ++currentIndex;
		if(kosaCrta) throw new LexerException();
		return token;
	}
	
	// vraća zadnji generirani token; može se pozivati
	// više puta; ne pokreće generiranje sljedećeg tokena
	
	/**
	 * Returns current token created by this lexer
	 *
	 * @return token current token
	 */
	
	public Token getToken() {
		return token;
	}
	
	/**
	 * Sets this lexer to given state
	 * 
	 * @param state that lexer should be switched to
	 */
	
	public void setState(LexerState state) {
		if(state!=null) {
			this.state = state;
		} else {
			throw new NullPointerException();
		}
	}

}
