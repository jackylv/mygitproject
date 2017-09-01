package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		fun1() ;

	}
	
	private static void fun1(){
		String str = "2017-06-22 07:16:34.203[WARN ][LogUtils.java:32] - ###utf-8:CUS.INT.CL1:10.3.37.48:BYTE-->#HEAD|V2.0|CUS_EAM_CLT|20170622181616|1|0|\n"
				+"#ITEM||U||1018667833880||201706221818||23803100||1||0||1||238007||241000||0.1||0||0.1||1||||||||||||安徽省||合肥市||巢湖市||||||中华人民共和国||238007||||||||||安徽省芜湖市弋江区||安徽省||芜湖市||弋江区||||||中华人民共和国||241000||4110101992||515199||||||||||||||20||||||||80000006380704||||340200||||0||\n"
				+"#END|3|" ;
		
		
//		Pattern pat = Pattern.compile(">.*$");
		Pattern pat = Pattern.compile("(?<=CUS\\.INT\\.CL1\\:10\\.3\\.37\\.48\\:BYTE-->)(.*[\\s\\S][\\d\\D]*)(?=\\#END)");
		Matcher m = m = pat.matcher(str); 
		if(m.find()){
			System.out.println( m.group() );
		}
		
	}

}
