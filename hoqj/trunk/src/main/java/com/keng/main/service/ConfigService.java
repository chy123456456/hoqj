package com.keng.main.service;
import java.util.List;

import com.keng.base.common.Pager;
import com.keng.main.entity.SysConfig;
import com.keng.main.entity.SysConfigKey;

public interface ConfigService {
    /**
     * 查询所有配置
     * @date 2014-3-21 下午1:50:07
     * @param pager
     * @return
     */
    public void queryPager(Pager pager);

    /**
     * 查询指定Id的配置
     * @date 2014-3-21 下午2:05:57
     * @param cfgId
     * @return
     */
    public List<SysConfig> getByCfgId(String cfgId);

    /**
     * 查询指定Id及Key的配置
     * @date 2014-3-21 下午2:05:57
     * @param cfgId
     * @param cfgKey
     * @return
     */
    public SysConfig getByCfgidAndCfgkey(String cfgId, String cfgKey);

    /**
     * 查询指定父编号的配置
     * @date 2014-3-21 下午2:06:29
     * @param parentCfg
     * @return
     */
    public List<SysConfig> getByParentCfg(String parentCfg);

    /**
     * 查询父编号及父Key的配置
     * @date 2014-3-21 下午2:07:26
     * @param parentCfg
     * @param parentKey
     * @return
     */
    public List<SysConfig> getByParentKey(String parentCfg, String parentKey);

    /**
     * 添加配置
     * @date 2014-4-8 下午5:37:30
     * @param conf
     * @return
     */
    public int addConf(SysConfig conf);

    /**
     * 修改配置
     * @date 2014-4-8 下午5:37:57
     * @param conf
     * @return
     */
    public int updConf(SysConfig conf);

    /**
     * 删除配置
     * @date 2014-4-8 下午5:38:18
     * @param key
     * @return
     */
    public int delConf(SysConfigKey key);

    /**
     * 根据id查询配置信息
     * @date 2015年1月16日 下午4:28:32
     * @param key
     * @param pkey
     * @return
     */
    public List<SysConfig> getByIdAndPkey(String key, String pkey);


}
