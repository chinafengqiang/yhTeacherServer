package com.study.utility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

/**
 * 字符集检测
 */
public class CharsetDetector
{

    private boolean found = false;
    private String result;
    private int lang;

    public String[] detectChineseCharset(InputStream in) throws IOException
    {
        lang = nsPSMDetector.CHINESE;
        String[] prob;
        // Initalize the nsDetector() ;
        nsDetector det = new nsDetector(lang);
        // Set an observer...
        // The Notify() will be called when a matching charset is found.

        det.Init(new nsICharsetDetectionObserver()
        {

            public void Notify(String charset)
            {
                found = true;
                result = charset;
            }
        });
        BufferedInputStream imp = new BufferedInputStream(in);
        byte[] buf = new byte[1024];
        int len;
        boolean isAscii = true;
        while ((len = imp.read(buf, 0, buf.length)) != -1)
        {
            // Check if the stream is only ascii.
            if (isAscii)
                isAscii = det.isAscii(buf, len);
            // DoIt if non-ascii and not done yet.
            if (!isAscii)
            {
                if (det.DoIt(buf, len, false))
                    break;
            }
        }
        imp.close();
        in.close();
        det.DataEnd();
        if (isAscii)
        {
            found = true;
            prob = new String[]
                    {
                        "ASCII"
                    };
        } else if (found)
        {
            prob = new String[]
                    {
                        result
                    };
        } else
        {
            prob = det.getProbableCharsets();
        }
        return prob;
    }

    public String[] detectAllCharset(InputStream in) throws IOException
    {
        try
        {
            lang = nsPSMDetector.ALL;
            return detectChineseCharset(in);
        } catch (IOException e)
        {
            throw e;
        }
    }
    
    /**
     * 判断是否是UTF-8字符集
     * @param in 输入流
     * @return
     */
    public static Boolean validateUTF8(InputStream in) {
    
		try {
	    	CharsetDetector charsetDetector = new CharsetDetector();
			String[] charsets = charsetDetector.detectChineseCharset(in);
	    	for (int i=0; i < charsets.length; i++) {
	    		if (charsets[i].equals("UTF-8")) {
	    			return true;
	    		}
	    	}
		} catch (IOException e) {
		}
		
		return false;

    }
    
    /**
     * 判断是否是UTF-8字符集
     * @param file 文件
     * @return
     */
    public static Boolean validateUTF8(File file) {
    
		try {
			FileInputStream in = new FileInputStream(file);
	    	CharsetDetector charsetDetector = new CharsetDetector();
			String[] charsets = charsetDetector.detectChineseCharset(in);
	    	for (int i=0; i < charsets.length; i++) {
	    		if (charsets[i].equals("UTF-8")) {
	    			return true;
	    		}
	    	}
		} catch (IOException e) {
		}
		
		return false;

    }
    
    public static void main(String argc[]) {
    	
    	try {
	    	File file = new File("e:\\1.txt");
	    	FileInputStream in = new FileInputStream(file);
	    	Boolean s = CharsetDetector.validateUTF8(in);
	    	System.out.println(s);
    	} catch (Exception ex) {
    		
    	}
    }
}