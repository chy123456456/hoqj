package com.keng.main.service;

import com.keng.base.common.Pager;
import com.keng.main.entity.Ylaqrb;

public interface MedicalSafeService {

	void query(Pager pager);

	void insert(Ylaqrb ylaqrb);

}
