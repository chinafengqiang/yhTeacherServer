package com.study.model.factory;
import java.util.List;

import com.study.enums.ExamServerStatusEnum;
import com.study.model.ExamServer;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 考试服务器数据工厂接口
 */
public interface ExamServerFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	ExamServer findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<ExamServer> findListByAll();
	
	/**
	 * 按名称获取考试服务器记录
	 * @param name 名称
	 * @return 考试服务器
	 */
	public ExamServer findByName(String name);
	
	/**
	 * 按链接地址获取考试服务器记录
	 * @param url 链接地址
	 * @return 考试服务器
	 */
	public ExamServer findByUrl(String url);
	
	/**
	 * 按内网链接地址获取考试服务器记录
	 * @param directUrl 内网链接地址
	 * @return 考试服务器
	 */
	 ExamServer findByDirectUrl(String directUrl);
	
	/**
	 * 按状态获取服务器列表
	 * @param statusEnum 状态
	 * @return
	 */
	List<ExamServer> findListByStatus(ExamServerStatusEnum statusEnum);
	
	/**
	 * 按搜索条件获取考试服务器分页列表
	 * @param paginateParamters 分页参数
	 * @return 考试服务器分页列表
	 */
	PaginateResult findListBySearch(PaginateParamters paginateParamters);

}
