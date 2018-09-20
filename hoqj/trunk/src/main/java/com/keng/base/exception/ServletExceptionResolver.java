package com.keng.base.exception;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.keng.base.common.Constants;

public class ServletExceptionResolver implements HandlerExceptionResolver {
    private static Log logger = LogFactory.getLog(ServletExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ex.printStackTrace();
        logger.error("500 Internal Server Error", ex);
        response.setStatus(500);
        ModelAndView model = new ModelAndView("errors/error_500.html");
        model.addObject("r", 0);
        model.addObject("m", "500 Internal Server Error");
        model.addObject("debug", Constants.DEBUG);
        return model;
    }
}
