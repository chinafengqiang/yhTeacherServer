package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.ArticleStatusEnum;
import com.study.enums.ArticleTypeEnum;
import com.study.model.Article;
import com.study.model.User;
import com.study.model.factory.ArticleFactory;
import com.study.utility.DataTypeConvertor;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 文章记录数据工厂实现类
 */
@Repository
public class ArticleFactoryImpl implements ArticleFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public Article findById(Long id) {
		
		return (Article)this.hibernateTemplate.get(Article.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<Article> findListByAll() {
		
		String sql = "select a.* from article a";
		return (List<Article>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Article.class).list();		
	}
	
	/**
	 * 按搜索条件获取文章分页列表
	 * @param articleTypeEnum 类型
	 * @param title 标题
	 * @param channel 发布区域
	 * @param statusEnum 状态
	 * @param paginateParamters 分页参数
	 * @return 文章分页列表
	 */
	public PaginateResult findListBySearch(ArticleTypeEnum articleTypeEnum, String title, String channel, ArticleStatusEnum statusEnum, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from article a where 1=1";
		
		if (articleTypeEnum != null) {
			sql = sql + " and a.article_type=" + articleTypeEnum.toValue();
		}
		
		if (!StringUtils.isBlank(title)) {
			sql = sql + " and (a.title like '%" + title + "%' or a.keyword like '%" + title + "%')";
		}
		
		if (!StringUtils.isBlank(channel)) {
			sql = sql + " and a.channel like '%" + channel + "%'";
		}

		if (statusEnum != null) {
			sql = sql + " and a.status=" + statusEnum.toValue();
		}
		
		sql = sql + " order by a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Article.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 按发布区域获取文章分页列表
	 * @param articleTypeEnum 类型
	 * @param channel 发布区域
	 * @param content 搜索内容
	 * @param paginateParamters 分页参数
	 * @return 文章分页列表
	 */
	public PaginateResult findListByChannel(ArticleTypeEnum articleTypeEnum, String channel, String content, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from article a where a.status=" + ArticleStatusEnum.Opened.toValue();
		
		if (articleTypeEnum != null) {
			sql = sql + " and a.article_type=" + articleTypeEnum.toValue();
		}
		
		if (!StringUtils.isBlank(channel)) {
			sql = sql + " and a.channel like '%" + channel + "%'";
		}
		
		if (!StringUtils.isBlank(content)) {
			sql = sql + " and (a.title like '%" + content + "%' or a.content like '%" + content + "%')";
		}

		sql = sql + " order by a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Article.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 按发布区域获取文章分页列表
	 * @param articleTypeEnum 类型
	 * @param channel 发布区域
	 * @param paginateParamters 分页参数
	 * @return 文章分页列表
	 */
	public PaginateResult findListByRecommended(ArticleTypeEnum articleTypeEnum, String channel, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from article a where a.status=" + ArticleStatusEnum.Opened.toValue() + " and a.can_recommended=" + DataTypeConvertor.booleanToInteger(true);
		
		sql = sql + " and a.article_type=" + articleTypeEnum.toValue();
		sql = sql + " and a.channel='" + channel + "'";
		sql = sql + " order by a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Article.class).paginateResult(paginateParamters);		
	}	
}
