package com.study.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPUtility {
	
	/**
	 * 验证IP地址是否合法
	 * @param ipAddress ip地址
	 * @return
	 */
	public static boolean validateIP(String ipAddress){ 
		
		Pattern pattern = Pattern.compile("((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))"); 
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }
	
	/**
	 * 验证IP地址段是否合法
	 * @param ipSegment IP地址段
	 * @return
	 */
	public static boolean validateIPSegment(String ipSegment){ 
		
		if (ipSegment.indexOf("-") < 0) {
			return validateIP(ipSegment);
		}
		
		String[] ipList = ipSegment.split("-");
		if (ipList.length > 2) {
			return false;
		}
		
		if (!validateIP(ipList[0])) {
			return false;
		}

		if (!validateIP(ipList[1])) {
			return false;
		}
		
        return arraySub(IPsplit(ipList[0]), IPsplit(ipList[1]));
    }
	
	/**
	 * 验证IP地址段列表是否合法
	 * @param ipSegmentList IP地址段列表
	 * @return
	 */
	public static boolean validateIPSegmentList(String ipSegmentList){ 
		
		String[] ipSegmentArray = ipSegmentList.split(";");
		
		for (int i=0; i<ipSegmentArray.length; i++) {
			if (!validateIPSegment(ipSegmentArray[i])) {
				return false;
			}
		}
		
		return true;
    }
	
	/**
	 * 验证IP地址是否符合IP地址段
	 * @param ip IP地址
	 * @param ipSegment IP地址段
	 * @return
	 */
	public static boolean validateIPBySegment(String ip, String ipSegment){ 
		
		if (!validateIP(ip)) {
			return false;
		}
		
		String[] ipList = ipSegment.split("-");
		return isBetween(ip, ipList[0], ipList[1]);
    }
	
	/**
	 * 验证IP地址是否符合IP地址段列表
	 * @param ip IP地址
	 * @param ipSegmentList IP地址段列表
	 * @return
	 */
	public static boolean validateIPBySegmentList(String ip, String ipSegmentList){ 
		
		if (!validateIP(ip)) {
			return false;
		}
		
		String[] ipSegmentArray = ipSegmentList.split(";");
		
		for (int i=0; i<ipSegmentArray.length; i++) { 
			if (validateIPBySegment(ip, ipSegmentArray[i])) {
				return true;
			}
		}
		
		return false;
    }	
	
	private static int[] IPsplit(String ip) {
        return IPsplit(ip,false);
    }
	
    /**
     * reutrn a int array with 4 size
     * @param ip
     * @return
     */
	private static int[] IPsplit(String ip,boolean max) {
        int[] inum = new int[4];
        try {
            String[] nums = ip.split("[.]");
            int len =nums.length;
            for (int i = 0; i < len; i++) {
                inum[i] = Integer.parseInt(nums[i]);
            }
            if(max) {//if max ,fill 256 to int[]
                if(len<inum.length) {
                    for (int j = len; j < inum.length; j++) {
                        inum[j]=256;
                    }
                }
            }
        } catch (Exception e) {
        }
        return inum;
    }

    public static boolean isBetween(String myIP, String ip) {
        return isBetween(myIP, ip, ip);
    }

    /**
     * return myIP is between ip1~ip2
     *
     * @param myIP
     * @param ip1
     * @param ip2
     * @return
     */
    public static boolean isBetween(String myIP, String ip1, String ip2) {
        if (myIP == null) {
            return true;
        }
        if (null == ip1) {
            ip1 = "0.0.0.0";
        }
        if (null == ip2) {
            ip2 = "256.0.0.0";
        }
        int[] myIPs = IPsplit(myIP);

        return arraySub(myIPs, IPsplit(ip2,true)) && arraySub(IPsplit(ip1), myIPs);
    }

    private static boolean arraySub(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            if (b[i] - a[i] > 0) {
                return true;
            } else if (b[i] - a[i] < 0) {
                return false;
            } else {
                if(i+1==a.length) {//check is end
                    return true;
                }else {
                    continue;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String myIP = "61.52.1.19";
        String ip1 = "61.52.01.18";
        String ip2 = "61.52.1.28";
        //System.out.println(isBetween(myIP, ip1,ip2));
        System.out.println(validateIPBySegmentList("0.0.8.10", "0.0.9.009-0.0.23.33;0.0.8.009-0.0.23.33"));
    } 

}
