package com.study.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.study.model.part.ReportExamResultScore;
import com.study.utility.ExcelWriter;

/**
 * 考试报表分数段列表Excel工具类
 */
public class ReportExamResultScoreExcelTools {

	/**
	 * 数据首行号
	 */
	public static final Integer dataRowFirst = 1;
	
	/**
	 * 将考试报表分数段数据集转换成工作簿
	 * @param scoreList 单位集
	 * @return 工作簿
	 * @throws IOException
	 */
	public static HSSFWorkbook getWorkbook(List<ReportExamResultScore> scoreList) throws IOException {
				
		ExcelWriter writer = new ExcelWriter("单位考试概况统计列表");

		writer.createCellList(dataRowFirst - 1, 0, getHeaderValueList(), getHeaderCellStyleList(writer.getWorkbook()), getHeaderWidthList(), 300);
		for (int i=0; i<scoreList.size(); i++) {
			writer.createCellList(dataRowFirst + i, 0, getBodyValueList(scoreList.get(i)), getBodyCellStyleList(writer.getWorkbook()), getHeaderWidthList(), 300);
		}
		return writer.getWorkbook();   
	}
	
	/**
	 * 获取Header行的值列表
	 * @return
	 */
	private static ArrayList<String> getHeaderValueList() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("分数段");
		list.add("达标人数");
		list.add("达标率");

		return list;
	}
	
	/**
	 * 获取Header行的单位格宽度值列表
	 * @return
	 */
	private static ArrayList<Integer> getHeaderWidthList() {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		list.add(30);
		list.add(15);
		list.add(15);

		return list;
	}
	
	/**
	 * 获取单位的数据行值列表
	 * @param score 单位
	 * @return 数据行值列表 
	 */
	private static ArrayList<String> getBodyValueList(ReportExamResultScore score) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.add(score.getName());
		list.add(score.getPassedNum().toString());
		list.add(score.getPassedRate());

		return list;
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
	 * 获取Header单元格的样式列表
	 * @param workbook 工作簿
	 * @return
	 */
	private static ArrayList<HSSFCellStyle> getHeaderCellStyleList(HSSFWorkbook workbook) {
		
		ArrayList<HSSFCellStyle> list = new ArrayList<HSSFCellStyle>();
		
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
		
		return list;
	}
}
