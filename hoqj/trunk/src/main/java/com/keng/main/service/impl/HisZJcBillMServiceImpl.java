package com.keng.main.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.keng.base.common.Pager;
import com.keng.main.mapper.HisZJcBillMMapper;
import com.keng.main.service.HisZJcBillMService;
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class HisZJcBillMServiceImpl implements HisZJcBillMService{

	@Autowired
	private HisZJcBillMMapper hisZJcBillMMapper;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getJyList(Pager pager) {
		//查询上一页的截止
		Integer lastRowNum = (pager.getPageNo()-1)*pager.getPageSize();
		Map map = pager.getParamsMap();
		map.put("lastRowNum", lastRowNum);
		List<String> ids = hisZJcBillMMapper.getPageIds(map);
		map.put("lastId",(ids==null||ids.size()==0)?null:ids.get(ids.size()-1));
		pager.setResult(hisZJcBillMMapper.selectPager(map));
	    pager.setTotalRecord(hisZJcBillMMapper.selectPagerCount(map));
		
	}

}
