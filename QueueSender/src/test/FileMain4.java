package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

public class FileMain4 {
	

	private static String[] endWiths = new String[]{"AE","AM","AN","AO","AR","AT","AU","AW","AZ",
		"BA","BD","BE","BF","BG","BH","BI","BJ","BM","BO","BR","BS","BW","BY",
		"CA","CD","CG","CH","CI","CL","CN","CN","CO","CR","CU","CV","CY","CZ",
		"DJ","DK","DO",
		"EC","EE","EG","ER","ES","ET",
		"FI","FJ","FR",
		"GA","GB","GH","GN","GQ","GR","GY",
		"HN","HR","HU",
		"ID","IE","IL","IN","IR","IT",
		"JO","JP",
		"KE","KG","KH","KP","KR","KW","KY","KZ",
		"LA","LK","LR","LT","LU","LV",
		"MA","MD","MG","MK","ML","MN","MR","MT","MU","MV","MX","MY","MZ",
		"NE","NG","NO","NP","NR","NZ","OM","PA","PE","PG","PH","PK","PL","PT","PY",
		"QA",
		"RO","RU","RW","SA","SD","SE","SG","SK","SL","SN","ST","SY",
		"TD","TG","TH","TM","TN","TR","TW","TZ",
		"UA","UG","US","UZ",
		"VE","VN","VU",
		"YE",
		"ZA","ZM","ZW","HK","MO","NL","WS"};
	

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		File f = new File("C:\\Users\\lvxg\\Desktop\\0615\\aaa.txt") ;
		f.createNewFile() ;
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(f)) ;
		
		
		List<String> mailList = new ArrayList<String>() ;
		File fff = new File("C:\\sjmail.txt") ;
		String tmp1 = null ;
		BufferedReader br1 = new BufferedReader( new InputStreamReader(new FileInputStream(fff),"utf-8") );
		while( (tmp1 = br1.readLine())!=null ){
			mailList.add(tmp1) ;
		}
		br1.close() ;
		
		List<String> list = new ArrayList<String>() ;
		File ff = new File("C:\\Users\\lvxg\\Desktop\\0615\\JDPT.EMSCO.ITEM.2017-06-15.log") ;
		String tmp = null ;
		BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(ff),"utf-8") );
		StringBuilder sb = new StringBuilder() ;
		while( (tmp = br.readLine())!=null ){
//			list.add(tmp) ;
			for(String mail:mailList){
				if(tmp.indexOf(mail)!=-1){
					bw1.write(tmp) ;
					bw1.write("\n") ;
				}
			}
		}
		bw1.flush() ;
		bw1.close() ;
		br.close() ;
		
		
			
			
			
	}
	

	public static boolean verifyInterMail(String mailNum) {
		boolean interFlag = false;//国际，默认false
		if (mailNum.startsWith("E")) {
			for (int i = 0; i < endWiths.length; i++) {
				String endStr = endWiths[i];
				if(mailNum.endsWith(endStr)){
					interFlag = true;
					break;
				}
			}
		}
		return interFlag;
	}
	

}
