package com.keng.base.common;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.keng.base.ehcache.ResCacheBean;
import com.keng.base.utils.CastToBean;
import com.keng.main.entity.SysUsers;

public class BaseAction {
  protected static Log            logger          = LogFactory.getLog(BaseAction.class);
  private @Autowired ResCacheBean ehcacheBean;
  protected final String          DATA            = "d";
  protected final String          ERROR           = "e";
  protected final String          RESULT          = "r";
  protected final String          MESSAGE         = "m";
  protected final String          AD_HTML         = "biz-logic/";
  private int                     defaultPageSize = 40;
  protected final static String   porject_key     = "wm-cms";

  protected String redirect(String url) {
    return "redirect:" + url;
  }

  protected String forward(String url) {
    return "forward:" + url;
  }

  /**
   * 创建分页对象
   * 
   * @param pageSize
   * @param pageNo
   * @return
   */
  public Pager createPager(int pageSize, int pageNo) {
    Pager pager = null;
    if (pageSize <= 0) {
      pageSize = defaultPageSize;
    }
    if (pageNo <= 0) {
      pageNo = 1;
    }
    pager = new Pager(pageSize, pageNo);
    return pager;
  }

  /**
   * 创建分页对象
   * 
   * @param request
   * @return
   */
  public Pager createPager(HttpServletRequest request) {
    Pager pager = null;
    String szPageSize = request.getParameter("rows");
    String szPageNo = request.getParameter("page");
    String sort = request.getParameter("sort");
    String order = request.getParameter("order");
    // if (StringUtils.isBlank(pageSize)) {
    // pageSize = String.valueOf(defaultPageSize);
    // }
    // if (StringUtils.isBlank(pageNo)) {
    // pageNo = "1";
    // }
    Integer pageSize = StringUtils.isNotEmpty(szPageSize) ? Integer.valueOf(szPageSize) : null;
    Integer pageNo = StringUtils.isNotEmpty(szPageNo) ? Integer.valueOf(szPageNo) : null;
    pager = new Pager(pageSize, pageNo);
    if (StringUtils.isNotEmpty(sort)) pager.setSort(sort);
    if (StringUtils.isNotEmpty(order)) pager.setOrder(order);
    return pager;
  }

  public Pager createPager(HttpServletRequest request, String... params) {
    Pager pager = createPager(request);
    String szText = null;
    for (String param : params) {
      szText = request.getParameter(param);
      if (StringUtils.isNotEmpty(szText)) {
        pager.addParam(param, szText);
      }
    }
    return pager;
  }

  /**
   * 日志打印请求参数
   * 
   * @param msg
   * @param request
   * @param logger
   */
  protected void logParams(String msg, ServletRequest request, Log logger) {
    String dMsg = msg + " <<< ";
    try {
      StringBuffer sb = new StringBuffer();
      Enumeration<?> enu = request.getParameterNames();
      while (enu.hasMoreElements()) {
        String paraName = (String) enu.nextElement();
        sb.append(paraName).append(" : ").append(request.getParameter(paraName)).append(", ");
      }
      int length = sb.length();
      if (length >= 2) {
        sb.delete(length - 2, length);
      }
      dMsg += "{" + sb.toString() + "}";
    } catch (Exception e) {
      logger.error(e);
    }
    logger.debug(dMsg);
  }

  /**
   * 获取完整的请求根路径
   * 
   * @param request
   * @return
   */
  public String getRequestRootPath(HttpServletRequest request) {
    String scheme = request.getScheme().toLowerCase();
    int port = request.getServerPort();
    StringBuffer sb = new StringBuffer(scheme);
    sb.append("://").append(request.getServerName());
    if (("http".equals(scheme) && port != 80) || ("https".equals(scheme) && port != 443)) {
      sb.append(':').append(port);
    }
    sb.append(request.getContextPath());
    return sb.toString();
  }

  public String getRemoteIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  /**
   * 获取result Map对象
   * 
   * @return
   */
  protected Map<String, Object> getResultMap() {
    return new HashMap<String, Object>();
  }

  /**
   * 获取系统登录用户
   * 
   * @param request
   * @return
   */
  public static SysUsers getUser() {
    Subject subject = SecurityUtils.getSubject();
    if (subject.isAuthenticated()) {
      return (SysUsers) subject.getPrincipal();
    }
    return null;
  }

  /**
   * 获取Session对象
   * 
   * @return
   */
  public static Session getSession() {
    return SecurityUtils.getSubject().getSession();
  }

  /**
   * 获取目录
   * 
   * @param path
   * @return
   */
  protected String getDir(String path) {
    if (StringUtils.isBlank(Constants.UPLOAD_PATH_PARENT)) {
      path = ehcacheBean.getRootPath() + path;
    } else {
      path = Constants.UPLOAD_PATH_PARENT + "/" + path;
    }
    File filePath = new File(path);
    if (!filePath.exists()) {
      filePath.mkdirs();
      logger.debug("目录不存在，创建成功！[路径:" + path + "]");
    }
    return filePath.getAbsolutePath();
  }

  /**
   * 文件上传
   * 
   * @param request
   * @param path
   * @return
   */
  protected FileUpload uploadFile(MultipartHttpServletRequest request, String field, String path) {
    MultipartFile patch = request.getFile(field);
    FileUpload upload = null;
    String newName = null, fileName = null, suffix = null;
    if (patch != null && !patch.isEmpty()) {
      try {
        fileName = patch.getOriginalFilename();
        int index = fileName.lastIndexOf('.');
        if (index > -1) {
          suffix = fileName.substring(index);
        }
        newName = String.valueOf(System.currentTimeMillis()) + (suffix == null ? "" : suffix);
        patch.transferTo(new File(getDir(path), newName));
        logger.debug("文件上传成功！name[" + fileName + "], size[" + Math.rint(patch.getSize() / 1024.0) + "]");
        upload = new FileUpload();
        upload.setLocalName(newName);
        upload.setLocalPath(path);
        upload.setRemoteName(fileName);
      } catch (IOException e) {
        logger.error("文件上传失败！", e);
      }
    }
    return upload;
  }

  protected <T> T getRequestParams(Class<T> cls, HttpServletRequest request, String field) {
    String value = request.getParameter(field);
    value = StringUtils.isBlank(value) ? null : value;
    return CastToBean.typeCast(cls, value);
  }

  /**
   * 获取日志输出对象
   * 
   * @param object
   * @return
   */
  public Log getLog(Object object) {
    return LogFactory.getLog(object.getClass());
  }

  /**
   * 生成表格数据格式
   * 
   * @param pager
   * @return
   */
  public Map<String, Object> getGridData(Pager pager) {
    Map<String, Object> result = getResultMap();
    result.put("total", pager.getTotalRecord());
    result.put("rows", pager.getResult());
    result.put("footer", pager.getFooter());
    return result;
  }

  /**
   * 获取客户端IP地址
   * 
   * @author 吴尚云 2014-2-25 下午5:44:12
   * @param request
   * @return
   */
  public String getClientIP(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  /**
   * 获取请求域名
   * 
   * @param request
   * @return
   */
  @Deprecated
  public static String getdDomain(HttpServletRequest request) {
    String path = request.getContextPath();
    String domain = request.getScheme() + "://";
    domain += request.getServerName();
    domain += request.getServerPort() == 80 ? "" : (":" + request.getServerPort());
    domain += path;
    return domain;
  }

  protected Map<String, Object> result(boolean issuccess, String msg) {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("r", issuccess);
    result.put("m", msg);
    return result;
  }

  public static String iso2utf_8(String str) throws UnsupportedEncodingException {
    return str == null ? null : new String(str.getBytes("iso-8859-1"), "UTF-8");
  }
  
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    /**
     * 第一种方式：使用WebDataBinder注册一个自定义的编辑器，编辑器是日期类型
     * 使用自定义的日期编辑器，日期格式：yyyy-MM-dd HH:mm:ss,第二个参数为是否为空  true代表可以为空
     */
    binder.registerCustomEditor(Date.class, new CustomDateEditor(
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
  }
}
