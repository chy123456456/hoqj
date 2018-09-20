package com.keng.base.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cache.Cache;
import org.mybatis.caches.ehcache.EhcacheCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keng.base.service.DisplayService;
import com.keng.main.entity.SysConfig;
import com.keng.main.entity.SysRoles;
import com.keng.main.entity.SysUsers;
import com.keng.main.mapper.SysConfigMapper;
import com.keng.main.mapper.SysRolesMapper;
import com.keng.main.mapper.SysUsersMapper;


@Service
public class DisplayServiceImpl implements DisplayService {
    private String       DISPLAY_CACHE_KEY = "DISPLAY_CACHE_KEY";
    private @Autowired
    SysUsersMapper       usersMapper;
    private @Autowired
    SysRolesMapper       rolesMapper;
    private @Autowired
    SysConfigMapper      confMapper;

    /**
     * 获取Cache对象
     * @date 2014-3-4 下午1:16:34
     * @param id
     * @return
     */
    private Cache getCache(String id) {
        return new EhcacheCache(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Object, String> displayUser() {
        Map<Object, String> result = null;
        // 获取缓存对象
        Cache cache = getCache(SysUsersMapper.class.getName());
        String key = DISPLAY_CACHE_KEY + "_DISPLAY_USER";
        Object cacheResult = cache.getObject(key);
        if (cacheResult != null) {
            result = (Map<Object, String>) cacheResult;
        } else {
            result = new HashMap<Object, String>();
            List<SysUsers> users = usersMapper.getAllUsers();
            for (SysUsers user : users) {
                result.put(user.getId(), user.getName());
            }
            cache.putObject(key, result);
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Object, String> displayRole() {
        Map<Object, String> result = null;
        // 获取缓存对象
        Cache cache = getCache(SysUsersMapper.class.getName());
        String key = DISPLAY_CACHE_KEY + "_DISPLAY_ROLE";
        Object cacheResult = cache.getObject(key);
        if (cacheResult != null) {
            result = (Map<Object, String>) cacheResult;
        } else {
            result = new HashMap<Object, String>();
            List<SysRoles> roles = rolesMapper.getAllRoles();
            for (SysRoles role : roles) {
                result.put(role.getRoleId(), role.getRoleName());
            }
            cache.putObject(key, result);
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Object, String> displayConf(String cfgId) {
        Map<Object, String> result;
        // 获取缓存对象
        Cache cache = getCache(SysConfigMapper.class.getName());
        String key = DISPLAY_CACHE_KEY + "_DISPLAY_CONF$" + cfgId;
        Object cacheResult = cache.getObject(key);
        if (cacheResult != null) {
            result = (Map<Object, String>) cacheResult;
        } else {
            result = new HashMap<Object, String>();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("cfgId", cfgId);
            List<SysConfig> confs = confMapper.selectByParams(params);
            for (SysConfig conf : confs) {
                result.put(conf.getCfgKey(), conf.getCfgVal());
                //result.put(conf.getId(), conf.getCfgVal());
            }
            cache.putObject(key, result);
        }
        return result;
    }

    
}
