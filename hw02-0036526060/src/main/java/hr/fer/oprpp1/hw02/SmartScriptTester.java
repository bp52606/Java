package hr.fer.oprpp1.hw02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

public class SmartScriptTester {

	public static void main(String[] args) {
		
		String document = "";
		try {
			document = loader("src\\test\\resources\\mojExtra\\primjer6.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SmartScriptParser sst = new SmartScriptParser(document);
		DocumentNode dn = new DocumentNode();
		dn = sst.getDocumentNode();
		System.out.println(dn.numberOfChildren());
		int i = 0;
		System.out.println("Document node");
		while(i<dn.numberOfChildren()) {
			System.out.println("-" + dn.getChild(i).toString());
			for(int k=0;k<dn.getChild(i).numberOfChildren();++k) {
				System.out.println("----" +((dn.getChild(i)).getChild(k)).toString());
			}
			++i;
		}
		
		System.out.println(dn.toString());
		SmartScriptParser ssp = new SmartScriptParser(dn.toString());
		String doc = ssp.getDocumentNode().toString();
		System.out.println(doc.toString());
		
	}
	
	private static String loader(String p) throws IOException {
		String docBody = new String(
				 Files.readAllBytes(Paths.get(p))
		);
		return docBody;
	}


}
