package com.study.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.study.enums.QuestionTypeEnum;
import com.study.model.Question;
import com.study.utility.ExcelReader;
import com.study.utility.ExcelWriter;

/**
 * 题目与Excel的转换工具
 */
public class QuestionExcelTools {

	/**
	 * 数据首行号
	 */
	public static final Integer dataRowFirst = 3;
	
	/**
	 * 将题目集转换成工作簿
	 * @param questionList 题目集
	 * @return 工作簿
	 * @throws IOException
	 */
	public static HSSFWorkbook getWorkbook(List<Question> questionList) throws IOException {
		
		String note = "说明：\n" 
			+ "1、题型只能是：单选题；多选题；判断题，否则试题不能导入系统中。\n"
			+ "2、难度分为1-5等，其中1表示最易，5表示最难。\n"
			+ "3、同一知识点、题型和难度的题目的分数应该尽量相同。\n"
			+ "4、可选项只对选择题有效，其他题型可选项为空。多个选项之间用半角英文分号(“;”)隔开。\n"
			+ "5、答案中单选题只能是A-Z的一个字母;多选题可以是多个字母，中间没有分隔符;判断题只能是1或0，1表示对，0表示错误。\n"
			+ "6、试题内容中不能出现“×、÷、>、&、<、±”等特殊符号，如果需要的话，可以使用“*、/、》、＋、《”或直接写文字表示。\n"
			+ "7、所有问题题目和答案加起来不能超过6000个英文字符或者1000个汉字。\n";
		
		ExcelWriter writer = new ExcelWriter("题目集");
		writer.createMergedRow(dataRowFirst - 3, 0, 7, "考试系统题目列表", getTitleCellStyle(writer.getWorkbook()), 600);
		writer.createMergedRow(dataRowFirst - 2, 0, 7, note, getNoteCellStyle(writer.getWorkbook()), 2400);
		writer.createCellList(dataRowFirst - 1, 0, getHeaderValueList(), getHeaderCellStyleList(writer.getWorkbook()), getHeaderWidthList(), 300);
		
		ArrayList<HSSFCellStyle> bodyCellStyleList= getBodyCellStyleList(writer.getWorkbook());
		for (int i=0; i<questionList.size(); i++) {
			writer.createCellList(dataRowFirst + i, 0, getBodyValueList(questionList.get(i)), bodyCellStyleList, getHeaderWidthList(), 300);
		}
		return writer.getWorkbook();   
	}
	
	/**
	 * 从Excel文件中获取题目列表
	 * @param file 文件
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Question> getQuestionList(File file) throws Exception {
		
		ExcelReader reader = new ExcelReader(file);	
		
		ArrayList<Question> list = new ArrayList<Question>();
		
		for(int i=dataRowFirst; i<reader.getRowCount(0); i++){
			
			String ken = reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i, 0)));
			String type = reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i, 1)));
			String difficulty = reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i, 2)));
			String score = reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i, 3)));
			String questionName = reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i,4)));
			String options = reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i,5)));
			String answer = reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i,6))); 
			String note = reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i,7)));
			
			//如果一行中的所有字段都为空，则跳过，默认表示到了最后一行
			if (StringUtils.isBlank(ken + type + difficulty + score + questionName + options + answer + note)) {
				break;
			}
			
			//校验题目类型
			Integer baseType = getBaseType(type);
			if (baseType == null){
				throw new Exception("题目校验出错:第" + (i + 1) + "行,无法获取基本题型!" );
			}
			
			//校验难度
			Integer difficultyValue = 0;
			try{
				difficultyValue = Integer.parseInt(difficulty);
			}
			catch(Exception e){
				throw new Exception("题目校验出错:第" + (i + 1) + "行,难度数据项应为数值!" );
			}
			
			//校验分数
			Double scoreValue = 0d;
			try{
				scoreValue = Double.parseDouble(score);
			}catch(Exception e){
				throw new Exception("题目校验出错:第" + (i + 1) + "行,分数数据项有问题!" );
			}
			
			//校验题目内容
			if (StringUtils.isBlank(questionName)) {
				throw new Exception("题目校验出错:第" + (i + 1) + "行,题目内容数据项有问题!" );
			}
			
			//校验可选项
			if (baseType.equals(QuestionTypeEnum.SingleSelect.toValue()) || baseType.equals(QuestionTypeEnum.MultiSelect.toValue())){
				
				//将选项的全角分号置换成半角分号
				options = options.replaceAll("；", ";");
				while (options.contains(";;")) {
					options = options.replaceAll(";;", ";");
				}
				
				//若无选项
				if (StringUtils.isBlank(options)) {
					throw new Exception("题目校验出错:第" + (i + 1) + "行,题目可选项有问题!" );
				}
			}
			
			//校验可选项(非选择题时，可选项不允许有内容)
			if (baseType.equals(QuestionTypeEnum.Judge.toValue())) {
				
				//若无选项
				if (!StringUtils.isBlank(options)) {
					throw new Exception("题目校验出错:第" + (i + 1) + "行,题目不是选择题，不可以有可选项!" );
				}
			}
			
			//判断题库选项是否为空格
			if (baseType.equals(QuestionTypeEnum.SingleSelect.toValue()) || baseType.equals(QuestionTypeEnum.MultiSelect.toValue())){
				
				String[] optionsList = options.split(";");
				for(String s:optionsList){
					if(StringUtils.isBlank(s)){
						throw new Exception("题目校验出错:第" + (i + 1) + "行,答案选项有问题：单选题或多选题答案选项不能以空格为答案");
					}
				}
			}
			
			//校验答案
			if (StringUtils.isBlank(answer)) {
				throw new Exception("题目校验出错:第" + (i + 1) + "行,题目答案数据项有问题!" );
			}
			
			//校验选择题答案
			if (baseType.equals(QuestionTypeEnum.SingleSelect.toValue()) || baseType.equals(QuestionTypeEnum.MultiSelect.toValue())){
		
				//将答案转换成大写
				answer = answer.toUpperCase();
				
				//判断单选题答案及数量是否越界
				if (baseType.equals(QuestionTypeEnum.SingleSelect.toValue()) && answer.length() > 1) {
					throw new Exception("题目校验出错:第" + (i + 1) + "行,单选题答案只能有一个答案!" );
				}
				
				//校验多选题答案选项是否与候选项匹配
				if (baseType.equals(QuestionTypeEnum.MultiSelect.toValue()) && answer.length() > options.split(";").length) {
					throw new Exception("题目校验出错:第" + (i + 1) + "行,多选题答案数量不能超过选项数量!" );
				}
				
				//判断答案是否越界ABCDEFGH
				if (!verifyAnswerOverflow(options, answer)) {
					System.out.println("options:"+options+"answer:"+answer);
					throw new Exception("题目校验出错:第" + (i + 1) + "行,题目答案只能是ABCDEFGH中的字母，且不能越界!" );
				}
				
				
				//判断多选题答案及数量是否越界
			}
			
			//校验判断题答案
			if (baseType.equals(QuestionTypeEnum.Judge.toValue())) {
				if(!answer.equals("0") && !answer.equals("1")) {
					throw new Exception("题目校验出错:第" + (i + 1) + "行,判断题的答案只能是0或1!" );
				}
			}
			
			Question question = new Question();
			
			question.setKen(ken);
			question.setQuestionType(baseType);
			question.setDifficulty(difficultyValue);
			question.setScore(scoreValue);
			question.setName(questionName);
			question.setOptions(options);
			question.setAnswer(answer);
			question.setNote(note);

			list.add(question);
		}
	
		return list;
	}
	
	/**根据自定义题型名字得到基本类型
	 * @param name 类型
	 * @return
	 */
	private static Integer getBaseType(String name){
		
		Integer baseType = null;
		TreeMap<Integer, String> mapQuestionType = QuestionTypeEnum.toMap();
		
		for(Iterator it = mapQuestionType.entrySet().iterator(); it.hasNext();){
			Map.Entry<Integer, String> e = (Map.Entry<Integer, String>)it.next();
			if (e.getValue().equals(name)){
				baseType = e.getKey();
			}
		}
		
		return baseType;
		
	}
	
	/**
	 * 校验答案是否越界
	 * @param options
	 * @param answer
	 * @return
	 */
	private static Boolean verifyAnswerOverflow(String options, String answer) {
		
		String temp = "ABCDEFGH";
		String[] optionsList = options.split(";");
		
		if (optionsList.length > 8) {
			return false;
		}
		
		temp = temp.substring(0, optionsList.length);
		
		for (int i=0; i < answer.length(); i++) {
			
			String tempChar = answer.substring(i, i + 1);
			if (!temp.contains(tempChar)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 获取题目的数据行值列表
	 * @param question 题目
	 * @return 数据行值列表 
	 */
	private static ArrayList<String> getBodyValueList(Question question) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.add(question.getKen());
		list.add(QuestionTypeEnum.valueOf(question.getQuestionType()).toName());
		list.add(question.getDifficulty().toString());
		
		Long temp = Math.round(question.getScore());
		Double ss = Double.parseDouble(temp.toString());
		if (ss.compareTo(question.getScore()) != 0) {
			list.add(question.getScore().toString());
		} else {
			list.add(question.getScore().toString().substring(0, question.getScore().toString().lastIndexOf(".")));
		}
		list.add(question.getName());
		list.add(question.getOptions());
		list.add(question.getAnswer());
		list.add(question.getNote());

		return list;
	}
	
	/**
	 * 获取Header行的值列表
	 * @return
	 */
	private static ArrayList<String> getHeaderValueList() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("知识点");
		list.add("题型");
		list.add("难度");
		list.add("分数");
		list.add("题目内容");
		list.add("可选项");
		list.add("答案");
		list.add("说明");

		return list;
	}
	
	/**
	 * 获取Header行的单位格宽度值列表
	 * @return
	 */
	private static ArrayList<Integer> getHeaderWidthList() {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		list.add(22);
		list.add(10);
		list.add(10);
		list.add(10);
		list.add(60);
		list.add(50);
		list.add(15);
		list.add(32);

		return list;
	}
	
	/**
	 * 获取标题行的单位格样式
	 * @return
	 */
	private static HSSFCellStyle getTitleCellStyle(HSSFWorkbook workbook) {
	
		HSSFFont font = workbook.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short)18);
		font.setColor(HSSFColor.RED.index);
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		
		return cellStyle;
	}
	
	/**
	 * 获取注释行的单位格样式
	 * @return
	 */
	private static HSSFCellStyle getNoteCellStyle(HSSFWorkbook workbook) {
	
		HSSFFont font = workbook.createFont();
		font.setFontName("新宋体");
		font.setFontHeightInPoints((short)9);
		font.setColor(HSSFColor.BLUE_GREY.index);
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		
		return cellStyle;
	}
	
	/**
	 * 获取Header行的单位格样式
	 * @return
	 */
	private static HSSFCellStyle getHeaderCellStyle(HSSFWorkbook workbook) {
	
		HSSFFont font = workbook.createFont();
		font.setFontName("新宋体");
		font.setFontHeightInPoints((short)9);
		font.setColor(HSSFColor.WHITE.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		cellStyle.setFont(font);
		
		return cellStyle;
	}
	
	/**
	 * 获取数据行的单位格样式
	 * @return
	 */
	private static HSSFCellStyle getBodyCellStyle(HSSFWorkbook workbook) {
		
		HSSFFont font = workbook.createFont();
		font.setFontName("新宋体");
		font.setFontHeightInPoints((short)9);
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		cellStyle.setFont(font);
		
		return cellStyle;
	}
	
	/**
	 * 获取数据行中题目名称的单位格样式
	 * @return
	 */
	private static HSSFCellStyle getBodyCellStyleForName(HSSFWorkbook workbook) {
		
		HSSFFont font = workbook.createFont();
		font.setFontName("新宋体");
		font.setFontHeightInPoints((short)9);
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		cellStyle.setFont(font);
		
		return cellStyle;
	}
	
	/**
	 * 获取Header单元格的样式列表
	 * @param workbook 工作簿
	 * @return
	 */
	private static ArrayList<HSSFCellStyle> getHeaderCellStyleList(HSSFWorkbook workbook) {
		
		ArrayList<HSSFCellStyle> list = new ArrayList<HSSFCellStyle>();
		
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		
		return list;
	}
	
	/**
	 * 获取数据行单元格的样式列表
	 * @param workbook 工作簿
	 * @return
	 */
	private static ArrayList<HSSFCellStyle> getBodyCellStyleList(HSSFWorkbook workbook) {
		
		ArrayList<HSSFCellStyle> list = new ArrayList<HSSFCellStyle>();
		
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyleForName(workbook));
		list.add(getBodyCellStyleForName(workbook));
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyleForName(workbook));
		
		return list;
	}
}
