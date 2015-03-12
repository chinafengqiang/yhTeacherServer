package com.study.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 系统工具类
 */
public class SystemUtility {

	/**
	 * 返回UUID
	 */
	public static String createUUID() {
		
		return UUID.randomUUID().toString();
	}
	
	public static Integer createRandomValue(Integer min, Integer max) {
		
		java.util.Random r = new java.util.Random(); 
		return r.nextInt(max - min + 1) + min;
	}
	
	/**
	 * 创建由数字组成的随机字符串
	 * @param count 字符串长度
	 * @return 随机字符串
	 */
	public static String createRandomString(Integer count) {
		
		String rt = "";
		
	    java.util.Random r = new java.util.Random(); 
	    for(int i=0;i<count;i++){ 
	        rt = rt + r.nextInt(9); 
	    }
	    
	    return rt;
	}
	
	/**
	 * 返回网卡MAC地址
	 */
	public static String getMACAddress() {

		String address = "";
		String os = System.getProperty("os.name");
		if (os != null) {
			if (os.startsWith("Windows")) {
				try {
					ProcessBuilder pb = new ProcessBuilder("ipconfig", "/all");
					Process p = pb.start();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(p.getInputStream()));
					String line;
					while ((line = br.readLine()) != null) {
						if (line.indexOf("Physical Address") != -1) {
							int index = line.indexOf(":");
							address = line.substring(index + 1);
							break;
						}
					}
					br.close();
					return address.trim();
				} catch (IOException e) {

				}
			} else if (os.startsWith("Linux")) {
				try {
					ProcessBuilder pb = new ProcessBuilder("ifconfig");
					Process p = pb.start();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(p.getInputStream()));
					String line;
					while ((line = br.readLine()) != null) {
						int index = line.indexOf("硬件地址");
						if (index != -1) {
							address = line.substring(index + 4);
							break;
						}
					}
					br.close();
					return address.trim();
				} catch (IOException ex) {
					
				}

			}
		}
		return address;
	}
	
	/**
	 * 获取磁盘分区总容量
	 * @param diskPartitionName 磁盘分区名称，例如：C:\
	 * @return
	 */
	public static Long getDickPartitionTotalSpace(String diskPartitionName) {
		
	    File[] roots = File.listRoots();

	    for (int i = 0; i < roots.length; i++) {
	    	if (roots[i].toString().toUpperCase().equals(diskPartitionName.toUpperCase())) {
	    		return roots[i].getTotalSpace();
	    	}
	    }
	    
	    return 0l;
	}
	
	/**
	 * 获取磁盘分区已用容量
	 * @param diskPartitionName 磁盘分区名称，例如：C:\
	 * @return
	 */
	public static Long getDickPartitionUsableSpace(String diskPartitionName) {
		
	    File[] roots = File.listRoots();

	    for (int i = 0; i < roots.length; i++) {
	    	if (roots[i].toString().toUpperCase().equals(diskPartitionName.toUpperCase())) {
	    		return roots[i].getUsableSpace();
	    	}
	    }
	    
	    return 0l;
	}
	
	/**
	 * 获取磁盘分区空闲容量
	 * @param diskPartitionName 磁盘分区名称，例如：C:\
	 * @return
	 */
	public static Long getDickPartitionFreeSpace(String diskPartitionName) {
		
	    File[] roots = File.listRoots();

	    for (int i = 0; i < roots.length; i++) {
	    	if (roots[i].toString().toUpperCase().equals(diskPartitionName.toUpperCase())) {
	    		return roots[i].getFreeSpace();
	    	}
	    }
	    
	    return 0l;
	}
	
	public static String getRegValue(String regPath) {
		
		String rt = null;
		
		try {
			Process ps = Runtime.getRuntime().exec("reg query \"" + regPath + "\"");
			ps.getOutputStream().close();
			InputStreamReader i = new InputStreamReader(ps.getInputStream());
			String line;
			BufferedReader ir = new BufferedReader(i);
			while ((line = ir.readLine()) != null) {
				rt = rt + line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return rt;
	}
	
	/**
	 * 获取src的绝对路径
	 */
	public static String getClassPath() {
		
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		path = StringUtils.replace(path, "%20", " ");
		return path;
	}
	
	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) { 

		String ip = request.getHeader("x-forwarded-for");
//		System.out.println("======x-forwarded-for==" + ip);
//		System.out.println("======Proxy-Client-IP==" + request.getHeader("Proxy-Client-IP"));
//		System.out.println("======WL-Proxy-Client-IP==" + request.getHeader("WL-Proxy-Client-IP"));
//		System.out.println("======getRemoteAddr==" + request.getRemoteAddr());

		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("Proxy-Client-IP"); 
		} 

		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("WL-Proxy-Client-IP"); 
		} 

		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getRemoteAddr(); 
		} 

		return ip; 
	} 
	
	/**
	 * 是否IP
	 * @param IP
	 * @return
	 */
	public static boolean isIp(String IP){//判断是否是一个IP
		
        boolean b = false;  
        IP = removeSpace(IP);  
        if(IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){  
            String s[] = IP.split("\\.");  
            if(Integer.parseInt(s[0])<256)  
                if(Integer.parseInt(s[1])<256)  
                    if(Integer.parseInt(s[2])<256)  
                        if(Integer.parseInt(s[3])<256)  
                            b = true;  
        }  
        return b;  
    }  
	
	private static String removeSpace(String IP){//去掉IP字符串前后所有的空格  
        while(IP.startsWith(" ")){  
               IP= IP.substring(1,IP.length()).trim();  
            }  
        while(IP.endsWith(" ")){  
               IP= IP.substring(0,IP.length()-1).trim();  
            }  
        return IP;  
    }
	
	 public static boolean ipIsValid(String ipSection, String ip) {  
	        if (ipSection == null)  
	            throw new NullPointerException("IP段不能为空！");  
	        if (ip == null)  
	            throw new NullPointerException("IP不能为空！");  
	        ipSection = ipSection.trim();  
	        ip = ip.trim();  
	        final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";  
	        final String REGX_IPB = REGX_IP + "\\-" + REGX_IP;  
	        if (!ipSection.matches(REGX_IPB) || !ip.matches(REGX_IP))  
	            return false;  
	        int idx = ipSection.indexOf('-');  
	        String[] sips = ipSection.substring(0, idx).split("\\.");  
	        String[] sipe = ipSection.substring(idx + 1).split("\\.");  
	        String[] sipt = ip.split("\\.");  
	        long ips = 0L, ipe = 0L, ipt = 0L;  
	        for (int i = 0; i < 4; ++i) {  
	            ips = ips << 8 | Integer.parseInt(sips[i]);  
	            ipe = ipe << 8 | Integer.parseInt(sipe[i]);  
	            ipt = ipt << 8 | Integer.parseInt(sipt[i]);  
	        }  
	        if (ips > ipe) {  
	            long t = ips;  
	            ips = ipe;  
	            ipe = t;  
	        }  
	        return ips <= ipt && ipt <= ipe;  
	    }  
	 
//	  public static void main(String[] args) {  
//	        if (ipIsValid("102.168.1.1-192.168.1.100", "192.168.1.54")) {  
//	            System.out.println("ip属于该网段");  
//	        } else  
//	            System.out.println("ip不属于该网段");  
//	    } 	 
	public static String runWindowsBat(String cmd) throws Exception {
	
		Process p;
		String result = "";    
        try {
            p = Runtime.getRuntime().exec(cmd);
            InputStream fis=p.getInputStream();
            InputStreamReader isr=new InputStreamReader(fis);
            BufferedReader br=new BufferedReader(isr);
            String line=null;
            while((line=br.readLine())!=null) {
            	result = result + line;
            }
            p.destroy();
            
            return result;
        } catch (IOException e) {
            throw new Exception("无法执行批处理文件");
        }
	 }
	 
	public static void main(String[] args) {
		 
		for (int i=0; i<300; i++) {
			System.out.println(SystemUtility.createRandomValue(3, 8));
		}
		
//		Integer p1 = 10;
//		Integer p2 = 8;
//		
//		if (p1.compareTo(p2) > 0) {
//			System.out.println("p1大");
//		}
    }
}
