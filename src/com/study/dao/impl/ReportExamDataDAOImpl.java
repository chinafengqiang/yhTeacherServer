package com.study.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.ReportExamDataDAO;
import com.study.enums.ReportExamDataSourceEnum;
import com.study.model.ReportExamData;
import com.study.utility.DAOUtility;

/**
 * 考试报表基础数据操作类
 */
@Repository
public class ReportExamDataDAOImpl implements ReportExamDataDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	@Resource
	private DataSource dataSource;
	
	public void insert(ReportExamData instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(ReportExamData instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(ReportExamData instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(ReportExamData.class, id));
	}
	
	/**
	 * 删除考试报表的所有学员记录
	 * @param reportExamId 考试报表编号
	 */
	public void deleteListByReportExamId(Long reportExamId) {

		Object[] params = {reportExamId};
		String sql = "delete from report_exam_data where report_exam_id=?";
		DAOUtility.createSQL(hibernateTemplate).setSql(sql).setParams(params).execute();
	}
	
	/**
	 * 删除考试报表某单位的所有学员记录
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 */
	public void deleteListByReportExamId_OrganId(Long reportExamId, Long organId) {

		Object[] params = {reportExamId, organId};
		String sql = "delete from report_exam_data where report_exam_id=? and organ_id=?";
		DAOUtility.createSQL(hibernateTemplate).setSql(sql).setParams(params).execute();
	}
	
	/**
	 * 插入列表数据
	 * @param list 列表
	 * @throws Exception 
	 */
	public void insertList(List<ReportExamData> list) throws Exception {

		if (list.size() <=0 ) {
			return;
		}
		
		try {
			Connection connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			final String sql = "insert into report_exam_data(report_exam_id, organ_id, exam_key, user_key, actual_organ, actual_name, duty_rank, trade, score, " +
					"source, status) values(?,?,?,?,?,?,?,?,?,?,?)";
	
			PreparedStatement ps = connection.prepareStatement(sql);
			Integer listSize = list.size();
			
			for(int i=0; i<listSize; i++) {
	            	
				ReportExamData reportExamData = (ReportExamData)list.get(i);
            	
            	ps.setLong(1, reportExamData.getReportExamId()); 
            	ps.setLong(2, reportExamData.getOrganId());
            	ps.setString(3, reportExamData.getExamKey());
            	ps.setString(4, reportExamData.getUserKey()); 
            	ps.setString(5, reportExamData.getActualOrgan());
            	ps.setString(6, reportExamData.getActualName()); 
            	ps.setString(7, reportExamData.getDutyRank()); 
            	ps.setString(8, reportExamData.getTrade());
            	ps.setDouble(9, reportExamData.getScore()); 
            	ps.setInt(10, reportExamData.getSource()); 
            	ps.setInt(11, reportExamData.getStatus());
            	ps.addBatch();
				
				if(i % 50000 == 0 || i == listSize - 1) {
					ps.executeBatch();
					connection.commit();
				}
			}
			
			ps.close();
			connection.close();
		
		} catch (Exception ex) {
			System.out.println("======Error!ReportExamDataDAOImpl.insertList:" + ex.getMessage());
			throw new Exception("无法插入考生数据列表！");
		}
	}
	
	/**
	 * 插入列表数据
	 * @param list 列表
	 * @throws Exception 
	 */
	public void updateList(List<ReportExamData> list) throws Exception {

		if (list.size() <=0 ) {
			return;
		}
		
		try {
			Connection connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			final String sql = "update report_exam_data set report_exam_id=?, organ_id=?, exam_key=?, user_key=?, actual_organ=?, actual_name=?, duty_rank=?, trade=?, score=?, " +
					"source=?, status=? where id=? ";
	
			PreparedStatement ps = connection.prepareStatement(sql);
			Integer listSize = list.size();
			
			for(int i=0; i<listSize; i++) {
	            	
				ReportExamData reportExamData = (ReportExamData)list.get(i);
            	
            	ps.setLong(1, reportExamData.getReportExamId()); 
            	ps.setLong(2, reportExamData.getOrganId());
            	ps.setString(3, reportExamData.getExamKey());
            	ps.setString(4, reportExamData.getUserKey()); 
            	ps.setString(5, reportExamData.getActualOrgan());
            	ps.setString(6, reportExamData.getActualName()); 
            	ps.setString(7, reportExamData.getDutyRank()); 
            	ps.setString(8, reportExamData.getTrade());
            	ps.setDouble(9, reportExamData.getScore()); 
            	ps.setInt(10, reportExamData.getSource()); 
            	ps.setInt(11, reportExamData.getStatus());
            	ps.setLong(12, reportExamData.getId());
            	ps.addBatch();
				
				if(i % 50000 == 0 || i == listSize - 1) {
					ps.executeBatch();
					connection.commit();
				}
			}
			
			ps.close();
			connection.close();
		
		} catch (Exception ex) {
			System.out.println("======Error!ReportExamDataDAOImpl.updateList:" + ex.getMessage());
			throw new Exception("无法更新考生数据列表！");
		}
	}

	
	/**
	 * 删除考试报表的所有报表记录
	 * @param reportExamId 考试报表编号
	 * @param sourceEnum 来源枚举
	 */
	public void deleteListByReportExamId_Source(Long reportExamId, ReportExamDataSourceEnum sourceEnum) {

		Object[] params = {reportExamId, sourceEnum.toValue()};
		String sql = "delete from report_exam_data where report_exam_id=? and source=?";
		DAOUtility.createSQL(hibernateTemplate).setSql(sql).setParams(params).execute();
	}
}
