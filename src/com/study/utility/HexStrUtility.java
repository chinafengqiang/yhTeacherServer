package com.study.utility;

import java.io.ByteArrayOutputStream;

public class HexStrUtility {


	/*
	 * 16进制数字字符集
	 */
	private static String hexString="0123456789ABCDEF";
	
	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encode(String str)
	{
		//根据默认编码获取字节数组
		byte[] bytes=str.getBytes();
		StringBuilder sb=new StringBuilder(bytes.length*2);
		//将字节数组中每个字节拆解成2位16进制整数
		for(int i=0;i<bytes.length;i++)
		{
		sb.append(hexString.charAt((bytes[i]&0xf0)>>4));
		sb.append(hexString.charAt((bytes[i]&0x0f)>>0));
		}
		return sb.toString();
	}
	
	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes)
	{
		ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2);
		//将每2位16进制整数组装成一个字节
		for(int i=0;i<bytes.length();i+=2)
		baos.write((hexString.indexOf(bytes.charAt(i))<<4 |hexString.indexOf(bytes.charAt(i+1))));
		return new String(baos.toByteArray());
	}
	
    public static void main(String[] args) throws Exception {
    	
        String content = "tes啊潍坊怕会飞啊飞哈我ie发哈；哦if嗯哈我；哦而非哈我；ot";
        String ss = encode(content);
        String ss1 = decode(ss);
        System.out.println("前：" + ss);
        System.out.println("后：" + ss1);
    }
    
}
