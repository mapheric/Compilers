
public class What {

	public static boolean check(String s) {
		s = s.replaceAll("\\s","");
		String[] arr = {"int", "return", "else", "if", "void", "while"};
		for(int i = 0; i<arr.length; i++) {
			//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! String: " + s);
			if(arr[i].equals(s)) {
				//System.out.println("---------------------------------------- String: " + s);
				return true;
			}
		}
		return false;
	}
}
