package com.keng.main.mapper;

import java.util.List;
import java.util.Map;

public interface HisZJcBillMMapper {
	
	public List<String> getPageIds(Map<String, Object> paramsMap);

	@SuppressWarnings("rawtypes")
	public List<?> selectPager(Map map);

	@SuppressWarnings("rawtypes")
	public int selectPagerCount(Map map);

}
