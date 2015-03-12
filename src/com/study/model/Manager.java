package com.study.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.ManagerGradeEnum;
import com.study.enums.ManagerStatusEnum;
import com.study.enums.SysAccessTypeEnum;

/**
 * 管理员数据类
 */
@Entity
@Table(name="manager")
public class Manager implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 2143434273975523542L;

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 姓名
	 */
	@Column(name="actual_name")
	private String actualName;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 手机 
	 */
	private String mobile;
	
	/**
	 * 电子邮件 
	 */
	private String email;
	
	/**
	 * 系统权限集
	 */
	@Column(name="sys_access")
	private String sysAccess;

	/**
	 * 级别 
	 */
	private Integer grade;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	//==================扩展字段 Begin ==================
	
	/**
	 * 级别名称
	 */
	@Transient
	public String getGradeName() {
		
		return ManagerGradeEnum.valueOf(this.grade).toName();
	}
	
	/**
	 * 状态名称
	 */
	@Transient
	public String getStatusName() {
		
		return ManagerStatusEnum.valueOf(this.status).toName();
	}
	
	/**
	 * 权限集名称
	 */
	@Transient
	public String getSysAccessName() {
		
		if (StringUtils.isBlank(this.sysAccess)) {
			return "";
		}
		
		String[] accessList = this.sysAccess.split(";");
		
		String accessNameList = "";
		for (String access : accessList) {
			accessNameList = accessNameList + SysAccessTypeEnum.valueOf(Integer.parseInt(access)).toName() + ";";
 		}
		return accessNameList;
	}
	
	//==================扩展字段 End ==================

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setSysAccess(String sysAccess) {
		this.sysAccess = sysAccess;
	}

	public String getSysAccess() {
		return sysAccess;
	}

}
