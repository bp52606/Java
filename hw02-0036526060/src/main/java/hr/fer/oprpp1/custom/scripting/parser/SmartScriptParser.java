package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.SmartLexerState;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.hw02.prob1.Token;
import hr.fer.oprpp1.hw02.prob1.TokenType;

/**
 * Class representation of a parser used for parsing given text. It creates a tree of nodes and uses a stack to store them
 * 
 * @author 38591
 *
 */

public class SmartScriptParser {

	private SmartScriptLexer ssl;
	private DocumentNode node;
	private String text;
	
	private ObjectStack os = new ObjectStack();
	
	/**
	 * Constructor of this parser with given text of the document we are parsing
	 * 
	 * @param text of the given document which lexer uses first, returns tokens and then parsers checks them
	 */
	
	public SmartScriptParser(String text) {
		this.text = text;
		ssl = new SmartScriptLexer(text);
		node = this.create();
		while(!os.isEmpty()) {
			if(os.pop() instanceof ForLoopNode) throw new SmartScriptParserException();
		}
		
	}
	
	/**
	 * this method creates a document tree model
	 * 
	 * @return DocumentNode of this parser
	 */
	
	private DocumentNode create() {
		node = new DocumentNode();
		os.push(node);
		try {
			while(ssl.hasNextToken()) {

				Token token = ssl.nextToken();
				if(token.getType() == TokenType.SYMBOL) {
					
					if(token.getValue().equals("{$")) {
						ssl.setState(SmartLexerState.TAG);
						if(ssl.hasNextToken()) {
							token = ssl.nextToken();
							if(token.getValue().equals("FOR")) {
								
								ElementVariable ev;
								Element se = null;
								Element end = null;
								Element ns = null;
								if(ssl.hasNextToken()) {
									
									token = ssl.nextToken();
									
									
									//mora bit VARIJABLA, dakle nužno počinje slovom
									
									if(!Character.isLetter(String.valueOf(token.getValue()).charAt(0))) throw new SmartScriptParserException();
									ev = new ElementVariable(String.valueOf(token.getValue()));
									
									if(ssl.hasNextToken()) {
										//start expression
										token = ssl.nextToken();
										
										if(token.getType() == TokenType.NUMBER) {
											
												//provjeri je li int ili double
											
											    
												if(String.valueOf(token.getValue()).contains(".")) {
													se = new ElementConstantDouble(Double.valueOf(String.valueOf(token.getValue())));
												} else {
													se = new ElementConstantInteger(Integer.valueOf(String.valueOf(token.getValue())));
												}
												
										} else if(token.getType() == TokenType.WORD) {
											char pam = String.valueOf(token.getValue()).charAt(0);
										if(pam!='-' && !Character.isLetter(pam)) throw new SmartScriptParserException();
											
											if(String.valueOf(token.getValue()).startsWith("@")) throw new SmartScriptParserException(); //ako je funkcija, baci error
											se = new ElementVariable(String.valueOf(token.getValue()));
										}	
											if(ssl.hasNextToken()) {
												token = ssl.nextToken();
												if(token.getType() == TokenType.WORD) {
													char pam = String.valueOf(token.getValue()).charAt(0);
													if(pam!='-' && !Character.isLetter(pam)) throw new SmartScriptParserException();
													ns = new ElementVariable(String.valueOf(token.getValue()));
												} else if(token.getType() == TokenType.NUMBER) {
													if(String.valueOf(token.getValue()).contains(".")) {
														ns = new ElementConstantDouble(Double.valueOf(String.valueOf(token.getValue())));
													} else {
														ns = new ElementConstantInteger(Integer.valueOf(String.valueOf(token.getValue())));
													}
												} else {
													throw new SmartScriptParserException();
												}
												if(ssl.hasNextToken()) {
													token = ssl.nextToken();
														if(token.getType() == TokenType.WORD) {
															char pam = String.valueOf(token.getValue()).charAt(0);
															if(pam!='-' && !Character.isLetter(pam)) {			
																throw new SmartScriptParserException();
															}
															end = new ElementVariable(String.valueOf(token.getValue()));
														} else if(token.getType() == TokenType.NUMBER) {
															if(String.valueOf(token.getValue()).contains(".")) {
																end = new ElementConstantDouble(Double.valueOf(String.valueOf(token.getValue())));
															} else {
																end = new ElementConstantInteger(Integer.valueOf(String.valueOf(token.getValue())));
															}
															if(ssl.hasNextToken() == false) {
																throw new SmartScriptParserException();
															}
														} else if(!token.getValue().equals("$}")) {
															throw new SmartScriptParserException();
														}
													if(!token.getValue().equals("$}")){
														if(ssl.hasNextToken()) {
															token = ssl.nextToken();
															if(!token.getValue().equals("$}") &&
																	!String.valueOf(token.getValue()).contains("\\r") &&
																	!String.valueOf(token.getValue()).contains("\\n") &&
																	!String.valueOf(token.getValue()).contains("\\t")) {
																
																throw new SmartScriptParserException();
															}
														} else if(!token.getValue().equals("$}")){
															throw new SmartScriptParserException();
														}
													}
												//znaci da nema stepExpressiona
												} else {
													end = ns;
													ns = null;
												}
											
											} else {
												throw new SmartScriptParserException();
											}
										} else {
											throw new SmartScriptParserException();
										}
																
								} else {
									throw new SmartScriptParserException();
								}
								
								ForLoopNode fln = new ForLoopNode(ev, se, ns, end);
								((Node)os.peek()).addChildNode(fln);
								os.push(fln);
								
							} else if(token.getValue().equals("=")) {
								Element[] elems = new Element[this.text.length()];
								int i = 0;
					
								if(ssl.hasNextToken()) {
									token = ssl.nextToken();
									
									while(!token.getValue().equals("$}")) {
										if(token.getType() == TokenType.WORD) {
											if(Character.isDigit((String.valueOf(token.getValue()).toCharArray()[0]))) throw new SmartScriptParserException();
											if(String.valueOf(token.getValue()).startsWith("@")) {
												elems[i] = new ElementFunction(String.valueOf(token.getValue()));
											} else {
												elems[i] = new ElementVariable(String.valueOf(token.getValue()));
											}
										} else if(token.getType() == TokenType.SYMBOL) {
											String str = String.valueOf(token.getValue());
											if(!str.equals("*") && !str.equals("+") && !str.equals("/") && !str.equals("-")
													&& !str.equals("^")) throw new SmartScriptParserException();
											elems[i] = new ElementOperator(String.valueOf(token.getValue()));
										} else if(token.getType() == TokenType.NUMBER) {
											if(String.valueOf(token.getValue()).contains(".")) {
												elems[i] = new ElementConstantDouble(Double.valueOf(String.valueOf(token.getValue())));
											} else {
												elems[i] = new ElementConstantInteger(Integer.valueOf(String.valueOf(token.getValue())));
											}
										} else {
											throw new SmartScriptParserException();
										}
										
										if(ssl.hasNextToken()) {
											token = ssl.nextToken();
											
										}
										++i;
									}
									
								} else {
									throw new SmartScriptParserException();
								}
								EchoNode en = new EchoNode(elems);
								((Node) os.peek()).addChildNode(en);
							} else if(String.valueOf(token.getValue()).equals("END")) {
								if(os.pop() == null) {
									throw new SmartScriptParserException();
								}
								token = ssl.nextToken();
								
							}
						}
				  } 
					  
				  } else if(token.getType() == TokenType.WORD) {
					  TextNode tn;
					  String s = String.valueOf(token.getValue());
					  
					  tn = new TextNode(s);
					  ((Node) os.peek()).addChildNode(tn);
					  
			   }
			
		   }
		} catch(SmartScriptParserException sspe) {
			throw new SmartScriptParserException();
		}
		return node;
	}

	/**
	 * This method returns document node of given text
	 * 
	 * @return DocumentNode of given text
	 */
	
	public DocumentNode getDocumentNode() {
		return this.node;
	}


}

