package com.study.service;

import java.io.File;
import java.util.List;

import com.study.enums.CourseStatusEnum;
import com.study.enums.CourseTypeEnum;
import com.study.model.Course;
import com.study.model.CourseCategory;
import com.study.model.CourseChapter;


/**
 * 课程业务接口
 */
public interface CourseService {

	/**
	 * 获取课程章节内容
	 * @param id 章节编号
	 * @return
	 * @throws Exception
	 */
	CourseChapter getCourseChapter(Long id) throws Exception;
		
	/**
	 * 创建分类同级节点
	 * @param curCategoryId 当前节点编号
	 * @param name 名称
	 * @param sortFlag 排序
	 * @return 分类节点
	 * @throws Exception
	 */
	CourseCategory createCategory(Long curCategoryId, String name, Integer sortFlag) throws Exception;
	
	/**
	 * 创建分类下级节点
	 * @param curCategoryId 当前节点编号
	 * @param name 名称
	 * @param sortFlag 排序
	 * @return 分类节点
	 * @throws Exception
	 */
	CourseCategory createChildCategory(Long curCategoryId, String name, Integer sortFlag) throws Exception;
	
	/**
	 * 修改分类节点
	 * @param curCategoryId 当前节点编号
	 * @param name 名称
	 * @param sortFlag 排序
	 * @return 分类节点
	 * @throws Exception
	 */
	CourseCategory modifyCategory(Long curCategoryId, String name, Integer sortFlag) throws Exception;
	
	/**
	 * 删除节点
	 * @param curCategoryId 当前节点编号
	 * @throws Exception
	 */
	void removeCategory(Long curCategoryId) throws Exception;
	
	/**
	 * 创建课程
	 * @param curCategoryId 分类编号
	 * @param courseTypeEnum 课程类型
	 * @param name 名称
	 * @param description 描述
	 * @param coverImageLink 封面链接
	 * @param classHour 学时
	 * @param credit 学分
	 * @param passCreditLimit 必修学分
	 * @param studyNum 学习次数
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @param canAllowAllUser 是否允许所有学员学习
	 * @param statusEnum 状态
	 * @param curManagerId 当前教师编号
	 * @return 课程
	 * @throws Exception
	 */
	Course createCourse(Long curCategoryId, CourseTypeEnum courseTypeEnum, 
			String name, String description, String coverImageLink, Integer classHour, 
			Integer credit,	Integer passCreditLimit, Integer studyNum, Boolean canMatchDutyRank,
			String dutyRank, Boolean canMatchTrade, String trade, Boolean canAllowAllUser,
			CourseStatusEnum statusEnum, Long curManagerId) throws Exception;
	
	/**
	 * 修改课程
	 * @param id 课程编号
	 * @param courseTypeEnum 课程类型
	 * @param name 名称
	 * @param description 描述
	 * @param coverImageLink 封面链接
	 * @param classHour 学时
	 * @param credit 学分
	 * @param passCreditLimit 必修学分
	 * @param studyNum 学习次数
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @param canAllowAllUser 是否允许所有学员学习
	 * @param statusEnum 状态
	 * @param curManagerId 当前教师编号
	 * @return 课程
	 * @throws Exception
	 */
	Course modifyCourse(Long id,	CourseTypeEnum courseTypeEnum, String name, String description, 
			String coverImageLink, Integer classHour, Integer credit,	Integer passCreditLimit, 
			Integer studyNum, Boolean canMatchDutyRank,	String dutyRank, Boolean canMatchTrade, 
			String trade, Boolean canAllowAllUser, CourseStatusEnum statusEnum, Long curManagerId) throws Exception;
	
	/**
	 * 修改课程学员范围
	 * @param id 编号
	 * @param userJoinedData 修改课程学员范围
	 * @param curManagerId 当前教师编号
	 * @return
	 * @throws Exception
	 */
	Course modifyCourseUserJoinedData(Long id, String userJoinedData, Long curManagerId) throws Exception;
	
	/**
	 * 删除课程
	 * @param id 课程编号
	 * @throws Exception 
	 */
	void removeCourse(Long id, Long curManagerId) throws Exception;
	
	/**
	 * 导入课程
	 * @param file Excel文件
	 * @param curManagerId 当前教师编号
	 * @throws Exception 
	 */
	void importCourse(File file, Long curManagerId) throws Exception;
	
	/**
	 * 导出课程
	 * @param courseId 课程编号
	 * @return
	 * @throws Exception 
	 */
	String exportCourse(Long id) throws Exception;
	
	/**
	 * 创建课程章节
	 * @param courseId 课程编号
	 * @param name 名称
	 * @param content 内容
	 * @param sortFlag 排序
	 * @param curManagerId 当前管理员编号
	 * @return 课程章节
	 * @throws Exception
	 */
	CourseChapter createCourseChapter(Long courseId, String name, String content, Integer sortFlag,
			Long curManagerId) throws Exception;
	
	/**
	 * 修改课程章节
	 * @param id 编号
	 * @param title 标题
	 * @param content 内容
	 * @param courseChapterGradeEnum 课程章节级别
	 * @param statusEnum 状态
	 * @param curManagerId 当前管理员编号
	 * @return
	 * @throws Exception
	 */
	CourseChapter modifyCourseChapter(Long id, String name, String content, Integer sortFlag,
			Long curManagerId) throws Exception;
	
	/**
	 * 删除课程章节
	 * @param id 编号
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	void removeCourseChapter(Long id, Long curManagerId) throws Exception;
		
	/**
	 * 上传课程图片
	 * @param file 文件
	 * @return 文件网址
	 * @throws Exception
	 */
	String uploadCourseImage(File file) throws Exception;
	
	/**
	 * 上传课程章节图片
	 * @param file 文件
	 * @return 文件网址
	 * @throws Exception
	 */
	String uploadCourseChapterImage(File file) throws Exception;
	
	/**
	 * 获取课程分类树列表
	 * @return
	 * @throws Exception
	 */
	List<CourseCategory> getCourseCategoryListByTree() throws Exception;
}
