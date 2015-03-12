package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.NoticeGradeEnum;
import com.study.enums.NoticeStatusEnum;
import com.study.model.Notice;
import com.study.model.factory.NoticeFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 公告记录数据工厂实现类
 */
@Repository
public class NoticeFactoryImpl implements NoticeFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public Notice findById(Long id) {
		
		return (Notice)this.hibernateTemplate.get(Notice.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<Notice> findListByAll() {
		
		String sql = "select a.* from notice a";
		return (List<Notice>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Notice.class).list();		
	}
	
	/**
	 * 获取指定数量的记录（按sortFlag倒排序）
	 */
	public List<Notice> findListByGrade_Limit(NoticeGradeEnum gradeEnum, Integer limit) {
		
		String sql = "select a.* from notice a where a.grade=" + gradeEnum.toValue() + " and a.status=" + NoticeStatusEnum.Opened.toValue() + " order by a.sort_flag desc, a.id desc limit " + limit;
		return (List<Notice>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Notice.class).list();		
	}
	
	/**
	 * 按搜索条件获取在教师平台公告分页列表
	 * @param noticeGradeEnum 类型
	 * @param statusEnum 状态
	 * @param title 标题
	 * @param paginateParamters 分页参数
	 * @return 公告分页列表
	 */
	public PaginateResult findListBySearch(NoticeGradeEnum noticeGradeEnum, NoticeStatusEnum statusEnum, String title, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from notice a where 1=1";
		
		if (noticeGradeEnum != null) {
			sql = sql + " and a.grade=" + noticeGradeEnum.toValue();
		}
		
		if (!StringUtils.isBlank(title)) {
			sql = sql + " and a.title like '%" + title + "%'";
		}
		
		if (statusEnum != null) {
			sql = sql + " and a.status=" + statusEnum.toValue();
		}
		
		sql = sql + " order by a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Notice.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 按搜索条件获取在单位平台显示的公告列表
	 * @param noticeGradeEnum 类型
	 * @param title 标题
	 * @param paginateParamters 分页参数
	 * @return 公告分页列表
	 */
	public PaginateResult findListByOrgan(NoticeGradeEnum noticeGradeEnum, String title, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from notice a where a.status=" + NoticeStatusEnum.Opened.toValue();
		
		if (noticeGradeEnum != null) {
			sql = sql + " and a.grade=" + noticeGradeEnum.toValue();
		}
		
		if (!StringUtils.isBlank(title)) {
			sql = sql + " and a.title like '%" + title + "%'";
		}
		
		sql = sql + " order by a.sort_flag desc, a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Notice.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 获取学员公告分页列表
	 * @param paginateParamters 分页参数
	 * @return 公告分页列表
	 */
	public PaginateResult findListByUser(PaginateParamters paginateParamters) {
		
		String sql = "select a.* from notice a where a.status=" + NoticeStatusEnum.Opened.toValue() + " and a.grade=" + NoticeGradeEnum.User.toValue();		
		sql = sql + " order by a.sort_flag desc, a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Notice.class).paginateResult(paginateParamters);		
	}	
}
