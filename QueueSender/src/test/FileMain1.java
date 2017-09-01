package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileMain1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		File f = new File("c:\\msgsplit\\05002.txt") ;
		
		String tmp = null ;
		String [] aaa ;
		BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(f),"utf-8") );
		File ff = new File("c:\\msgsplit\\05002-1.txt") ;
		ff.createNewFile() ;
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(ff)) ;
		StringBuilder sb = new StringBuilder() ;
		while( (tmp = br.readLine())!=null ){
			aaa = tmp.split("\t") ;
			sb = new StringBuilder() ;
			System.out.println(aaa[19]+"-->"+aaa[20]);
			
			aaa[20] = "99" ;
			
			for(int i=0;i<aaa.length;i++){
				sb.append( aaa[i] ) ;
				if(i!=aaa.length-1){
					sb.append("\t") ;
				}
			}
			sb.append("\n") ;
			
			
			bw1.write( sb.toString() ) ;
		}
		br.close() ;
		bw1.flush() ;
		bw1.close() ;
	}

}
