package com.study.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 文件工具类
 */
public class FileUtility {

	 /**
	 * 加密KEY不能随便改动
	 */
	static final byte[] KEYVALUE = "6^)(9-p35@%3#4S!4S0)$Y%%^&5(j.&^&o(*0)$Y%!#O@*GpG@=+@j.&6^)(0-=+6^)(9-p35@%3#4S!4S0)$Y%%^&5(j.&^&o(*0)$Y%!#O@*GpG@=+@j.&6^)(0-=+".getBytes();

	 /**
	 * 长度=KeyValue.length*8
	 */
	static final int BUFFERLEN = 1024;
	
	/**
	 * 删除文件
	 * @param fileName 文件名
	 * @return
	 */
	public static Boolean deleteFile(String fileName) {
		
		//删除zip文件
		File file = new File(fileName);
		return file.delete();
	}
	
	/**
	 * 获取文件的字节流
	 * @param fileName 文件名
	 * @return
	 * @throws IOException
	 */
	public static ByteArrayInputStream getByteArrayInputStream(String fileName) throws IOException {
		
		//获取打包好的文件字节输入流
		FileInputStream fis = new FileInputStream(fileName);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		int ch; 
		while((ch = fis.read())!=-1)   { 
			bos.write(ch); 
		}
		
		fis.close();
		
		return new ByteArrayInputStream(bos.toByteArray());
	}
	/**
	 * 拷贝文件
	 * @param sourceFilePath 源文件绝对路径
	 * @param targetFilePath 目标文件绝对路径
	 * @throws IOException
	 */
	public static void copyFile(String sourceFilePath, String targetFilePath) throws Exception {
		
		File source = new File(sourceFilePath);
		File target = new File(targetFilePath);
		
		copyFileByJava(source, target);
	}
	
	/**
	 * 拷贝文件
	 * @param sourceFilePath 源文件绝对路径
	 * @param targetFilePath 目标文件绝对路径
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, String targetFilePath) throws Exception {
		
		try{
		    FileInputStream is = new FileInputStream(sourceFile);
		    FileOutputStream os = new FileOutputStream(targetFilePath);
		     
		    byte buffer[] = new byte[8192];
		    int count = 0;
		    while ((count = is.read(buffer)) > 0)
		    {
		        os.write(buffer, 0, count);
		    }
		    os.close();
		    is.close();
		     
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("无法拷贝文件！");
		}
	}
	
	/**
	 * 拷贝文件
	 * @param is 源文件数据流
	 * @param targetFilePath 目标文件绝对路径
	 * @throws IOException
	 */
	public static void copyFile(InputStream is, String targetFilePath) throws Exception {
		
		try{
		    FileOutputStream os = new FileOutputStream(targetFilePath);
		     
		    byte buffer[] = new byte[8192];
		    int count = 0;
		    while ((count = is.read(buffer)) > 0)
		    {
		        os.write(buffer, 0, count);
		    }
		    os.close();
		    is.close();
		     
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("无法拷贝文件！");
		}
	}
	
	/**
	 * 通过管道对管道的方式拷贝文件
	 * @param source
	 * @param target
	 * @throws IOException 
	 */
	public static void copyFileByTransfer(File source, File target) throws Exception {
		
        int length=2097152;
        FileInputStream in=new FileInputStream(source);
        FileOutputStream out=new FileOutputStream(target);
        FileChannel inC=in.getChannel();
        FileChannel outC=out.getChannel();
        int i=0;
        while(true){
            if(inC.position()==inC.size()){
                inC.close();
                outC.close();
            }
            if((inC.size()-inC.position())<20971520)
                length=(int)(inC.size()-inC.position());
            else
                length=20971520;
            inC.transferTo(inC.position(),length,outC);
            inC.position(inC.position()+length);
            i++;
        }
	}
	
	/**
	 * 通过内存映射文件拷贝文件
	 * @param source
	 * @param target
	 * @throws IOException 
	 */
	public static void copyFileByImage(File source, File target) throws Exception {
		
        int length=2097152;
        FileInputStream in=new FileInputStream(source);
        RandomAccessFile out=new RandomAccessFile(target,"rw");
        FileChannel inC=in.getChannel();
        MappedByteBuffer outC=null;
        MappedByteBuffer inbuffer=null;
        byte[] b=new byte[length];
        while(true){
            if(inC.position()==inC.size()){
                inC.close();
                outC.force();
                out.close();
            }
            if((inC.size()-inC.position())<length){
                length=(int)(inC.size()-inC.position());
            }else{
                length=20971520;
            }
            b=new byte[length];
            inbuffer=inC.map(MapMode.READ_ONLY,inC.position(),length);
            inbuffer.load();
            inbuffer.get(b);
            outC=out.getChannel().map(MapMode.READ_WRITE,inC.position(),length);
            inC.position(b.length+inC.position());
            outC.put(b);
            outC.force();
        }
	}
	
	/**
	 * 通过Java字节数组方式拷贝文件
	 * @param source
	 * @param target
	 * @throws IOException 
	 */
	public static void copyFileByJava(File source, File target) throws Exception {
		
		//int length=2097152;
		int length=8192;
		
		FileInputStream in=new FileInputStream(source);
		FileOutputStream out=new FileOutputStream(target);
		byte[] buffer=new byte[length];
		while(true){
			int ins=in.read(buffer);
			if(ins==-1){
				in.close();
				out.flush();
				out.close();
				break;
			}else
				out.write(buffer,0,ins);
		}
	}
	
	/**
	 * 通过Java字节数组方式拷贝文件
	 * @param source
	 * @param target
	 * @throws IOException 
	 */
	public static void copyFileByChannel(File source, File target) throws IOException {
		
        int length=2097152;
        FileInputStream in=new FileInputStream(source);
        FileOutputStream out=new FileOutputStream(target);
        FileChannel inC=in.getChannel();
        FileChannel outC=out.getChannel();
        ByteBuffer b=null;
        while(true){
            if(inC.position()==inC.size()){
                inC.close();
                outC.close();
            }
            if((inC.size()-inC.position())<length){
                length=(int)(inC.size()-inC.position());
            }else
                length=2097152;
            b=ByteBuffer.allocateDirect(length);
            inC.read(b);
            b.flip();
            outC.write(b);
            outC.force(false);
        }
	}	
	
	/**
	 * 判断文件是否存在
	 * @param fileName
	 */
	public static Boolean fileExists(String fileName) {
		
		File file = new File(fileName); 
		return file.exists();
	}
	
	/**
	 * 判断目录是否存在
	 * @param dirName
	 */
	public static Boolean dirExists(String dirName) {
		
		File file = new File(dirName); 
		return file.exists();
	}

	/**
	 * 创建目录
	 * @param dir
	 */
	public static void createDir(String dir) {
		
		File file = new File(dir); 
		if (!file.exists()) 
		{ 
			file.mkdirs(); 
		}
	}
	
	/**
	 * 获取文件大小
	 * @param filePath 文件路径
	 * @return
	 */
	public static Long getFileSize(String filePath) {
		
		File file = new File(filePath);
		return file.length();
	}
	
	/**
	 * 获取文件路径
	 * @param filePath 文件绝对路径
	 * @return
	 */
	public static String getFilePath(String filePath) { 
		
		char flag = '\\';
		if (filePath.indexOf('/') > -1) {
			flag = '/';
		}
		
		String rt = "";
	    if ((filePath != null) && (filePath.length() > 0)) { 
	    	int i = filePath.lastIndexOf(flag); 
	
	        if ((i >-1) && (i < (filePath.length() - 1))) { 
	            rt = filePath.substring(0,i + 1); 
	        } 
	    } 
	    
	    return rt; 
	}
	
	/**
	 * 获取文件后缀名
	 * @param filePath 文件绝对路径
	 * @return
	 */
	public static String getFileExtension(String filePath) { 
		
		String rt = "";
	    if ((filePath != null) && (filePath.length() > 0)) { 
	    	int i = filePath.lastIndexOf('.'); 
	
	        if ((i >-1) && (i < (filePath.length() - 1))) { 
	            rt = filePath.substring(i); 
	        } 
	    } 
	    
	    return rt; 
	} 
	
	/**
	 * 获取文件后缀名
	 * @param filePath 文件绝对路径
	 * @return
	 */
	public static String getFileExtensionNoRadixPoint(String filePath) { 
		
		String rt = "";
	    if ((filePath != null) && (filePath.length() > 0)) { 
	    	int i = filePath.lastIndexOf('.'); 
	
	        if ((i >-1) && (i < (filePath.length() - 1))) { 
	            rt = filePath.substring(i + 1); 
	        } 
	    } 
	    
	    return rt; 
	}
	
	/**
	 * 将文件的后缀名置换成小写的
	 * @param fileName 文件名（含后缀名）
	 * @return
	 */
	public static String getFileNameByLowerExtension(String fileName) {
		
		return getFileName(fileName) + getFileExtension(fileName).toLowerCase();
	}
	
	/**
	 * 获取文件名称
	 * @param filePath 文件绝对路径
	 * @return
	 */
	public static String getFileName(String filePath) { 
		
		char flag = '\\';
		if (filePath.indexOf('/') > -1) {
			flag = '/';
		}
		
		String rt = "";
	    if ((filePath != null) && (filePath.length() > 0)) { 
	    	int first = filePath.lastIndexOf(flag); 
	    	int last = filePath.lastIndexOf('.');

	    	if (first <= -1) {
	    		first = 0;
	    	} else {
	    		first = first + 1;
	    	}
	    	if (last <= -1) {
	    		last = filePath.length();
	    	}
	    	rt = filePath.substring(first,last); 
	    } 
	    
	    return rt; 
	}
	
	 /**
	 * 加密文件
	 * @param sourceFilePath 源文件路径
	 * @param targetFilePath 目标文件路径
	 * @throws Exception
	 */
	public static void encryptFile(String sourceFilePath, String targetFilePath) throws Exception {
		 
		FileInputStream in = new FileInputStream(sourceFilePath);
		File file = new File(targetFilePath);
		if (!file.exists()) file.createNewFile();
		 
	 	FileOutputStream out = new FileOutputStream(file);
	 	 
	 	int c,pos;
	 	pos = 0;
	    byte buffer[] = new byte[BUFFERLEN];  
	     
	    while ((c = in.read(buffer)) != -1) {
	    	 
	    	byte bufferTemp[] = new byte[c];  
	    	for (int i = 0; i < c; i++) {
	    		 
		        buffer[i] ^= KEYVALUE[pos];
		        bufferTemp[i] = buffer[i];
		        pos++;
		        if (pos == KEYVALUE.length) pos = 0;
	    	}
	    	out.write(bufferTemp);
	    }
	    in.close();
		out.close();
 	}
	
	 /**
	  * 解密文件
	 * @param sourceFilePath 源文件路径
	 * @param targetFilePath 目标文件路径
	 * @throws Exception
	 */
	public static void decryptFile(String sourceFilePath, String targetFilePath) throws Exception {
		 
		encryptFile(sourceFilePath, targetFilePath);
	}

	/**
	 * 获取文件/文件夹空间大小名称
	 * @param fileSize 空间大小（单位KB）
	 * @return
	 */
	public static String getFileSizeNameByKB(Long fileSize) {
		
		if (fileSize <= 0) {
			return "0KB";
		}
		
		if (fileSize < 1024) {
			return fileSize.toString() + "KB";
		}

		NumberFormat doubleFormat = new DecimalFormat( "0.00"); 

		if (fileSize < 1024 * 1024) {
			
			return doubleFormat.format((double) fileSize / 1024) +  "MB";
		}
		

		return doubleFormat.format((double) fileSize / (1024 * 1024)) +  "GB";
	}
	
	/**
	 * 获取文件/文件夹空间大小名称
	 * @param fileSize 空间大小（单位B）
	 * @return
	 */
	public static String getFileSizeName(Long fileSize) {
		
		if (fileSize <= 0) {
			return "0";
		}
		
		if (fileSize < 1024) {
			return fileSize.toString() + " B";
		}

		NumberFormat doubleFormat = new DecimalFormat( "0.#"); 

		if (fileSize < 1024 * 1024) {
			
			return doubleFormat.format((double) fileSize / 1024) +  " KB";
		}
		
		if (fileSize < 1024 * 1024 * 1024) {
			
			return doubleFormat.format((double) fileSize / (1024 * 1024)) +  " MB";
		}

		return doubleFormat.format((double) fileSize / (1024 * 1024 * 1024)) +  " GB";
	}
	
    /**   
     * 删除目录（文件夹）以及目录下的文件   
     * @param   dir 被删除目录的文件路径   
     * @return  目录删除成功返回true,否则返回false   
     */    
    public static boolean deleteDirectory(String dir){ 
    	
        //如果dir不以文件分隔符结尾，自动添加文件分隔符      
        if(!dir.endsWith(File.separator)){     
            dir = dir+File.separator;     
        }     
        
        File dirFile = new File(dir);     
        
        //如果dir对应的文件不存在，或者不是一个目录，则退出      
        if(!dirFile.exists() || !dirFile.isDirectory()){     
            return false;     
        }
        
        boolean flag = true;     
        
        //删除文件夹下的所有文件(包括子目录)      
        File[] files = dirFile.listFiles();     
        for(int i=0;i<files.length;i++){     
        
        	//删除子文件      
            if(files[i].isFile()){     
                flag = deleteFile(files[i].getAbsolutePath());     
                if(!flag){     
                    break;     
                }     
            }     
            //删除子目录      
            else{     
                flag = deleteDirectory(files[i].getAbsolutePath());     
                if(!flag){     
                    break;     
                }     
            }     
        }     
             
        if(!flag){     
            return false;     
        }     
             
        //删除当前目录      
        if(dirFile.delete()){     
            return true;     
        }else{     
            return false;     
        }     
    }     
	
    /**
     * 验证文件名或目录名称是否合法
     * @param name 名称
     * @return
     */
    public static boolean validateName(String name) {
    	
    	if (name.indexOf("/") >= 0) {
    		return false;
    	}
    	
    	if (name.indexOf("\\") >= 0) {
    		return false;
    	}

    	if (name.indexOf(":") >= 0) {
    		return false;
    	}
    	
    	if (name.indexOf("*") >= 0) {
    		return false;
    	}
    	
    	if (name.indexOf("?") >= 0) {
    		return false;
    	}
    	
    	if (name.indexOf("\"") >= 0) {
    		return false;
    	}
    	
    	if (name.indexOf(">") >= 0) {
    		return false;
    	}

    	if (name.indexOf("<") >= 0) {
    		return false;
    	}

    	if (name.indexOf("|") >= 0) {
    		return false;
    	}
    	return true;
    }
    
	/**
	 * 读取文本文件
	 * @param File 文件
	 * @return
	 * @throws Exception 
	 */
	public static String readTXT(File file, String charsetName) throws Exception {
		
		Long fileLengthLong = file.length();
		byte[] fileContent = new byte[fileLengthLong.intValue()];
		
        FileInputStream inputStream = new FileInputStream(file);
        
        inputStream.read(fileContent);
		inputStream.close();
		
		return  new String(fileContent, charsetName);
	}
	
	public static void main(String[] args) {
		
		String sourceFilePath = "D:\\SystemDataPath\\文档列表备份-2011年12月12日.dat";
		String targetFilePath = "D:\\SystemDataPath\\文档列表备份-2011年12月12日.xls";
		try {
			FileUtility.decryptFile(sourceFilePath, targetFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
