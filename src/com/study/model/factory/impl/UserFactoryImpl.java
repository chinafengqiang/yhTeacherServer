package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.CourseStatusEnum;
import com.study.enums.CourseTypeEnum;
import com.study.enums.CourseUserStatusEnum;
import com.study.enums.UserStatusEnum;
import com.study.model.User;
import com.study.model.factory.UserFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 学员记录数据工厂实现类
 */
@Repository
public class UserFactoryImpl implements UserFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public User findById(Long id) {
		
		return (User)this.hibernateTemplate.get(User.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<User> findListByAll() {
		
		String sql = "select a.* from user a";
		return (List<User>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(User.class).list();		
	}
	
	/**
	 * 获取单位的学员数量
	 * @param organId 单位编号
	 * @return 子单位数量
	 */
	public Integer findCountByOrgan(Long organId) {
		
		String sql = "select count(a.id) as countValue from user a where organ_id=" + organId;
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
	
	/**
	 * 按登录帐号获取学员记录
	 * @param name 登录帐号
	 * @return 学员
	 */
	public User findByName(String name) {
		
		String sql = "select a.* from user a where a.name='" + name + "'";
		return (User)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(User.class).object();
	}
	
	/**
	 * 按真实姓名和单位获取学员记录
	 * @param organId 单位帐号
	 * @param actualName 姓名
	 * @return 学员
	 */
	public User findByOrganId_ActualName(Long organId, String actualName) {
		
		String sql = "select a.* from user a where a.actual_name='" + actualName + "' and organ_id=" + organId;
		return (User)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(User.class).object();
	}
	
	/**
	 * 按学员标示获取学员记录
	 * @param userKey 学员标示
	 * @return 学员
	 */
	public User findByUserKey(String userKey) {
		
		String sql = "select a.* from user a where a.user_key='" + userKey + "'";
		return (User)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(User.class).object();
	}
	
	/**
	 * 按搜索条件获取学员分页列表
	 * @param actualOrgan 所属单位
	 * @param name 帐号
	 * @param actualName 姓名
	 * @param organId 当前单位编号
	 * @param paginateParamters 分页参数
	 * @return 学员分页列表
	 */
	public PaginateResult findListBySearch(String actualOrgan, String name, String actualName, Long organId, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from user a where organ_id=" + organId;
		
		if (!StringUtils.isBlank(actualOrgan)) {
			sql = sql + " and actual_organ like '%" + actualOrgan + "%'";
		}
		
		if (!StringUtils.isBlank(name)) {
			sql = sql + " and a.name like '%" + name + "%'";
		}

		if (!StringUtils.isBlank(actualName)) {
			sql = sql + " and a.actual_name like '%" + actualName + "%'";
		}
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(User.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 按搜索条件获取单位平台的可选择的学员分页列表
	 * @param actualOrgan 所属单位
	 * @param actualName 姓名
	 * @param dutyRank 职务级别
	 * @param trade 行业 
	 * @param organId 当前单位编号
	 * @param paginateParamters 分页参数
	 * @return 学员分页列表
	 */
	public PaginateResult findListByOrganSelect(String actualOrgan, String actualName, String dutyRank, String trade, Long organId, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from user a where organ_id=" + organId;
		
		if (!StringUtils.isBlank(actualOrgan)) {
			sql = sql + " and actual_organ like '%" + actualOrgan + "%'";
		}
		
		if (!StringUtils.isBlank(dutyRank)) {
			sql = sql + " and a.duty_rank='" + dutyRank + "'";
		}

		if (!StringUtils.isBlank(trade)) {
			sql = sql + " and a.trade='" + trade + "'";
		}

		if (!StringUtils.isBlank(actualName)) {
			sql = sql + " and a.actual_name like '%" + actualName + "%'";
		}
		
		sql = sql + " order by a.actual_name";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(User.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 按搜索条件获取教师平台的可选择的学员分页列表
	 * @param actualOrgan 所属单位
	 * @param actualName 姓名
	 * @param dutyRank 职务级别
	 * @param trade 行业 
	 * @param organId 单位编号
	 * @param paginateParamters 分页参数
	 * @return 学员分页列表
	 */
	public PaginateResult findListByManagerSelect(String actualOrgan, String actualName, String dutyRank, String trade, Long organId, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from user a where 1= 1";
		
		if (organId != null) {
			sql = sql + " and organ_Id=" + organId;
		}
		
		if (!StringUtils.isBlank(actualOrgan)) {
			sql = sql + " and actual_organ like '%" + actualOrgan + "%'";
		}
		
		if (!StringUtils.isBlank(dutyRank)) {
			sql = sql + " and a.duty_rank='" + dutyRank + "'";
		}

		if (!StringUtils.isBlank(trade)) {
			sql = sql + " and a.trade='" + trade + "'";
		}

		if (!StringUtils.isBlank(actualName)) {
			sql = sql + " and a.actual_name like '%" + actualName + "%'";
		}
		
		sql = sql + " order by a.actual_name";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(User.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 获取单位的所有学员
	 * @param organId 单位编号
	 * @return 单位的所有学员
	 */
	public List<User> findListByOrgan(Long organId) {
		
		String sql = "select a.* from user a where a.organ_id=" + organId;
		return (List<User>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(User.class).list();		
	}
	
	/**
	 * 获取符合条件的单位的所有已启用的学员
	 * @param organIdList 单位编号列表
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @param requiredCourseNumber 必修课数量
	 * @return 单位的所有学员
	 */
	public List<User> findListByExtract(String organIdList, Boolean canMatchDutyRank, String dutyRank, Boolean canMatchTrade, String trade, Integer requiredCourseNumber) {
		
		String sql = "select a.* from user a where a.status=" + UserStatusEnum.Opened.toValue();
		
		if (!StringUtils.isBlank(organIdList)) {
			sql = sql + " and organ_id in (" + organIdList + ") ";
		}
		
		if (canMatchDutyRank) {
			sql = sql + " and LOCATE(duty_rank,'" + dutyRank + "') > 0 ";
		}

		if (canMatchTrade) {
			sql = sql + " and LOCATE(trade,'" + trade + "') > 0 ";
		}
		
		if (requiredCourseNumber != null) {
			sql = sql + " and (select count(b.id) from course_user b, course c where b.course_id=c.id and b.status=" + CourseUserStatusEnum.Done.toValue() + " and c.status=" + CourseStatusEnum.Opened.toValue() + " and c.course_type=" + CourseTypeEnum.Required.toValue() + " and b.user_id=a.id) >= " + requiredCourseNumber;
		}
		
		return (List<User>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(User.class).list();		
	}
	
	/**
	 * 校验学员是否符合条件
	 * @param userId 学员编号
	 * @param organIdList 单位编号列表
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @return 单位的所有学员
	 */
	public User findByVerify(Long userId, String organIdList, Boolean canMatchDutyRank, String dutyRank, Boolean canMatchTrade, String trade) {
		
		String sql = "select a.* from user a where a.status=" + UserStatusEnum.Opened.toValue() + " and id=" + userId;
		
		if (!StringUtils.isBlank(organIdList)) {
			sql = sql + " and organ_id in (" + organIdList + ") ";
		}
		
		if (canMatchDutyRank) {
			sql = sql + " and LOCATE(duty_rank,'" + dutyRank + "') > 0 ";
		}

		if (canMatchTrade) {
			sql = sql + " and LOCATE(trade,'" + trade + "') > 0 ";
		}
		
		return (User)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(User.class).object();		
	}
}
