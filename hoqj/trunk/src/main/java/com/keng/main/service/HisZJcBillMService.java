package com.keng.main.service;

import com.keng.base.common.Pager;

/**
 * HIS系统检查检验
 * @author Administrator
 *
 */
public interface HisZJcBillMService {

	/**
	 * 获取HIS检验列表信息
	 * @param pager
	 */
	void getJyList(Pager pager);

}
