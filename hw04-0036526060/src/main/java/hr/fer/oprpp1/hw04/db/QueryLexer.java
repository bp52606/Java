package hr.fer.oprpp1.hw04.db;
/**
 * Class representation of a query analyzer
 * 
 * @author 38591
 *
 */
public class QueryLexer {
	
	/**
	 * An array containing elements of a query separated by a single space
	 * 
	 */
	
	private String[] query;
	
	/**
	 * Counter which memorizes last analyzed element in a field of query elements
	 * 
	 */
	
	private int counter = 0;
	
	/**
	 * Token representation of an element that query contains
	 * 
	 */
	
	private Token token;
	
	/**
	 * Constructor receiving query elements separated with a single space
	 * 
	 * @param query String array of query elements
	 */
	
	public QueryLexer(String[] query) {
		this.query = new String[query.length];
		int i = 0;
		for(String s : query) {
			this.query[i] = s;
			++i;
		}
	}
	
	/**
	 * Method that groups elements of a query to a token and returns it
	 * 
	 * @return Token representation of a query element
	 */
	
	public Token getNext() {
		
		boolean expression = false;
		String current = "";
		boolean literal = false;
		
		if(this.hasNext()) {
			
			StringBuilder stringBuilder = new StringBuilder();
			current = query[counter];
			
			if((isAttribute(current) && isOperator(current))
					|| (isOperator(current) && current.contains("\""))) expression = true;
			
			if(!current.equals(" ") && !current.isEmpty()) {
				
				char[] charData = current.toCharArray();
				
				for(int i=0;i<charData.length;++i) {
					if(charData[i]!='"')stringBuilder.append(charData[i]);
					
					boolean optAttribute = isAttribute(stringBuilder.toString());
					boolean optOperator = isOperator(stringBuilder.toString());
					
					
					if(optAttribute) {
						token = new Token(TokenType.ATTRIBUTE, stringBuilder.toString());
						stringBuilder = new StringBuilder();
						break;
					}
					if(optOperator) {
						token = new Token(TokenType.OPERATOR, stringBuilder.toString());
						stringBuilder = new StringBuilder();
						break;
					}
					
					if(charData[i] == '"' && !literal) {
						
						literal = true;
						
					} else if(charData[i] == '"'){
						
						literal = false;
						token = new Token(TokenType.STRINGLITERAL,stringBuilder.toString());
						stringBuilder = new StringBuilder();
						break;
						
					} else if(stringBuilder.toString().toLowerCase().equals("and") && !literal) {
						
						token = new Token(TokenType.AND, "and");
						break;
					
					}
					
				}
				
			} else {
				token = new Token(null,"empty");
			}			
		} else {
			counter = 0;
		}
		if(expression) {
			query[counter] = current.substring(current.indexOf(token.getValue().toString()) + 
					token.getValue().toString().length());
		} else {
			++counter;
		}
		return token;
	}
	
	
	/**
	 * Checks whether query has more unchecked elements
	 * 
	 * @return true if query has more elements, false otherwise
	 */
	
	public boolean hasNext() {
		return counter < query.length;
	}
	
	/**
	 * Checks if detected token in a query is an operator
	 * 
	 * @param string String value of detected token
	 * @return true if token is an operator, false otherwise
	 */

	public boolean isOperator(String string) {
		
		return ((string.contains("=")) ||
		(string.contains(">")) ||
		(string.contains("<")) ||
		(string.contains(">=")) ||
		(string.contains("<=")) ||
		(string.contains("LIKE")) ||
		(string.contains("!=")));
	}
	
	/**
	 * Checks if detected token in a query is an attribute
	 * 
	 * @param string String value of detected token
	 * @return true if token is an attribute, false otherwise
	 */
	
	public boolean isAttribute(String string) {
		
		return ((string.contains("firstName")) ||
		(string.contains("lastName")) ||
		(string.contains("jmbag")));

	}
	
}
