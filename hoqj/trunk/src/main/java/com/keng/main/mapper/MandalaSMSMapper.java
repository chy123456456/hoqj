package com.keng.main.mapper;

import java.util.List;
import java.util.Map;

public interface MandalaSMSMapper {

	List<?> selectPager(Map<String, Object> paramsMap);

	int selectPagerCount(Map<String, Object> paramsMap);
	
	List<String> selectSMStables();

	List<String> getPageIds(Map map);

}
