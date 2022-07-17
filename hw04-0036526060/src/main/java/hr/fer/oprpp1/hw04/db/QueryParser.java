package hr.fer.oprpp1.hw04.db;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representation of a query parser
 * 
 * @author 38591
 *
 */

public class QueryParser {
	
	/**
	 * QueryLeyer that this parser uses to generate tokens in queries
	 * 
	 */
	
	private QueryLexer lexer;
	
	/**
	 * List of conditional expressions found in a query
	 * 
	 */
	
	private List<ConditionalExpression> queryList;
	
	/**
	 *  An array containing elements of a query separated by a single space
	 * 
	 */
	
	private String[] query;
	
	
	/**
	 * Constructor that receives String format of given query
	 * 
	 * @param query
	 */
	
	public QueryParser(String query) {
		this.query = query.split(" ");
	}
	
	/**
	 * Method that checks if query was direct
	 * 
	 * @return true if query is direct, false otherwise
	 */
	
	public boolean isDirectQuery() {
		List<ConditionalExpression> list = this.getQuery();
		return ((list.size() == 1) &&
				list.get(0).getFieldGetter().equals(FieldValuesGetter.JMBAG)
				&& list.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS));	
	}
	
	
	/**
	 * Method that returns queried JMBAG if query is direct
	 * 
	 * @return String value of queried JMBAG
	 */
	
	public String getQueriedJMBAG() {
		if(isDirectQuery()) {
			return queryList.get(0).getStringLiteral();
		} else {
			throw new IllegalStateException();
		}
		
	}
	
	/**
	 * Method that creates a list of conditional expressions found in a query. It combines
	 * tokens that it's QueryLexer has detected and creates conditional expressions from them.
	 * 
	 * @return
	 */
	
	public List<ConditionalExpression> getQuery() {
		
		lexer = new QueryLexer(query);
		queryList = new LinkedList<ConditionalExpression>();
		ConditionalExpression current;
		
		IFieldValueGetter getter = null;
		String literal = null;
		IComparisonOperator strategy = null;
		
		boolean and = false;
		
		while(lexer.hasNext()) {
			
			Token token = lexer.getNext();
			if(token.getValue().equals("empty")) continue;
			if(token.getType().equals(TokenType.ATTRIBUTE) && getter==null) {
				
				if(!and && !queryList.isEmpty()) throw new IllegalStateException();
				if(and) and = false;
				getter = findGetter(token.getValue());
				
			} else if(token.getType().equals(TokenType.OPERATOR) && strategy == null
					&& getter!=null) {
				
				strategy = findOperator(token.getValue());
			
			} else if(token.getType().equals(TokenType.STRINGLITERAL) && literal == null
					&& getter!=null && strategy!=null) {
				
				literal = String.valueOf(token.getValue());
			
				current = new ConditionalExpression(getter, literal, strategy);
				queryList.add(current);
				getter = null;
				literal = null;
				strategy = null;
				
			} else if(token.getType().equals(TokenType.AND) && !queryList.isEmpty()) {
				and = true;
				continue;
			} else {
				throw new IllegalStateException();
			}
		}
		
		if(getter!=null || literal!=null || strategy !=null || and) throw new IllegalStateException();
		return queryList;
	}

	
	/**
	 * Method returns a type of comparison operator found in a lexer
	 * 
	 * @param value of a token that represents an operator
	 * @return a comparison operator given as a value of a token
	 */
	
	private IComparisonOperator findOperator(Object value) {
		IComparisonOperator operator = null;
		if(value.equals("=")) {
			operator = ComparisonOperators.EQUALS;
		} else if(value.equals(">")) {
			operator = ComparisonOperators.GREATER;
		} else if(value.equals("<")) {
			operator = ComparisonOperators.LESS;
		} else if(value.equals("<=")) {
			operator = ComparisonOperators.LESS_OR_EQUALS;
		} else if(value.equals(">=")) {
			operator = ComparisonOperators.GREATER_OR_EQUALS;
		} else if(value.equals("LIKE")) {
			operator = ComparisonOperators.LIKE;
		} else if(value.equals("!=")) {
			operator = ComparisonOperators.NOT_EQUALS;
		}
		return operator;
	}

	
	/**
	 * Method returns a type of a FieldValueGetter found in a lexer
	 * 
	 * @param value of a token that represents an attribute of an expression
	 * @return IFieldValueGetter value of an attribute given as a value of a token
	 */
	
	private IFieldValueGetter findGetter(Object value) {
		
		IFieldValueGetter ret = null;
		if(value.equals("firstName")) {
			ret = FieldValuesGetter.FIRST_NAME;
		} else if(value.equals("lastName")) {
			ret = FieldValuesGetter.LAST_NAME;
		} else if(value.equals("jmbag")) {
			ret = FieldValuesGetter.JMBAG;
		}
		return ret;
	
	}
	
		
}
