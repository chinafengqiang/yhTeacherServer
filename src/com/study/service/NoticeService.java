package com.study.service;

import java.io.File;

import com.study.enums.NoticeGradeEnum;
import com.study.enums.NoticeStatusEnum;
import com.study.model.Notice;
import com.study.utility.DateUtility;


/**
 * 公告业务接口
 */
public interface NoticeService {

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
	Notice createNotice(String title, String content, NoticeGradeEnum noticeGradeEnum, Integer sortFlag,
			NoticeStatusEnum statusEnum, Long curManagerId) throws Exception;
	
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
	Notice modifyNotice(Long id, String title, String content, NoticeGradeEnum noticeGradeEnum, Integer sortFlag,
			NoticeStatusEnum statusEnum, Long curManagerId) throws Exception;
	
	/**
	 * 删除公告
	 * @param id 编号
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	void removeNotice(Long id, Long curManagerId) throws Exception;
	
	/**
	 * 导入公告
	 * @param file 文件
	 * @param curManagerId 当前管理员
	 * @throws Exception
	 */
	void importNotice(File file, Long curManagerId) throws Exception;
	
	/**
	 * 导出公告
	 * @param id 公告编号
	 * @param curManagerId 当前管理员
	 * @return 公告数据
	 * @throws Exception
	 */
	String exportNotice(Long id, Long curManagerId) throws Exception;
	
	/**
	 * 上传公告图片
	 * @param file 文件
	 * @return 文件网址
	 * @throws Exception
	 */
	String uploadNoticeImage(File file) throws Exception;
}
