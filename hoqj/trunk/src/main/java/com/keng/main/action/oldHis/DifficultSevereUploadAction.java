package com.keng.main.action.oldHis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.keng.main.entity.Device;
import com.keng.main.entity.HisUpbgh;
import com.keng.main.entity.SysUsers;
import com.keng.main.entity.Zzynrb;
import com.keng.main.mapper.CommonMapper;
import com.keng.main.service.DifficultSevereService;

/**
 * 数据上报-重症与疑难患者日报
 * @author administrator
 *
 */
@Controller
public class DifficultSevereUploadAction extends BaseAction {
	
	@Autowired
	private DifficultSevereService difficultSevereService;
	@Autowired
	private CommonMapper commonMapper;
	
	private final String getZDxxSQL="select * from n_ss_grjk where zyh=";
	
	@RequestMapping(value="menus/oldhis_difficultSevere.html",method=RequestMethod.GET)
	public Object index(){
		return AD_HTML +"oldhis/difficultSevere_mgr";
	}
	/**
	 * 列表查询
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/oldhisDifficultSevere/list", method = RequestMethod.POST)
	@ResponseBody
	public Object getList(ModelMap model, HttpServletRequest request) {
		logger.info("oldhisDifficultSevere[list]==>重症与疑难患者日报列表查询");
		Pager pager = createPager(request);
		pager.addParam("eDate", getRequestParams(String.class, request, "eDate"));
		pager.addParam("bDate", getRequestParams(String.class, request, "bDate"));
		pager.addParam("deptName", getRequestParams(String.class, request, "deptName"));
		pager.addParam("docName", getRequestParams(String.class, request, "docName"));
		pager.addParam("zyh", getRequestParams(String.class, request, "zyh"));
		pager.addParam("xm", getRequestParams(String.class, request, "xm"));
		DbcontextHolder.setDbType(Constants.dbzdhris6sqlserver);
		difficultSevereService.query(pager);
		DbcontextHolder.setDbType(Constants.dbmysql);
		return getGridData(pager);
	}
	
	/**
	 * 根据住院号获取诊断信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/oldhisDifficultSevere/getZDxxByZYH", method = RequestMethod.POST)
	@ResponseBody
	public Object getZDxxByZYH(ModelMap model, HttpServletRequest request,String zyh) {
		logger.info("oldhisDifficultSevere[getZDxxByZYH]==>重症与疑难患者日报住院号查询诊断信息  zyh="+zyh);
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtil.isEmpty(zyh)){
			result.put(RESULT, false);
			result.put(MESSAGE, "请输入住院号！");
			return result;
		}
		DbcontextHolder.setDbType(Constants.dbSybase);
		List<Map<String, Object>> r = commonMapper.selectByDynamicSql(getZDxxSQL+"'"+zyh+"'");
		DbcontextHolder.setDbType(Constants.dbmysql);
		result.put(RESULT, true);
		result.put(DATA, r);
		return result;
	}
	
	/**
	 * 上报疑难与重症患者
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/oldhisDifficultSevere/add", method = RequestMethod.POST)
	@ResponseBody
	public Object getZDxxByZYH(ModelMap model, HttpServletRequest request,Zzynrb zzynrb,String nlstr) {
		logger.info("oldhisDifficultSevere[getZDxxByZYH]==>重症与疑难患者日报住院号查询诊断信息  Zzynrb="+zzynrb+"  nlstr="+nlstr);
		Map<String, Object> result = new HashMap<String, Object>();
		String nHZ = "[^\u4e00-\u9fa5]";//非汉字匹配规则
		String HZ = "[\u4e00-\u9fa5]";//汉字匹配规则
		zzynrb.setNl(Float.valueOf(nlstr.replaceAll(HZ, "")));
		zzynrb.setNldw(nlstr.replaceAll(nHZ, ""));
		zzynrb.setSbr(getUser().getName());
		zzynrb.setSbsj(new Date());
		try {
			DbcontextHolder.setDbType(Constants.dbzdhris6sqlserver);
			difficultSevereService.insert(zzynrb);
			DbcontextHolder.setDbType(Constants.dbmysql);
			result.put(RESULT, true);
			result.put(DATA, zzynrb);
		} catch (Exception e) {
			e.printStackTrace();
			result.put(RESULT, false);
			result.put(MESSAGE, "上报出错，请联系管理员！");
		}
		return result;
	}
	
 	
}
