package com.study.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
import com.study.enums.QuestionTypeEnum;
import com.study.model.TestPaper;
import com.study.model.TestPaperQuestion;
import com.study.model.part.TestPaperOption;
import com.study.utility.JSONConvertor;
import com.study.utility.StringUtility;

/**
 * 试卷导出到Word工具类
 */
public class TestPaperWordTools {

	/**
	 * 获取Word输入流
	 * @param testPaper 试卷
	 * @param series 套数
	 * @param testPaperQuestionList 此套数的所有试题
	 * @return Word输入流
	 * @throws Exception
	 */
	public static InputStream getWord(TestPaper testPaper, Integer series, List<TestPaperQuestion> testPaperQuestionList) throws Exception {
		
		//初始化文档
		Document document = new Document(PageSize.A4); 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		RtfWriter2.getInstance(document, baos); 
		
		//打开文档
        document.open();
        
        //定义基本字体
        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);          
         
        //增加空行
        document.add(new Paragraph(""));

        //设置试卷名称
        Font fontTestPaperName=new Font(bfChinese,14,Font.HELVETICA);  
        Paragraph pTestPaperName =new Paragraph(testPaper.getName(), fontTestPaperName);
        pTestPaperName.setAlignment(1);      
        document.add(pTestPaperName);
        
      //增加空行
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        
        //获取试卷题型配置
        List<TestPaperOption> optionList = JSONConvertor.json2List(testPaper.getTestPaperOptions(), TestPaperOption.class); 
        List<TestPaperOption> optionListShow = new ArrayList<TestPaperOption>();
        
        for (TestPaperOption testPaperOption : optionList) {
        	if (testPaperOption.getNumber().intValue() > 0) {
        		optionListShow.add(testPaperOption);
        	}
        }
        
        //排序题型
        Collections.sort(optionListShow, new TestPaperOptionComparator());
        
        //显示题型列表
        for (int i=0; i<optionListShow.size(); i++) {
        	
        	//获取题型名称
        	String testPaperOptionName = StringUtility.numberToChineseNub(i) + "、" + QuestionTypeEnum.valueOf(optionListShow.get(i).getQuestionType()).toName();
        	if (!StringUtils.isBlank(optionListShow.get(i).getNote())) {
        		testPaperOptionName = testPaperOptionName + "（" + optionListShow.get(i).getNote() + "）";
        	}
        	
        	//显示题型
        	Font fontTestPaperOptionName=new Font(bfChinese,11,Font.HELVETICA);  
            Paragraph pTestPaperOptionName =new Paragraph(testPaperOptionName, fontTestPaperOptionName);
            pTestPaperOptionName.setAlignment(0);      
            document.add(pTestPaperOptionName);
            
          //增加空行
            document.add(new Paragraph(""));
            
            //显示题型中的题目
            Integer index = 1;
            for (TestPaperQuestion question : testPaperQuestionList) {
            	
            	//判断此题目是否符合当前题型
            	if (question.getQuestionType().intValue() == optionListShow.get(i).getQuestionType().intValue()) {
            		
            		//显示题目的题干
            		String testPaperQuestionName = "　　" + index + "、" + question.getName();
                	Font fontTestPaperQuestionName=new Font(bfChinese,9,Font.NORMAL);  
                    Paragraph pTestPaperQuestionName =new Paragraph(testPaperQuestionName, fontTestPaperQuestionName);
                    pTestPaperOptionName.setAlignment(0);  
                    pTestPaperOptionName.setSpacingBefore(10f);
                    document.add(pTestPaperQuestionName);
                    
                    //显示题目的选项
                    index = index + 1;
                    String testPaperQuestionOptions = getTestPaperQuestionOptions(question);
                	Font fontTestPaperQuestionOptions = new Font(bfChinese,9,Font.NORMAL);  
                    Paragraph pTestPaperQuestionOptions =new Paragraph(testPaperQuestionOptions, fontTestPaperQuestionOptions);
                    pTestPaperQuestionOptions.setAlignment(0);      
                    pTestPaperQuestionOptions.setSpacingBefore(5f);
                    document.add(pTestPaperQuestionOptions);
             
                    //增加空行
                    document.add(new Paragraph(""));
            	}          	
            }
        }
        
        //关闭文档
        document.close();
        
        //返回输入流
		byte[] ba = baos.toByteArray();   
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);   
        
        return bais;
	}
	
	/**
	 * 获取题目选项内容
	 * @param testPaperQuestion 题目
	 * @return
	 */
	private static String getTestPaperQuestionOptions(TestPaperQuestion testPaperQuestion) {
	
		//判断题
		if (testPaperQuestion.getQuestionType().equals(QuestionTypeEnum.Judge.toValue())) {
			return "　　答： 对     错  ";
		}
		
		//单选或多选题
		String[] options = testPaperQuestion.getOptions().split(";");
		
		String rt = "　　答： ";
		Integer index = 0;
		for (String option : options) {
			rt = rt + StringUtility.numberToString(index) + ":" + option + "    ";
			index = index + 1 ;
		}
		
 		return rt;
	}
}
