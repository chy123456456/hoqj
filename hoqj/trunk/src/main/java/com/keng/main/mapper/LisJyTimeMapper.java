package com.keng.main.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface LisJyTimeMapper {

	@SuppressWarnings("rawtypes")
	List<Map> getListByIds(@Param(value="ids")List<String> ids);
	
}
