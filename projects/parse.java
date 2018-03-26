
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class parse {

	static boolean peek = false;
	static boolean bad = false;
	static String token = "";
	static int i = 0;
	static int countConsume = 0;
	static boolean rejected = false;
	
	static List<String> grammy = null;
	
	
	
//-------------------------------Trace Methods--------------------------------------	
	
	public static void printInput(){
		ListIterator<String> parser = grammy.listIterator();
		int jjj = 0;
		while(parser.hasNext()) {
			String yy = parser.next();
			if(!(yy.equalsIgnoreCase("Error"))){
				//System.out.println("-----:  " + yy);
			}
			jjj++;
		}//end of while loop
		
	}
	public static void printOut(){
		////System.out.println();
		int jk = 0;
		String cell = "";
		for(int kk = 0; kk<grammy.size() - 1; kk++){
			cell = grammy.get(kk);
			//System.out.println(".........:  " + cell);
			jk = kk;
		}
		jk = (jk - 1);
		
	}
	
	public static void traceMethod(String method){
		////System.out.print("In ------>   " + method);
		////System.out.print("          Token:   " + token);
		////System.out.println();
		////System.out.println("Rejected: " + rejected);
	}

	//------------------------------------Non-Grammar Methods-----------------------------------
		/*
		 * How does reject work?
		 * 	if reject is false (it's false by default), then the method prints out a Reject statement
		 * 		Why? If the reject method has been called, without using System.exit, then there's no way to exit
		 * 		the numerous nested methods; 
		 * 			with the 'rejected' boolean, and a check in each method, we can completely exit
		 * 		
		 * Why peek?
		 * 		to peek, we have to 'try out' the options; but we can't reject right away - we must first 
		 * 		determine if there's another option that might work. So we do a 'soft' check, by logging the 
		 * 		reject, to be held until all other methods try
		 */
	public static void accept(){ //test file 38
		if(rejected){return;}  
		
//		if(!rejected){
//			//System.out.println("ACCEPT");
//			System.exit(0);
//		}
		System.out.println("ACCEPT");
		System.exit(0);
		rejected = true;
		return;
	}
	public static void reject(){ 
		if(rejected){return;} 
		traceMethod("reject");
		System.out.println("REJECT");
		
//		if(!rejected && !peek){
//			//System.out.println("REJECT");
//			//System.out.println("Consume count: " + countConsume + ". i :  " + i);
//			//rejected = true;
//		}else if(peek){
			//bad = true;
			//push();
			//rejected = true;
		//}
		System.exit(0);
		rejected = true;
			return;
	}
	
	public static void clear(){
		
	}
	
//------------------------------------Non-Grammar Methods-----------------------------------
	public static void setList(List<String> input){
		grammy = input;
		grammy.add("$");
	}
	
	public static void go(){
		token = "";
		rejected = false;
		countConsume = 0;
		peek = false;
		bad = false;
		//badStack.clear();
		//grammy = null;
		i = 0;
		setToken();
		program();
	}
	
	public static void setToken(){ 
		//if(rejected){return;}  
		traceMethod("setToken");
		
		if(i < grammy.size()){
			token = grammy.get(i);
		}else {
			//System.out.println("Grammy is too big.");
		}
	}
	public static void consume(){ 
		if(rejected){return;}   
		traceMethod("CONSUME");
		//consume the current token..probably just iterate
		i++;
		setToken();
		countConsume++;
	}
	public static void empty(){ 
		if(rejected){return;}   
		traceMethod("Empty");
		//what to do with empty....
	//consume();
		return;
	}

	public static void push(String newToken){//to help trace program
		////System.out.println("...........   " + newToken);
	}

	public static void replaceWith() {
		//replaces current token with incoming token
		
	}
	
//-------------------------------[Deprecated] Implementing-- a Stack--------------------------------------
//	static List<Integer> badStack = new ArrayList<Integer>();
//	
//	public static void push(){
//		badStack.add(1);
//	}
//	public static void pop(){
//		badStack.remove(badStack.size() - 1);
//		if(badStack.isEmpty()){
//			rejected = false;
//		}
//	}
//	public static boolean isBad(){
//		//it's NOT bad if it's empty;
//		return(!badStack.isEmpty());
//	}
//	

//-----------------------------------------------------------------------------------------

//------------------------------------Grammar Methods--------------------------------------	
	
	
public static void program(){
	traceMethod("program");
	declarationList();
	if(token.trim().equals("$")){
		accept();
	}
}
public static void declarationList(){
	traceMethod("declarationList");
	declaration();
	Q();
}
public static void declaration(){
	traceMethod("declaration");
	if(token.trim().equals("int") || token.trim().equals("void") || token.trim().equals("float")){
		consume();
		if(token.trim().equals("id") || token.trim().equals("num")){
			consume();
			decPrime();
		}//end of inside if statement
	}else{
		reject();
	}
}
public static void varDeclaration(){
	if(rejected){return;} 
	traceMethod("varDeclaration");
	if(token.trim().equals("void")  || token.trim().equals("int") || token.trim().equals("float")){
		consume();
		if(token.trim().equals("id") || token.trim().equals("num") || token.trim().equals("num")){
			consume();
			Y();
		}//inside if statement
	}else{
		reject();
	}
}
public static void decPrime(){
	if(rejected){return;} 
	traceMethod("decPrime");
	if(token.trim().equals(";")  || token.trim().equals("[")){
		Y();
	}else if(token.trim().equals("(")){
		consume();
		params();
		if(token.trim().equals(")")){
			consume();
			compoundStatement();
		}else{
			reject();
		}
	}else{
		reject();
	}
}
public static void params(){
	if(rejected){return;} 
	traceMethod("params");
	if(token.trim().equals("void")){
		consume();
		X();
	}else if(token.trim().equals("int") || token.trim().equals("float")){
		consume();
		if(token.trim().equals("id") || token.trim().equals("num")){
			consume();
			Z();
			pPrime();
		}
	}else{
		reject();
	}
}
public static void X(){
	if(rejected){return;} 
	traceMethod("X");
	if(token.trim().equals("id") || token.trim().equals("num")){
		consume();
		Z();
		pPrime();
	}else if(token.trim().equals(")")){
		empty();
	}else{
		reject();
	}
}
public static void pPrime(){
	if(rejected){return;} 
	traceMethod("pPrime");
	if(token.trim().equals(",")){
		consume();
		param();
		pPrime();
	}else if(token.trim().equals(")")){
		empty();
	}else{
		reject();
	}
}
public static void param(){
	if(rejected){return;}
	traceMethod("param");
	if(token.trim().equals("int") || token.trim().equals("void") || token.trim().equals("float")){
		consume();
		if(token.trim().equals("id") || token.trim().equals("num")){
			consume();
			Z();
		}else{
			reject();
		}
	}else{
		reject();
	}
}
public static void Z(){
	if(rejected){return;} 
	traceMethod("Z");
	if(token.trim().equals("[")){
		consume();
		if(token.trim().equals("]")){
			consume();
		}
	}else if(token.trim().equals(",") || token.trim().equals("int") || token.trim().equals("void") || token.trim().equals("float") ||
			token.trim().equals(")")){	
		empty();
	}else{
		reject();
	}
}

public static void statementList(){
	if(rejected){return;} 
	traceMethod("statementList");
	if(token.trim().equals(";")
            || token.trim().equals("{") || token.trim().equals("if")
            || token.trim().equals("while")|| token.trim().equals("return")
            || token.trim().equals("id") || token.trim().equals("num") || token.trim().equals("(")
            || token.trim().equals("int")|| token.trim().equals("float")){

		statement();
		statementList();
    }else if(token.trim().equals("}")){
    	empty();
    }else{
    	reject();
    }
}
public static void Y(){
	if(rejected){return;} 
	traceMethod("Y");
	if(token.trim().equals(";")){
		consume();
	}else if(token.trim().equals("[")){
		consume();
		if(token.trim().equals("num")){
			consume();
			if(token.trim().equals("]")){
				consume();
				if(token.trim().equals(";")){
					consume();
				}else{
					reject();
				}
			}else{
				reject();
			}
		}else{
			reject();
		}
	}else{
		reject();
	}
}
public static void statement(){
	if(rejected){return;} 
	traceMethod("statement");
	if(token.trim().equals("id") || token.trim().equals("num") || token.trim().equals("(")){
        expressionStatement();
    }else if(token.trim().equals("{")){
        compoundStatement();
    }else if(token.trim().equals("if")){
        selectionStatement();
    }else if(token.trim().equals("while")){
        iterationStatement();
    }else if(token.trim().equals("return")){
        returnStatement();
    }else{
    	reject();
    }
}
public static void expressionStatement(){
	if(rejected){return;} 
	traceMethod("expressionStatement");
	if(token.trim().equals("id") || token.trim().equals("num")){
		expression();
		if(token.trim().equals(";")){
			consume();
		}else{
			reject();
		}
	}else if(token.trim().equals(";")){
		consume();
	}else{
		reject();
	}
}
public static void selectionStatement(){
	if(rejected){return;} 
	traceMethod("selectionStatement");
	if(token.trim().equals("if")){
		consume();
		if(token.trim().equals("(")){
			consume();
			expression();
			if(token.trim().equals(")")){
				consume();
				statement();
				J();
			}else{
				reject();
			}
		}else{
			reject();
		}
	}else{
		reject();
	}
}

public static void compoundStatement(){
	if(rejected){return;} 
	traceMethod("compoundStatement");
	if(token.trim().equals("{")){
		consume();
		localDeclarations();
		statementList();
		if (token.trim().equals("}")){
			consume();
		}else{
			reject();
		}
	}else{
		reject();
	}
}
public static void localDeclarations(){
	if(rejected){return;} 
	traceMethod("localDeclarations");
	if(token.trim().equals("int") || token.trim().equals("void")
			|| token.trim().equals("float")){
		varDeclaration();
		localDeclarations();
	}else if(token.trim().equals(";")
            || token.trim().equals("{") || token.trim().equals("if")
            || token.trim().equals("while")|| token.trim().equals("return")
            || token.trim().equals("id") || token.trim().equals("num") || token.trim().equals("(")
            || token.trim().equals("int")|| token.trim().equals("float")
            || token.trim().equals("}")){
        //consume();
		empty();
    }else{
    	reject();
    }
}

public static void iterationStatement(){
	if(rejected){return;} 
	traceMethod("iterationStatement");
	if(token.trim().equals("while")){
		consume();
		if(token.trim().equals("(")){
			consume();
			expression();
			if(token.trim().equals(")")){
				consume();
				statement();
			}else{
				reject();
			}
		}else{
			reject();
		}
	}else{
		reject();
	}
}
public static void returnStatement(){
	if(rejected){return;} 
	traceMethod("returnStatement");
	if(token.trim().equals("return")){
		consume();
		R();
	}else{
		reject();
	}
}
public static void R(){
	if(rejected){return;} 
	traceMethod("R");
	if(token.trim().equals(";")){
		consume();
	}else{
		expression();
		if(token.trim().equals(";")){
			consume();
		}else{
			reject();
		}
	}
}
public static void expression(){
	if(rejected){return;} 
	traceMethod("expression");
	if(token.trim().equals("id") || token.trim().equals("num")){
		consume();
		V();
	}else if(token.trim().equals("(")){
		consume();
		expression();
		if(token.trim().equals(")")){
			consume();
			TT();
			EE();
			relationalOperation();
		}else{
			reject();
		}
	}else if(token.trim().equals("int") || token.trim().equals("float")){
		consume();
		TT();
		EE();
		relationalOperation();
	}else{
		reject();
	}
}
public static void V(){
	if(rejected){return;} 
	traceMethod("V");
	if(token.trim().equals("[") ||token.trim().equals("=")
            ||token.trim().equals(")") || token.trim().equals(";")
            ||token.trim().equals("!=")||token.trim().equals("==")
            ||token.trim().equals("*") ||token.trim().equals("/")
            ||token.trim().equals(",") ||token.trim().equals("]")            
            ||token.trim().equals(">=")||token.trim().equals(">")
            ||token.trim().equals("<")||token.trim().equals("<=")
            ||token.trim().equals("-")||token.trim().equals("+")){
		CC();
		BB();
	}else if(token.trim().equals("(")){
		consume();
		args();
		if(token.trim().equals(")")){
			consume();
			TT();
			EE();
			relationalOperation();
		}else{
			reject();
		}
	}else{
		reject();
	}
}
public static void BB(){
	if(rejected){return;} 
	traceMethod("BB");
	if(token.trim().equals("=")){
		////System.out.println("i - before - is:  " + i);
		consume();
		////System.out.println("i - after - is:  " + i);
		expression();
	}else if(token.trim().equals("*") || token.trim().equals("/")
            || token.trim().equals("<=") || token.trim().equals("<")
            || token.trim().equals("==") || token.trim().equals("!=")
            || token.trim().equals("+")|| token.trim().equals("-")
            || token.trim().equals(">") || token.trim().equals(">=")            
            || token.trim().equals(";") || token.trim().equals(")")
            || token.trim().equals("]") || token.trim().equals(",") ){
       TT();
       EE();
       relationalOperation();
    }else{
		reject();
	}
}
public static void CC(){
	if(rejected){return;} 
	traceMethod("CC");
	if(token.trim().equals("[")){
		consume();
		expression();
		if(token.trim().equals("]")){
			consume();
		}else{
			reject();
		}
	}else if(token.trim().equals("=")
            || token.trim().equals("*") || token.trim().equals("/")
            || token.trim().equals(",") || token.trim().equals("]")
            || token.trim().equals(")")|| token.trim().equals(";")
            || token.trim().equals("!=")  || token.trim().equals("==")
            || token.trim().equals(">=")|| token.trim().equals(">")
            || token.trim().equals("<") || token.trim().equals("<=")
            || token.trim().equals("-")|| token.trim().equals("+")
            || token.trim().equals(",") ){
		empty();
    }else{
		reject();
	}
}
public static void relationalOperation(){
	if(rejected){return;} 
	traceMethod("relationalOperation");
	if(token.trim().equals("<=")
            || token.trim().equals("<") || token.trim().equals(">")
            || token.trim().equals(">=")|| token.trim().equals("==")
            || token.trim().equals("!=")){
		consume();
		factor();
		EE();
	}else if(token.trim().equals(";")||token.trim().equals(")")
            ||token.trim().equals("]") ||token.trim().equals(",")){

		empty();
    }else{
    	reject();
    }
}
public static void EE(){
	if(rejected){return;} 
	traceMethod("EE");
	if(token.trim().equals("+") || token.trim().equals("-")){
		additionOperation();
		term();
		EE();
	}else if(token.trim().equals("<=")
            || token.trim().equals("<") || token.trim().equals(">")
            || token.trim().equals(">=")|| token.trim().equals("==")
            || token.trim().equals("!=")|| token.trim().equals(";")
            || token.trim().equals(")")|| token.trim().equals("]")
            || token.trim().equals(",")){

		empty();
    }else{
    	reject();
    }
}
public static void additionOperation(){
	if(rejected){return;} 
	traceMethod("additionalOperation");
	if(token.trim().equals("+") || token.trim().equals("-")){
		consume();
	}else{
		reject();
	}
}
public static void term(){
	if(rejected){return;} 
	traceMethod("term");
	factor();
	TT();
}
public static void TT(){
	if(rejected){return;} 
	traceMethod("TT");
	if(token.trim().equals("*") || token.trim().equals("/")){
		multiplicationOperation();
		factor();
		TT();
	}else if(token.trim().equals(";")
            || token.trim().equals(")") || token.trim().equals("]")
            || token.trim().equals(",")|| token.trim().equals("!=")
            || token.trim().equals("==")|| token.trim().equals(">=")
            || token.trim().equals(">")|| token.trim().equals("<")
            || token.trim().equals("<=")|| token.trim().equals("-")
            || token.trim().equals("+")){

		empty();
    }else{
    	reject();
    }
}
public static void multiplicationOperation(){
	if(rejected){return;} 
	traceMethod("multiplicationOperation");
	if(token.trim().equals("*") || token.trim().equals("/")){
		consume();
	}else{
		reject();
	}
}
public static void factor(){
	if(rejected){return;} 
	traceMethod("factor");
	if(token.trim().equals("(")){
		consume();
		expression();
		if(token.trim().equals(")")){
			consume();
		}
	}else if(token.trim().equals("int") || token.trim().equals("float")){
		consume();
		
	}else if(token.trim().equals("id") || token.trim().equals("num")){
		consume();
		if(token.trim().equals("(")){
			G();
		}else{
			CC();
		}
	}else{
		reject();
	}
}
public static void G(){
	if(rejected){return;} 
	traceMethod("G");
	if(token.trim().equals("(")){
		consume();
		args();
		if(token.trim().equals(")")){
			consume();
		}else{
			reject();
		}
	}else{
		reject();
	}
}
public static void args(){
	if(rejected){return;} 
	traceMethod("args");
	if(token.trim().equals(")")){
		empty();
	}else{
		argsList();
	}
}
public static void argsList(){
	if(rejected){return;} 
	traceMethod("argsList");
	expression();
	argsListPrime();
}
public static void argsListPrime(){
	if(rejected){return;} 
	traceMethod("argsListPrime");
	if(token.trim().equals(",")){
		consume();
		expression();
		argsListPrime();
	}else if(token.trim().equals(")")){
		empty();
	}else{
		reject();
	}
}
public static void J(){
	if(rejected){return;} 
	traceMethod("J");
	if(token.trim().equals("else")){
		consume();
		statement();
	}else if(token.trim().equals(";")
            || token.trim().equals("{") || token.trim().equals("if")
            || token.trim().equals("while")|| token.trim().equals("return")
            || token.trim().equals("id") || token.trim().equals("num") || token.trim().equals("(")
            || token.trim().equals("int") || token.trim().equals("float")
            || token.trim().equals("}")){

		empty();
    }else{
    	reject();
    }
}
public static void Q(){
	if(rejected){return;} 
	traceMethod("Q");
	if(token.trim().equals("int") || token.trim().equals("void")
			|| token.trim().equals("float")){
		declaration();
		Q();
	}else if(token.trim().equals("$")){
		empty();
		//-----------------------------------------------------------------------accept Here??
	}
}

		
//		public static void consume(){ 
//			if(rejected){return;}   traceMethod("CONSUME");
//		//consume the current token..probably just iterate
//			i++;
//			setToken();
//		////System.out.println();
//			countConsume++;
//		}
//		
//		public static void traceMethod(String method){
//			////System.out.print("In ------>   " + method);
//			////System.out.println("              Token:   " + token);
//		}
		

	
	
	

}//end of class


