package test;

import java.io.File;
import java.io.FilenameFilter;

import utils.CharsetUtils;

public class CharsetMain {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
//		String src = args[0] ;
//		String from = args[1] ;
//		String to = args[2] ;
//		String endfix = args[3] ;
		
		String src = "d:\\del.txt" ;
		String from = "UTF-8" ;
		String to ="GBK" ;
		final String endfix = "txt" ;
		
		CharsetUtils.convert( src , from, to ,new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith(endfix)){
					return true ;
				}
				return false;
			}
		} ) ;
	}

}
