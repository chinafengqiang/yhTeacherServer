package com.study.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.rmi.repository.URLRemoteRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.dao.DAOFacade;
import com.study.enums.QuestionTypeEnum;
import com.study.model.Question;
import com.study.model.QuestionCategory;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.QuestionService;
import com.study.service.SystemService;
import com.study.utility.ReadConfig;
import com.study.utility.StringUtility;
import com.study.utility.SystemUtility;

/**
 * 题目业务接口实现类
 */
@Service
public class QuestionServiceImpl implements QuestionService {

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(QuestionServiceImpl.class);

	/**
	 * 题目分类树列表缓存Key
	 */
	private static String QuestionCategoryListByTreeMemKey = "QuestinoCategoryListByTreeMemKey";

	/**
	 * 数据操作门面
	 */
	@Resource
	private DAOFacade daoFacade;
	
	/**
	 * 数据工厂门面 
	 */
	@Resource
	private ModelFactoryFacade modelFactoryFacade;

	/**
	 * Memcached客户端
	 */
	@Resource
	private MemcachedClient memcachedClient;
	
	/**
	 * 系统业务接口 
	 */
	@Resource
	private SystemService systemService;
	
	/**
	 * 创建分类同级节点
	 * @param curCategoryId 当前节点编号
	 * @param name 名称
	 * @param sortFlag 排序
	 * @return 分类节点
	 * @throws Exception
	 */
	public QuestionCategory createCategory(Long curCategoryId, String name, Integer sortFlag) throws Exception {
		
		//获取当前节点
		QuestionCategory curCategory = this.modelFactoryFacade.getQuestionCategoryFactory().findById(curCategoryId);
		
		//判断当前节点是否存在
		if (curCategory == null) {
			throw new Exception("当前分类节点已不存在!");
		}
		
		//判断是否顶级节点
		if (curCategory.getParentId().equals(0l)) {
			throw new Exception("不可创建同级的顶级节点!");
		}
		
		//创建同级节点
		QuestionCategory category = new QuestionCategory();
		
		category.setName(name);
		category.setSortFlag(sortFlag);
		category.setParentId(curCategory.getParentId());
		category.setLevel(curCategory.getLevel());
		
		this.daoFacade.getQuestionCategoryDAO().insert(category);
		
 		return category;
	}
	
	/**
	 * 创建分类下级节点
	 * @param curCategoryId 当前节点编号
	 * @param name 名称
	 * @param sortFlag 排序
	 * @return 分类节点
	 * @throws Exception
	 */
	public QuestionCategory createChildCategory(Long curCategoryId, String name, Integer sortFlag) throws Exception {
	
		//获取当前节点
		QuestionCategory curCategory = this.modelFactoryFacade.getQuestionCategoryFactory().findById(curCategoryId);
		
		//判断当前节点是否存在
		if (curCategory == null) {
			throw new Exception("当前分类节点已不存在!");
		}
		
		//创建下级节点
		QuestionCategory category = new QuestionCategory();
		
		category.setName(name);
		category.setSortFlag(sortFlag);
		category.setParentId(curCategoryId);
		category.setLevel(curCategory.getLevel() + 1);
		
		this.daoFacade.getQuestionCategoryDAO().insert(category);
		
		//清除分类树缓存
		this.clearQuestionCategoryListByTreeCache();

 		return category;
	}
	
	/**
	 * 修改分类节点
	 * @param curCategoryId 当前节点编号
	 * @param name 名称
	 * @param sortFlag 排序
	 * @return 分类节点
	 * @throws Exception
	 */
	public QuestionCategory modifyCategory(Long curCategoryId, String name, Integer sortFlag) throws Exception {
	
		//获取当前节点
		QuestionCategory curCategory = this.modelFactoryFacade.getQuestionCategoryFactory().findById(curCategoryId);
		
		//判断当前节点是否存在
		if (curCategory == null) {
			throw new Exception("当前分类节点已不存在!");
		}
		
		curCategory.setName(name);
		curCategory.setSortFlag(sortFlag);
		
		this.daoFacade.getQuestionCategoryDAO().update(curCategory);
		
		//清除分类树缓存
		this.clearQuestionCategoryListByTreeCache();
		
 		return curCategory;
	}
	
	/**
	 * 删除节点
	 * @param curCategoryId 当前节点编号
	 * @throws Exception
	 */
	public void removeCategory(Long curCategoryId) throws Exception {
		
		//获取当前节点
		QuestionCategory curCategory = this.modelFactoryFacade.getQuestionCategoryFactory().findById(curCategoryId);
		
		//判断当前节点是否存在
		if (curCategory == null) {
			return;
		}
		
		//判断是否顶级节点
		if (curCategory.getParentId().equals(0l)) {
			throw new Exception("不可删除顶级节点!");
		}
		
		//判断是否存在下级
		if (this.modelFactoryFacade.getQuestionCategoryFactory().findCountByParant(curCategoryId) > 0) {
			throw new Exception("存在下级分类节点，不可删除!");
		}
		
		//判断是否存在题目
		if (this.modelFactoryFacade.getQuestionFactory().findCountByQuestionCategory(curCategoryId) > 0) {
			throw new Exception("此分类节点下存在题目，不可删除!");
		}
		
		//删除题目分类
		this.daoFacade.getQuestionCategoryDAO().delete(curCategoryId);
		
		//清除分类树缓存
		this.clearQuestionCategoryListByTreeCache();
	}
	
	/**
	 * 校验题目
	 * @param question 题目
	 * @throws Exception
	 */
	private void validateQuestion(Question question) throws Exception {
		
		QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.valueOf(question.getQuestionType());
		String answer = question.getAnswer();
		String options = question.getOptions();
		Double score = question.getScore();
		
		//判断题目内容是否为空
		if (StringUtils.isBlank(question.getName().trim())) {
			throw new Exception("缺题目内容");
		}
		
		//判断难度值是否存在
		if (question.getDifficulty() == null) {
			throw new Exception("缺题目难度值！");
		}
		
		//判断难度值是否合理
		if (question.getDifficulty() <= 0 || question.getDifficulty() > 5) {
			throw new Exception("题目难度值填写错误,应该是1-5！");
		}
		
		//判断答案是否为空
		if (StringUtils.isBlank(question.getAnswer().trim())) {
			throw new Exception("缺答案");
		}
		
		//判断答案中是否存在汉字
		if (StringUtility.checkChinese(question.getAnswer())) {
			throw new Exception("答案有问题：含有汉字");
		}
		
		//校验试卷分数不能小于0 
		if(score <= 0) {
			throw new Exception("题目分值应大于0" );
		}
		
		//校验试卷分数不能大于100
		if(score > 100) {
			throw new Exception("题目分值应小于等于100" );
		}
		
		//清理多选题的答案过多情况
		if (questionTypeEnum.equals(QuestionTypeEnum.MultiSelect)) {
			if (question.getAnswer().length() > question.getOptions().split(";").length) {
				throw new Exception("答案有问题：多选题答案选项不匹配");
			}
		}
		
		//清理单选题或判断题的答案过多情况
		if (questionTypeEnum.equals(QuestionTypeEnum.SingleSelect) || questionTypeEnum.equals(QuestionTypeEnum.Judge)) {
			if (question.getAnswer().length() > 1) {
				throw new Exception("答案有问题：单选题或判断题答案选项不匹配");
			}
		}
		
		//判断题目选项是否为空格 
		if(questionTypeEnum.equals(QuestionTypeEnum.MultiSelect) || questionTypeEnum.equals(QuestionTypeEnum.SingleSelect)){
			String[] optionsList = question.getOptions().split(";");
			for(String s:optionsList){
				if(StringUtils.isBlank(s)){
					throw new Exception("答案选项有问题：单选题或多选题答案选项不能以空格为答案");
				}
			}
		}
		
		//校验选择题答案数量
		if (questionTypeEnum.equals(QuestionTypeEnum.SingleSelect) || questionTypeEnum.equals(QuestionTypeEnum.MultiSelect)){
				
			//判断单选题答案及数量是否越界
			if (questionTypeEnum.equals(QuestionTypeEnum.SingleSelect) && answer.length() > 1) {
				throw new Exception("单选题答案只能有一个答案!" );
			}
			
			//校验多选题答案选项是否与候选项匹配
			if (questionTypeEnum.equals(QuestionTypeEnum.MultiSelect) && answer.length() > options.split(";").length) {
				throw new Exception("多选题答案数量不能超过选项数量!" );
			}
			
			//判断答案是否越界ABCDEFGH
			if (!verifyAnswerOverflow(options, answer)) {
				throw new Exception("题目答案只能是ABCDEFGH中的字母，且不能越界!" );
			}
		}
		
		//判断同一分类节点中是否存在重复的题目
		if (this.modelFactoryFacade.getQuestionFactory().findCountByRepeatQuestion(question.getQuestionCategoryId(), question.getId(), questionTypeEnum, question.getName(), options) > 0) {
			throw new Exception("在同一分类节点中存在相同题目!" );
		}
	}
	
	/**
	 * 校验答案是否越界
	 * @param options 选项
	 * @param answer 答案
	 * @return
	 */
	private Boolean verifyAnswerOverflow(String options, String answer) {
		
		String temp = "ABCDEFGH";
		String[] optionsList = options.split(";");
		
		if (optionsList.length > 8) {
			return false;
		}
		
		temp = temp.substring(0, optionsList.length);
		
		for (int i=0; i < answer.length(); i++) {
			
			String tempChar = answer.substring(i, i + 1);
			if (!temp.contains(tempChar)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 创建题目
	 * @param curCategoryId 分类节点
	 * @param questionTypeEnum 题目类型
	 * @param name 题干
	 * @param answer 答案
	 * @param note 备注
	 * @param options 选项
	 * @param score 分数
	 * @param difficulty 难度
	 * @param ken 知识点
	 * @return
	 * @throws Exception
	 */
	public Question createQuestion(Long curCategoryId, QuestionTypeEnum questionTypeEnum, 
			String name, String answer, String note, String options, Double score, 
			Integer difficulty, String ken) throws Exception {

		//获取当前节点
		QuestionCategory curCategory = this.modelFactoryFacade.getQuestionCategoryFactory().findById(curCategoryId);
		
		//判断当前节点是否存在
		if (curCategory == null) {
			throw new Exception("当前分类节点已不存在!");
		}
		
		//创建题目
		Question question = new Question();
		
		question.setQuestionCategoryId(curCategoryId);
		question.setName(name);
		question.setQuestionType(questionTypeEnum.toValue());
		question.setDifficulty(difficulty);
		question.setOptions(options);
		question.setAnswer(StringUtility.trimSpaceCharacter(answer).toUpperCase());
		question.setNote(note);
		question.setDifficulty(difficulty);
		question.setKen(ken);
		question.setScore(score);
		
		//校验题目
		this.validateQuestion(question);
		
		this.daoFacade.getQuestionDAO().insert(question);
		
		return question;
	}
	
	/**
	 * 修改题目
	 * @param id 题目编号
	 * @param name 题干
	 * @param answer 答案
	 * @param note 备注
	 * @param options 选项
	 * @param score 分数
	 * @param difficulty 难度
	 * @param ken 知识点
	 * @return 题目
	 * @throws Exception
	 */
	public Question modifyQuestion(Long id,	String name, String answer, String note, String options, Double score, 
			Integer difficulty, String ken) throws Exception {
	
		//获取题目
		Question question = this.modelFactoryFacade.getQuestionFactory().findById(id);
		
		//判断此题目是否存在
		if (question == null) {
			throw new Exception("此题目已不存在!");
		}
		
		//修改题目
		question.setName(name);
		question.setOptions(options);
		question.setDifficulty(difficulty);
		question.setAnswer(StringUtility.trimSpaceCharacter(answer).toUpperCase());
		question.setNote(note);
		question.setDifficulty(difficulty);
		question.setKen(ken);
		question.setScore(score);
		
		//校验题目
		this.validateQuestion(question);
		
		this.daoFacade.getQuestionDAO().update(question);
		
		return question;
	}
	
	/**
	 * 删除题目
	 * @param id 题目编号
	 */
	public void removeQuestion(Long id) {
		
		//获取题目
		Question question = this.modelFactoryFacade.getQuestionFactory().findById(id);
		
		//判断此题目是否存在
		if (question == null) {
			return;
		}
		
		//删除题目
		this.daoFacade.getQuestionDAO().delete(id);
	}
	
	/**
	 * 导入题目集
	 * @param questionCategoryId 题目分类编号
	 * @param file Excel文件
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void importQuestionList(Long questionCategoryId, File file) throws Exception {
			
		//从Excel中解析出题目集
		List<Question> questionList = QuestionExcelTools.getQuestionList(file);
		
		//导入题目
		for (int i=0; i<questionList.size(); i++) {
			
			//获取行号
			Integer row = QuestionExcelTools.dataRowFirst + i + 1;
			
			//当前题目
			Question question = questionList.get(i);
			
			//创建题目
			try {
				this.createQuestion(questionCategoryId, 
						QuestionTypeEnum.valueOf(question.getQuestionType()), 
						question.getName(), 
						question.getAnswer(), 
						question.getNote(), 
						question.getOptions(), 
						question.getScore(), 
						question.getDifficulty(), 
						question.getKen());
			} catch (Exception ex) {
				System.out.println("题目校验错误，第" + row + "行出错：" + ex.getMessage());
				throw new Exception("题目校验错误，第" + row + "行出错：" + ex.getMessage());
			}
		}
	}
	
	/**
	 * 导出题目分类的所有题目到Excel
	 * @param questionCategoryId 题目分类编号
	 * @return
	 * @throws IOException
	 */
	public InputStream exportQuestionList(Long questionCategoryId) throws IOException {
		
		//获取题目分类的所有题目
		List<Question> questionList = this.modelFactoryFacade.getQuestionFactory().findListByQuestionCategory(questionCategoryId);
		
		//将题目集转换成工作簿
		HSSFWorkbook workbook = QuestionExcelTools.getWorkbook(questionList);
		
		//创建文件数据流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();   
		workbook.write(baos);   
		
		byte[] ba = baos.toByteArray();   
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);   
		
		return bais;
	}
	
	/**
	 * 导出题目模板
	 * @return
	 * @throws IOException
	 */
	public InputStream exportQuestionExcelTemplet() throws IOException {
		
		//获取模板中的所有题目
		List<Question> questionList = new ArrayList<Question>();
		
		//装载模拟题目
		Question q1 = new Question();
		
		q1.setQuestionType(QuestionTypeEnum.SingleSelect.toValue());
		q1.setName("中国的首都是哪个城市？");
		q1.setKen("地理");
		q1.setDifficulty(1);
		q1.setScore(1.5);
		q1.setNote("");
		q1.setOptions("天津;北京;上海");
		q1.setAnswer("B");
		
		Question q2 = new Question();
		
		q2.setQuestionType(QuestionTypeEnum.MultiSelect.toValue());
		q2.setName("中国的直辖市包括以下哪几个？");
		q2.setKen("地理");
		q2.setDifficulty(3);
		q2.setScore(2d);
		q2.setNote("");
		q2.setOptions("北京;天津;上海;重庆;南昌");
		q2.setAnswer("ABCD");
		
		Question q3 = new Question();
		
		q3.setQuestionType(QuestionTypeEnum.Judge.toValue());
		q3.setName("中国的首都是上海");
		q3.setKen("地理");
		q3.setDifficulty(5);
		q3.setScore(5d);
		q3.setNote("");
		q3.setOptions("");
		q3.setAnswer("0");
		
		questionList.add(q1);
		questionList.add(q2);
		questionList.add(q3);

		//将题目集转换成工作簿
		HSSFWorkbook workbook = QuestionExcelTools.getWorkbook(questionList);
		
		//创建文件数据流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();   
		workbook.write(baos);   
		
		byte[] ba = baos.toByteArray();   
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);   
		
		return bais;
	}
	
	/**
	 * 获取题库分类树列表
	 * @return
	 * @throws Exception
	 */
	public List<QuestionCategory> getQuestionCategoryListByTree() throws Exception {
	
		//从缓存中获取数据
		List<QuestionCategory> list = loadQuestionCategoryListByTreeCache();
		
		//若数据存在则返回
		if (list != null && list.size() > 0) {
			return list;
		}
		
		//重新生成数据并存储到缓冲中
		list = genarateQuestionCategoryListByTree();
		this.saveQuestionCategoryListByTreeCache(list);
		
		return list;
	}
	
	/**
	 * 从缓存中获取分类树数据
	 * @return
	 * @throws Exception
	 */
	private List<QuestionCategory> loadQuestionCategoryListByTreeCache() throws Exception {
		
		return this.memcachedClient.get(QuestionCategoryListByTreeMemKey, this.systemService.getMemGetTimeOut());
	}
	
	/**
	 * 保存分类树数据到缓存中
	 * @return
	 * @throws Exception
	 */
	private void saveQuestionCategoryListByTreeCache(List<QuestionCategory> list) throws Exception {
		
		this.memcachedClient.set(QuestionCategoryListByTreeMemKey, 3600*24, list);
	}
	
	/**
	 * 清除缓存中的分类树数据
	 * @return
	 * @throws Exception
	 */
	private void clearQuestionCategoryListByTreeCache() throws Exception {
		
		this.memcachedClient.delete(QuestionCategoryListByTreeMemKey);
	}

	/**
	 * 生成分类树数据
	 * @return
	 */
	private List<QuestionCategory> genarateQuestionCategoryListByTree() {
	
		List<QuestionCategory> list = new ArrayList<QuestionCategory>();
		
		QuestionCategory top = this.modelFactoryFacade.getQuestionCategoryFactory().findTop();
		doGenarateQuestionCategoryListByTree(top, list);
		
		return list;
	}
	
	/**
	 * 深度递归生成子分类树数据
	 * @param questionCategory 分类节点
	 * @param list
	 */
	private void doGenarateQuestionCategoryListByTree(QuestionCategory questionCategory, List<QuestionCategory> list) {
		
		list.add(questionCategory);
		
		List<QuestionCategory> childList = this.modelFactoryFacade.getQuestionCategoryFactory().findListByParentId(questionCategory.getId());
		
		for (QuestionCategory child : childList) {
			doGenarateQuestionCategoryListByTree(child, list);
		}
	}

	private static Repository repository = null;
	
	/**
	 * 初始化
	 */
	private Repository initRepository(){
		if(null == repository){
			String url = ReadConfig.getString("file.rmi");
			try {
				repository = new URLRemoteRepository(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return repository;
	}
	
	/**
	 * 获得节点
	 * @param root
	 * @param name
	 * @return
	 */
	public Node getNode(Node root,String name){
		Node node=null;
		try {
			if(root.hasNode(name)){
				node= root.getNode(name);
			}else{
				node= root.addNode(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return node;
	}
	
	/**
	 * 获得seesion
	 * @return
	 */
	public Session getSession(){
		Session session=null;
		try {
			session=initRepository().login(new SimpleCredentials("admin", "admin".toCharArray()));
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return session;
	}
	
	
	
	@Override
	public boolean deleteFile(String fileId, String userId) {
		boolean flag=false;
		Session session=getSession();
		try {
			Node root = session.getRootNode();
			Node n=getNode(root, userId);
			NodeIterator iterator=n.getNodes(fileId);
			while(iterator.hasNext()){
				Node fnode=iterator.nextNode();
				fnode.remove();
			}
			session.save();
			session.logout();
			flag=true;
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public InputStream getFileById(String fileId, String userId) {
		InputStream is=null;
		Session session=getSession();
		try {
			Node root=session.getRootNode();
			Node n=getNode(root, userId);
			NodeIterator iterator=n.getNodes(fileId);
			while(iterator.hasNext()){
				Node filenode=iterator.nextNode();
				NodeIterator ni1=filenode.getNodes();
				while(ni1.hasNext()){
					Node ni=ni1.nextNode();
					if(ni.getName().equals("jcr:content")){
						is=ni.getProperty("jcr:data").getStream();
					}
				}
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		session.logout();
		return is;
	}

	@Override
	public String save(InputStream is, String userId) {
		String fileId = SystemUtility.createUUID();
		Session session=getSession();
		try {
			Node root=session.getRootNode();
			Node n=getNode(root, userId);
			String mimeType = "application/octet-stream";
			Node filenode=n.addNode(fileId,"nt:file");
			Node resourceNode=filenode.addNode("jcr:content","nt:resource");
			resourceNode.setProperty("jcr:mimeType", mimeType);
			resourceNode.setProperty("jcr:encoding", "");
			resourceNode.setProperty("jcr:data", is);
			Calendar lastmodifyDate=Calendar.getInstance();
			resourceNode.setProperty("jcr:lastModified", lastmodifyDate);
			session.save();
			is.close();
			session.logout();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileId;
	}
}
