package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "| 1003917926524 |          27129 | 23803100    |" ;
		
		Pattern pat =  Pattern.compile("(?<=\\|)(.+?)(?=\\|)");
		Matcher m = pat.matcher(str) ;
		
		if(m.find()){
			System.out.println(m.group(1));
		}

	}

}
