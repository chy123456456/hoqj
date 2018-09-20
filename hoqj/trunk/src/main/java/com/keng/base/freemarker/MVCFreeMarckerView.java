package com.keng.base.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.keng.base.common.BaseAction;
import com.keng.base.common.BeansManager;
import com.keng.base.common.Constants;
import com.keng.base.ehcache.ResCacheBean;

public class MVCFreeMarckerView extends FreeMarkerView {
  private ResCacheBean resCache = BeansManager.getBean(ResCacheBean.class);

  @Override
  protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
    super.exposeHelpers(model, request);
    model.put("v", "?v=" + resCache.resCacheVersion());
    model.put("base", request.getContextPath());
    model.put("url", request.getRequestURI().replaceAll(request.getContextPath(), ""));
    model.put("debug", Constants.DEBUG);
    model.put("isCompress", Constants.HTML_IS_COMPRESS);
    model.put("user", BaseAction.getUser());
    model.put("jsPath", resCache.getResJsPath(this.getBeanName()));
    model.put("cssPath", resCache.getResCssPath(this.getBeanName()));
//    if (BaseAction.getUser() != null) {
//      model.put("userType", BaseAction.getUser().getType());
//      model.put("language", BaseAction.getUser().getLanguage());
//    }
  }
}
