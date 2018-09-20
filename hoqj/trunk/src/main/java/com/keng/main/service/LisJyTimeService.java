package com.keng.main.service;

import java.util.List;
import java.util.Map;

public interface LisJyTimeService {

	/**
	 * 根据检验申请单号ids 查询lis申请单时间状态
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getListByIds(List<String> ids);

}
