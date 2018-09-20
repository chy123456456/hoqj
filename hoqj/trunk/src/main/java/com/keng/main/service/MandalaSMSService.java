package com.keng.main.service;

import java.util.List;

import com.keng.base.common.Pager;

public interface MandalaSMSService {

	void getAllsmss(Pager pager);

	List<?> getAllSendTables();

}
