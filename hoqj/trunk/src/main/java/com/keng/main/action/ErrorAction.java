package com.keng.main.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keng.base.common.BaseAction;

@Controller
public class ErrorAction extends BaseAction {
    @RequestMapping("/error/404.html")
    public String _error404(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "errors/error_404";
    }

    @RequestMapping("/error/error_404.html")
    public String error404(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return redirect("/error/404.html");
    }
}
