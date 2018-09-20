package com.keng.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.keng.base.common.Pager;
import com.keng.main.entity.Ylaqrb;
import com.keng.main.mapper.YlaqrbMapper;
import com.keng.main.service.MedicalSafeService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class MedicalSafeServiceImpl implements MedicalSafeService{
	@Autowired
	private YlaqrbMapper ylaqrbMapper;

	@Override
	public void query(Pager pager) {
		pager.setResult(ylaqrbMapper.selectPager(pager.getParamsMap()));
	    pager.setTotalRecord(ylaqrbMapper.selectPagerCount(pager.getParamsMap()));
	}

	@Override
	public void insert(Ylaqrb ylaqrb) {
		ylaqrbMapper.insertSelective(ylaqrb);
	}
}
