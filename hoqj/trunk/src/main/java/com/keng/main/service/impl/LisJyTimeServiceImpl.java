package com.keng.main.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.keng.main.mapper.LisJyTimeMapper;
import com.keng.main.service.LisJyTimeService;
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class LisJyTimeServiceImpl implements LisJyTimeService{

	@Autowired
	LisJyTimeMapper jyTimeMapper;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getListByIds(List<String> ids) {
	    return jyTimeMapper.getListByIds(ids);
	}

}
