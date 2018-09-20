package com.keng.base.common;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keng.main.mapper.CommonMapper;

@Service
public class BaseService {
    protected Log          log;
    @Autowired
    protected CommonMapper commonMapper;

    public BaseService() {
        log = LogFactory.getLog(getClass());
    }

    public Date getCurrentDate() {
        // Map<String, Object> params = null;
        // return commonMapper.selectDbCurrentDate(params);
        return new Date();
    }

    public Date getCurrentTimestamp() {
        Map<String, Object> params = null;
        return commonMapper.selectDbCurrentTimestamp(params);
    }

    public CommonMapper getCommonMapper() {
        return commonMapper;
    }

    public Log getLogger() {
        return log;
    }

    public void setCommonMapper(CommonMapper commonMapper) {
        this.commonMapper = commonMapper;
    }
}
