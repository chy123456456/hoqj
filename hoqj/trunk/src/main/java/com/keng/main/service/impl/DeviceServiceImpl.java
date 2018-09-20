package com.keng.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.keng.base.common.Pager;
import com.keng.main.entity.Device;
import com.keng.main.mapper.DeviceMapper;
import com.keng.main.service.DeviceService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class DeviceServiceImpl implements DeviceService {
	@Autowired
	private DeviceMapper deviceMapper;

	@Override
	public void query(Pager pager) {
		pager.setResult(deviceMapper.selectPager(pager.getParamsMap()));
	    pager.setTotalRecord(deviceMapper.selectPagerCount(pager.getParamsMap()));
	}

	@Override
	public int add(Device device) {
		return deviceMapper.insertSelective(device);
	}

	@Override
	public int edit(Device device) {
		return deviceMapper.updateByPrimaryKeySelective(device);
	}

	@Override
	public List<Device> selectByDeviceNo(String deviceNo) {
		Device device = new Device();
		device.setDeviceNo(deviceNo);
		return deviceMapper.selectByDeviceNo(device);
	}
}
