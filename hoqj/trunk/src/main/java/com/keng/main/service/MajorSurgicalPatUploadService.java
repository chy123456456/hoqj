package com.keng.main.service;

import com.keng.base.common.Pager;
import com.keng.main.entity.Zdssrb;
import com.keng.main.entity.Zzynrb;

public interface MajorSurgicalPatUploadService {

	void query(Pager pager);

	void insert(Zdssrb zdssrb);

}
