package com.study.action.form;

import com.study.model.Notice;

/**
 * 公告  ActionForm
 */
public class NoticeActionForm extends BaseActionForm {
	
	/**
	 * 公告数据
	 */
	private Notice notice;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 频道
	 */
	private String channel;
	
	/**
	 * 公告类型
	 */
	private Integer noticeGrade;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getNoticeGrade() {
		return noticeGrade;
	}

	public void setNoticeGrade(Integer noticeGrade) {
		this.noticeGrade = noticeGrade;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public Notice getNotice() {
		return notice;
	}
}
