package com.keng.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.keng.base.common.Pager;
import com.keng.main.entity.Zdssrb;
import com.keng.main.entity.Zzynrb;
import com.keng.main.mapper.ZdssrbMapper;
import com.keng.main.service.MajorSurgicalPatUploadService;
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class MajorSurgicalPatUploadServiceImpl implements MajorSurgicalPatUploadService {

	@Autowired
	private ZdssrbMapper zdssrbMapper;
	@Override
	public void query(Pager pager) {
		pager.setResult(zdssrbMapper.selectPager(pager.getParamsMap()));
	    pager.setTotalRecord(zdssrbMapper.selectPagerCount(pager.getParamsMap()));
	}
	@Override
	public void insert(Zdssrb zdssrb) {
		zdssrbMapper.insertSelective(zdssrb);
	}

}
