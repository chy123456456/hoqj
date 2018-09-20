package com.keng.main.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.keng.main.entity.HisUpbgh;
import com.keng.main.entity.SysDept;

public interface HisUpbghMapper {

	public List<?> selectPager(Map<String, Object> paramsMap);

	public int selectPagerCount(Map<String, Object> paramsMap);

	public List<SysDept> getDeptsByTime(HisUpbgh gh);

	/**
	 * 根据科室id获取排班医生列表
	 * @param gh
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getDocsByDeptId(HisUpbgh gh);

	public Object addGh(@Param(value="ghdeptId")String ghdeptId, @Param(value="docId")String docId);

	public Object addGh5(@Param(value="ghdeptId")String ghdeptId, @Param(value="docId")String docId);

	public List<String> getPageIds(Map map);

	public Object getGhTime();

	/**
	 * 修改挂号起止时间
	 * @param map
	 * @return
	 */
	public int editGhTime(Map map);

}
