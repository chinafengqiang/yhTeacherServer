package com.study.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

public class StringUtility {
	
	public static String getPercent(Integer item,Integer count){
		
		DecimalFormat df=new DecimalFormat("##%");
		
		double c=0.0;
		double a=item/1.0;
		double b=count/1.0;
		
		if(count != 0){
			c=a/b;
		}
		
		return df.format(c);
	}
	
	public static String getPercent(double number){
		
		DecimalFormat df=new DecimalFormat("##%");
		
		double numberValue = number / 1.0;
		
		return df.format(numberValue);
	}
	
	
	public static String numberToString(int index){
		
		switch(index){
		
		case 0:
			return "A";
			
		case 1:
			return "B";
		
		case 2:
			return "C";
			
		case 3:
			return "D";
			
		case 4:
			return "E";
			
		case 5:
			return "F";
			
		case 6:
			return "H";
			
		case 7:
			return "F";
		
		default:
			return "";
		
		}
		
	}
	
	public static String  numberToChineseNub(int num){
		
		switch(num){
			
		case 0:
			
			return "一";
			
		case 1:
			
			return "二";
			
		case 2:
			
			return "三";
			
		case 3:
			
			return "四";
		
		 case 4:
			   
		   return "五";
  
		   case 5:
		   
		   return "六";
   
		   case 6:
		   
		   return "七";

		   case 7:
		   
		   return "八";

		   case 8:
		   
		   return "九";

		   case 9:
		   
		   return "十";

		   case 10:
		  
		   return "十一";
		   case 11:
		  
		   return "十二";

		   case 12:
		   
		   return "十三";

		   case 13:
		   
		   return "十四";

		   case 14:
		   
		   return "十五";

		   case 15:
		   
		   return "十六";
 
		   case 16:
		   
		   return "十七";

		   case 17:
		   
		   return "十八";

		   case 18:
		   
		   return "十九";

		   case 19:
		   
		   return "二十";

		   default:
		   return "";
		}
		
	}
	
	
	/**
	 * 去掉字符串中的空格
	 * @param s
	 * @return
	 */
	public static String trimSpaceCharacter(String s) {
		
		String rt = s;
		rt = rt.replaceAll("[ ]*", "");
		rt = rt.replaceAll("[　]*", "");
		rt = rt.replaceAll("[]*", "");
		return rt;
	}
	
	/**
	 * 判断字符串中是否存在汉字
	 * @param s
	 * @return Boolean
	 */
	public static Boolean checkChinese(String str) {
		
		Matcher m = Pattern.compile("[\u4e00-\u9fa5]").matcher(str); 
		return m.find();
	}
	
	/**
	 * GZIP压缩文件
	 * @param text文件
	 * @return
	 */
	public static String gzip(String text) {
		 
		ByteArrayInputStream   in = new ByteArrayInputStream(text.getBytes());   
		ByteArrayOutputStream  out = new ByteArrayOutputStream();

		System.out.println("out Size=" + out.size());
		try {
			GZIPOutputStream gzout = new GZIPOutputStream(out);
			byte[] buf=new byte[1024];//设定读入缓冲区尺寸 
			int num; 
	
			while (-1 != (num = in.read(buf))) 
			{ 
				gzout.write(buf,0,num); 
			} 
			gzout.close();//!!!关闭流,必须关闭所有输入输出流.保证输入输出完整和释放系统资源. 
			System.out.println("out Size=" + out.size());
			String rt = new String(out.toByteArray());
			out.close(); 
			in.close(); 
			return rt;

		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return "";
	}
	
	/**
	 * GZIP解压文件
	 * @param text文件
	 * @return
	 */
	public static String gunzip(String text){
		
        ByteArrayInputStream byteStream = new ByteArrayInputStream(text.getBytes());

        GZIPInputStream gzipStream;
		try {
			gzipStream = new GZIPInputStream(byteStream);
	        ObjectInputStream objectStream = new ObjectInputStream(gzipStream);

	        Object object;
			try {
				object = objectStream.readObject();
		        objectStream.close();

		        gzipStream.close();

		        return object.toString();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}


	    } catch (IOException e) {
			e.printStackTrace();
		}
	    return "";

	}
	
	public static String stringConvert(String s){
		if (s == null) {
			return null ;
		}

        String result = "" ;
        byte[] temp ;

        try{
//            temp = s.getBytes("iso-8859-1");
//            result =  new String(temp,"utf-8");
        	result = new String(s.getBytes("iso-8859-1"),"utf-8");
        } catch(Exception ex) {
       	 	ex.printStackTrace() ;
        }
        
        return result;
    }
	
	/**
	 * 自动截取字符串，并加上省略号
	 * @param str
	 * @param maxLength
	 * @return
	 */
	public static String subStringCN(final String str, final int maxLength) {
		if (str == null) {
			return str;
		}
		String suffix = "...";
		int suffixLen = suffix.length();
		
		final StringBuffer sbuffer = new StringBuffer();
		final char[] chr = str.trim().toCharArray();
		int len = 0;
		for (int i = 0; i < chr.length; i++) {
			
			if (chr[i] >= 0xa1) {
				len += 2;
			} else {
				len++;
			}
		}
		
		if(len<=maxLength){
			return str;
		}
		
		len = 0;
		for (int i = 0; i < chr.length; i++) {
 
			if (chr[i] >= 0xa1) {
				len += 2;
				if (len + suffixLen > maxLength) {
					break;
				}else {
					sbuffer.append(chr[i]);
				}
			} else {
				len++;
				if (len + suffixLen > maxLength) {
					break;
				}else {
					sbuffer.append(chr[i]);
				}
			}
		}
		sbuffer.append(suffix);
		return sbuffer.toString();
	}
	
	/**
	 * 用于修正数据列表的JSON
	 * @param json
	 * @return
	 */
	public static String fixDataListJson(String json) {
		
		if (StringUtils.isBlank(json)) {
			return json;
		}
		
		if (json.length() < 4) {
			return json;
		}
		
		if (json.startsWith("\"[")) {
			return json.substring(1, json.length() - 1);
		} else {
			return json;
		}
	}
	
	public static void main(String[] args) {
		 
//		String st2 = "是个好人啊,是个好人啊";
//		System.out.println(subStringCN(st2, 9));
// 
//		String st3 = "是个好人啊 persen";
//		System.out.println(subStringCN(st3, 12));
// 
//		String st4 = " persen是个好人啊";
//		System.out.println(subStringCN(st4, 12));
//		
//		String st1 = "个好人啊";
//		System.out.println(subStringCN(st1, 12));
// 
		String st0 = "\"[{\"questionType\":2,\"score\":\"10\",\"number\":\"3\",\"sortFlag\":\"1\",\"note\":null},{\"questionType\":0,\"score\":\"10\",\"number\":\"3\",\"sortFlag\":\"2\",\"note\":null},{\"questionType\":1,\"score\":\"10\",\"number\":\"4\",\"sortFlag\":\"3\",\"note\":null}]\"";
		System.out.println(fixDataListJson(st0));
	}	
}
