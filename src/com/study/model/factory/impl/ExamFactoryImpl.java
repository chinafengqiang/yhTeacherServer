package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.CourseStatusEnum;
import com.study.enums.ExamStatusEnum;
import com.study.model.Exam;
import com.study.model.factory.ExamFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 考试记录数据工厂实现类
 */
@Repository
public class ExamFactoryImpl implements ExamFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public Exam findById(Long id) {
		
		return (Exam)this.hibernateTemplate.get(Exam.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<Exam> findListByAll() {
		
		String sql = "select a.* from exam a";
		return (List<Exam>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Exam.class).list();		
	}
	
	/**
	 * 获取相同考试代号的考试列表
	 */
	public List<Exam> findListByExamCode(String examCode) {
		
		String sql = "select a.* from exam a where exam_code='" + examCode + "'";
		return (List<Exam>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Exam.class).list();		
	}
	
	/**
	 * 获取引用此试卷的考试数量
	 * @param testPaperId 试卷编号
	 * @return 考试数量
	 */
	public Integer findCountByTestPaper(Long testPaperId) {
		
		String sql = "select count(a.id) as countValue from exam a where test_paper_id=" + testPaperId;
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
	
	/**
	 * 按本地标示获取考试记录
	 * @param examKey 考试本地标示
	 * @return 考试
	 */
	public Exam findByExamKey(String examKey) {
		
		String sql = "select a.* from exam a where a.exam_key='" + examKey + "'";
		return (Exam)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Exam.class).object();
	}
	
	/**
	 * 按考试服务器考试标示获取考试记录
	 * @param esExamKey 考试服务器考试标示
	 * @return 考试
	 */
	public Exam findByEsExamKey(String esExamKey) {
		
		String sql = "select a.* from exam a where a.es_exam_key='" + esExamKey + "'";
		return (Exam)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Exam.class).object();
	}
	
	/**
	 * 按搜索条件获取考试分页列表
	 * @param category 类型
	 * @param statusEnum 状态
	 * @param name 名称
	 * @param paginateParamters 分页参数
	 * @return 考试分页列表
	 */
	public PaginateResult findListBySearch(String category, ExamStatusEnum statusEnum, String name, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from exam a where 1=1";
		
		if (statusEnum != null) {
			sql = sql + " and a.status=" + statusEnum.toValue();
		}
		
		if (!StringUtils.isBlank(category)) {
			sql = sql + " and a.category like '%" + category + "%'";
		}
		
		if (!StringUtils.isBlank(name)) {
			sql = sql + " and a.name like '%" + name + "%'";
		}
		
		sql = sql + " order by a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Exam.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 按搜索条件获取考试分页列表
	 * @param category 类型
	 * @param statusEnum 状态
	 * @param name 名称
	 * @param organId 单位编号
	 * @param paginateParamters 分页参数
	 * @return 考试分页列表
	 */
	public PaginateResult findListByOrgan(String category, ExamStatusEnum statusEnum, String name, Long organId, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from exam a where a.status<>" + ExamStatusEnum.Created.toValue();
		
		if (statusEnum != null) {
			sql = sql + " and a.status=" + statusEnum.toValue();
		}
		
		if (!StringUtils.isBlank(category)) {
			sql = sql + " and a.category like '%" + category + "%'";
		}
		
		if (!StringUtils.isBlank(name)) {
			sql = sql + " and a.name like '%" + name + "%'";
		}
		
		sql = sql + " and (a.joined_organs='' or isNull(a.joined_organs) or LOCATE('," + organId + ",',a.joined_organs) > 0)";
		sql = sql + " order by a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Exam.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 获取学员平台的考试分页列表
	 * @param userId 学员编号
	 * @param paginateParamters 分页参数
	 * @return 考试分页列表
	 */
	public PaginateResult findListByUser(Long userId, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from exam a, exam_user b where a.status<>" + ExamStatusEnum.Created.toValue();
		sql = sql + " and a.id=b.exam_id";
		sql = sql + " and b.user_id=" + userId;
		sql = sql + " order by a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Exam.class).paginateResult(paginateParamters);		
	}
}
