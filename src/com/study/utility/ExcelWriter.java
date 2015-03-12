package com.study.utility;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

public class ExcelWriter {

	/**
	 * 工作簿
	 */
	private HSSFWorkbook workbook = null;
	
	/**
	 * 定义表格
	 */
	private HSSFSheet sheet;
	
	/**
	 * 初始化表格
	 * @param sheetName 表格名称
	 */
	public ExcelWriter(String sheetName) {
		
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet(sheetName);
	}
	
	/**
	 * 创建单元格
	 * @param rowIndex 行号
	 * @param colIndex 列号
	 * @param value 值
	 * @param style 样式
	 * @param width 单位格宽度
	 * @param height 单位格高度
	 * @return 返回单元格
	 */
	public void createCell(HSSFRow row, Integer colIndex, String value, HSSFCellStyle style, Integer width, Integer height) {
		
		HSSFCell cell = row.createCell(colIndex.shortValue());
		
		cell.setCellValue(new HSSFRichTextString(value));
		cell.setCellStyle(style);
		
		row.setHeight(height.shortValue());
		sheet.setColumnWidth(colIndex.shortValue(), 256 * width.shortValue());
	}
	

	/**
	 * 创建单元格列表
	 * @param rowIndex 行号
	 * @param colIndex 列号
	 * @param valueList 值列表
	 * @param firstColStyle 首列样式
	 * @param style 样式
	 * @param lastColStyle 尾列样式
	 * @param widthList 单位格宽度列表
	 * @param height 单位格高度
	 */
	public void createCellList(Integer rowIndex, Integer colIndex, ArrayList<String> valueList, HSSFCellStyle firstColStyle, ArrayList<HSSFCellStyle> styleList, HSSFCellStyle lastColStyle, ArrayList<Integer> widthList, Integer height) {
		
		HSSFRow row = sheet.createRow(rowIndex.shortValue());
		for (int i=0; i<valueList.size(); i++) {
			
			if (i == 0) {
				this.createCell(row, colIndex + i, valueList.get(i), firstColStyle, widthList.get(i), height);
				continue;
			}
			
			if (i == valueList.size() - 1) {
				this.createCell(row, colIndex + i, valueList.get(i), lastColStyle, widthList.get(i), height);
				continue;
			}
			
			this.createCell(row, colIndex + i, valueList.get(i), styleList.get(i), widthList.get(i), height);
		}
	}
	
	/**
	 * 创建单元格列表
	 * @param rowIndex 行号
	 * @param colIndex 列号
	 * @param valueList 值列表
	 * @param style 样式
	 * @param widthList 单位格宽度列表
	 * @param height 单位格高度
	 */
	public void createCellList(Integer rowIndex, Integer colIndex, ArrayList<String> valueList, ArrayList<HSSFCellStyle> styleList, ArrayList<Integer> widthList, Integer height) {
		
		HSSFRow row = sheet.createRow(rowIndex.shortValue());
		for (int i=0; i<valueList.size(); i++) {
			this.createCell(row, colIndex + i, valueList.get(i), styleList.get(i), widthList.get(i), height);
		}
	}
	
	/**
	 * 创建合并的行
	 * @param rowIndex 行号
	 * @param colFirst 列首号
	 * @param colLast 列尾号
	 * @param value 值列表
	 * @param style 样式
	 * @param height 单位格高度
	 */
	public void createMergedRow(Integer rowIndex, Integer colFirst, Integer colLast, String value, HSSFCellStyle style, Integer height) {
		
		HSSFRow row = sheet.createRow(rowIndex.shortValue());
		this.sheet.addMergedRegion(new Region(rowIndex, colFirst.shortValue(), rowIndex, colLast.shortValue()));
		
		HSSFCell cell = row.createCell(colFirst.shortValue());
		
		cell.setCellValue(new HSSFRichTextString(value));
		cell.setCellStyle(style);
		
		row.setHeight(height.shortValue());
	}
	
	/**
	 * 合并单元格
	 * @param rowFirst 行号首
	 * @param colFirst 列号首
	 * @param rowLast 行号尾
	 * @param colLast 列号尾
	 */
	public void addMergedRegion(Integer rowFirst, Integer colFirst, Integer rowLast, Integer colLast) {
		
		this.sheet.addMergedRegion(new Region(rowFirst, colFirst.shortValue(), rowLast, colLast.shortValue()));
	}
	
	/**
	 * 获得工作簿
	 */
	public HSSFWorkbook getWorkbook() {
		return workbook;
	}
}
