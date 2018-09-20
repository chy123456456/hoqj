package com.keng.main.service;

import java.util.List;
import java.util.Map;

import com.keng.base.common.Pager;
import com.keng.main.entity.HisUpbgh;
import com.keng.main.entity.SysDept;

public interface SchedulingService {

	/**
	 * 获取当日排班的科室
	 * @return
	 */
	List<SysDept> getDeptsByTime(HisUpbgh gh);

	/**
	 * 查询排班列表
	 * @param pager
	 */
	public void getList(Pager pager);

	@SuppressWarnings("rawtypes")
	List<Map> getDocsByDeptId(HisUpbgh gh);

	/**
	 * 加号
	 * @param ghdeptId
	 * @param docId
	 * @param ghnum
	 * @return
	 */
	Object addGh(String ghdeptId, String docId, Integer ghnum);

	/**
	 * 获取挂号起止时间数据
	 * @return
	 */
	Object getGhTime();

	/**
	 * 修改挂号起止时间
	 * @param c_code
	 * @param c_value
	 * @return
	 */
	int editGhTime(String c_code, String c_value);


}
