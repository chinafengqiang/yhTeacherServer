package com.study.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.study.dao.DAOFacade;
import com.study.enums.ArticleStatusEnum;
import com.study.enums.ArticleTypeEnum;
import com.study.model.Article;
import com.study.model.factory.ModelFactoryFacade;
import com.study.model.us.USLink;
import com.study.service.ArticleService;
import com.study.service.ManagerService;
import com.study.service.SystemService;
import com.study.utility.DateUtility;
import com.study.utility.FileUtility;
import com.study.utility.JSONConvertor;
import com.study.utility.StringUtility;
import com.study.utility.SystemUtility;

/**
 * 资讯业务接口实现类
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(ArticleServiceImpl.class);
	
	/**
	 * 数据操作门面
	 */
	@Resource
	private DAOFacade daoFacade;
	
	/**
	 * 数据工厂门面 
	 */
	@Resource
	private ModelFactoryFacade modelFactoryFacade;
	
	/**
	 * 教师业务接口 
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * 系统业务接口 
	 */
	@Resource
	private SystemService systemService;
	
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
	public Article createArticle(String title, String content, ArticleTypeEnum articleTypeEnum,
			String channel, String source, String keyword, Boolean canRecommended,
			ArticleStatusEnum statusEnum, Long curManagerId) throws Exception {
	
		//创建文章
		Article article = new Article();
		
		article.setTitle(title);
		article.setContent(content);
		article.setArticleType(articleTypeEnum.toValue());
		article.setChannel(channel);
		article.setSource(source);
		article.setKeyword(keyword);
		article.setCanRecommended(canRecommended);
		article.setStatus(statusEnum.toValue());
		article.setCreatedTime(DateUtility.getCurDate());
		article.setCreator(curManagerId);
		
		this.daoFacade.getArticleDAO().insert(article);
		
		return article;
	}
	
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
	public Article modifyArticle(Long id, String title, String content, ArticleTypeEnum articleTypeEnum,
			String channel, String source, String keyword, Boolean canRecommended,
			ArticleStatusEnum statusEnum, Long curManagerId) throws Exception {
	
		//获取文章
		Article article = this.modelFactoryFacade.getArticleFactory().findById(id);
		
		//判断是否存在
		if (article == null) {
			throw new Exception("此文章已不存在！");
		}
		
		//校验记录编辑权限
		this.managerService.validateManagerDataAccess(article.getCreator(), curManagerId);
		
		//修改文章
		article.setTitle(title);
		article.setContent(content);
		article.setArticleType(articleTypeEnum.toValue());
		article.setChannel(channel);
		article.setSource(source);
		article.setKeyword(keyword);
		article.setCanRecommended(canRecommended);
		article.setStatus(statusEnum.toValue());
		
		this.daoFacade.getArticleDAO().update(article);
		
		return article;
	}
	
	/**
	 * 删除文章
	 * @param id 编号
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	public void removeArticle(Long id, Long curManagerId) throws Exception {
		
		//获取文章
		Article article = this.modelFactoryFacade.getArticleFactory().findById(id);
		
		//判断是否存在
		if (article == null) {
			throw new Exception("此文章已不存在！");
		}
		
		//校验记录编辑权限
		this.managerService.validateManagerDataAccess(article.getCreator(), curManagerId);
		
		//删除记录
		this.daoFacade.getArticleDAO().delete(id);
	}
	
	/**
	 * 导入文章
	 * @param file 文件
	 * @param curManagerId 当前管理员
	 * @throws Exception
	 */
	public void importArticle(File file, Long curManagerId) throws Exception {
		
		String data = FileUtility.readTXT(file, "UTF-8");
		
		//进行解压缩、解密处理
		String json = this.systemService.decryptData(data);
		
		//析出记录并创建
		Article article = (Article)JSONConvertor.json2Bean(json, Article.class);
		this.createArticle(article.getTitle(), article.getContent(), ArticleTypeEnum.valueOf(article.getArticleType()), article.getChannel(), article.getSource(), article.getKeyword(), article.getCanRecommended(), ArticleStatusEnum.valueOf(article.getStatus()), curManagerId);
	}
	
	/**
	 * 导出文章
	 * @param id 文章编号
	 * @param curManagerId 当前管理员
	 * @return 文章数据
	 * @throws Exception
	 */
	public String exportArticle(Long id, Long curManagerId) throws Exception {
		
		//获取文章
		Article article = this.modelFactoryFacade.getArticleFactory().findById(id);
		
		//判断是否存在
		if (article == null) {
			throw new Exception("此文章已不存在！");
		}
		
		//校验记录编辑权限
		this.managerService.validateManagerDataAccess(article.getCreator(), curManagerId);
		
		//构造Json
		String json = JSONConvertor.bean2Json(article);
		
		//加密压缩
		json = this.systemService.encryptData(json);
		
		return json;
	}
	
	/**
	 * 上传文章图片
	 * @param file 文件
	 * @return 文件网址
	 * @throws Exception
	 */
	public String uploadArticleImage(File file) throws Exception {
		
		//获取OSS存储空间配置
        String accessKeyId = this.systemService.getOSSAccessKeyId();
        String accessKeySecret = this.systemService.getOSSAccessKeySecret();
        String bucketName = this.systemService.getOSSBucketName();
        String bucketUrl = this.systemService.getOSSBucketUrl();
        
        //生成文件名
        String key = "article/" + SystemUtility.createUUID() + ".jpg";
        
        //初始化客户端
        OSSClient client = new OSSClient(accessKeyId, accessKeySecret);

        //配置元数据
	    ObjectMetadata meta = new ObjectMetadata();
	    meta.setContentType("image/jpeg");
	    meta.setContentLength(file.length());

	    //上传文件
	    InputStream content = new FileInputStream(file);
	    client.putObject(bucketName, key, content, meta);
	    
	    //返回图片调用网址
	    return bucketUrl + key;
	}
	
	/**
	 * 修理文章名称
	 * @param list
	 * @param maxSize
	 */
	public void fixTitle(List<Article> list, Integer maxSize) {
		
		for (Article article : list) {
			
			article.setTitle(StringUtility.subStringCN(article.getTitle(),maxSize));
		}
	}
}
