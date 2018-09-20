package com.keng.main.action.common;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.keng.base.common.BaseAction;

/**
 * 页面公共配置脚本
 * @date 2014-3-3 下午4:04:41
 */
@Controller
public class ConfigAction extends BaseAction {

    @RequestMapping(value = "/config.js", method = RequestMethod.GET)
    public String index(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
        response.setContentType("application/javascript; charset=UTF-8");
        model.put("link", request.getParameter("link"));
        return "common/config";
    }

}
