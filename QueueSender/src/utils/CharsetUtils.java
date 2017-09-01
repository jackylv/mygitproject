package utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class CharsetUtils {

	public static void main(String[] args) throws Exception {
		convert("/Users/lvxg/Downloads/aaa.log",
				"gbk", "utf-8", new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith("log");
					}
				});
	}
	
	/**
	 * 把指定文件或目录转换成指定的编码
	 * 
	 * @param fileName
	 *            要转换的文件
	 * @param fromCharsetName
	 *            源文件的编码
	 * @param toCharsetName
	 *            要转换的编码
	 * @throws Exception
	 */
	public static String convertContent(String fileName, String fromCharsetName,String toCharsetName) throws Exception {
		return convertContent(new File(fileName), fromCharsetName, toCharsetName);
	}
	
	
	/**
	 * 把指定文件或目录转换成指定的编码
	 * 
	 * @param file
	 *            要转换的文件或目录
	 * @param fromCharsetName
	 *            源文件的编码
	 * @param toCharsetName
	 *            要转换的编码
	 * @param filter
	 *            文件名过滤器
	 * @throws Exception
	 */
	public static String convertContent(File file, String fromCharsetName,String toCharsetName) throws Exception {
		if (file.isDirectory()) {
			return "ERROR:FILE IS DIRECTORY!" ;
		} else {
			String target = "" ;
			String fileContent = getFileContentFromCharset(file,fromCharsetName);

			target = new String(fileContent.getBytes(fromCharsetName),toCharsetName) ;
			
			return target ;
		}
	}

	/**
	 * 把指定文件或目录转换成指定的编码
	 * 
	 * @param fileName
	 *            要转换的文件
	 * @param fromCharsetName
	 *            源文件的编码
	 * @param toCharsetName
	 *            要转换的编码
	 * @throws Exception
	 */
	public static void convert(String fileName, String fromCharsetName,
			String toCharsetName) throws Exception {
		convert(new File(fileName), fromCharsetName, toCharsetName, null);
	}

	/**
	 * 把指定文件或目录转换成指定的编码
	 * 
	 * @param file
	 *            要转换的文件或目录
	 * @param fromCharsetName
	 *            源文件的编码
	 * @param toCharsetName
	 *            要转换的编码
	 * @throws Exception
	 */
	public static void convert(File file, String fromCharsetName,
			String toCharsetName) throws Exception {
		convert(file, fromCharsetName, toCharsetName, null);
	}

	/**
	 * 把指定文件或目录转换成指定的编码
	 * 
	 * @param file
	 *            要转换的文件或目录
	 * @param fromCharsetName
	 *            源文件的编码
	 * @param toCharsetName
	 *            要转换的编码
	 * @param filter
	 *            文件名过滤器
	 * @throws Exception
	 */
	public static void convert(String fileName, String fromCharsetName,
			String toCharsetName, FilenameFilter filter) throws Exception {
		convert(new File(fileName), fromCharsetName, toCharsetName, filter);
	}

	/**
	 * 把指定文件或目录转换成指定的编码
	 * 
	 * @param file
	 *            要转换的文件或目录
	 * @param fromCharsetName
	 *            源文件的编码
	 * @param toCharsetName
	 *            要转换的编码
	 * @param filter
	 *            文件名过滤器
	 * @throws Exception
	 */
	public static void convert(File file, String fromCharsetName,
			String toCharsetName, FilenameFilter filter) throws Exception {
		if (file.isDirectory()) {
			File[] fileList = null;
			if (filter == null) {
				fileList = file.listFiles();
			} else {
				fileList = file.listFiles(filter);
			}
			for (File f : fileList) {
				convert(f, fromCharsetName, toCharsetName, filter);
			}
		} else {
			if (filter == null
					|| filter.accept(file.getParentFile(), file.getName())) {
				String fileContent = getFileContentFromCharset(file,
						fromCharsetName);
				saveFile2Charset(file, toCharsetName, fileContent);
			}
		}
	}

	/**
	 * 以指定编码方式读取文件，返回文件内容
	 *
	 * @param file
	 *            要转换的文件
	 * @param fromCharsetName
	 *            源文件的编码
	 * @return
	 * @throws Exception
	 */
	public static String getFileContentFromCharset(File file,
			String fromCharsetName) throws Exception {
		if (!Charset.isSupported(fromCharsetName)) {
			throw new UnsupportedCharsetException(fromCharsetName);
		}
		InputStream inputStream = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(inputStream,fromCharsetName);
		char[] chs = new char[(int) file.length()];
		reader.read(chs);
		String str = new String(chs).trim();
//		System.out.println("-->"+str);
		reader.close();
		return str;
	}

	/**
	 * 以指定编码方式写文本文件，存在会覆盖
	 * 
	 * @param file
	 *            要写入的文件
	 * @param toCharsetName
	 *            要转换的编码
	 * @param content
	 *            文件内容
	 * @throws Exception
	 */
	public static void saveFile2Charset(File file, String toCharsetName,
			String content) throws Exception {
		if (!Charset.isSupported(toCharsetName)) {
			throw new UnsupportedCharsetException(toCharsetName);
		}
		
		String ex = getExtensionName(file.getAbsolutePath()) ;
		
		if("".equals(ex) || ex.length() == file.getAbsolutePath().length()){
			return  ;
		}
		String name = getFileNameNoEx(file.getAbsolutePath()) ;
		File target = new File(name + "-" + toCharsetName + "." +  ex) ;
		target.createNewFile() ;
		OutputStream outputStream = new FileOutputStream(target);
		OutputStreamWriter outWrite = new OutputStreamWriter(outputStream,toCharsetName);
		outWrite.write(content);
		outWrite.close();
	}
	
	
	/* 
	 * Java文件操作 获取文件扩展名 
	 * 
	 *  Created on: 2011-8-2 
	 *      Author: blueeagle 
	 */  
	    public static String getExtensionName(String filename) {   
	        if ((filename != null) && (filename.length() > 0)) {   
	            int dot = filename.lastIndexOf('.');   
	            if ((dot >-1) && (dot < (filename.length() - 1))) {   
	                return filename.substring(dot + 1);   
	            }   
	        }   
	        return filename;   
	    }   
	/* 
	 * Java文件操作 获取不带扩展名的文件名 
	 * 
	 *  Created on: 2011-8-2 
	 *      Author: blueeagle 
	 */  
	    public static String getFileNameNoEx(String filename) {   
	        if ((filename != null) && (filename.length() > 0)) {   
	            int dot = filename.lastIndexOf('.');   
	            if ((dot >-1) && (dot < (filename.length()))) {   
	                return filename.substring(0, dot);   
	            }   
	        }   
	        return filename;   
	    }   
}