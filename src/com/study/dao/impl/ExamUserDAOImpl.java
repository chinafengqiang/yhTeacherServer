package com.study.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.ExamUserDAO;
import com.study.model.ExamUser;
import com.study.utility.DAOUtility;
import com.study.utility.DateUtility;

/**
 * 考生数据操作类
 */
@Repository
public class ExamUserDAOImpl implements ExamUserDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	@Resource
	private DataSource dataSource;
	
	public void insert(ExamUser instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(ExamUser instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(ExamUser instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(ExamUser.class, id));
	}
	
	/**
	 * 删除考试的所有学员记录
	 * @param examId 考试编号
	 */
	public void deleteListByExam(Long examId) {

		Object[] params = {examId};
		String sql = "delete from exam_user where exam_id=?";
		DAOUtility.createSQL(hibernateTemplate).setSql(sql).setParams(params).execute();
	}
	
	/**
	 * 删除学员的所有考试记录
	 * @param userId 学员编号
	 */
	public void deleteListByUser(Long userId) {

		Object[] params = {userId};
		String sql = "delete from exam_user where user_id=?";
		DAOUtility.createSQL(hibernateTemplate).setSql(sql).setParams(params).execute();
	}
		
	/**
	 * 插入列表数据
	 * @param list 列表
	 * @throws Exception 
	 */
	public void insertList(List<ExamUser> list) throws Exception {

		if (list.size() <=0 ) {
			return;
		}
		
		try {
			Connection connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			final String sql = "insert into exam_user(exam_id, user_id, user_key, exam_server_data, backup_exam_server_data, actual_organ, actual_name, organ_id, test_paper_id," +
					"series, can_limit_valid_time, valid_first_time, valid_last_time, begin_time, end_time, score, credit, can_pass, total_time, leave_time, status) " +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
			PreparedStatement ps = connection.prepareStatement(sql);
			Integer listSize = list.size();
			
			for(int i=0; i<listSize; i++) {
	            	
	            	ExamUser examUser = (ExamUser)list.get(i);
	            	
	            	ps.setLong(1, examUser.getExamId()); 
	            	ps.setLong(2, examUser.getUserId());
	            	ps.setString(3, examUser.getUserKey());
	            	ps.setString(4, examUser.getExamServerData()); 
	            	ps.setString(5, examUser.getBackupExamServerData());
	            	ps.setString(6, examUser.getActualOrgan()); 
	            	ps.setString(7, examUser.getActualName()); 
	            	ps.setLong(8, examUser.getOrganId());
	            	ps.setLong(9, examUser.getTestPaperId()); 
	            	ps.setInt(10, examUser.getSeries()); 
	            	ps.setBoolean(11, examUser.getCanLimitValidTime()); 
	            	ps.setString(12, DateUtility.dateToAllString(examUser.getValidFirstTime())); 
	            	ps.setString(13, DateUtility.dateToAllString(examUser.getValidLastTime())); 
	            	ps.setString(14, DateUtility.dateToAllString(examUser.getBeginTime())); 
	            	ps.setString(15, DateUtility.dateToAllString(examUser.getEndTime())); 
	            	ps.setDouble(16, examUser.getScore());
	            	ps.setInt(17, examUser.getCredit());
	            	ps.setBoolean(18, examUser.getCanPass());
	            	ps.setInt(19, examUser.getTotalTime()); 
	            	ps.setInt(20, examUser.getLeaveTime()); 
	            	ps.setInt(21, examUser.getStatus());            	
	            	ps.addBatch();
					
					if(i % 50000 == 0 || i == listSize - 1) {
						ps.executeBatch();
						connection.commit();
					}
				}
				
				ps.close();
				connection.close();
		
		} catch (Exception ex) {
			System.out.println("======Error!ExamUserDAOImpl.insertList:" + ex.getMessage());
			throw new Exception("无法插入考生数据列表！");
		}
	}
	
	/**
	 * 更新列表数据
	 * @param list 列表
	 * @throws Exception 
	 */
	public void updateList(List<ExamUser> list) throws Exception {

		if (list.size() <=0 ) {
			return;
		}
		
		try {
			Connection connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			final String sql = "update exam_user set exam_id=?, user_id=?, user_key=?, exam_server_data=?, backup_exam_server_data=?, actual_organ=?, actual_name=?, organ_id=?, test_paper_id=?," +
					" series=?, can_limit_valid_time=?, valid_first_time=?, valid_last_time=?, begin_time=?, end_time=?, score=?, credit=?, can_pass=?, total_time=?, leave_time=?, status=? " +
					" where id=?";
	
			PreparedStatement ps = connection.prepareStatement(sql);
			Integer listSize = list.size();
			
			for(int i=0; i<listSize; i++) {
	            	
	            	ExamUser examUser = (ExamUser)list.get(i);
	            	
	            	ps.setLong(1, examUser.getExamId()); 
	            	ps.setLong(2, examUser.getUserId());
	            	ps.setString(3, examUser.getUserKey());
	            	ps.setString(4, examUser.getExamServerData()); 
	            	ps.setString(5, examUser.getBackupExamServerData());
	            	ps.setString(6, examUser.getActualOrgan()); 
	            	ps.setString(7, examUser.getActualName()); 
	            	ps.setLong(8, examUser.getOrganId());
	            	ps.setLong(9, examUser.getTestPaperId()); 
	            	ps.setInt(10, examUser.getSeries()); 
	            	ps.setBoolean(11, examUser.getCanLimitValidTime()); 
	            	ps.setString(12, DateUtility.dateToAllString(examUser.getValidFirstTime())); 
	            	ps.setString(13, DateUtility.dateToAllString(examUser.getValidLastTime())); 
	            	ps.setString(14, DateUtility.dateToAllString(examUser.getBeginTime())); 
	            	ps.setString(15, DateUtility.dateToAllString(examUser.getEndTime())); 
	            	ps.setDouble(16, examUser.getScore());
	            	ps.setInt(17, examUser.getCredit());
	            	ps.setBoolean(18, examUser.getCanPass());
	            	ps.setInt(19, examUser.getTotalTime()); 
	            	ps.setInt(20, examUser.getLeaveTime()); 
	            	ps.setInt(21, examUser.getStatus());   
	            	ps.setLong(22, examUser.getId());
	            	ps.addBatch();
					
					if(i % 50000 == 0 || i == listSize - 1) {
						ps.executeBatch();
						connection.commit();
					}
				}
				
				ps.close();
				connection.close();
		
		} catch (Exception ex) {
			System.out.println("======Error!ExamUserDAOImpl.updateList:" + ex.getMessage());
			throw new Exception("无法更新考生数据列表！");
		}
	}	
}
