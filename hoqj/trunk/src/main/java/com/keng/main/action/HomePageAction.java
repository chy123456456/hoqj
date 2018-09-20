/**
 * 
 */
package com.keng.main.action;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.keng.base.common.BaseAction;

@Controller
public class HomePageAction extends BaseAction {


    @RequestMapping(value = "/menus/homePage.html", method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.put("today", null); // 查询今天的订单数和成交额
        model.put("weeks", null); // 查询本周的订单数和成交额
        model.put("months", null); // 查询本周的订单数和成交额
        model.put("todolist", null); // todolist (待办)
        model.put("nowDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        return AD_HTML + "homePage";
    }
}
