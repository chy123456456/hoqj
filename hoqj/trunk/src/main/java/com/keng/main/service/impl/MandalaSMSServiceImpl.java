package com.keng.main.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.keng.base.common.Pager;
import com.keng.main.mapper.MandalaSMSMapper;
import com.keng.main.service.MandalaSMSService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class MandalaSMSServiceImpl implements MandalaSMSService {

	@Autowired
	private MandalaSMSMapper mandalaSMSMapper;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getAllsmss(Pager pager) {
		// 查询上一页的截止
		Integer lastRowNum = (pager.getPageNo() - 1) * pager.getPageSize();
		Map map = pager.getParamsMap();
		map.put("lastRowNum", lastRowNum);
		List<String> ids = mandalaSMSMapper.getPageIds(map);
		map.put("lastId",(ids==null||ids.size()==0)?null:ids.get(ids.size()-1));
		pager.setResult(mandalaSMSMapper.selectPager(pager.getParamsMap()));
		pager.setTotalRecord(mandalaSMSMapper.selectPagerCount(pager.getParamsMap()));
		
	}

	@Override
	public List<?> getAllSendTables() {
		return mandalaSMSMapper.selectSMStables();
	}

}
