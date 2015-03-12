package com.study.model.factory;

import java.util.List;

import com.study.enums.NoticeGradeEnum;
import com.study.enums.NoticeStatusEnum;
import com.study.model.Notice;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 公告数据工厂接口
 */
public interface NoticeFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	Notice findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<Notice> findListByAll();
	
	/**
	 * 获取指定数量的记录（按sortFlag倒排序）
	 */
	List<Notice> findListByGrade_Limit(NoticeGradeEnum gradeEnum, Integer limit);

	/**
	 * 按搜索条件获取在教师平台公告分页列表
	 * @param noticeGradeEnum 类型
	 * @param statusEnum 状态
	 * @param title 标题
	 * @param paginateParamters 分页参数
	 * @return 公告分页列表
	 */
	PaginateResult findListBySearch(NoticeGradeEnum noticeGradeEnum, NoticeStatusEnum statusEnum, String title, PaginateParamters paginateParamters);
	
	/**
	 * 按搜索条件获取在单位平台显示的公告列表
	 * @param noticeGradeEnum 类型
	 * @param title 标题
	 * @param paginateParamters 分页参数
	 * @return 公告分页列表
	 */
	PaginateResult findListByOrgan(NoticeGradeEnum noticeGradeEnum, String title, PaginateParamters paginateParamters);
	
	/**
	 * 获取学员公告分页列表
	 * @param paginateParamters 分页参数
	 * @return 公告分页列表
	 */
	PaginateResult findListByUser(PaginateParamters paginateParamters);
}
