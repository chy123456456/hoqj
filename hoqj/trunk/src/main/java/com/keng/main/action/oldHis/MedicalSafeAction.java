package com.keng.main.action.oldHis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.keng.main.entity.Ylaqrb;
import com.keng.main.entity.Zdssrb;
import com.keng.main.mapper.CommonMapper;
import com.keng.main.service.MajorSurgicalPatUploadService;
import com.keng.main.service.MedicalSafeService;

/**
 * 重大手术患者日报
 * 
 * @author Administrator
 *
 */
@Controller
public class MedicalSafeAction extends BaseAction {
	@Autowired
	private MedicalSafeService medicalSafeService;
	@Autowired
	private CommonMapper commonMapper;

	@RequestMapping(value = "menus/medicalSafe.html", method = RequestMethod.GET)
	public Object index(Model model) {
		model.addAttribute("user",getUser());
		model.addAttribute("date",DateUtil.getDatetime());
		return AD_HTML + "oldhis/medicalSafe_mgr";
	}

	/**
	 * 列表查询
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/medicalSafe/list", method = RequestMethod.POST)
	@ResponseBody
	public Object getList(ModelMap model, HttpServletRequest request) {
		logger.info("medicalSafe[list]==>医疗安全日报列表查询");
		Pager pager = createPager(request);
		pager.addParam("eDate", getRequestParams(String.class, request, "eDate"));
		pager.addParam("bDate", getRequestParams(String.class, request, "bDate"));
		pager.addParam("ks", getRequestParams(String.class, request, "ks"));
		pager.addParam("sbry", getRequestParams(String.class, request, "sbry"));
		DbcontextHolder.setDbType(Constants.dbzdhris6sqlserver);
		medicalSafeService.query(pager);
		DbcontextHolder.setDbType(Constants.dbmysql);
		return getGridData(pager);
	}


	/**
	 * 上报医疗安全患者
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/medicalSafe/add", method = RequestMethod.POST)
	@ResponseBody
	public Object getZDxxByZYH(ModelMap model, HttpServletRequest request, Ylaqrb ylaqrb, String nlstr) {
		logger.info("medicalSafe[getZDxxByZYH]==>上报医疗安全患者  zdssrb=" + ylaqrb + "  nlstr=" + nlstr);
		Map<String, Object> result = new HashMap<String, Object>();
//		String nHZ = "[^\u4e00-\u9fa5]";// 非汉字匹配规则
//		String HZ = "[\u4e00-\u9fa5]";// 汉字匹配规则
//		zdssrb.setNl(Float.valueOf(nlstr.replaceAll(HZ, "")));
//		zdssrb.setNldw(nlstr.replaceAll(nHZ, ""));
//		zdssrb.setSbr(getUser().getName());
		ylaqrb.setSbrq(new Date());
		try {
			DbcontextHolder.setDbType(Constants.dbzdhris6sqlserver);
			medicalSafeService.insert(ylaqrb);
			DbcontextHolder.setDbType(Constants.dbmysql);
			result.put(RESULT, true);
			// result.put(DATA, zzynrb);
		} catch (Exception e) {
			e.printStackTrace();
			result.put(RESULT, false);
			result.put(MESSAGE, "上报出错，请联系管理员！");
		}
		return result;
	}
}
