package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.enums.ExamServerStatusEnum;
import com.study.model.ExamServer;
import com.study.model.factory.ExamServerFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 考试服务器记录数据工厂实现类
 */
@Repository
public class ExamServerFactoryImpl implements ExamServerFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public ExamServer findById(Long id) {
		
		return (ExamServer)this.hibernateTemplate.get(ExamServer.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<ExamServer> findListByAll() {
		
		String sql = "select a.* from exam_server a";
		return (List<ExamServer>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamServer.class).list();		
	}
	
	/**
	 * 按名称获取考试服务器记录
	 * @param name 名称
	 * @return 考试服务器
	 */
	public ExamServer findByName(String name) {
		
		String sql = "select a.* from exam_server a where a.name='" + name + "'";
		return (ExamServer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamServer.class).object();
	}
	
	/**
	 * 按链接地址获取考试服务器记录
	 * @param url 链接地址
	 * @return 考试服务器
	 */
	public ExamServer findByUrl(String url) {
		
		String sql = "select a.* from exam_server a where a.url='" + url + "'";
		return (ExamServer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamServer.class).object();
	}
	
	/**
	 * 按内网链接地址获取考试服务器记录
	 * @param directUrl 内网链接地址
	 * @return 考试服务器
	 */
	public ExamServer findByDirectUrl(String directUrl) {
		
		String sql = "select a.* from exam_server a where a.direct_url='" + directUrl + "'";
		return (ExamServer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamServer.class).object();
	}
	
	/**
	 * 按状态获取服务器列表
	 * @param statusEnum 状态
	 * @return
	 */
	public List<ExamServer> findListByStatus(ExamServerStatusEnum statusEnum) {
		
		String sql = "select a.* from exam_server a where a.status=" + statusEnum.Opened.toValue();
		return (List<ExamServer>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamServer.class).list();		
	}
	
	/**
	 * 按搜索条件获取考试服务器分页列表
	 * @param paginateParamters 分页参数
	 * @return 考试服务器分页列表
	 */
	public PaginateResult findListBySearch(PaginateParamters paginateParamters) {
		
		String sql = "select a.* from exam_server a where 1=1";		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamServer.class).paginateResult(paginateParamters);		
	}
}
