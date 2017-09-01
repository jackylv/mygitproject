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

public class LogFolderSearch {
	

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
		String charset = "UTF-8" ;
		File logFiles = new File("c:\\filesplit\\0621") ;
		File srcFile = new File("C:\\Users\\lvxg\\Desktop\\数采2017.txt") ;
		BufferedReader srcBr = new BufferedReader( new InputStreamReader(new FileInputStream(srcFile),charset) );
		String srcTmp = null ;
		
		File resultFile = new File("C:\\Users\\lvxg\\Desktop\\result.txt") ;
		resultFile.createNewFile() ;
		BufferedWriter resultBw = new BufferedWriter(new FileWriter(resultFile)) ;
		
//		List<String> logContent = new ArrayList<String>() ;
		String logTmp = null ;
		BufferedReader logBr = null ;
		int i = 1 ;
		while( (srcTmp = srcBr.readLine())!=null ){
			boolean findFlag = false ;
			
			if( logFiles.isDirectory() ){
				for(File logFile:logFiles.listFiles()){
					logBr = new BufferedReader( new InputStreamReader(new FileInputStream(logFile),charset) );
					while( (logTmp = logBr.readLine())!=null ){
						if(logTmp.indexOf(srcTmp)!=-1){
							resultBw.write(logFile.getName()+"==>") ;
							resultBw.write(logTmp) ;
							resultBw.write("\n") ;
						}
					}
				}
			}
			
			if(!findFlag){
				System.out.println( i+"-->"+srcTmp );
				i++ ;
			}
		}
		
		
		
		
		
		
		resultBw.flush() ;
		resultBw.close() ;
		srcBr.close() ;
		logBr.close() ;
		System.out.println("finished...");
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
