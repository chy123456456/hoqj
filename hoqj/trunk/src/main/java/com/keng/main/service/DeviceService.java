package com.keng.main.service;

import java.util.List;

import com.keng.base.common.Pager;
import com.keng.main.entity.Device;

public interface DeviceService {

	/**
	 * 分页查询
	 * @param pager
	 * @return
	 */
	void query(Pager pager);

	int add(Device device);

	int edit(Device device);

	List<Device> selectByDeviceNo(String deviceNo);

}
