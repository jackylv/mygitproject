package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class LogSearch1 {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String charset = "UTF-8" ;
		File logFile = new File("C:\\Users\\lvxg\\Desktop\\\\JDPT.EMSCO.ITEM.2017-07-04.log") ;  //日志文件
//		File logFile = new File("C:\\Users\\lvxg\\Desktop\\result111.txt") ;
		File srcFile = new File("C:\\Users\\lvxg\\Desktop\\74.txt") ;  //寄递给的全量收寄邮件号
		
		File resultFile = new File("C:\\Users\\lvxg\\Desktop\\searchresult.txt") ;
		resultFile.createNewFile() ;
		BufferedWriter resultBw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(resultFile), "gbk") );
		
		
		List<String> logContent = new ArrayList<String>() ;
		String logTmp = null ;
		BufferedReader logBr = new BufferedReader( new InputStreamReader(new FileInputStream(logFile),charset) );
		while( (logTmp = logBr.readLine())!=null ){
			logContent.add(logTmp) ;
		}
		
		BufferedReader srcBr = new BufferedReader( new InputStreamReader(new FileInputStream(srcFile),charset) );
		String srcTmp = null ;
		int i = 1 ;
		while( (srcTmp = srcBr.readLine())!=null ){
			boolean findFlag = false ;
			srcTmp = StringUtils.trim(srcTmp) ;
			for(String tmp:logContent){
				if(tmp.indexOf(srcTmp)!=-1){
					findFlag = true ;
					resultBw.write(tmp) ;
					resultBw.write("\n") ;
					break ;
				}
			}
			
			if(!findFlag){
				System.out.println( srcTmp + "-->" + i);
				i++ ;
			}
		}
		resultBw.flush() ;
		resultBw.close() ;
		srcBr.close() ;
		logBr.close() ;
		System.out.println("finished...");
	}
	

}
