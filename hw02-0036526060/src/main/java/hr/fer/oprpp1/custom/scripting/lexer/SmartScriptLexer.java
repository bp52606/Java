package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import hr.fer.oprpp1.hw02.prob1.Token;
import hr.fer.oprpp1.hw02.prob1.TokenType;

/**
 * Class that represents a lexer which reads a text and analyzes it.
 * It returns token which certain part of given text represents
 * 
 * @author 38591
 *
 */

public class SmartScriptLexer {

	private int counter;
	private char[] chars;
	private SmartLexerState state;
	private Token token;
	
	/**
	 * Constructor of this lexer
	 * 
	 * @param text given text that lexer checks
	 */
	
	public SmartScriptLexer(String text) {

		this.chars = text.toCharArray();
		this.state = SmartLexerState.TEXT;
		this.counter = 0;
		this.token = new Token(TokenType.WORD, null);
		
		//dodajemo DocumentNode na stog
		//os.push(curr);
	}
	
	/**
	 * Returns next token from given text
	 * 
	 * @return next token from given text
	 */
	
	public Token nextToken() {
		
		boolean slova = false;
		boolean brojevi = false;
		boolean simboli = false;
		boolean decimalni = false;
		boolean crta = false;
		boolean n = false;
		int add = 0;
		
		
		StringBuilder sb = new StringBuilder();
		for(int i = counter; i<chars.length; ++i) {
			String s = String.valueOf(chars[i]);
			
			if(chars[i] == '"' && !crta && this.state == SmartLexerState.TEXT) {
				 ++counter;
				 continue;
			}
			
			
			//baci iznimku ako je escape na nedozvoljenoj lokaciji
			if(n) {
				if(!Character.isLetter(chars[i])) {
					throw new SmartScriptParserException();
				} else {
					n = false;
				}
			}
			
			
			//ako je uocen \n u tekstu, zabiljezi to
			if(crta && s.equals("n")) {
				n = true;
				++counter;
				continue;
			}
			
			//zabiljezi ako je uocen znak \
			if(s.equals("\\") && !crta) {
				crta = true;	
				++counter;
				continue;
			}
			if(this.state == SmartLexerState.TEXT) {
			
				
				//provjeri raid li se o novom tagu i prema tome promijeni(ili ne) stanje lexera
				if(chars[i] == '{' && !crta && add==0) {
					if(slova) return token;
					slova = true;
					if(i+1<chars.length) {
						if(chars[i+1] == '$') {
							counter +=2;
							this.state = SmartLexerState.TAG;
							token = new Token(TokenType.SYMBOL, new String("{$"));
							crta = false;
							return token;
						}
					}
				} else {
					//ako naidemo na escapeove, pretvorimo ih u slova i dodajmo u node
					if(s.equals("\n")) {
						s = "\\n";
					} else if(s.equals("\r")) {
						s = "\\r";
					} else if(s.equals("\t")) {
						s = "\\t";
					}
					++add;
					if(crta && (s.equals("{") || s.equals("\\"))) {
						sb.append(s);
						crta = false;
					} else if(crta) {
						throw new SmartScriptParserException();
					} else {
						sb.append(s);
					}
					
					//ako smo dodali simbol koji tretiramo kao slovo, obiljezimo da smo "iskoristili" crtu
					if(crta) {
						crta = false;
					}
					
					//jer smo dodali nesto sto je tipa = slovo
					slova = true;
					token = new Token(TokenType.WORD, sb.toString());
					
					//ako je ono sto slijedi neki tag, gotovi smo sa stvaranjem text nodea
					if(i+2 < chars.length) {
						if(chars[i+1] == '{' && chars[i+2]=='$') {
							++counter;
							slova = false;
							crta = false;
							return token;
						} 
					}
				}
			} else {
				//provjera radi li se o FOR-tagu
				if(s.toLowerCase().equals("f")) {
					slova = true;
					if(i+2 < chars.length) {
						if(String.valueOf(chars[i+1]).equals("o") && String.valueOf(chars[i+2]).equals("r")) {
							sb.append("FOR");
							counter +=3;
							token = new Token(TokenType.EOF, "FOR");
							return token;
						}
					}
				//provjera radi li se o =-tagu
					
				} else if(chars[i] == '=') {
					token = new Token(TokenType.SYMBOL, "=");
					++counter;
					return token;
					
				//ovo se izvodi ako se kreira broj
				}	else if(Character.isDigit(chars[i]) && !slova && !crta && !simboli) {
					sb.append(s);
					brojevi = true;
					int k = i+1;
					boolean appended = false;
					++counter;
					++add;
					while(k<chars.length && (Character.isDigit(chars[k]) || chars[k] == '.')) {
						if(chars[k] == '.') decimalni = true;
						appended = true;
						sb.append(chars[k]);
						++k;
						++counter;
					}
					//provjeri radi li se o decimalnom broju ili cijelom
					if(!decimalni) {
						token = new Token(TokenType.NUMBER, Long.valueOf(sb.toString()));
					} else {
						token = new Token(TokenType.NUMBER, Double.valueOf(sb.toString()));
					}
					if(appended) {
						decimalni = false;
						return token;
					}
					continue;
					
				//ako smo naisli na znak $, a prethodno nije uocen \, radi se o zatvaranju taga
				} else if(chars[i] == '$' && !crta) {
					if(i+1 < chars.length) {
						if(chars[i+1] == '}') {
							
							if(sb.toString().toLowerCase().equals("end") || add>0)  {

								return token;
							}
							
							this.state = SmartLexerState.TEXT;
							token = new Token(TokenType.SYMBOL, new String("$}"));
							counter += 2;
							return token;
						}
					}
					
				//ako je uocen znak -,a prethodno nije postojao znak \, radi se o minusu koji pripada broju
				} else if(chars[i] == '-' && !crta) {
					if(brojevi || slova) return token;
					simboli = true;
					sb.append(chars[i]);
					++counter;
					++add;
					int k = i+1;
					boolean appended = false;
					//dodaji brojeve nakon decimalne tocke, dok ih ima
					while(k<chars.length && (Character.isDigit(chars[k]) || chars[k] == '.')) {
						if(chars[k] == '.') decimalni = true;
						appended = true;
						sb.append(chars[k]);
						++counter;
						++add;
						++k;
					}
					if(appended) {
						if(!decimalni) {
							token = new Token(TokenType.NUMBER, Long.valueOf(sb.toString()));
						} else {
							token = new Token(TokenType.NUMBER, Double.valueOf(sb.toString()));
						}
						decimalni = false;
						return token;
					} else {
						token = new Token(TokenType.SYMBOL, Long.valueOf(sb.toString()));
					}
					continue;
				} 
				
				
				if(s.equals(" ") && add>0) {
					++counter;
					return token;

				} else if(!s.equals(" ") && chars[i] != '"' && !crta){
					if(Character.isLetter(chars[i]) || Character.isDigit(chars[i])){
						slova = true;
						sb.append(s);
						token = new Token(TokenType.WORD, sb.toString());
						if(i+2 < chars.length) {
							if(chars[i+1] == '$' && chars[i+2] == '}') {
								++counter;
								++add;
								return token;
							}
						}
					} else {
						simboli = true;
						if(!s.equals(" ")) {
							sb.append(s);
							token = new Token(TokenType.SYMBOL, sb.toString());
						}
					}
					++add;
				} else if(crta) {
					++add; 
					slova = true;
					if(!s.equals(" ")) {
						sb.append(s);
						token = new Token(TokenType.WORD, sb.toString());
						crta = false;
					}
				}
			
			}
			++counter;
		}
		return token;
	}

	/**
	 * This method return true if lexer can create next token, false otherwise
	 * 
	 * @return true if text has next token, false otherwise
	 */
	public boolean hasNextToken() {
		if(counter < chars.length) {
			return true;
		}
		return false;
	}
	/**
	 * This method sets this lexer to given state
	 * 
	 * @param sls given state to set this lexer to
	 */
	public void setState(SmartLexerState sls) {
		this.state = sls;
	}

		
}
