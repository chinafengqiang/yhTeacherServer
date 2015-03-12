package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.CourseUserStatusEnum;
import com.study.model.CourseUser;
import com.study.model.ExamUser;
import com.study.model.factory.CourseUserFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 课程学员记录数据工厂实现类
 */
@Repository
public class CourseUserFactoryImpl implements CourseUserFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public CourseUser findById(Long id) {
		
		return (CourseUser)this.hibernateTemplate.get(CourseUser.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<CourseUser> findListByAll() {
		
		String sql = "select a.* from course_user a";
		return (List<CourseUser>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(CourseUser.class).list();		
	}
	
	/**
	 * 获取课程的学员数量
	 * @param courseId 课程编号
	 * @return 课程学员数量
	 */
	public Integer findCountByCourse(Long courseId) {
		
		String sql = "select count(a.id) as countValue from course_user a where course_id=" + courseId;
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
	
	/**
	 * 按状态获取学员的课程数量
	 * @param userId 学员编号
	 * @param statusEnum 状态
	 * @return 课程数量
	 */
	public Integer findCountByUser_Status(Long userId, CourseUserStatusEnum statusEnum) {
		
		String sql = "select count(a.id) as countValue from course_user a where user_id=" + userId + " and status=" + statusEnum.toValue();
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
	
	/**
	 * 按课程编号和学员编号获取课程学员记录
	 * @param courseId 课程编号
	 * @param userId 学员编号
	 * @return 课程学员记录
	 */
	public CourseUser findByCourseId_UserId(Long courseId, Long userId) {
		
		String sql = "select a.* from course_user a where a.course_id=" + courseId + " and user_id=" + userId;
		return (CourseUser)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(CourseUser.class).object();
	}
	
	/**
	 * 获取教师平台的课程学员分页列表
	 * @param courseId 课程编号
	 * @param actualOrgan 单位
	 * @param actualName 姓名
	 * @param paginateParamters 分页参数
	 * @return 课程学员分页列表
	 */
	public PaginateResult findListByCourse_Manager(Long courseId, String actualOrgan, String actualName, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from course_user a where course_id=" + courseId;
		
		if (!StringUtils.isBlank(actualOrgan)) {
			sql = sql + " and actual_organ like '%" + actualOrgan + "%'";
		}

		if (!StringUtils.isBlank(actualName)) {
			sql = sql + " and actual_name like '%" + actualName + "%'";
		}
		
		sql = sql + " order by a.actual_organ, a.actual_name";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(CourseUser.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 获取单位平台的课程学员分页列表
	 * @param courseId 课程编号
	 * @param organId 单位编号
	 * @param actualOrgan 单位
	 * @param actualName 姓名
	 * @param statusEnum 状态
	 * @param paginateParamters 分页参数
	 * @return 课程学员分页列表
	 */
	public PaginateResult findListByCourse_Organ(Long courseId, Long organId, String actualOrgan, String actualName, CourseUserStatusEnum statusEnum, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from course_user a where course_id=" + courseId + " and organ_id=" + organId;
		
		if (!StringUtils.isBlank(actualOrgan)) {
			sql = sql + " and actual_organ like '%" + actualOrgan + "%'";
		}

		if (!StringUtils.isBlank(actualName)) {
			sql = sql + " and actual_name like '%" + actualName + "%'";
		}
		
		if (statusEnum != null) {
			sql = sql + " and status =" + statusEnum.toValue();
		}
		sql = sql + " order by a.actual_name";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(CourseUser.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 获取学员的所有课程的学分总和
	 * @param testPaperId 试卷编号编号
	 * @return 学分总和
	 */
	public Integer findSumCreditByUserId(Long userId) {
		
		String sql = "select ifnull(sum(a.credit), 0) as sumValue from course_user a where a.user_id=" + userId;
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("sumValue");
	}
	
	/**
	 * 获取学员的课程学习记录
	 * @param userId 学员编号
	 * @return 课程学习记录列表
	 */
	public List<CourseUser> findListByUserId(Long userId) {
		
		String sql = "select a.* from course_user a where a.user_id=" + userId;
		return (List<CourseUser>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(CourseUser.class).list();		
	}
}
