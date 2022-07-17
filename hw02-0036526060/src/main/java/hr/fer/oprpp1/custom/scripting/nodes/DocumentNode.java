package hr.fer.oprpp1.custom.scripting.nodes;
/**
 * Class represents an implementation of a node representing an entire document. It inherits from Node class
 * 
 * @author 38591
 *
 */
public class DocumentNode extends Node {
	
	/**
	 * Returns text very similar to one that is given
	 * 
	 * @return String value of this DocumentNode 
	 */
	
	public String toString() {
		int i = 0;
		String sb = "";
		while(i<this.numberOfChildren()) {
			String s = this.getChild(i).toString();
			//dodaj sadrzaj EchoNodea
			if(this.getChild(i) instanceof EchoNode) {
				String keep = s;
				char[] ch = keep.toCharArray();
				int pam = 0;
				for(int k=0;k<ch.length;++k) {
					if(ch[k]=='\\' || ch[k] == '"' ) {

									s = keep.substring(0,k+pam);
									s +=String.valueOf("\\");
									s +=keep.substring(k+pam,keep.length());
									++pam;
									
							keep = s;
					}
				}
				sb +="{$=" + s + "$}";
			//dodaj sadrzaj ForLoopNodea
			} else if(this.getChild(i) instanceof ForLoopNode) {
				sb +="{$ FOR " + s + "$}";
				sb = this.countKids(this.getChild(i), sb);
				sb += "{$END$}";		
			//u slucaju da se neki simbol tretirao kao slovo
			} else if(this.getChild(i) instanceof TextNode) {
				s = ocisti(s);
				
				String keep = s;
				char[] ch = keep.toCharArray();
				int pam = 0;
				for(int k=0;k<ch.length;++k) {
					if((Character.isDigit(ch[k]) || ch[k] == '{' || ch[k]=='\\' || ch[k] == '"' || !Character.isLetter(ch[k])) && ch[k]!= ' ' && ch[k]!='\n'
							&& ch[k]!='\r' && ch[k] != '\t') {
							if((ch[k] =='{' && k+1<ch.length && ch[k+1] == '$') || ch[k] =='\\' || Character.isDigit(ch[k])
									|| ch[k] =='"') {
									if(ch[k] == '$' && k+1<ch.length && ch[k+1] =='}') continue;
									s = keep.substring(0,k+pam);
									s +=String.valueOf("\\");
									s +=keep.substring(k+pam,keep.length());
									++pam;
									
							}
							keep = s;
					}
				}
				sb +=s;
			//u slucaju da se radi o obicnom tekstu bez posebnosti
				
			} else {
				sb +=s;
			}

			// u tekstu ne smiju biti vidiljivi escapeovi, tabovi i spaceovi
			sb = ocisti(sb);
			++i;		
		}

		return sb;
	}

	/**
	 * Finds children nodes of a given node
	 * 
	 * @param child given node
	 * @param sb StringBuilder to which this method appends String representation of given node's children
	 * @return String representation of given node's children
	 */
	
	public String countKids(Node child, String sb) {
		for(int k=0;k<child.numberOfChildren();++k) {
			String str = child.getChild(k).toString();
			//System.out.println(str);
			if(child.getChild(k) instanceof EchoNode) {
				String keep = str;
				char[] ch = keep.toCharArray();
				int pam = 0;
				for(int j=0;j<ch.length;++j) {
					if(ch[j]=='\\' || ch[j] == '"' ) {

									str = keep.substring(0,j+pam);
									str +=String.valueOf("\\");
									str +=keep.substring(j+pam,keep.length());
									++pam;
									
							keep = str;
					}
				}
				sb +="{$=" + str + "$}";
			
			} else if(child.getChild(k) instanceof ForLoopNode) {
				sb +="{$ FOR " + str + "$}";
				sb = this.countKids(child.getChild(k), sb);
				sb +="{$END$}";
			
			} else if(child instanceof TextNode) {
				str = ocisti(str);
				
				String keep = str;
				char[] ch = keep.toCharArray();
				int pam = 0;
				for(int j=0;j<ch.length;++j) {
					if((Character.isDigit(ch[j]) || ch[j] == '{' || ch[j]=='\\' || ch[j] == '"' || !Character.isLetter(ch[j])) && ch[j]!= ' ' && ch[j]!='\n'
							&& ch[j]!='\r' && ch[j] != '\t') {
							if((ch[j] =='{' && j+1<ch.length && ch[j+1] == '$') || ch[j] =='\\' || Character.isDigit(ch[j])
									|| ch[j] =='"') {
								if(ch[j] == '$' && j+1<ch.length && ch[j+1] =='}') continue;
									str= keep.substring(0,j+pam);
									str +=String.valueOf("\\");
									str +=keep.substring(j+pam,keep.length());
									++pam;
									
							}
							keep = str;
					}
				}
				sb +=str;
			} else {
				sb+=str;
			}
		}
		return sb;
	}
	
	/**
	 * Turns escapes in a String to String elements, adds them to given String and returns altered String
	 * 
	 * @param sb given String 
	 * @return cleaned String
	 */
	
	public String ocisti(String sb) {
		while(sb.contains("\\r\\n")) {
			String k = sb;
			sb = sb.substring(0, k.indexOf("\\r\\n"));
			sb += "\r\n";
			sb += k.substring(k.indexOf("\\r\\n")+4, k.length());
		
		} 
		while(sb.contains("\\n")) {
			String k = sb;
			sb = sb.substring(0, k.indexOf("\\n"));
			sb +=String.valueOf('\n');
			sb += k.substring(k.indexOf("\\n")+2, k.length());
		}
		while(sb.contains("\\r")) {
			String k = sb;
			sb = sb.substring(0, k.indexOf("\\r"));
			sb +="\r";
			sb += k.substring(k.indexOf("\\r")+2, k.length());
		}
		while(sb.contains("\\t")) {
			String k = sb;
			sb = sb.substring(0, k.indexOf("\\t"));
			sb +="\t";
			sb += k.substring(k.indexOf("\\r")+2, k.length());
			
		}
		return sb;
	}
	
	/**
	 * Returns true if nodes have equal String value, false otherwise
	 * 
	 * @param dn other DocumentNode
	 * @return true if nodes are equal, false otherwise
	 */
	
	public boolean equals(DocumentNode dn) {
		if(dn.toString().equals(this.toString())) return true;
		return false;
	}
}
