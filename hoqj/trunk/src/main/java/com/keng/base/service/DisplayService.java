package com.keng.base.service;
import java.util.Map;

public interface DisplayService {
    /**
     * 翻译用户信息
     * @date 2014-3-4 下午1:09:42
     * @return
     */
    public Map<Object, String> displayUser();

    /**
     * 翻译角色信息
     * @date 2014-3-4 下午1:15:41
     * @return
     */
    public Map<Object, String> displayRole();

    /**
     * 翻译配置信息
     * @param cfgId
     * @return
     */
    public Map<Object, String> displayConf(String id);
    


}
