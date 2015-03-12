package com.study.model.part;


/**
 * 按分数段统计考试报表数据类
 */
public class ReportExamResultScore implements java.io.Serializable {
	
	/**
	 * UID
	 */
	private static final long serialVersionUID = -192258998208495216L;
	
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 达标人数
	 */
	private Integer passedNum;

	/**
	 * 达标比例
	 */
	private String passedRate;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPassedNum(Integer passedNum) {
		this.passedNum = passedNum;
	}

	public Integer getPassedNum() {
		return passedNum;
	}

	public void setPassedRate(String passedRate) {
		this.passedRate = passedRate;
	}

	public String getPassedRate() {
		return passedRate;
	}


}
