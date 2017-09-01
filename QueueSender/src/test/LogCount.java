package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class LogCount {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String charset = "UTF-8" ;
//		File logFile = new File("g:\\filesplit\\0622\\JDPT.EMSCO.ITEM.2017-06-22.log") ;
		File logFile = new File("g:\\filesplit\\0622\\msg.2017-06-22.log") ;
//		File srcFile = new File("C:\\Users\\lvxg\\Desktop\\数采2017.txt") ;
		
		File resultFile = new File("C:\\Users\\lvxg\\Desktop\\gnresult.txt") ;
		resultFile.createNewFile() ;
		BufferedWriter resultBw = new BufferedWriter(new FileWriter(resultFile)) ;
		
//		Pattern pat = Pattern.compile("(?<=-->)(.*)");
		Pattern pat = Pattern.compile("(?<=CUS\\.INT\\.CL1\\:10\\.3\\.37\\.48\\:BYTE-->)(.*[\\s\\S][\\d\\D]*)(?=\\#END)");  //国内收寄
//		Pattern pat = Pattern.compile("(?<=CUS\\.INT\\.CLT\\:10\\.3\\.37\\.48\\:BYTE-->)(.*[\\s\\S][\\d\\D]*)(?=\\#END)");  //国际收寄
		Matcher m = null ;
		
		List<String> logContent = new ArrayList<String>() ;
		String logTmp = null ;
		BufferedReader logBr = new BufferedReader( new InputStreamReader(new FileInputStream(logFile),charset) );
		String tmp = null ;
		int repeat = 0 ;
		String content = "" ;
		boolean findfinished = false ;
		while( (logTmp = logBr.readLine())!=null ){
			if(logTmp.indexOf("CUS.INT.CL1:10.3.37.48:BYTE")!=-1 || logTmp.startsWith("#ITEM") || logTmp.startsWith("#END") || logTmp.startsWith("#FEE") || logTmp.startsWith("#A")){//国内收寄
//			if(logTmp.indexOf("CUS.INT.CLT:10.3.37.48:BYTE")!=-1 || logTmp.startsWith("#ITEM") || logTmp.startsWith("#END") || logTmp.startsWith("#FEE") || logTmp.startsWith("#A")){//国际收寄
				content += logTmp+"\n" ;
				findfinished = false ;
				continue ;
			}else{
				findfinished = true ;
			}
			
			if( findfinished && StringUtils.isBlank(content)){
				continue ;
			}
			m = pat.matcher(content); 
			if(m.find()){
				tmp = m.group() ;
				resultBw.write(tmp) ;
				if(!logContent.contains(tmp)){
					logContent.add( tmp );
				}else{
					repeat ++ ;
					System.out.println(tmp);
				}
			}
			content = "" ;
		}
		
		resultBw.flush() ;
		resultBw.close() ;
		logBr.close() ;
		System.out.println("repeat:"+repeat);
		System.out.println( "datacount:"+logContent.size());
		System.out.println("finished...");
	}

}
