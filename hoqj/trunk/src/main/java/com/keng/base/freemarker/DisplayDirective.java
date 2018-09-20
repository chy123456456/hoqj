package com.keng.base.freemarker;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.keng.base.service.DisplayService;
import com.keng.base.utils.JacksonBuilder;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * freemarker 自定义标签 用于翻译静态数据
 */
public class DisplayDirective implements TemplateDirectiveModel {
    private static Log log = LogFactory.getLog(DisplayDirective.class);
    private @Autowired
    DisplayService     displayMgr;

    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Object typeObject = params.get("type");
        Object keyObject = params.get("key");
        String type = typeObject == null ? null : typeObject.toString();
        String key = keyObject == null ? null : keyObject.toString();
        Map<Object, String> display = null;
        try {
            if (type == null) {
                env.getOut().write("");
            } else if ("user".equals(type.toLowerCase())) {
                display = displayMgr.displayUser();
            } else if ("role".equals(type.toLowerCase())) {
                display = displayMgr.displayRole();
            } else if (type.startsWith("conf$")) {
                display = displayMgr.displayConf(type.split("\\$")[1]);
            }
            if (display != null) {
                if (key == null) {
                    env.getOut().write(JacksonBuilder.mapper().writeValueAsString(display));
                } else {
                    String name;
                    try {
                        name = display.get(Integer.valueOf(key));
                    } catch (Exception e) {
                        name = display.get(key);
                    }
                    env.getOut().write(name == null ? "无" : name);
                }
            } else {
                env.getOut().write("{}");
            }
        } catch (Exception e) {
            env.getOut().write("{}");
            log.error("Freemarker翻译异常！");
            e.printStackTrace();
        }
    }
}