package com.study.model.part;


/**
 * 考试服务器监控数据类
 */
public class ExamServerData implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 9131384961389208092L;
	
	/**
	 * 考试服务器编号
	 */
	private Long examServerId;

	/**
	 * 考试编号
	 */
	private Long examId;
	
	/**
	 * 考试服务器名称
	 */
	private String examServerName;

	/**
	 * 考试名称
	 */
	private Long examName;

	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 应考人数
	 */
	private Integer totalNum;
	
	/**
	 * 参考人数
	 */
	private Integer joinedNum;
	
	/**
	 * 考完人数
	 */
	private Integer finishedNum;

	public Long getExamServerId() {
		return examServerId;
	}

	public void setExamServerId(Long examServerId) {
		this.examServerId = examServerId;
	}

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public String getExamServerName() {
		return examServerName;
	}

	public void setExamServerName(String examServerName) {
		this.examServerName = examServerName;
	}

	public Long getExamName() {
		return examName;
	}

	public void setExamName(Long examName) {
		this.examName = examName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

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

	public Integer getFinishedNum() {
		return finishedNum;
	}

	public void setFinishedNum(Integer finishedNum) {
		this.finishedNum = finishedNum;
	}
	
}
