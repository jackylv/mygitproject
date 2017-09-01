package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONArray;

public class JsonMain {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws Exception {
		File f = new File("c:\\Users\\lvxg\\Desktop\\peo_pco_item_receive_receivelist.txt") ;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8")) ;
		
		StringBuffer sb = new StringBuffer() ;
		String tmp = null ;
		while( (tmp=br.readLine())!=null ){
			sb.append(tmp) ;
		}
		
		JSONArray ja = JSONArray.parseArray(sb.toString()) ;
		System.out.println(ja.getJSONObject(0).toJSONString());
		
	}

}
