package com.keng.main.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.keng.base.common.Pager;
import com.keng.main.entity.SysConfig;
import com.keng.main.entity.SysConfigKey;
import com.keng.main.mapper.SysConfigMapper;
import com.keng.main.service.ConfigService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private SysConfigMapper configMapper;

    public void queryPager(Pager pager) {
        pager.setResult(configMapper.selectPager(pager.getParamsMap()));
        pager.setTotalRecord(configMapper.selectPagerCount(pager.getParamsMap()));
    }

    public List<SysConfig> getByCfgId(String cfgId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", 1);
        params.put("cfgId", cfgId);
        return configMapper.selectByParams(params);
    }

    @Override
    public SysConfig getByCfgidAndCfgkey(String cfgId, String cfgKey) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", 1);
        params.put("cfgId", cfgId);
        params.put("cfgKey", cfgKey);
        List<SysConfig> confs = configMapper.selectByParams(params);
        if (confs.size() > 0) {
            return confs.get(0);
        }
        return null;
    }

    public List<SysConfig> getByParentCfg(String parentCfg) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", 1);
        params.put("parentCfg", parentCfg);
        return configMapper.selectByParams(params);
    }

    public List<SysConfig> getByParentKey(String parentCfg, String parentKey) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", 1);
        params.put("parentCfg", parentCfg);
        params.put("parentKey", parentKey);
        return configMapper.selectByParams(params);
    }

    @Override
    public int addConf(SysConfig conf) {
        return configMapper.insertSelective(conf);
    }

    @Override
    public int updConf(SysConfig conf) {
        return configMapper.updateByPrimaryKey(conf);
    }

    @Override
    public int delConf(SysConfigKey key) {
        return configMapper.deleteByPrimaryKey(key);
    }

    @Override
    public List<SysConfig> getByIdAndPkey(String cfgId, String pkey) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (pkey != null) {
            params.put("parentKey", pkey);
        }
        params.put("status", 1);
        params.put("cfgId", cfgId);
        return configMapper.selectByParams(params);
    }

}
