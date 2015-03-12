package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.CourseStatusEnum;
import com.study.enums.CourseTypeEnum;
import com.study.model.Course;
import com.study.model.factory.CourseFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 课程记录数据工厂实现类
 */
@Repository
public class CourseFactoryImpl implements CourseFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public Course findById(Long id) {
		
		return (Course)this.hibernateTemplate.get(Course.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<Course> findListByAll() {
		
		String sql = "select a.* from course a";
		return (List<Course>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Course.class).list();		
	}
	
	/**
	 * 获取某分类节点的课程数量
	 * @param courseCategoryId 分类节点编号
	 * @return 课程数量
	 */
	public Integer findCountByCourseCategory(Long courseCategoryId) {
		
		String sql = "select count(a.id) as countValue from course a where a.course_category_id=" + courseCategoryId;
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
	
	/**
	 * 按课程类型和状态获取课程数量
	 * @param courseTypeEnum 课程类型
	 * @param statusEnum 状态
	 * @return 课程数量
	 */
	public Integer findCountByCourseType_Status(CourseTypeEnum courseTypeEnum, CourseStatusEnum statusEnum) {
		
		String sql = "select count(a.id) as countValue from course a where a.course_type=" + courseTypeEnum.toValue() + " and a.status=" + statusEnum.toValue();
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
	
	/**
	 * 按本地标示获取课程记录
	 * @param courseKey 课程本地标示
	 * @return 课程
	 */
	public Course findByCourseKey(String courseKey) {
		
		String sql = "select a.* from course a where a.course_key='" + courseKey + "'";
		return (Course)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Course.class).object();
	}
	
	/**
	 * 按搜索条件获取课程分页列表
	 * @param courseCategoryId 分类编号
	 * @param courseTypeEnum 类型
	 * @param statusEnum 状态
	 * @param name 名称
	 * @param paginateParamters 分页参数
	 * @return 课程分页列表
	 */
	public PaginateResult findListBySearch(Long courseCategoryId, CourseTypeEnum courseTypeEnum, CourseStatusEnum statusEnum, String name, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from course a where course_category_id=" + courseCategoryId;
		
		if (courseTypeEnum != null) {
			sql = sql + " and a.course_type=" + courseTypeEnum.toValue();
		}
		
		if (statusEnum != null) {
			sql = sql + " and a.status=" + statusEnum.toValue();
		}
		
		if (!StringUtils.isBlank(name)) {
			sql = sql + " and a.name like '%" + name + "%'";
		}
		
		sql = sql + " order by a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Course.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 按搜索条件获取课程分页列表
	 * @param courseCategoryId 分类编号
	 * @param courseTypeEnum 类型
	 * @param name 名称
	 * @param organId 单位编号
	 * @param paginateParamters 分页参数
	 * @return 课程分页列表
	 */
	public PaginateResult findListByOrgan(Long courseCategoryId, CourseTypeEnum courseTypeEnum, String name, Long organId, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from course a where a.status=" + CourseStatusEnum.Opened.toValue() + " and course_category_id=" + courseCategoryId;
		
		if (courseTypeEnum != null) {
			sql = sql + " and a.course_type=" + courseTypeEnum.toValue();
		}

		if (!StringUtils.isBlank(name)) {
			sql = sql + " and a.name like '%" + name + "%'";
		}
		
		sql = sql + " and (a.joined_organs='' or isNull(a.joined_organs) or LOCATE('," + organId + ",',a.joined_organs) > 0)";
		sql = sql + " order by a.id desc";

		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Course.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 获取学员平台的课程分页列表
	 * @param userId 学员编号
	 * @param paginateParamters 分页参数
	 * @return 课程分页列表
	 */
	public PaginateResult findListByUser(Long userId, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from course a, user b where a.status=" + CourseStatusEnum.Opened.toValue();	
		
		sql = sql + " and b.id=" + userId;
		sql = sql + " and (a.joined_organs='' or isNull(a.joined_organs) or LOCATE(CONCAT(',',CAST(b.organ_id AS CHAR),','),a.joined_organs) > 0)";
		sql = sql + " order by a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Course.class).paginateResult(paginateParamters);		
	}
}
