package com.study.action.form;

import com.study.model.SysParam;



/**
 * 系统 ActionForm
 */
public class SystemActionForm extends BaseActionForm {

	/**
	 * 枚举名称
	 */
	private String enumName;
	
	/**
	 * 参数名称
	 */
	private String sysParamName;
	
	/**
	 * 系统参数数据
	 */
	private SysParam sysParam;

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	public String getEnumName() {
		return enumName;
	}

	public void setSysParamName(String sysParamName) {
		this.sysParamName = sysParamName;
	}

	public String getSysParamName() {
		return sysParamName;
	}

	public void setSysParam(SysParam sysParam) {
		this.sysParam = sysParam;
	}

	public SysParam getSysParam() {
		return sysParam;
	}
}
