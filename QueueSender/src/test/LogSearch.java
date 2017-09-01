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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class LogSearch {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		
		File dir = new File(".") ;
//		System.out.println(dir.getAbsolutePath());
		String srcName = "" ;
		String logName = "" ;
		String tmpName = "" ;
		for(File tmp : dir.listFiles()){
			tmpName = tmp.getName() ;
			
			if("searchresult.txt".equalsIgnoreCase(tmpName) || "说明.txt".equalsIgnoreCase(tmpName)){
				continue ;
			}
			int idx1 = tmpName.lastIndexOf(".") ;
//			System.out.println(idx1+"-->"+tmpName.substring(idx1+1));
			
			if(tmpName.indexOf("ITEM")!=-1 && "log".equalsIgnoreCase(tmpName.substring(idx1+1) )){
				logName = tmpName ;
			}else if( "txt".equalsIgnoreCase(tmpName.substring(idx1+1) ) ){
				srcName = tmpName ;
			}
			
//			System.out.println(tmp.getName());
		}
		
		System.out.println( srcName + "-->" + logName );
		
		
//		srcName = "c:\\Users\\lvxg\\Desktop\\bbb.txt" ;
//		logName = "g:\\test\\JDPT.EMSCO.ITEM.2017-06-28.log" ;
		
		String charset = "UTF-8" ;
		File logFile = new File(logName) ;  //日志文件
//		File logFile = new File("C:\\Users\\lvxg\\Desktop\\result111.txt") ;
		File srcFile = new File(srcName) ;  //寄递给的全量收寄邮件号
		
		File resultFile = new File("searchresult.txt") ;
		resultFile.createNewFile() ;
		BufferedWriter resultBw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(resultFile), charset) );
		
		
		List<String> logContent = new ArrayList<String>() ;
		String logTmp = null ;
		BufferedReader logBr = new BufferedReader( new InputStreamReader(new FileInputStream(logFile),charset) );
		while( (logTmp = logBr.readLine())!=null ){
			logContent.add(logTmp) ;
		}
		
		BufferedReader srcBr = new BufferedReader( new InputStreamReader(new FileInputStream(srcFile),charset) );
		String srcTmp = null ;
		int i = 1 ;
		int count = 0 ;
		int errorCount = 0 ;
		Pattern pat =  Pattern.compile("(?<=\\|)(.+?)(?=\\|)");
		Matcher m ;
		while( (srcTmp = srcBr.readLine())!=null ){
			boolean findFlag = false ;
			
			m = pat.matcher(srcTmp) ;
			if(m.find()){
				srcTmp = StringUtils.trim( m.group(1) ) ;
			}
			srcTmp = StringUtils.trim( srcTmp ) ;
			if( !hasDigit(srcTmp)){
				continue;
			}
			count ++ ;
			for(String tmp:logContent){
				if(tmp.indexOf(srcTmp)!=-1){
					findFlag = true ;
					break ;
				}
			}
			
			if(!findFlag){
				errorCount ++ ;
				resultBw.write(srcTmp + "-->" + i) ;
				resultBw.write("\r\n") ;
				i++ ;
			}
		}
		resultBw.flush() ;
		resultBw.close() ;
		srcBr.close() ;
		logBr.close() ;
		System.out.println("邮件共"+count+"个,异常邮件共"+errorCount+"个.");
		System.out.println("finished...");
	}
	
	private static Pattern p = Pattern.compile(".*\\d+.*");
	public static boolean hasDigit(String content) {
		
		if(content==null || "".equals(content)){
			return false ;
		}
	    boolean flag = false;
	    
	    Matcher m = p.matcher(content);
	    if (m.matches()) {
	        flag = true;
	    }
	    return flag;
	}
	

}
