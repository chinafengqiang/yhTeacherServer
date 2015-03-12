package com.study.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.OrganStatusEnum;
import com.study.enums.OrganTypeEnum;

/**
 * 单位数据类
 */
@Entity
@Table(name="organ")
public class Organ implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 3692920457260983770L;

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	/**
	 * 帐号
	 */
	private String name;
	
	/**
	 * 上级单位编号
	 */
	@Column(name="parent_id")
	private Long parentId;
	
	/**
	 * 名称
	 */
	@Column(name="actual_name")
	private String actualName;
	
	/**
	 * 简称
	 */
	@Column(name="short_name")
	private String shortName;
	
	/**
	 * 单位类型
	 */
	@Column(name="organ_type")
	private Integer organType;

	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 联系人
	 */
	private String linkman;
	
	/**
	 * 电话
	 */
	private String tel;
	
	/**
	 * 手机
	 */
	private String mobile;
	
	/**
	 * 地区
	 */
	private String area;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 学员人数限制
	 */
	@Column(name="user_number_limit")
	private Integer userNumberLimit;

	/**
	 * 服务期限
	 */
	@Column(name="service_time_limit")
	private Date serviceTimeLimit;
	
	/**
	 * 最后激活时间
	 */
	@Column(name="last_activated_time")
	private Date lastActivatedTime;

	//==================扩展字段 Begin ==================
	
	/**
	 * 单位类型名称
	 */
	@Transient
	public String getOrganTypeName() {
		
		return OrganTypeEnum.valueOf(this.organType).toName();
	}
	
	/**
	 * 状态名称
	 */
	@Transient
	public String getStatusName() {
		
		return OrganStatusEnum.valueOf(this.status).toName();
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Integer getOrganType() {
		return organType;
	}

	public void setOrganType(Integer organType) {
		this.organType = organType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getActualName() {
		return actualName;
	}

	public void setUserNumberLimit(Integer userNumberLimit) {
		this.userNumberLimit = userNumberLimit;
	}

	public Integer getUserNumberLimit() {
		return userNumberLimit;
	}

	public void setServiceTimeLimit(Date serviceTimeLimit) {
		this.serviceTimeLimit = serviceTimeLimit;
	}

	public Date getServiceTimeLimit() {
		return serviceTimeLimit;
	}

	public void setLastActivatedTime(Date lastActivatedTime) {
		this.lastActivatedTime = lastActivatedTime;
	}

	public Date getLastActivatedTime() {
		return lastActivatedTime;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea() {
		return area;
	}
	
}
