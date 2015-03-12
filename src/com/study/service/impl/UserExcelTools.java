package com.study.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.study.model.User;
import com.study.utility.ExcelReader;
import com.study.utility.ExcelWriter;
import com.study.utility.StringUtility;

/**
 * 学员与Excel的转换工具
 */
public class UserExcelTools {

	/**
	 * 数据首行号
	 */
	public static final Integer dataRowFirst = 3;
	
	/**
	 * 将学员集转换成工作簿
	 * @param userList 学员集
	 * @return 工作簿
	 * @throws IOException
	 */
	public static HSSFWorkbook getWorkbook(List<User> userList, String dutyRank, String trade) throws IOException {
		
		String note = "说明：\n" 
			    + "1、学员信息必须与您系统中的保持一致，否则信息不能导入系统中。\n"
				+ "2、职务级别如下，请按下面职务进行填写。\n"
				+ dutyRank+"\n"
				+ "3、行业如下，请按下面行业进行填写。\n"
				+ trade+"\n"
				+ "4、所填内容中不能出现“×、÷、>、&、<、±”等特殊符号，如果需要的话，可以使用“*、/、》、＋、《”或直接写文字表示。\n"
				+ "5、学员信息若每两人之间存在空行，则会造成信息导入不完整；在完成本表后请将原本表中自带的示例删除。\n"
				+ "6、登录帐号为6-12位有效数字或字母，必学填写，推荐按此格式：单位编码00001。手机号码需要正确填写\n";
		
		ExcelWriter writer = new ExcelWriter("学员列表");
		writer.createMergedRow(dataRowFirst - 3, 0, 7, "系统学员列表", getTitleCellStyle(writer.getWorkbook()), 600);
		writer.createMergedRow(dataRowFirst - 2, 0, 7, note, getNoteCellStyle(writer.getWorkbook()), 2400);
		writer.createCellList(dataRowFirst - 1, 0, getHeaderValueList(), getHeaderCellStyleList(writer.getWorkbook()), getHeaderWidthList(), 300);
		
		ArrayList<HSSFCellStyle> bodyCellStyleList= getBodyCellStyleList(writer.getWorkbook());
		for (int i=0; i<userList.size(); i++) {
			writer.createCellList(dataRowFirst + i, 0, getBodyValueList(userList.get(i)), bodyCellStyleList, getHeaderWidthList(), 300);
		}
		return writer.getWorkbook();   
	}
	
	/**
	 * 从Excel文件中获取学员列表
	 * @param file 文件
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<User> getUserList(File file) throws Exception {
		
		ExcelReader reader = new ExcelReader(file);	
		
		ArrayList<User> list = new ArrayList<User>();
		
		for(int i=dataRowFirst; i<reader.getRowCount(0); i++){
			
			//获取Excel行数据
			String actualName = StringUtility.trimSpaceCharacter(reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i, 0))));
			String dutyRank = reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i, 1)));
			String organ = reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i, 2)));
			String name = StringUtility.trimSpaceCharacter(reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i, 3))));
			String trade = reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i, 4)));
			String mobile = reader.isNull(reader.getCellValueByStringAndInteger(reader.getCell(0, i, 5)));
	
			//如果一行中的所有字段都为空，则跳过，默认表示到了最后一行
			if (StringUtils.isBlank(actualName + dutyRank + organ + trade )) {
				break;
			}
			
			//校验登录帐号
			if(StringUtils.isBlank(name)){
				throw new Exception("数据校验出错:第" + (i + 1) + "行,登录帐号为空!" );
			}
			
			//校验真实姓名
			if (StringUtils.isBlank(actualName)){
				throw new Exception("数据校验出错:第" + (i + 1) + "行,学员姓名为空!" );
			}
					
			//校验职务级别
			if (StringUtils.isBlank(dutyRank)) {
				throw new Exception("数据校验出错:第" + (i + 1) + "行,职务级别项有问题!" );
			}
	
			//校验所在单位
			if (StringUtils.isBlank(organ)) {
				throw new Exception("数据校验出错:第" + (i + 1) + "行,所在单位项有问题!" );
			}
			
			User user = new User();
			
			user.setActualName(actualName);
			user.setDutyRank(dutyRank);
			user.setTrade(trade);
			user.setActualOrgan(organ);
			user.setName(name);
			user.setMobile(mobile);
	
			list.add(user);
		}
	
		return list;
	}
	
	/**
	 * 获取学员的数据行值列表
	 * @param user 学员
	 * @return 数据行值列表 
	 */
	private static ArrayList<String> getBodyValueList(User user) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.add(user.getActualName());
		list.add(user.getDutyRank());
		list.add(user.getActualOrgan());
		list.add(user.getName());
		list.add(user.getTrade());
		list.add(user.getMobile());

		return list;
	}
	
	/**
	 * 获取Header行的值列表
	 * @return
	 */
	private static ArrayList<String> getHeaderValueList() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("姓名");
		list.add("职务级别");
		list.add("所属单位");
		list.add("登录帐号");
		list.add("行业");
		list.add("手机");

		return list;
	}
	
	/**
	 * 获取Header行的单位格宽度值列表
	 * @return
	 */
	private static ArrayList<Integer> getHeaderWidthList() {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		list.add(22);
		list.add(22);
		list.add(60);
		list.add(22);
		list.add(22);
		list.add(30);

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
	 * 获取数据行中学员名称的单位格样式
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
		list.add(getBodyCellStyle(workbook));
		
		return list;
	}
}
