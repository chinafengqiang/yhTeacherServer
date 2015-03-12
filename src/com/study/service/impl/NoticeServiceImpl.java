package com.study.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.study.dao.DAOFacade;
import com.study.enums.NoticeGradeEnum;
import com.study.enums.NoticeStatusEnum;
import com.study.model.Notice;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ManagerService;
import com.study.service.NoticeService;
import com.study.service.SystemService;
import com.study.utility.DateUtility;
import com.study.utility.FileUtility;
import com.study.utility.JSONConvertor;
import com.study.utility.SystemUtility;

/**
 * 公告业务接口实现类
 */
@Service
public class NoticeServiceImpl implements NoticeService {

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(NoticeServiceImpl.class);
	
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
	 * 教师业务接口 
	 */
	@Resource
	private SystemService systemService;
	
	/**
	 * 创建公告
	 * @param title 标题
	 * @param content 内容
	 * @param noticeGradeEnum 公告级别
	 * @param sortFlag 排序
	 * @param statusEnum 状态
	 * @param curManagerId 当前管理员编号
	 * @return 公告
	 * @throws Exception
	 */
	public Notice createNotice(String title, String content, NoticeGradeEnum noticeGradeEnum, Integer sortFlag,
			NoticeStatusEnum statusEnum, Long curManagerId) throws Exception {
	
		//创建公告
		Notice notice = new Notice();
		
		notice.setTitle(title);
		notice.setContent(content);
		notice.setGrade(noticeGradeEnum.toValue());
		notice.setSortFlag(sortFlag);
		notice.setStatus(statusEnum.toValue());
		notice.setCreatedTime(DateUtility.getCurDate());
		notice.setCreator(curManagerId);
		
		this.daoFacade.getNoticeDAO().insert(notice);
		
		return notice;
	}
	
	/**
	 * 修改公告
	 * @param id 编号
	 * @param title 标题
	 * @param content 内容
	 * @param noticeGradeEnum 公告级别
	 * @param sortFlag 排序
	 * @param statusEnum 状态
	 * @param curManagerId 当前管理员编号
	 * @return
	 * @throws Exception
	 */
	public Notice modifyNotice(Long id, String title, String content, NoticeGradeEnum noticeGradeEnum, Integer sortFlag,
			NoticeStatusEnum statusEnum, Long curManagerId) throws Exception {
	
		//获取公告
		Notice notice = this.modelFactoryFacade.getNoticeFactory().findById(id);
		
		//判断是否存在
		if (notice == null) {
			throw new Exception("此公告已不存在！");
		}
		
		//校验记录编辑权限
		this.managerService.validateManagerDataAccess(notice.getCreator(), curManagerId);
		
		//修改公告
		notice.setTitle(title);
		notice.setContent(content);
		notice.setGrade(noticeGradeEnum.toValue());
		notice.setSortFlag(sortFlag);
		notice.setStatus(statusEnum.toValue());
		
		this.daoFacade.getNoticeDAO().update(notice);
		
		return notice;
	}
	
	/**
	 * 删除公告
	 * @param id 编号
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	public void removeNotice(Long id, Long curManagerId) throws Exception {
		
		//获取公告
		Notice notice = this.modelFactoryFacade.getNoticeFactory().findById(id);
		
		//判断是否存在
		if (notice == null) {
			return;
		}
		
		//校验记录编辑权限
		this.managerService.validateManagerDataAccess(notice.getCreator(), curManagerId);
		
		//删除记录
		this.daoFacade.getNoticeDAO().delete(id);
	}
	
	/**
	 * 导入公告
	 * @param file 文件
	 * @param curManagerId 当前管理员
	 * @throws Exception
	 */
	public void importNotice(File file, Long curManagerId) throws Exception {
		
		String data = FileUtility.readTXT(file, "UTF-8");
		
		//进行解压缩、解密处理
		String json = this.systemService.decryptData(data);
		
		//析出记录并创建
		Notice notice = (Notice)JSONConvertor.json2Bean(json, Notice.class);
		this.createNotice(notice.getTitle(), notice.getContent(), NoticeGradeEnum.valueOf(notice.getGrade()), notice.getSortFlag(), NoticeStatusEnum.valueOf(notice.getStatus()), curManagerId);
	}
	
	/**
	 * 导出公告
	 * @param id 公告编号
	 * @param curManagerId 当前管理员
	 * @return 公告数据
	 * @throws Exception
	 */
	public String exportNotice(Long id, Long curManagerId) throws Exception {
		
		//获取公告
		Notice notice = this.modelFactoryFacade.getNoticeFactory().findById(id);
		
		//判断是否存在
		if (notice == null) {
			throw new Exception("此公告已不存在！");
		}
		
		//校验记录编辑权限
		this.managerService.validateManagerDataAccess(notice.getCreator(), curManagerId);
		
		//构造Json
		String json = JSONConvertor.bean2Json(notice);
		
		//加密压缩
		json = this.systemService.encryptData(json);
		
		return json;
	}
	
	/**
	 * 上传公告图片
	 * @param file 文件
	 * @return 文件网址
	 * @throws Exception
	 */
	public String uploadNoticeImage(File file) throws Exception {
		
		//获取OSS存储空间配置
        String accessKeyId = this.systemService.getOSSAccessKeyId();
        String accessKeySecret = this.systemService.getOSSAccessKeySecret();
        String bucketName = this.systemService.getOSSBucketName();
        String bucketUrl = this.systemService.getOSSBucketUrl();
        
        //生成文件名
        String key = "notice/" + SystemUtility.createUUID() + ".jpg";
        
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
}
