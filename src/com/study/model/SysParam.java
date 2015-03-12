package com.study.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.SysParamTypeEnum;
import com.study.enums.SysParamValueTypeEnum;

/**
 * 系统参数数据类
 */
@Entity
@Table(name="sys_param")
public class SysParam implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 5937808632809240270L;

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	/**
	 * 类型
	 */
	@Column(name="sys_param_type")
	private Integer sysParamType;
	
	/**
	 * 参数值类型
	 */
	@Column(name="sys_param_value_type")
	private Integer sysParamValueType;

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 内容 
	 */
	private String value;
	
	/**
	 * 备注
	 */
	private String note;

	/**
	 * 是否可维护
	 */
	@Column(name="can_modify")
	private Boolean canModify;
	
	//==================扩展字段 Begin ==================
	
	/**
	 * 参数类型名称
	 */
	@Transient
	public String getSysParamTypeName() {
		
		return SysParamTypeEnum.valueOf(this.sysParamType).toName();
	}
	
	/**
	 * 参数值类型名称
	 */
	@Transient
	public String getSysParamValueTypeName() {
		
		return SysParamValueTypeEnum.valueOf(this.sysParamValueType).toName();
	}
	
	//==================扩展字段 End ==================
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getCanModify() {
		return canModify;
	}

	public void setCanModify(Boolean canModify) {
		this.canModify = canModify;
	}

	public void setSysParamType(Integer sysParamType) {
		this.sysParamType = sysParamType;
	}

	public Integer getSysParamType() {
		return sysParamType;
	}

	public void setSysParamValueType(Integer sysParamValueType) {
		this.sysParamValueType = sysParamValueType;
	}

	public Integer getSysParamValueType() {
		return sysParamValueType;
	}

}
