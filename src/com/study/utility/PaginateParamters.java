package com.study.utility;


/**
 * 分页参数
 */
public class PaginateParamters {

	/**
	 * 页码
	 */
	private Integer pageNo;
	
	/**
	 * 每页记录数
	 */
	private Integer perPageNumber;

	public PaginateParamters() {
		
	}
	
	public PaginateParamters(Integer pageNo, Integer perPageNumber) {
		
		this.pageNo = pageNo;
		this.perPageNumber = perPageNumber;
	}
	
	public PaginateParamters(Integer pageNo) {
		
		this.pageNo = pageNo;
		this.perPageNumber = 20;
	}
	
	public Integer getStart() {
		
		return (this.pageNo - 1) * this.perPageNumber;
	}
	
	public Integer getLimit() {
		
		return this.perPageNumber;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPerPageNumber() {
		return perPageNumber;
	}

	public void setPerPageNumber(Integer perPageNumber) {
		this.perPageNumber = perPageNumber;
	}

}
