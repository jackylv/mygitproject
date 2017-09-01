package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class TestWork {

	/**
	 * @param args
	 * @throws Exception 
	 */
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd") ;
	
	private static List<Calendar> workDayList  ;
	private static List<Calendar> holidayList  ;
	
	
	public static void main(String[] args) throws Exception {
		
		//总数
		int total = 2641 ;
		
		String startStr = "2017-01-09" ;
		Calendar cal = getCalendar( startStr ) ;
		workDayList = getDataList("work.txt") ;
		holidayList = getDataList("holiday.txt") ;
		
		List<Calendar> workCals = new ArrayList<Calendar>() ;
		
		Map<String,List<String>> userMap = new LinkedHashMap<String, List<String>>() ;
		for(int i=0;i<80;i++){
			userMap.put( String.valueOf(i), new ArrayList<String>())	;
		}

		int current = 0  ;
		int min = 60 ;
		
		int num = 0 ;
		
		List<Integer> userLimit = null ;
		while(current<total){
			userLimit = new ArrayList<Integer>() ;
			//每天得多少人
			num = getRandomWithMin(min, userMap.size()) ;
			
			if(current + num > total){
				num = total - current ;
			}
			
			int count = 0 ;
			while(count<num){
				int ran = getRandom(num) ;
				while( userLimit.contains(new Integer(ran)) ){
					ran = getRandom(userMap.size()) ; 
				}
				userLimit.add(ran) ;
				//获取人 一个人只能出现一次
				System.out.println("ran:"+ran) ;
				List<String> userDayList = userMap.get( String.valueOf(ran) ) ;
				userDayList.add( format.format(cal.getTime()) ) ;
				
				count ++ ;
			}
			
			workCals.add(cal) ;
			cal = getNextWorkDay(cal) ;
			current += num ;
		}
		
		int a =  cal.get(Calendar.DAY_OF_YEAR) ;
		int b =  getCalendar( startStr ).get(Calendar.DAY_OF_YEAR)  ;
		
		
		Set<String> keySet = userMap.keySet() ;
		
		StringBuffer sb = new StringBuffer() ;
		for(String key:keySet){
			List<String> tmpList = userMap.get(key) ;
			
			sb.setLength(0) ;
			sb.append(key).append("|").append(tmpList.size()).append("|") ;
			for(String tmp : tmpList){
				sb.append(tmp).append(",") ;
			}
			
			System.out.println( sb.toString() );
		}
		
		System.out.println(format.format( cal.getTime() ) + "-->" + workCals.size() ); 
	}
	
	private static int getRandom(int max){
		return new Random().nextInt(max) ;
	}
	
	private static int getRandomWithMin(int min,int max){
		Random ran = new Random() ;
		int num = 0 ;
		
		while(num<min){
			num = ran.nextInt(max) ;
		}
		return  num;
	}
	
	private static List<Calendar> getDataList(String fileName ) throws Exception{
		List<Calendar> list = new ArrayList<Calendar>() ;
		InputStream  is = TestWork.class.getClassLoader().getResourceAsStream("data/"+fileName) ;
		
		BufferedReader workBr = new BufferedReader( new InputStreamReader(is) );
		String tmp = null ;
		
		while( (tmp = workBr.readLine())!=null ){
			list.add(getCalendar(tmp)) ;
		}
		return list ;
	}
	
	private static Calendar getNextWorkDay(Calendar start){
		
		start.add(Calendar.DATE, 1) ;
		
		while( !isWorkDay(start, workDayList, holidayList)){
			start.add(Calendar.DATE, 1) ;
		}
		return start ;
	}
	
	/** 
	 * 判断是否是周末 
	 * @return 
	 */  
	private static boolean isWeekend(Calendar cal){  
	    int week=cal.get(Calendar.DAY_OF_WEEK)-1;  
	    if(week ==6 || week==0){//0代表周日，6代表周六  
	        return true;  
	    }  
	    return false;  
	}  

	/** 
	 * 判断是否是工作日
	 * @return 
	 */  
	private static boolean isWorkDay(Calendar cal,List<Calendar> workDayList,List<Calendar> holidayList){  

		//如果是节假日  直接返回false
		for(Calendar hol:holidayList){
			if(cal.compareTo(hol)==0){
				return false ;
			}
		}
		
		//如果是特殊工作日  直接返回true
		for(Calendar work:workDayList){
			if(cal.compareTo(work)==0){
				return true ;
			}
		}
		
		//判断周末
	    int week=cal.get(Calendar.DAY_OF_WEEK)-1;  
	    if(week ==6 || week==0){//0代表周日，6代表周六  
	        return false;  
	    }  
	    return true;  
	}  
	
	private static Calendar getCalendar(String dateStr) throws Exception{
		Calendar cal = Calendar.getInstance( ) ;
		cal.setTime( format.parse(dateStr) ) ;
		return cal ;
	}
	

}
