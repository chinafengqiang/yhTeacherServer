package com.study.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.study.enums.OrganTypeEnum;
import com.study.model.Organ;
import com.study.utility.DateUtility;
import com.study.utility.ExcelWriter;

/**
 * 单位Excel工具类
 */
public class OrganExcelTools {

	/**
	 * 数据首行号
	 */
	public static final Integer dataRowFirst = 1;
	
	/**
	 * 将单位集转换成工作簿
	 * @param organList 单位集
	 * @return 工作簿
	 * @throws IOException
	 */
	public static HSSFWorkbook getWorkbook(List<Organ> organList, Boolean bAll) throws IOException {
				
		ExcelWriter writer = new ExcelWriter("单位列表");

		writer.createCellList(dataRowFirst - 1, 0, getHeaderValueList(bAll), getHeaderCellStyleList(writer.getWorkbook(), bAll), getHeaderWidthList(bAll), 300);
		
		ArrayList<HSSFCellStyle> bodyCellStyleList= getBodyCellStyleList(writer.getWorkbook(), bAll);
		for (int i=0; i<organList.size(); i++) {
			writer.createCellList(dataRowFirst + i, 0, getBodyValueList(organList.get(i), bAll), bodyCellStyleList, getHeaderWidthList(bAll), 300);
		}
		return writer.getWorkbook();   
	}
	
	/**
	 * 获取Header行的值列表
	 * @return
	 */
	private static ArrayList<String> getHeaderValueList(Boolean bAll) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("编号");
		list.add("帐号");
		if (bAll) {
			list.add("密码");
			list.add("上级单位编号");
		}
		list.add("单位类型");
		list.add("单位名称");
		list.add("单位简称");
		list.add("地址");
		list.add("电话");
		list.add("联系人");
		list.add("手机");
		list.add("区县");
		list.add("地址");
		list.add("状态");
		if (bAll) {
			list.add("学员人数限制");
			list.add("服务期限");
			list.add("最后激活时间");
		}

		return list;
	}
	
	/**
	 * 获取Header行的单位格宽度值列表
	 * @return
	 */
	private static ArrayList<Integer> getHeaderWidthList(Boolean bAll) {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		list.add(10);
		list.add(15);
		if (bAll) {
			list.add(15);
			list.add(15);
		}
		list.add(15);
		list.add(40);
		list.add(20);
		list.add(40);
		list.add(15);
		list.add(15);
		list.add(15);
		list.add(15);
		list.add(30);
		list.add(15);	
		if (bAll) {
			list.add(15);
			list.add(15);
			list.add(15);
		}

		return list;
	}
	
	/**
	 * 获取单位的数据行值列表
	 * @param organ 单位
	 * @return 数据行值列表 
	 */
	private static ArrayList<String> getBodyValueList(Organ organ, Boolean bAll) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.add(organ.getId().toString());
		list.add(organ.getName());
		if (bAll) {
			list.add(organ.getPassword());
			list.add(organ.getParentId().toString());
		}
		list.add(OrganTypeEnum.valueOf(organ.getOrganType()).toName());
		list.add(organ.getActualName());
		list.add(organ.getShortName());
		list.add(organ.getAddress());
		list.add(organ.getTel());
		list.add(organ.getLinkman());
		list.add(organ.getMobile());
		list.add(organ.getArea());
		list.add(organ.getAddress());
		list.add(organ.getStatusName());
		if (bAll) {
			list.add(organ.getUserNumberLimit().toString());
			list.add(DateUtility.dateToChineseString(organ.getServiceTimeLimit(), true));
			list.add(DateUtility.dateToChineseString(organ.getLastActivatedTime(), true));
		}
		
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
	private static ArrayList<HSSFCellStyle> getHeaderCellStyleList(HSSFWorkbook workbook, Boolean bAll) {
		
		ArrayList<HSSFCellStyle> list = new ArrayList<HSSFCellStyle>();
		
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		if (bAll) {
			list.add(getHeaderCellStyle(workbook));
			list.add(getHeaderCellStyle(workbook));
		}
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		list.add(getHeaderCellStyle(workbook));
		if (bAll) {
			list.add(getHeaderCellStyle(workbook));
			list.add(getHeaderCellStyle(workbook));
			list.add(getHeaderCellStyle(workbook));
		}
		return list;
	}
	
	/**
	 * 获取数据行单元格的样式列表
	 * @param workbook 工作簿
	 * @return
	 */
	private static ArrayList<HSSFCellStyle> getBodyCellStyleList(HSSFWorkbook workbook, Boolean bAll) {
		
		ArrayList<HSSFCellStyle> list = new ArrayList<HSSFCellStyle>();
		
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		if (bAll) {
			list.add(getBodyCellStyle(workbook));
			list.add(getBodyCellStyle(workbook));
		}
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		list.add(getBodyCellStyle(workbook));
		if (bAll) {
			list.add(getBodyCellStyle(workbook));
			list.add(getBodyCellStyle(workbook));
			list.add(getBodyCellStyle(workbook));
		}
		
		return list;
	}
}
