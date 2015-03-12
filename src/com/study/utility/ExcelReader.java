package com.study.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * Excel阅读器
 */
public class ExcelReader {

	/**
	 * 工作簿
	 */
	private HSSFWorkbook workbook;
	
	/**
	 * 读excel文件
	 * @param excelFile
	 * @throws IOException
	 */
	public ExcelReader(File excelFile) throws IOException {
		
		InputStream is = new FileInputStream(excelFile);
		POIFSFileSystem pfs = new POIFSFileSystem(is);
		workbook = new HSSFWorkbook(pfs);
	}
	
	/**
	 * 获得工作簿表数据
	 * @return
	 */
	public Integer getSheetCount() {
		return workbook.getNumberOfSheets();
	}
	
	/**
	 * 获得表最后行号
	 * @param sheetIndex
	 * @return
	 */
	public Integer getRowCount(Integer sheetIndex) {
		return workbook.getSheetAt(sheetIndex).getLastRowNum() + 1;
	}
	
	/**
	 * 获得单元格最后行号
	 * @param sheetIndex
	 * @param rowIndex
	 * @return
	 */
	public int getColCount(Integer sheetIndex, Integer rowIndex) {
		return (int)workbook.getSheetAt(sheetIndex).getRow(rowIndex).getLastCellNum();
	}
	
	/**
	 * 获得单元号
	 * @param sheetIndex
	 * @param rowIndex
	 * @param colIndex
	 * @return
	 */
	public HSSFCell getCell(Integer sheetIndex, Integer rowIndex, int colIndex) {
		return workbook.getSheetAt(sheetIndex).getRow(rowIndex).getCell((short) colIndex);
	}
	
	/**
	 * 获得单元号
	 * @param rowIndex
	 * @param colIndex
	 * @return
	 */
	public HSSFCell getCell(Integer rowIndex, int colIndex) {
		return workbook.getSheetAt(0).getRow(rowIndex).getCell((short) colIndex);
	}
	
	/**
	 * 获取单元格的字符串
	 * @param cell 单元格
	 * @return
	 */
	public String getCellValueByString(HSSFCell cell) {
		
		if (cell == null){
			return null;
		}else{
		    String cellValue = cell.getRichStringCellValue().toString(); 
		    return cellValue;
		}
	}
	
	/**
	 * 获取单元格的值
	 * @param cell
	 * @return
	 */
	public String  getCellValueByStringAndInteger(HSSFCell cell) {
		
	    if (cell == null) {
	    	return null;
	    }
	    
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			Double temp = cell.getNumericCellValue();
			if (temp != null) {
				BigDecimal bd = new BigDecimal(temp);
				
				return bd.toString();
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:
			String text = cell.getRichStringCellValue()
					.toString().trim();
			if (text.length() > 0) {
			    return cell.getRichStringCellValue().toString();
			}
			break;
		default:
		
		}
		return null;
		
	}
	
	/**
	 * 判读字符串是否为空，若非空则截取
	 * @param value
	 * @return
	 */
	public String isNull(String value) {
		
		if (value == null) {
			return "";
		} else {
			return value.trim();
		}
	}
	
	/**
	 * 获取单元格的整数值
	 * @param cell 单元格
	 * @return
	 */
	public Integer getCellValueByInteger(HSSFCell cell) {
		
		if (cell == null){
			return null;
		}else{
			try {
				return Integer.parseInt(cell.getRichStringCellValue().getString());
			} catch (Exception ex) {
				return null;
			}
		}
	}
	
	/**
	 * 获取单元格的小数值
	 * @param cell 单元格
	 * @return
	 */
	public Double getCellValueByDouble(HSSFCell cell) {
		
		if (cell == null){
			return null;
		}else{
			try{
				return Double.parseDouble(cell.getRichStringCellValue().getString());
			} catch (Exception ex){
				return null;
			}
		}
	}
	
	/**
	 * 获取单元格日期
	 * @param cell 单元格
	 * @return
	 * @throws ParseException
	 */
	public Date getCellValueByDate(HSSFCell cell) throws ParseException {
		
		if (cell == null){
			return null;
		}else{
			String cellValue = cell.getRichStringCellValue().getString();
			DateFormat df = DateFormat.getDateInstance();   
			return df.parse(cellValue);
		}
	}
}
