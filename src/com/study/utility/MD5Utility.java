package com.study.utility;

import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

/**
 * MD5工具类
 */
public class MD5Utility {

	/**
	 * 获取MD5串
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
		
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
	        BASE64Encoder base64en = new BASE64Encoder();
	        String rt = base64en.encode(md5.digest(str.getBytes("utf-8")));
	        return rt;
		} catch (Exception ex) {
			return "";
		}
	}
	
	public static String getMd5ByHex(String str) {
		try {
			byte[] btInput = str.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16)
					sb.append("0");
				sb.append(Integer.toHexString(val));

			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println("sss = " + getMD5("aaaaa"));
		System.out.println("sss = " + getMd5ByHex("aaaaa"));
	}
}
