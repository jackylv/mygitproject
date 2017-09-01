package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String folder = "C:\\Users\\lvxg\\Desktop\\msg" ;
		
		File fold = new File(folder) ;
		File[] files = fold.listFiles() ;
		
		List<String> list1 = new ArrayList<String>() ;
		List<String> list2 = new ArrayList<String>() ;
		List<String> list3 = new ArrayList<String>() ;
		
		
		File f03013 = new File("c:\\msgsplit\\03013.txt") ;
		File f05002 = new File("c:\\msgsplit\\05002.txt") ;
		File f03012 = new File("c:\\msgsplit\\03012.txt") ;
		
		f03013.createNewFile() ;
		f05002.createNewFile() ;
		f03012.createNewFile() ;
		
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(f03013)) ;
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(f05002)) ;
		BufferedWriter bw3 = new BufferedWriter(new FileWriter(f03012)) ;
		
		String tmp = null ;
		String key = "" ;
		for(File f : files){
			System.out.println(f.getName());
			BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(f),"utf-8") );
			while( (tmp = br.readLine())!=null ){
				
				if(!"".equals(key)){
					if(tmp.startsWith("I	")){  //找到了
						
						if("03013".equals(key) && !list1.contains(tmp)){
							list1.add(tmp) ;
							bw1.write(tmp) ;
							bw1.write("\n") ;
						}else if("05002".equals(key)  && !list2.contains(tmp)){
							list2.add(tmp) ;
							bw2.write(tmp) ;
							bw2.write("\n") ;
						}else if("03012".equals(key)  && !list3.contains(tmp)){
							list3.add(tmp) ;
							bw3.write(tmp) ;
							bw3.write("\n") ;
						}
					}
				}
				
				if(tmp.indexOf("DW.03013:10.3.26.163")!=-1){
					key = "03013" ;
					continue ;
				}else if(tmp.indexOf("DW.05002:10.3.26.164")!=-1){  
					key = "05002" ;
					continue ;
				}else if(tmp.indexOf("DW.03012:10.3.37.48")!=-1){
					key = "03012" ;
					continue ;
				}else if( !tmp.startsWith("I	") ){
					key = "" ;
					continue ;
				}
			}
			
			br.close() ;
		}
		
		bw1.flush() ;
		bw1.close() ;
		bw2.flush() ;
		bw2.close() ;
		bw3.flush() ; 
		bw3.close() ;
	}

}
