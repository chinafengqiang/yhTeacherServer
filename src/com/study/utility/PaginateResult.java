package com.study.utility;

import java.util.List;

/**
 * 分页结果
 */
public class PaginateResult {

	/**
	 * 分页后的数据集
	 */
	private List list;
	
	/**
	 * 记录总数
	 */
	private Integer count;
	
	/**
	 * 页码
	 */
	private Integer pageNo;
	
	/**
	 * 每页记录数
	 */
	private Integer perPageNumber;
	
	public PaginateResult() {
		
	}
	
	public PaginateResult(List list, Integer count) {
		
		this.list = list;
		this.count = count;
	}
	
	public Integer getPageCount() {
		
		Integer pageCount = count / perPageNumber;
		
		if (count > pageCount * perPageNumber) {
			pageCount = pageCount + 1;
		}
		return pageCount;
	}
	
	public static String getTotalCountSql(String sql) {
		
		return "select count(*) as count_value from (" + sql + ") countTable";
	}
	
	public static String getTotalCountFieldName() {
		
		return "count_value";
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPerPageNumber(Integer perPageNumber) {
		this.perPageNumber = perPageNumber;
	}

	public Integer getPerPageNumber() {
		return perPageNumber;
	}

}
