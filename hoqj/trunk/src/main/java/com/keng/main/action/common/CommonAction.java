package com.keng.main.action.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keng.base.common.BaseAction;
import com.keng.base.utils.StringUtil;
import com.keng.base.utils.SystemUtil;

/**
 * 公共action
 * 
 * @date 2014-3-3 下午4:04:41
 */
@Controller
public class CommonAction extends BaseAction {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/common/getRemotePcInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object index(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		response.setContentType("application/javascript; charset=UTF-8");
		Map map = new HashMap<>();
		Map<String, Object> result = getResultMap();
		String ip = SystemUtil.getRemoteIpAddr(request);
		map.put("ip", ip);
		logger.debug("客户端系统名称："+request.getRemoteHost()); 
		try {
			String mac = SystemUtil.getMACAddress(ip);
			map.put("mac", mac);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put(DATA, map);
		result.put(RESULT, true);
		result.put(MESSAGE, "新增失败！设备编号不能为空");
		return result;
	}

}
