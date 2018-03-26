
public class Uno {

	public static boolean check(char c) {
		char[] idUno = {'*', '+', '-', ';', '(', ')', '{', '}', '[', ']', '/', ','};
		for(int i = 0; i < idUno.length; i++) {
			if(idUno[i] == c) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean dos(char c) {
		char[] idUno = {'<', '>', '='};
		for(int i = 0; i < idUno.length; i++) {
			if(idUno[i] == c) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean tres(char c, char d) {
		char[] idUno = {'<', '>', '='};
		char[] dos = {'=', '=', '='};
		for(int i = 0; i < idUno.length; i++) {
			if((idUno[i] == c) && (dos[i] == d)) {
				return true;
			}
//			if(idUno[i] == c) {
//				return true;
//			}
		}
		return false;
	}
}
