package com.keng.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.keng.base.common.Pager;
import com.keng.main.entity.Zzynrb;
import com.keng.main.mapper.ZzynrbMapper;
import com.keng.main.service.DifficultSevereService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class DifficultSevereServiceImpl implements DifficultSevereService {
	@Autowired
	private ZzynrbMapper zzynrbMapper;

	@Override
	public void query(Pager pager) {
		pager.setResult(zzynrbMapper.selectPager(pager.getParamsMap()));
	    pager.setTotalRecord(zzynrbMapper.selectPagerCount(pager.getParamsMap()));
	}

	@Override
	public void insert(Zzynrb zzynrb) {
		zzynrbMapper.insertSelective(zzynrb);
	}

}
