package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.OrganStatusEnum;
import com.study.enums.OrganTypeEnum;
import com.study.model.Organ;
import com.study.model.factory.OrganFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 单位记录数据工厂实现类
 */
@Repository
public class OrganFactoryImpl implements OrganFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public Organ findById(Long id) {
		
		return (Organ)this.hibernateTemplate.get(Organ.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<Organ> findListByAll() {
		
		String sql = "select a.* from organ a order by a.parent_Id, a.name";
		return (List<Organ>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Organ.class).list();		
	}
	
	/**
	 * 获取所有停用的单位记录列表
	 */
	public List<Organ> findListByClosed() {
		
		String sql = "select a.* from organ a where a.status=" + OrganStatusEnum.Closed.toValue() + " order by a.parent_Id, a.name";
		return (List<Organ>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Organ.class).list();		
	}
	
	/**
	 * 获取供选择的单位记录
	 * @param organTypeEnum 单位类型
	 * @param actualName 单位名称
	 * @param parentId 最高单位编号
	 * @param statusEnum 状态
	 * @param paginateParamters 分页参数
	 * @return
	 */
	public PaginateResult findListBySelect(OrganTypeEnum organTypeEnum, String actualName, Long parentId, OrganStatusEnum statusEnum, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from organ a where a.status=" + OrganStatusEnum.Opened.toValue();
		
		if (organTypeEnum != null) {
			sql = sql + " and a.organ_type=" + organTypeEnum.toValue();
		}
		
		if (statusEnum != null) {
			sql = sql + " and a.status=" + statusEnum.toValue();
		}
		
		if (!StringUtils.isBlank(actualName)) {
			sql = sql + " and a.actual_name like '%" + actualName + "%'";
		}
		
		if (parentId != null) {
			sql = sql + " and FIND_IN_SET(id, getOrganChildIdList(" + parentId + "))";
		}
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Organ.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 获取所有下级记录
	 */
	public List<Organ> findListByChild(Long id) {
		
		String sql = "select a.* from organ a where a.parent_id=" + id + " order by a.name";
		return (List<Organ>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Organ.class).list();		
	}
	
	/**
	 * 按帐号获取单位记录
	 * @param name 帐号
	 * @return 单位
	 */
	public Organ findByName(String name) {
		
		String sql = "select a.* from organ a where a.name='" + name + "'";
		return (Organ)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Organ.class).object();
	}
	
	/**
	 * 按名称获取单位记录
	 * @param actualName名称
	 * @return 单位
	 */
	public Organ findByActualName(String actualName) {
		
		String sql = "select a.* from organ a where a.actual_name='" + actualName + "'";
		return (Organ)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Organ.class).object();
	}
	
	/**
	 * 获取顶级单位记录
	 * @return 单位
	 */
	public Organ findByTop() {
		
		String sql = "select a.* from organ a where a.parent_id=0";
		return (Organ)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Organ.class).object();
	}
	
	/**
	 * 获取某单位的子单位数量
	 * @param parantId 父单位编号
	 * @return 子单位数量
	 */
	public Integer findCountByParant(Long parantId) {
		
		String sql = "select count(a.id) as countValue from organ a where parent_id=" + parantId;
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
	
	/**
	 * 按搜索条件获取本单位及下级单位分页列表
	 * @param organTypeEnum 级别枚举
	 * @param statusEnum 类型枚举
	 * @param actualName 名称
	 * @param curOrganId 当前单位编号
	 * @param paginateParamters 分页参数
	 * @return 单位分页列表
	 */
	public PaginateResult findListBySearch(OrganTypeEnum organTypeEnum, OrganStatusEnum statusEnum, String actualName, Long curOrganId, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from organ a where (a.id=" + curOrganId + " or a.parent_id=" + curOrganId + ")" ;
		
		if (organTypeEnum != null) {
			sql = sql + " and a.organ_type=" + organTypeEnum.toValue();
		}
		
		if (statusEnum != null) {
			sql = sql + " and a.status=" + statusEnum.toValue();
		}
		
		if (!StringUtils.isBlank(actualName)) {
			sql = sql + " and a.actual_name like '%" + actualName + "%'";
		}
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Organ.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 递归获取自身及下级单位的所有ID列表
	 * @param organIdList 单位ID列表
	 * @return 单位ID列表
	 */
	public String findIdListBySelf_Child_Opened(String organIdList) {
	
		//获取当前层的所有本单位及下级单位
		String sql = "select * from organ where status=" + OrganStatusEnum.Opened.toValue() + " and (parent_id in (" + organIdList + ") or id in (" + organIdList + "))";	
		List<Organ> list = (List<Organ>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Organ.class).list();
		
		//组装单位列表的编号列表字符串
		String nextOrganIdList = "";
		for (Organ organ : list) {
			if (!nextOrganIdList.equals("")) {
				nextOrganIdList = nextOrganIdList + ",";
			}
			nextOrganIdList = nextOrganIdList + organ.getId();
		}
		
		//若编号列表字符串不变，意味着没有更多的下级了，若变化，继续递归寻找下级
		if (organIdList.split(",").length == nextOrganIdList.split(",").length) {
			return nextOrganIdList;
		} else {
			return findIdListBySelf_Child_Opened(nextOrganIdList);
		}
	}
	
	/**
	 * 获取拥有考试报表数据的所有单位记录
	 * @param reportExamId 考试报表编号
	 */
	public List<Organ> findListByReportExam(Long reportExamId) {
		
		String sql = "select a.* from organ a where a.id in (select distinct organ_id from report_exam_data where report_exam_id=" + reportExamId + ")";
		return (List<Organ>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Organ.class).list();		
	}
	
	/**
	 * 获取拥有考试报表数据的所有单位记录
	 * @param reportExamId 考试报表编号
	 */
	public List<Organ> findListByOrganType(OrganTypeEnum organTypeEnum) {
		
		String sql = "select a.* from organ a where a.organ_type=" + organTypeEnum.toValue();
		return (List<Organ>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Organ.class).list();		
	}
	
	/**
	 * 获取本级及下级所有启动的单位
	 * @param id 本单位编号
	 */
	public List<Organ> findListBySelf_Child_Opened(Long organId) {
		
		String sql = "select * from organ where status=" + OrganStatusEnum.Opened.toValue() + " and FIND_IN_SET(id, getOrganChildIdList(" + organId + ")) order by organ_type, id ";
		return (List<Organ>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Organ.class).list();		
	}
}
