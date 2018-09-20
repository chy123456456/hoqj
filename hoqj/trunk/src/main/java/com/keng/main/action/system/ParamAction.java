package com.keng.main.action.system;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keng.base.common.BaseAction;
import com.keng.base.common.Pager;
import com.keng.main.entity.SysConfig;
import com.keng.main.entity.SysConfigKey;
import com.keng.main.service.ConfigService;

/**
 * ParamAction Desc：参数管理
 * @date 2016年4月11日 上午10:44:37
 */
@Controller
public class ParamAction extends BaseAction {
    @Autowired
    private ConfigService configService;

    /**
     * 初始化页面
     * @return
     */
    @RequestMapping(value = "/menus/config_mgr.html")
    public String configMgr() {
        return AD_HTML + "system/config_mgr";
    }

    /**
     * 参数列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/conf/list.html")
    public Object list(HttpServletRequest request) {
        Pager pager = createPager(request);
        pager.addParam("cfgId", getRequestParams(String.class, request, "cfgId"));
        pager.addParam("status", getRequestParams(Integer.class, request, "status"));
        pager.addParam("parentCfg", getRequestParams(String.class, request, "parentCfg"));
        configService.queryPager(pager);
        return getGridData(pager);
    }

    /**
     * 新增参数
     * @param conf
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/conf/add.html")
    public Object add(SysConfig conf) {
        Map<String, Object> result = getResultMap();
        try {
            if (conf.getCfgId() == null || conf.getCfgKey() == null || conf.getCfgVal() == null) {
                result.put(RESULT, false);
                result.put(MESSAGE, "提交数据不完善！");
            } else if (configService.addConf(conf) > 0) {
                result.put(RESULT, true);
                result.put(MESSAGE, "添加成功！");
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "添加失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(ERROR, "添加配置异常！");
            getLog(this).error("添加配置异常！", e);
        }
        return result;
    }

    /**
     * 修改参数
     * @param conf
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/conf/upd.html")
    public Object upd(SysConfig conf) {
        Map<String, Object> result = getResultMap();
        try {
            if (conf.getCfgId() == null || conf.getCfgKey() == null || conf.getCfgVal() == null) {
                result.put(RESULT, false);
                result.put(MESSAGE, "提交数据不完善！");
            } else if (configService.updConf(conf) > 0) {
                result.put(RESULT, true);
                result.put(MESSAGE, "修改成功！");
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "修改失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(ERROR, "修改配置异常！");
            getLog(this).error("修改配置异常！", e);
        }
        return result;
    }

    /**
     * 删除参数
     * @param key
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/conf/del.html")
    public Object del(SysConfigKey key) {
        Map<String, Object> result = getResultMap();
        try {
            if (key.getCfgId() == null || key.getCfgKey() == null) {
                result.put(RESULT, false);
                result.put(MESSAGE, "提交数据不完善！");
            } else if (configService.delConf(key) > 0) {
                result.put(RESULT, true);
                result.put(MESSAGE, "删除成功！");
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "删除失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(ERROR, "删除配置异常！");
            getLog(this).error("删除配置异常！", e);
        }
        return result;
    }
}
