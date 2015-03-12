package com.study.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.TestActionForm;
import com.study.dao.DAOFacade;
import com.study.enums.ExamModeEnum;
import com.study.enums.ExamServerStatusEnum;
import com.study.enums.ExamStatusEnum;
import com.study.enums.QuestionFetchTypeEnum;
import com.study.enums.QuestionOptionsSortTypeEnum;
import com.study.enums.QuestionShowTypeEnum;
import com.study.enums.QuestionSortTypeEnum;
import com.study.enums.QuestionTypeEnum;
import com.study.enums.TestPaperStatusEnum;
import com.study.enums.TimerModeEnum;
import com.study.model.Exam;
import com.study.model.ExamServer;
import com.study.model.ExamUser;
import com.study.model.Organ;
import com.study.model.TestPaper;
import com.study.model.TestPaperQuestion;
import com.study.model.User;
import com.study.model.factory.ModelFactoryFacade;
import com.study.model.part.TestPaperOption;
import com.study.service.ServiceFacade;
import com.study.utility.DateUtility;
import com.study.utility.JSONConvertor;
import com.study.utility.SystemUtility;

/**
 * 资讯业务操作类
 */
public class TestAction extends BaseActionSupport implements ModelDriven<TestActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(TestAction.class);
	
	/**
	 * 数据操作层门面 
	 */
	@Resource
	private DAOFacade daoFacade;
	
	/**
	 * 数据工厂层门面 
	 */
	@Resource
	private ModelFactoryFacade modelFactoryFacade;
	
	/**
	 * 业务层门面 
	 */
	@Resource
	private ServiceFacade serviceFacade;
	
	/**
	 * Action表单
	 */
	private TestActionForm actionForm = new TestActionForm();
	
	/**
	 * 插入大量的测试数据
	 */
	public String insertExamUserList() {
		
		try {
			System.out.println("insertExamUserList 准备:" + DateUtility.dateToLongString(DateUtility.getCurDate()));
			
			List<ExamUser> examUserList = new ArrayList<ExamUser>();
			for (int i=0; i<200000; i++) {
				ExamUser examUser = new ExamUser();
				examUser.setExamId(1l);
				examUser.setUserId(i+1l);
				examUser.setOrganId(2l);
				examUser.setStatus(0);
				examUser.setTestPaperId(1l);
				examUser.setSeries(1);
				examUser.setCanLimitValidTime(true);
				examUser.setCanPass(true);
				examUser.setCredit(0);
				examUser.setTotalTime(1);
				examUser.setLeaveTime(1);
				examUser.setScore(1.1);
				
				examUserList.add(examUser);
			}
	
			System.out.println("insertExamUserList begin:" + DateUtility.dateToLongString(DateUtility.getCurDate()));
			this.daoFacade.getExamUserDAO().insertList(examUserList);
			System.out.println("insertExamUserList end:" + DateUtility.dateToLongString(DateUtility.getCurDate()));	
		} catch (Exception ex) {
			
		}
		return SUCCESS;
	}
	
	public String updateExamUserList() {
		try {
			System.out.println("updateExamUserList 准备:" + DateUtility.dateToLongString(DateUtility.getCurDate()));
			List<ExamUser> list = this.modelFactoryFacade.getExamUserFactory().findListByAll();
			System.out.println("updateExamUserList begin:" + DateUtility.dateToLongString(DateUtility.getCurDate()));
			for (int i=0; i<list.size(); i++) {
				list.get(i).setActualName("aaaa" + list.get(i).getId());
			}
			this.daoFacade.getExamUserDAO().updateList(list);
	
			System.out.println("updateExamUserList end:" + DateUtility.dateToLongString(DateUtility.getCurDate()));	
			
		} catch (Exception ex) {
			
		}
		return SUCCESS;
	}
	
	public String deleteExamUserList() {
		
		System.out.println("deleteExamUserList begin:" + DateUtility.dateToLongString(DateUtility.getCurDate()));
		this.daoFacade.getExamUserDAO().deleteListByExam(1l);
		System.out.println("deleteExamUserList end:" + DateUtility.dateToLongString(DateUtility.getCurDate()));	
		return SUCCESS;
	}
	
	/**
	 * 插入部署考试用到的测试数据
	 */
	public String insertTestDataForDesployExam() {
		
		try {
//			Organ organ = new Organ();
//			organ.setName("organ1");
//			organ.setActualName("organ1");
//			organ.setParentId(0l);
//			organ.setStatus(0);
//			this.daoFacade.getOrganDAO().insert(organ);
//			
//			User user1 = new User();
//			user1.setActualName("张1");
//			user1.setActualOrgan("局1");
//			user1.setName("zhang1");
//			user1.setPassword("888888");
//			user1.setOrganId(organ.getId());
//			user1.setUserKey(SystemUtility.createUUID());
//			user1.setStatus(0);
//			this.daoFacade.getUserDAO().insert(user1);
//			
//			User user2 = new User();
//			user2.setActualName("张2");
//			user2.setActualOrgan("局2");
//			user2.setName("zhang2");
//			user2.setPassword("888888");
//			user2.setOrganId(organ.getId());
//			user2.setUserKey(SystemUtility.createUUID());
//			user2.setStatus(0);
//			this.daoFacade.getUserDAO().insert(user2);
			
			List<TestPaperOption> testPaperOptionList = new ArrayList<TestPaperOption>();
			
			TestPaperOption testPaperOption1 = new TestPaperOption();
			testPaperOption1.setQuestionType(QuestionTypeEnum.Judge.toValue());
			testPaperOption1.setScore(2.0);
			testPaperOption1.setNumber(10);
			testPaperOption1.setSortFlag(3);
			testPaperOption1.setNote("爱我飞啊飞453");
			testPaperOptionList.add(testPaperOption1);
			
			TestPaperOption testPaperOption2 = new TestPaperOption();
			testPaperOption2.setQuestionType(QuestionTypeEnum.SingleSelect.toValue());
			testPaperOption2.setScore(3.0);
			testPaperOption2.setNumber(10);
			testPaperOption2.setSortFlag(1);
			testPaperOption2.setNote("爱我飞啊飞3535");
			testPaperOptionList.add(testPaperOption2);
			
			TestPaperOption testPaperOption3 = new TestPaperOption();
			testPaperOption3.setQuestionType(QuestionTypeEnum.MultiSelect.toValue());
			testPaperOption3.setScore(4.0);
			testPaperOption3.setNumber(10);
			testPaperOption3.setSortFlag(2);
			testPaperOption3.setNote("爱我飞啊飞4564");
			testPaperOptionList.add(testPaperOption3);
			
			
			TestPaper testPaper = new TestPaper();
			testPaper.setName("setName");
			testPaper.setTestPaperKey(SystemUtility.createUUID());
			testPaper.setDescription("");
			testPaper.setCategory("");
			testPaper.setTestPaperOptions(JSONConvertor.list2Json(testPaperOptionList));
			testPaper.setQuestionShowType(QuestionShowTypeEnum.All.toValue());
			testPaper.setQuestionSortType(QuestionSortTypeEnum.Random.toValue());
			testPaper.setQuestionOptionsSortType(QuestionOptionsSortTypeEnum.Random.toValue());
			testPaper.setCredit(10);
			testPaper.setTotalScore(100);
			testPaper.setTotalSeries(3);
			testPaper.setPassScore(60);
			testPaper.setCanIgnoreQuestionScore(true);
			testPaper.setStatus(TestPaperStatusEnum.Opened.toValue());
			testPaper.setCreatedTime(DateUtility.getCurDate());
			testPaper.setCreator(1l);
			
			this.daoFacade.getTestPaperDAO().insert(testPaper);
			
			TestPaperQuestion testPaperQuestion11 = new TestPaperQuestion();
			testPaperQuestion11.setTestPaperId(testPaper.getId());
			testPaperQuestion11.setSeries(1);
			testPaperQuestion11.setQuestionType(QuestionTypeEnum.Judge.toValue());
			testPaperQuestion11.setName("判断题第1套11");
			testPaperQuestion11.setOptions(null);
			testPaperQuestion11.setAnswer("1");
			testPaperQuestion11.setDifficulty(1);
			testPaperQuestion11.setKen("ken1");
			testPaperQuestion11.setSortFlag(0);
			testPaperQuestion11.setScore(10.0);
			this.daoFacade.getTestPaperQuestionDAO().insert(testPaperQuestion11);
			
			TestPaperQuestion testPaperQuestion21 = new TestPaperQuestion();
			testPaperQuestion21.setTestPaperId(testPaper.getId());
			testPaperQuestion21.setSeries(2);
			testPaperQuestion21.setQuestionType(QuestionTypeEnum.Judge.toValue());
			testPaperQuestion21.setName("判断题第2套21");
			testPaperQuestion21.setOptions(null);
			testPaperQuestion21.setAnswer("1");
			testPaperQuestion21.setDifficulty(1);
			testPaperQuestion21.setKen("ken1");
			testPaperQuestion21.setSortFlag(0);
			testPaperQuestion21.setScore(10.0);
			this.daoFacade.getTestPaperQuestionDAO().insert(testPaperQuestion21);
			
			TestPaperQuestion testPaperQuestion31 = new TestPaperQuestion();
			testPaperQuestion31.setTestPaperId(testPaper.getId());
			testPaperQuestion31.setSeries(3);
			testPaperQuestion31.setQuestionType(QuestionTypeEnum.Judge.toValue());
			testPaperQuestion31.setName("判断题第3套31");
			testPaperQuestion31.setOptions(null);
			testPaperQuestion31.setAnswer("1");
			testPaperQuestion31.setDifficulty(1);
			testPaperQuestion31.setKen("ken1");
			testPaperQuestion31.setSortFlag(0);
			testPaperQuestion31.setScore(10.0);
			this.daoFacade.getTestPaperQuestionDAO().insert(testPaperQuestion31);
			
			TestPaperQuestion testPaperQuestion12 = new TestPaperQuestion();
			testPaperQuestion12.setTestPaperId(testPaper.getId());
			testPaperQuestion12.setSeries(1);
			testPaperQuestion12.setQuestionType(QuestionTypeEnum.SingleSelect.toValue());
			testPaperQuestion12.setName("单选题题第1套12");
			testPaperQuestion12.setOptions("aaa1;aaa2;aaa3;aaa4;");
			testPaperQuestion12.setAnswer("A");
			testPaperQuestion12.setDifficulty(1);
			testPaperQuestion12.setKen("ken1");
			testPaperQuestion12.setSortFlag(0);
			testPaperQuestion12.setScore(10.0);
			this.daoFacade.getTestPaperQuestionDAO().insert(testPaperQuestion12);
			
			TestPaperQuestion testPaperQuestion22 = new TestPaperQuestion();
			testPaperQuestion22.setTestPaperId(testPaper.getId());
			testPaperQuestion22.setSeries(2);
			testPaperQuestion22.setQuestionType(QuestionTypeEnum.SingleSelect.toValue());
			testPaperQuestion22.setName("单选题题第2套22");
			testPaperQuestion22.setOptions("bbb1;bbb2;bbb3;bbb4;");
			testPaperQuestion22.setAnswer("B");
			testPaperQuestion22.setDifficulty(1);
			testPaperQuestion22.setKen("ken1");
			testPaperQuestion22.setSortFlag(0);
			testPaperQuestion22.setScore(10.0);
			this.daoFacade.getTestPaperQuestionDAO().insert(testPaperQuestion22);
			
			TestPaperQuestion testPaperQuestion32 = new TestPaperQuestion();
			testPaperQuestion32.setTestPaperId(testPaper.getId());
			testPaperQuestion32.setSeries(3);
			testPaperQuestion32.setQuestionType(QuestionTypeEnum.SingleSelect.toValue());
			testPaperQuestion32.setName("单选题题第3套32");
			testPaperQuestion32.setOptions("ccc1;ccc2;ccc3;ccc4;");
			testPaperQuestion32.setAnswer("C");
			testPaperQuestion32.setDifficulty(1);
			testPaperQuestion32.setKen("ken1");
			testPaperQuestion32.setSortFlag(0);
			testPaperQuestion32.setScore(10.0);
			this.daoFacade.getTestPaperQuestionDAO().insert(testPaperQuestion32);	
			
			TestPaperQuestion testPaperQuestion13 = new TestPaperQuestion();
			testPaperQuestion13.setTestPaperId(testPaper.getId());
			testPaperQuestion13.setSeries(1);
			testPaperQuestion13.setQuestionType(QuestionTypeEnum.MultiSelect.toValue());
			testPaperQuestion13.setName("多选题题第1套13");
			testPaperQuestion13.setOptions("aaa1;aaa2;aaa3;aaa4;");
			testPaperQuestion13.setAnswer("AB");
			testPaperQuestion13.setDifficulty(1);
			testPaperQuestion13.setKen("ken1");
			testPaperQuestion13.setSortFlag(0);
			testPaperQuestion13.setScore(10.0);
			this.daoFacade.getTestPaperQuestionDAO().insert(testPaperQuestion13);
			
			TestPaperQuestion testPaperQuestion23 = new TestPaperQuestion();
			testPaperQuestion23.setTestPaperId(testPaper.getId());
			testPaperQuestion23.setSeries(2);
			testPaperQuestion23.setQuestionType(QuestionTypeEnum.MultiSelect.toValue());
			testPaperQuestion23.setName("多选题题第2套23");
			testPaperQuestion23.setOptions("bbb1;bbb2;bbb3;bbb4;");
			testPaperQuestion23.setAnswer("BC");
			testPaperQuestion23.setDifficulty(1);
			testPaperQuestion23.setKen("ken1");
			testPaperQuestion23.setSortFlag(0);
			testPaperQuestion23.setScore(10.0);
			this.daoFacade.getTestPaperQuestionDAO().insert(testPaperQuestion23);
			
			TestPaperQuestion testPaperQuestion33 = new TestPaperQuestion();
			testPaperQuestion33.setTestPaperId(testPaper.getId());
			testPaperQuestion33.setSeries(3);
			testPaperQuestion33.setQuestionType(QuestionTypeEnum.MultiSelect.toValue());
			testPaperQuestion33.setName("多选题题第3套33");
			testPaperQuestion33.setOptions("ccc1;ccc2;ccc3;ccc4;");
			testPaperQuestion33.setAnswer("CD");
			testPaperQuestion33.setDifficulty(1);
			testPaperQuestion33.setKen("ken1");
			testPaperQuestion33.setSortFlag(0);
			testPaperQuestion33.setScore(10.0);
			this.daoFacade.getTestPaperQuestionDAO().insert(testPaperQuestion33);		
			
			Exam exam = new Exam();
			exam.setName("2014年公务员正式考试1");
			exam.setDescription("");
			exam.setExamCode("2014sss");
			exam.setExamMode(ExamModeEnum.Together.toValue());
			exam.setExamKey(SystemUtility.createUUID());
			exam.setEsExamKey(SystemUtility.createUUID());
			exam.setCategory("");
			exam.setTestPaperId(testPaper.getId());
			exam.setQuestionFetchType(QuestionFetchTypeEnum.Random.toValue());
			exam.setValidFirstTime(null);
			exam.setValidLastTime(null);
			exam.setTimerMode(TimerModeEnum.Limit.toValue());
			exam.setTimerLimit(60);
			exam.setCanAllowAllUser(true);
			exam.setUserJoinedData("");
			exam.setJoinedOrgans("");
			exam.setCanAllowMultiJoin(true);
			exam.setCanCourseStudyLimit(false);
			exam.setCanKeepSecretScore(false);
			exam.setCanQueryAnswer(true);
			exam.setCanLimitValidTime(false);
			exam.setCanLimitCommitTime(false);
			exam.setCanMatchDutyRank(false);
			exam.setDutyRank("");
			exam.setCanMatchTrade(false);
			exam.setTrade("");
			exam.setStatus(ExamStatusEnum.Created.toValue());
			exam.setCreatedTime(DateUtility.getCurDate());
			exam.setCreator(1l);
			
			this.daoFacade.getExamDAO().insert(exam);
			
			ExamServer examServer = new ExamServer();
			examServer.setName("泰山考试服务器");
			examServer.setUrl("http://127.0.0.1:8080/ExamServer");
			examServer.setNote("");
			examServer.setStatus(ExamServerStatusEnum.Opened.toValue());
			
			this.daoFacade.getExamServerDAO().insert(examServer);
			
			this.setJsonResult_ActionResult(exam);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	@Override
	public TestActionForm getModel() {
		return actionForm;
	}

}

