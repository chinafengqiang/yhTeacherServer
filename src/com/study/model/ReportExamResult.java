package com.study.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 考试报表结果数据类
 */
@Entity
@Table(name="report_exam_result")
public class ReportExamResult implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 7694624840734004080L;

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	/**
	 * 考试报表编号
	 */
	@Column(name="report_exam_id")
	private Long reportExamId;
	
	/**
	 * 上级单位编号
	 */
	@Column(name="parent_organ_id")
	private Long parentOrganId;
	
	/**
	 * 单位编号
	 */
	@Column(name="organ_id")
	private Long organId;
	
	/**
	 * 单位名称
	 */
	@Column(name="organ_name")
	private String organName;
	
	/**
	 * 单位类型
	 */
	@Column(name="organ_type")
	private Integer organType;
	
	/**
	 * 统计结果类型
	 */
	@Column(name="result_type")
	private Integer resultType;
	
	/**
	 * 应考人数
	 */
	@Column(name="total_num")
	private Integer totalNum;
	
	/**
	 * 参考人数
	 */
	@Column(name="joined_num")
	private Integer joinedNum;
	
	/**
	 * 通过人数
	 */
	@Column(name="passed_num")
	private Integer passedNum;

	/**
	 * 0-59分人数
	 */
	@Column(name="score_59_num")
	private Integer score59Num;
	
	/**
	 * 60-69分人数
	 */
	@Column(name="score_60to69_num")
	private Integer score60TO69Num;

	/**
	 * 70-79分人数
	 */
	@Column(name="score_70to79_num")
	private Integer score70TO79Num;

	/**
	 * 80-89分人数
	 */
	@Column(name="score_80to89_num")
	private Integer score80TO89Num;

	/**
	 * 90分以上人数
	 */
	@Column(name="score_90_num")
	private Integer score90Num;

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
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

	public Integer getPassedNum() {
		return passedNum;
	}

	public void setPassedNum(Integer passedNum) {
		this.passedNum = passedNum;
	}

	public Integer getScore59Num() {
		return score59Num;
	}

	public void setScore59Num(Integer score59Num) {
		this.score59Num = score59Num;
	}

	public Integer getScore60TO69Num() {
		return score60TO69Num;
	}

	public void setScore60TO69Num(Integer score60to69Num) {
		score60TO69Num = score60to69Num;
	}

	public Integer getScore70TO79Num() {
		return score70TO79Num;
	}

	public void setScore70TO79Num(Integer score70to79Num) {
		score70TO79Num = score70to79Num;
	}

	public Integer getScore80TO89Num() {
		return score80TO89Num;
	}

	public void setScore80TO89Num(Integer score80to89Num) {
		score80TO89Num = score80to89Num;
	}

	public Integer getScore90Num() {
		return score90Num;
	}

	public void setScore90Num(Integer score90Num) {
		this.score90Num = score90Num;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReportExamId() {
		return reportExamId;
	}

	public void setReportExamId(Long reportExamId) {
		this.reportExamId = reportExamId;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public void setParentOrganId(Long parentOrganId) {
		this.parentOrganId = parentOrganId;
	}

	public Long getParentOrganId() {
		return parentOrganId;
	}

	public void setOrganType(Integer organType) {
		this.organType = organType;
	}

	public Integer getOrganType() {
		return organType;
	}

	public void setResultType(Integer resultType) {
		this.resultType = resultType;
	}

	public Integer getResultType() {
		return resultType;
	}
	
}