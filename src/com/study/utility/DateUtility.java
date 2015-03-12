package com.study.utility;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 日期工具类
 * @author 沈志辉
 */
public class DateUtility {

	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getCurDate() {
	
		Calendar now = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		now.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		calendar.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
		
		return calendar.getTime();
	}
	
	/**
	 * 获取两个日期的相隔天数
	 * @param firstDate 起始时间
	 * @param lastDate 结束时间
	 * @return 天数
	 */
	public static Long getDays(Date firstDate, Date lastDate) {
		
		Calendar   t1   =   Calendar.getInstance();   
		Calendar   t2   =   Calendar.getInstance();   
		t1.setTime(firstDate);   
		t2.setTime(lastDate);   
		return (t2.getTimeInMillis()-t1.getTimeInMillis())/(1000*60*60*24);
	}
	
	/**
	 * 获取两个日期的相隔秒数
	 * @param firstDate 起始时间
	 * @param lastDate 结束时间
	 * @return 秒数
	 */
	public static Long getSeconds(Date firstDate, Date lastDate) {
		
		return (lastDate.getTime() - firstDate.getTime())/1000;
	}

	/**
	 * 获取两个日期的相隔分钟
	 * @param firstDate 起始时间
	 * @param lastDate 结束时间
	 * @return 分钟
	 */
	public static Long getMinutes(Date firstDate, Date lastDate) {
		
		return getSeconds(firstDate, lastDate)/60 + 1;
	}
	
	/**
	 * 获取两个日期的相隔小时
	 * @param firstDate 起始时间
	 * @param lastDate 结束时间
	 * @return 小时
	 */
	public static Long getHours(Date firstDate, Date lastDate) {
		
		return getMinutes(firstDate, lastDate)/60;
	}
	
	/**
	 * @param date 日期
	 * @param hourOfDay 小时
	 * @param minute 分钟
	 * @return 日期型
	 */
	public static Date stringToDate(String date,Integer hourOfDay,Integer minute){

		//初始化日期帮助类
		Calendar calendar = Calendar.getInstance();
	
		//将年月日分别放入数组
		String[] dateArray = date.split("-");
	
		//得到传过来的月份
		Integer initMonth = new Integer(dateArray[1]);
		
		//如果月份不是0，月份减1，因为Calendar中set的月份是从0开始的
		if (initMonth > 0) {
			
			initMonth = initMonth -1;
		}
		
		//设置年、月、日、时、分
		calendar.set(new Integer(dateArray[0]), initMonth, new Integer(dateArray[2]), hourOfDay, minute);
		
		//返回data类型
		return calendar.getTime();
		
		
	}
	
	/**
	 * @param date日期
	 * @param time时间
	 * @return日期型转为字符串
	 */
	public static String formateDateToString(Date date){
		
		String d = new SimpleDateFormat("yyyy-MM-dd").format(date);
	
		return d;
	}
	
	/**
	 * @param date日期
	 * @param time时间
	 * @return日期型转为字符串
	 */
	public static String formateDateToShortChinesDate(Date date){
		
		String d = new SimpleDateFormat("MM月dd日").format(date);
	
		return d;
	}	
	
	/**
	 * 得到当前年度
	 * @return
	 */
	public static Integer getCurYear() {
		
		GregorianCalendar g = new GregorianCalendar(); 
		Integer year=(int)g.get(Calendar.YEAR); 
		return year; 
	}
	
	/**
	 * 得到当前小时
	 * @return
	 */
	public static Integer getHour(){
		
		GregorianCalendar g = new GregorianCalendar(); 
		Integer hour=(int)g.get(Calendar.HOUR_OF_DAY); 
		return hour; 
	}
	
	/**
	 * 得到当前分钟
	 * @return
	 */
	public static Integer getMinute(){
		
		GregorianCalendar g = new GregorianCalendar(); 
		Integer minute=(int)g.get(Calendar.MINUTE); 
		return minute; 
	}
	
	/**
	 * 得到当前月
	 * @return
	 */
	public static Integer getMouth(){
		
		GregorianCalendar g = new GregorianCalendar(); 
		Integer mouth=(int)g.get(Calendar.MONTH); 
		return mouth; 
	}
	/**
	 * 以字符串yyyy-MM-dd形式返回当前日期
	 * @return
	 */
	public static String getDateByString(){
		
		Date d = new Date();
		
		String date = new SimpleDateFormat("yyyy-MM-dd").format(d);
		
		return date; 
	}
	
	/**
	 * 以字符串yyyy-MM-dd形式返回日期字符串
	 * @return
	 */
	public static String getDateByString(Date date){
		
		String rt = new SimpleDateFormat("yyyy-MM-dd").format(date);
		
		return rt; 
	}
	
	/**
	 * 获取日期字符串
	 * @param date
	 * @param bShort
	 * @return
	 */
	public static String dateToString(Date date, boolean bShort) {
		if(date!=null){
			if (bShort)
				return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
			else
				return (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * 获取长日期字符串
	 * @param date
	 * @return
	 */
	public static String dateToLongString(Date date) {
		if(date!=null){
			return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * 获取中文日期字符串
	 * @param date
	 * @param bShort
	 * @return
	 */
	public static String dateToChineseString(Date date, boolean bShort) {
		if(date!=null){
			if (bShort)
				return (new SimpleDateFormat("yyyy年MM月dd日")).format(date);
			else
				return (new SimpleDateFormat("yyyy年MM月dd日 HH:mm")).format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * 将中文日期字符串转换成日期
	 * @param str (格式：yyyy年MM月dd日）
	 * @return 日期
	 */
	public static Date chineseStringToDate(String strDate) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		ParsePosition pos = new ParsePosition(0);
	    Date strtodate = formatter.parse(strDate, pos);
	    return strtodate;	
	}
	
	/**
	 * 在WebLogic环境中比较日期
	 * @param firstDate 起始日期
	 * @param lastDate 结束日期
	 * @return
	 */
	public static Integer compareDateByWebLoic(Date firstDate, Date lastDate) {
		
		Long timeFirst = firstDate.getTime();
		Long timeLast = lastDate.getTime();
		return timeFirst.compareTo(timeLast);
	}
	
	/**
	 * 给日期增加天数
	 * @param date 当期日期
	 * @param days 天数
	 * @return 增加之后的日期
	 */
	public static Date addDays(Date date, Integer days) {
		
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.DATE, days);
		return now.getTime();
	}
	
	/**
	 * 给日期增加月份
	 * @param date 当期日期
	 * @param days 天数
	 * @return 增加之后的日期
	 */
	public static Date addMonths(Date date, Integer months) {
		
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.MONTH, months);
		return now.getTime();
	}
	
	/**
	 * 给日期增加年数
	 * @param date 当期日期
	 * @param days 天数
	 * @return 增加之后的日期
	 */
	public static Date addYears(Date date, Integer years) {
		
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.YEAR, years);
		return now.getTime();
	}
	
	/**
	 * 给日期增加分钟数
	 * @param date 当期日期
	 * @param minutes 分钟
	 * @return 增加之后的日期
	 */
	public static Date addMinutes(Date date, Integer minutes) {
		
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.MINUTE, minutes);
		return now.getTime();
	}
	
	/**
	 * 给日期增加小时数
	 * @param date 当期日期
	 * @param hours 小时数
	 * @return 增加之后的日期
	 */
	public static Date addHours(Date date, Integer hours) {
		
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.HOUR, hours);
		return now.getTime();
	}
	
	/**
	 * 获取日期字符串
	 * @param date
	 * @param bShort
	 * @return
	 */
	public static String dateToAllString(Date date) {
		if(date != null){
			return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
		} else {
			return null;
		}
	}
	
	/**
	 * 设秒数为0
	 * @param date
	 * @return
	 */
	public static Date cancelSenconds(Date date){
		date.setSeconds(0);
		return date;
	}
	
	/**
	 * 获取中文时间字符串
	 * @param date
	 * @param bShort
	 * @return
	 */
	public static String dateToTimeString(Date date, boolean bShort) {
		if(date!=null){
			if (bShort)
				return (new SimpleDateFormat("HH:mm")).format(date);
			else
				return (new SimpleDateFormat("HH:mm:ss")).format(date);
		}else{
			return "";
		}
	}
	
    public static void main(String[] args) {

//    	String str = "2001年12月12日";
//        System.out.println(dateToChineseString(chineseStringToDate(str), false));
    	Integer totalValue = 100;
    	Integer partValue = 10;
		if (totalValue.intValue() == 0) {
			System.out.println("result=");
			return;
		} 
		
		DecimalFormat df = new DecimalFormat("#.##");   
		Double temp = partValue.doubleValue() * 100 / totalValue;
		
		System.out.println("result=" + df.format(temp) + "%");
    } 

		
}
