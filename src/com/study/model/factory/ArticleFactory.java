package com.study.model.factory;

import java.util.List;

import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.ArticleStatusEnum;
import com.study.enums.ArticleTypeEnum;
import com.study.model.Article;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 文章数据工厂接口
 */
public interface ArticleFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	Article findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<Article> findListByAll();

	/**
	 * 按搜索条件获取文章分页列表
	 * @param articleTypeEnum 类型
	 * @param title 标题
	 * @param channel 发布区域
	 * @param statusEnum 状态
	 * @param paginateParamters 分页参数
	 * @return 文章分页列表
	 */
	PaginateResult findListBySearch(ArticleTypeEnum articleTypeEnum, String title, String channel, ArticleStatusEnum statusEnum, PaginateParamters paginateParamters);
	
	/**
	 * 按发布区域获取文章分页列表
	 * @param articleTypeEnum 类型
	 * @param channel 发布区域
	 * @param content 搜索内容
	 * @param paginateParamters 分页参数
	 * @return 文章分页列表
	 */
	PaginateResult findListByChannel(ArticleTypeEnum articleTypeEnum, String channel, String content, PaginateParamters paginateParamters);
	
	/**
	 * 按发布区域获取文章分页列表
	 * @param articleTypeEnum 类型
	 * @param channel 发布区域
	 * @param paginateParamters 分页参数
	 * @return 文章分页列表
	 */
	PaginateResult findListByRecommended(ArticleTypeEnum articleTypeEnum, String channel, PaginateParamters paginateParamters);
}
