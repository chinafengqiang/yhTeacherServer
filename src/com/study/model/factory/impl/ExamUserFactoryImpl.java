package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.ExamUserStatusEnum;
import com.study.model.ExamUser;
import com.study.model.factory.ExamUserFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 考生记录数据工厂实现类
 */
@Repository
public class ExamUserFactoryImpl implements ExamUserFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public ExamUser findById(Long id) {
		
		return (ExamUser)this.hibernateTemplate.get(ExamUser.class, id);
	}
	
	/**
	 * 按考试编号和学员编号获取考生记录
	 * @param examId 考试编号
	 * @param userId 学员编号
	 * @return 考生记录
	 */
	public ExamUser findByExamId_UserId(Long examId, Long userId) {
		
		String sql = "select a.* from exam_user a where a.exam_id=" + examId + " and user_id=" + userId;
		return (ExamUser)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamUser.class).object();
	}
	
	/**
	 * 按考试编号和学员标示获取考生记录
	 * @param examId 考试编号
	 * @param userKey 学员标示
	 * @return 考生记录
	 */
	public ExamUser findByExamId_UserKey(Long examId, String userKey) {
		
		String sql = "select a.* from exam_user a where a.exam_id=" + examId + " and user_key='" + userKey + "'";
		return (ExamUser)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamUser.class).object();
	}
	
	/**
	 * 获取所有记录
	 */
	public List<ExamUser> findListByAll() {
		
		String sql = "select a.* from exam_user a";
		return (List<ExamUser>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamUser.class).list();		
	}
	
	/**
	 * 获取考试服务器的考试记录
	 * @param examId 考试编号
	 * @param examServerData 考试服务器数据
	 * @return 考生列表
	 */
	public List<ExamUser> findListByExamServerData(Long examId, String examServerData) {
		
		String sql = "select a.* from exam_user a where a.exam_id=" + examId + " and (a.exam_server_data='" + examServerData + "' or a.backup_exam_server_data='" + examServerData + "')";
		return (List<ExamUser>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamUser.class).list();		
	}
	
	/**
	 * 获取相同考试代号的考试记录
	 * @param examCode 考试代号
	 * @return 考生列表
	 */
	public List<ExamUser> findListByExamCode(String examCode) {
		
		String sql = "select a.* from exam_user a, exam b where a.exam_id=b.id and b.exam_code='" + examCode + "'";
		return (List<ExamUser>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamUser.class).list();		
	}
	
	/**
	 * 获取学员的考生记录
	 * @param userId 学员编号
	 * @return 考生列表
	 */
	public List<ExamUser> findListByUserId(Long userId) {
		
		String sql = "select a.* from exam_user a where user_id=" + userId;
		return (List<ExamUser>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamUser.class).list();		
	}
	
	/**
	 * 获取教师平台的考试学员分页列表
	 * @param examId 考试编号
	 * @param actualOrgan 单位
	 * @param actualName 姓名
	 * @param paginateParamters 分页参数
	 * @return 考试学员分页列表
	 */
	public PaginateResult findListByExam_Manager(Long examId, String actualOrgan, String actualName, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from exam_user a where exam_id=" + examId;
		
		if (!StringUtils.isBlank(actualOrgan)) {
			sql = sql + " and actual_organ like '%" + actualOrgan + "%'";
		}

		if (!StringUtils.isBlank(actualName)) {
			sql = sql + " and actual_name like '%" + actualName + "%'";
		}
		
		sql = sql + " order by a.actual_organ, a.actual_name";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamUser.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 获取单位平台的考试学员分页列表
	 * @param examId 考试编号
	 * @param organId 单位编号
	 * @param actualOrgan 单位
	 * @param actualName 姓名
	 * @param statusEnum 状态
	 * @param paginateParamters 分页参数
	 * @return 考试学员分页列表
	 */
	public PaginateResult findListByExam_Organ(Long examId, Long organId, String actualOrgan, String actualName, ExamUserStatusEnum statusEnum, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from exam_user a where exam_id=" + examId + " and organ_id=" + organId;
		
		if (!StringUtils.isBlank(actualOrgan)) {
			sql = sql + " and actual_organ like '%" + actualOrgan + "%'";
		}

		if (!StringUtils.isBlank(actualName)) {
			sql = sql + " and actual_name like '%" + actualName + "%'";
		}
		
		if (statusEnum != null) {
			sql = sql + " and status=" + statusEnum.toValue();
		}
		
		sql = sql + " order by a.actual_name";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamUser.class).paginateResult(paginateParamters);		
	}
}
