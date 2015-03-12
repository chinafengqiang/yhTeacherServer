package com.study.model.part;


/**
 * 按下级各单位统计考试报表数据类
 */
public class ReportExamResultOrgan implements java.io.Serializable {
	
	/**
	 * UID
	 */
	private static final long serialVersionUID = -192258998208495216L;
	
	/**
	 * 单位名称
	 */
	private String name;
	
	/**
	 * 应考人数
	 */
	private Integer totalNum;
	
	/**
	 * 参考人数
	 */
	private Integer joinedNum;
	
	/**
	 * 通过人数
	 */
	private Integer passedNum;

	/**
	 * 参考比例
	 */
	private String joinedRate;

	/**
	 * 通过比例
	 */
	private String passedRate;

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getJoinedNum() {
		return joinedNum;
	}

	public void setJoinedNum(Integer joinedNum) {
		this.joinedNum = joinedNum;
	}

	public Integer getPassedNum() {
		return passedNum;
	}

	public void setPassedNum(Integer passedNum) {
		this.passedNum = passedNum;
	}

	public String getJoinedRate() {
		return joinedRate;
	}

	public void setJoinedRate(String joinedRate) {
		this.joinedRate = joinedRate;
	}

	public String getPassedRate() {
		return passedRate;
	}

	public void setPassedRate(String passedRate) {
		this.passedRate = passedRate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
