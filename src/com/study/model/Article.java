package com.study.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.ArticleStatusEnum;
import com.study.enums.ArticleTypeEnum;
import com.study.utility.DateUtility;

/**
 * 文章数据类
 */
@Entity
@Table(name="article")
public class Article implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 2260072584300511267L;

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 内容 
	 */
	private String content;
	
	/**
	 * 类型
	 */
	@Column(name="article_type")
	private Integer articleType;
	
	/**
	 * 发布频道
	 */
	private String channel;
	
	/**
	 * 来源 
	 */
	private String source;
	
	/**
	 * 关键词 
	 */
	private String keyword;
	
	/**
	 * 是否被推荐
	 */
	@Column(name="can_recommended")
	private Boolean canRecommended;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 创建时间 
	 */
	@Column(name="created_time")
	private Date createdTime;

	/**
	 * 创建人
	 */
	private Long creator;

	//==================扩展字段 Begin ==================
	
	/**
	 * 发布日期名称
	 */
	@Transient
	public String getCreatedTimeName() {
		
		if (this.createdTime != null) {
			return DateUtility.dateToChineseString(this.createdTime, true);
		} else {
			return "";
		}
	}
	
	/**
	 * 文章类型名称
	 */
	@Transient
	public String getArticleTypeName() {
		
		return ArticleTypeEnum.valueOf(this.articleType).toName();
	}
	
	/**
	 * 状态名称
	 */
	@Transient
	public String getStatusName() {
		
		return ArticleStatusEnum.valueOf(this.status).toName();
	}
	
	/**
	 * 是否推荐的名称
	 */
	@Transient
	public String getCanRecommendedName() {
		
		if (this.canRecommended) {
			return "是";
		} else {
			return "";
		}
	}
	
	//==================扩展字段 End ==================
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setArticleType(Integer articleType) {
		this.articleType = articleType;
	}

	public Integer getArticleType() {
		return articleType;
	}

	public void setCanRecommended(Boolean canRecommended) {
		this.canRecommended = canRecommended;
	}

	public Boolean getCanRecommended() {
		return canRecommended;
	}

}
