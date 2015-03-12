package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.TestPaperStatusEnum;
import com.study.model.Article;
import com.study.model.TestPaper;
import com.study.model.factory.TestPaperFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 试卷记录数据工厂实现类
 */
@Repository
public class TestPaperFactoryImpl implements TestPaperFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public TestPaper findById(Long id) {
		
		return (TestPaper)this.hibernateTemplate.get(TestPaper.class, id);
	}
	
	/**
	 * 按本地标示获取试卷记录
	 * @param testPaperKey 试卷本地标示
	 * @return 试卷
	 */
	public TestPaper findByTestPaperKey(String testPaperKey) {
		
		String sql = "select a.* from test_paper a where a.test_paper_key='" + testPaperKey + "'";
		return (TestPaper)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(TestPaper.class).object();
	}
	
	/**
	 * 获取所有记录
	 */
	public List<TestPaper> findListByAll() {
		
		String sql = "select a.* from test_paper a";
		return (List<TestPaper>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(TestPaper.class).list();		
	}
	
	/**
	 * 按搜索条件获取试卷分页列表
	 * @param name 名称
	 * @param category 分类
	 * @param statusEnum 状态
	 * @param paginateParamters 分页参数
	 * @return 试卷分页列表
	 */
	public PaginateResult findListBySearch(String name, String description, TestPaperStatusEnum statusEnum, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from test_paper a where 1=1";
				
		if (!StringUtils.isBlank(name)) {
			sql = sql + " and a.name like '%" + name + "%'";
		}
		
		if (!StringUtils.isBlank(description)) {
			sql = sql + " and a.description="+ description;
		}

		if (statusEnum != null) {
			sql = sql + " and a.status=" + statusEnum.toValue();
		}
		
		sql = sql + " order by a.id";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(TestPaper.class).paginateResult(paginateParamters);		
	}

}
