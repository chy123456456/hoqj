package com.keng.main.action.mandala.dxpt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keng.base.common.BaseAction;
import com.keng.base.common.Constants;
import com.keng.base.common.DbcontextHolder;
import com.keng.base.common.Pager;
import com.keng.base.utils.DateUtil;
import com.keng.base.utils.StringUtil;
import com.keng.main.entity.SysUsers;
import com.keng.main.service.MandalaSMSService;

/**
 * 曼荼罗短信平台
 * 
 * @author administrator
 *
 */
@Controller
public class MandalaSMSAction extends BaseAction {

	@Autowired
	private MandalaSMSService mandalaSMSService;

	/**
	 * 初始化页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/menus/mandalaSMS_mgr.html", method = RequestMethod.GET)
	public String index(ModelMap model) {
		DbcontextHolder.setDbType(Constants.dbdxptsqlserver);
		model.put("allyear", mandalaSMSService.getAllSendTables());
		DbcontextHolder.setDbType(Constants.dbmysql);
		return AD_HTML + "mandala/mandalaSMS_mgr";
	}

	/**
	 * 短信列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/mandalaSMS/list.html")
	public Object list(HttpServletRequest request, ModelMap model,String sendYear) {
		SysUsers user = getUser();
		Pager pager = createPager(request);
		pager.addParam("phone", getRequestParams(String.class, request, "phone"));
		pager.addParam("memo", getRequestParams(String.class, request, "memo"));
		pager.addParam("status", getRequestParams(Integer.class, request, "status"));
		pager.addParam("bDate", getRequestParams(String.class, request, "bDate"));
		pager.addParam("eDate",getRequestParams(String.class, request, "eDate"));
		pager.addParam("sendYear",StringUtil.isBlank(sendYear)?("MsgHistory_"+DateUtil.getYear()):sendYear);
		DbcontextHolder.setDbType(Constants.dbdxptsqlserver);
		mandalaSMSService.getAllsmss(pager);
		DbcontextHolder.setDbType(Constants.dbmysql);
		return getGridData(pager);
	}
}
