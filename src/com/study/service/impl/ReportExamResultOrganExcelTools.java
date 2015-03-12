package com.study.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.study.model.part.ReportExamResultOrgan;
import com.study.utility.ExcelWriter;

/**
 * 考试报表单位列表Excel工具类
 */
public class ReportExamResultOrganExcelTools {

	/**
	 * 数据首行号
	 */
	public static final Integer dataRowFirst = 1;
	
	/**
	 * 将考试报表单位数据集转换成工作簿
	 * @param organList 单位集
	 * @return 工作簿
	 * @throws IOException
	 */
	public static HSSFWorkbook getWorkbook(List<ReportExamResultOrgan> organList) throws IOException {
				
		ExcelWriter writer = new ExcelWriter("单位考试概况统计列表");

		writer.createCellList(dataRowFirst - 1, 0, getHeaderValueList(), getHeaderCellStyleList(writer.getWorkbook()), getHeaderWidthList(), 300);
		for (int i=0; i<organList.size(); i++) {
			writer.createCellList(dataRowFirst + i, 0, getBodyValueList(organList.get(i)), getBodyCellStyleList(writer.getWorkbook()), getHeaderWidthList(), 300);
		}
		return writer.getWorkbook();   
	}
	
	/**
	 * 获取Header行的值列表
	 * @return
	 */
	private static ArrayList<String> getHeaderValueList() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("单位名称");
		list.add("应考人数");
		list.add("参考人数");
		list.add("通过人数");
		list.add("参考率");
		list.add("通过率");

		return list;
	}
	
	/**
	 * 获取Header行的单位格宽度值列表
	 * @return
	 */
	private static ArrayList<Integer> getHeaderWidthList() {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		list.add(40);
		list.add(15);
		list.add(15);
		list.add(15);
		list.add(15);
		list.add(15);

		return list;
	}
	
	/**
	 * 获取单位的数据行值列表
	 * @param organ 单位
	 * @return 数据行值列表 
	 */
	private static ArrayList<String> getBodyValueList(ReportExamResultOrgan organ) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.add(organ.getName());
		list.add(organ.getTotalNum().toString());
		list.add(organ.getJoinedNum().toString());
		list.add(organ.getPassedNum().toString());
		list.add(organ.getJoinedRate());
		list.add(organ.getPassedRate());

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
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		
		return list;
	}
}
