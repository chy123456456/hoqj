package com.keng.base.freemarker;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.keng.main.entity.SysConfig;
import com.keng.main.service.ConfigService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ConfigDirective implements TemplateDirectiveModel {
    private static final Logger logger = LoggerFactory.getLogger(ConfigDirective.class);
    @Autowired
    private ConfigService       configMgr;

    @Override
    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Object typeObject = params.get("type");
        Object keyObject = params.get("key");
        Object valObject = params.get("val");
        Object pkeyObject = params.get("pkey");
        String type = typeObject == null ? null : typeObject.toString();
        String key = keyObject == null ? null : keyObject.toString();
        String val = valObject == null ? null : valObject.toString();
        String pkey = pkeyObject == null ? null : pkeyObject.toString();
        try {
            List<SysConfig> cfgs = configMgr.getByIdAndPkey(key, pkey);
            if ("options".equals(type.toLowerCase())) {
                for (SysConfig cfg : cfgs) {
                    if (cfg.getCfgKey().equals(val)) {
                        env.getOut().write("<option selected=\"selected\" value=\"" + cfg.getCfgKey() + "\">" + cfg.getCfgVal() + "</option>");
                    } else {
                        env.getOut().write("<option value=\"" + cfg.getCfgKey() + "\">" + cfg.getCfgVal() + "</option>");
                    }
                }
            } else if ("display".equals(type.toLowerCase())) {
                if (!StringUtils.isBlank(val)) {
                    for (SysConfig cfg : cfgs) {
                        if (cfg.getCfgKey().equals(val)) {
                            env.getOut().write(cfg.getCfgVal());
                        }
                    }
                } else {
                    env.getOut().write("");
                }
            }
        } catch (Exception e) {
            env.getOut().write("");
            logger.error("Freemarker缈昏瘧寮傚父锛�");
            e.printStackTrace();
        }
    }
}
