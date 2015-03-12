package com.study.service;

import java.io.File;
import java.util.List;

import com.study.enums.ArticleStatusEnum;
import com.study.enums.ArticleTypeEnum;
import com.study.model.Article;


/**
 * 资讯业务接口
 */
public interface ArticleService {

	/**
	 * 创建文章
	 * @param title 标题
	 * @param content 内容
	 * @param articleTypeEnum 文章类型
	 * @param channel 频道
	 * @param source 来源
	 * @param keyword 关键词
	 * @param canRecommended 是否推荐
	 * @param statusEnum 状态
	 * @param curManagerId 当前管理员编号
	 * @return 文章
	 * @throws Exception
	 */
	Article createArticle(String title, String content, ArticleTypeEnum articleTypeEnum,
			String channel, String source, String keyword, Boolean canRecommended,
			ArticleStatusEnum statusEnum, Long curManagerId) throws Exception;
	
	/**
	 * 修改文章
	 * @param id 编号
	 * @param title 标题
	 * @param content 内容
	 * @param articleTypeEnum 文章类型
	 * @param channel 频道
	 * @param source 来源
	 * @param keyword 关键词
	 * @param canRecommended 是否推荐
	 * @param statusEnum 状态
	 * @param curManagerId 当前管理员编号
	 * @return
	 * @throws Exception
	 */
	Article modifyArticle(Long id, String title, String content, ArticleTypeEnum articleTypeEnum,
			String channel, String source, String keyword, Boolean canRecommended,
			ArticleStatusEnum statusEnum, Long curManagerId) throws Exception;
	
	/**
	 * 删除文章
	 * @param id 编号
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	void removeArticle(Long id, Long curManagerId) throws Exception;
	
	/**
	 * 导入文章
	 * @param file 文件
	 * @param curManagerId 当前管理员
	 * @throws Exception
	 */
	void importArticle(File file, Long curManagerId) throws Exception;
	
	/**
	 * 导出文章
	 * @param id 文章编号
	 * @param curManagerId 当前管理员
	 * @return 文章数据
	 * @throws Exception
	 */
	String exportArticle(Long id, Long curManagerId) throws Exception;
	
	/**
	 * 上传文章图片
	 * @param file 文件
	 * @return 文件网址
	 * @throws Exception
	 */
	String uploadArticleImage(File file) throws Exception;
	
	/**
	 * 修理文章名称
	 * @param list
	 * @param maxSize
	 */
	void fixTitle(List<Article> list, Integer maxSize);
}
