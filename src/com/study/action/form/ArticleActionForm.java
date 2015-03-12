package com.study.action.form;

import com.study.model.Article;

/**
 * 资讯 ActionForm
 */
public class ArticleActionForm extends BaseActionForm {
	
	/**
	 * 文章数据
	 */
	private Article article;
	
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
	 * 内容
	 */
	private String content;
	
	/**
	 * 文章类型
	 */
	private Integer articleType;

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

	public Integer getArticleType() {
		return articleType;
	}

	public void setArticleType(Integer articleType) {
		this.articleType = articleType;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Article getArticle() {
		return article;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
