package com.keng.main.action.common;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.keng.base.common.BaseAction;

/**
 * 页面基础数据翻译
 * @date 2014-3-3 下午4:05:01
 */
@Controller
public class DisplayAction extends BaseAction {
    @RequestMapping(value = "/display.js", method = RequestMethod.GET)
    public String index(String type, HttpServletResponse response, ModelMap model) {
        response.setContentType("application/javascript; charset=UTF-8");
        List<String> arr = new ArrayList<String>(), conf = null;
        if (!StringUtils.isBlank(type)) {
            for (String str : type.split(",")) {
                if (str.startsWith("conf")) {
                    str = str.replace("conf[", "").replace("]", "");
                    conf = Arrays.asList(str.split("\\|"));
                } else {
                    arr.add(str);
                }
            }
            model.put("types", arr);
            model.put("confs", conf);
        }
        return "common/display";
    }
}
