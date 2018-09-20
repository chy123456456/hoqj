package com.keng.main.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.keng.base.common.BaseAction;
import com.keng.base.common.Constants;
import com.keng.main.entity.SysUsers;

/**
 * Title：LoginAction Desc：用户登陆action
 * 
 * @date 2016-3-16 下午4:10:47
 */
@Controller
public class LoginAction extends BaseAction {
	
	
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public String login(HttpServletRequest request, ModelMap model) {
		return AD_HTML + "login";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String _login(HttpServletRequest request) {
		return AD_HTML + "login";
	}

	@ResponseBody
	@RequestMapping(value = "/login.html", method = RequestMethod.POST)
	public Object _login(String username, String password, String authcode, Boolean rememberMe,
			HttpServletRequest request, ModelMap model) throws Exception{
		logger.info("登录用户信息==》username=" + username);
		logger.info("登录用户信息==》password=" + password);
//		DbcontextHolder.setDbType("dataSource_sybase");
//		List list = hisUpbghMapper.getTodayDepts();
//		System.out.println("list:"+list);
//		Thread.sleep(60000);
//		DbcontextHolder.setDbType("dataSource_sybase");
////		List listq = areaService.queryParent1();
//		System.out.println("sybae:"+listq);
//		Thread.sleep(6000000);
		String lang = RequestContextUtils.getLocale(request).getDisplayLanguage();
		if ("Chinese".equals(lang) || "中文".equals(lang)) {
			lang = "中文";
		}
		if ("English".equals(lang) || "英文".equals(lang)) {
			lang = "英文";
		}
		logger.info(lang);
		Map<String, Object> result = getResultMap();
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(rememberMe == null ? false : rememberMe);
		try {
			if (subject.isAuthenticated()) {// 重复登录
				SysUsers user = getUser();
				if (!user.getCode().equals(username)) {// 如果登录名不同
					subject.logout();
					subject.login(token);
				}
			} else {
				subject.login(token);
			}
			result.put(RESULT, true);
		} catch (AuthenticationException e) {
			result.put(ERROR, "英文".equals(lang) ? "wrong username or password！" : "用户名或密码错误！");
			token.clear();
			e.printStackTrace();
			getLog(this).error("登录失败错误信息:" + e);
		} catch (NullPointerException e) {
			result.put(ERROR, "英文".equals(lang) ? "no permissions！" : "用户未分配岗位！");
			token.clear();
			getLog(this).error("登录失败错误信息:" + e);
		}
		return result;
	}

	@RequestMapping(value = "/logout.html", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		SecurityUtils.getSubject().logout();
		try {
			response.sendRedirect(Constants.UPLOAD_PATH_PARENT + "/login.html");
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	@RequestMapping(value = "/main.html", method = RequestMethod.GET)
	public String main(HttpServletRequest request, ModelMap model) {
		SysUsers user = getUser();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		model.put("sysDate", format.format(Calendar.getInstance().getTime()));
		model.put("menus", user.getMenus());
		model.put("nick", user.getName());
//		model.put("userType", user.getUserType());
		return AD_HTML + "main";
	}
}
