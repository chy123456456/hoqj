
package com.keng.main.action.economic;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.keng.base.utils.ExcelUtil;
import com.keng.main.entity.HisUpbgh;
import com.keng.main.entity.SysFiles;
import com.keng.main.mapper.SysFilesMapper;
import com.keng.main.service.SchedulingService;

/**
 * 预算管理
 * @author Administrator
 *
 */
@Controller
public class BudgetAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(BudgetAction.class);

	@Autowired
	private SchedulingService schedulingService;
	
	@Autowired
	private SysFilesMapper sysFilesMapper;

	/**
	 * 初始化页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/menus/economic_budgetMgr.html", method = RequestMethod.GET)
	public String listPage(ModelMap model) {
		logger.info("BudgetAction[listPage]==>进入预算管理界面");
		HisUpbgh gh = new HisUpbgh();
		String bDate = DateUtil.getDate();
		String eDate = DateUtil.parseDtToStrForYYYYMMDD(DateUtil.getNextDate());
		gh.setbDate(bDate);
		gh.seteDate(eDate);
//		DbcontextHolder.setDbType(Constants.dbSybase);
//		model.put("todayDepts", schedulingService.getDeptsByTime(gh));
//		DbcontextHolder.setDbType(Constants.dbmysql);
		model.put("bDate", bDate);
		model.put("eDate", eDate);
		return AD_HTML + "economic/budgetMgr";
	}
	
	/**
	 * 
	 * @param fileId
	 * @return
	 */
	@RequestMapping(value = "/economic/analysis.html", method = RequestMethod.POST)
	public String analysis(String fileId){
		SysFiles file = sysFilesMapper.selectByPrimaryKey(Integer.valueOf(fileId));
		String path = Constants.UPLOAD_PATH_PARENT+"/"+file.getSavePath()+"/"+file.getLocalName();
		ExcelUtil.parseExcelToList(new File(path));
		return AD_HTML;
	}

//	/**
//	 * 列表页面
//	 * 
//	 * @param request
//	 * @param model
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/scheduling/list.html", method = RequestMethod.POST)
//	public Object list(HttpServletRequest request, ModelMap model, String bDate, String eDate) {
//		Pager pager = createPager(request);
//		pager.addParam("deptq", getRequestParams(String.class, request, "deptq"));
//		pager.addParam("docq", getRequestParams(String.class, request, "docq"));
//		pager.addParam("isHandAddq", getRequestParams(String.class, request, "isHandAddq"));
//		pager.addParam("bDate", bDate);
//		pager.addParam("eDate", eDate);
//		DbcontextHolder.setDbType(Constants.dbSybase);
//		schedulingService.getList(pager);
//		DbcontextHolder.setDbType(Constants.dbmysql);
//		return getGridData(pager);
//	}
//
//	/**
//	 * 根据科室id查询医生
//	 * 
//	 * @param request
//	 * @param model
//	 * @param deptId
//	 * @return
//	 */
//	@SuppressWarnings("rawtypes")
//	@ResponseBody
//	@RequestMapping(value = "/scheduling/getDocByDeptId", method = RequestMethod.POST)
//	public Object getDocByDeptId(HttpServletRequest request, ModelMap model, String deptId) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		HisUpbgh gh = new HisUpbgh();
//		String bDate = DateUtil.getDate();
//		String eDate = DateUtil.parseDtToStrForYYYYMMDD(DateUtil.getNextDate());
//		gh.setbDate(bDate);
//		gh.seteDate(eDate);
//		gh.setDeptId(deptId);
//		DbcontextHolder.setDbType(Constants.dbSybase);
//		List<Map> docs = schedulingService.getDocsByDeptId(gh);
//		model.put("docs", docs);
//		DbcontextHolder.setDbType(Constants.dbmysql);
//		result.put(RESULT, true);
//		result.put(DATA, docs);
//		return result;
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/scheduling/addGH", method = RequestMethod.POST)
//	public Object addGH(HttpServletRequest request, ModelMap model, String ghdeptId, String docId, Integer ghnum) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		DbcontextHolder.setDbType(Constants.dbSybase);
//		Object docs = schedulingService.addGh(ghdeptId, docId, ghnum);
//		DbcontextHolder.setDbType(Constants.dbmysql);
//		result.put(RESULT, true);
//		result.put(DATA, docs);
//		return result;
//	}
//
//	/**
//	 * 查询挂号开始-截止时间
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/scheduling/getGhTime", method = RequestMethod.POST)
//	public Object getGhTime(HttpServletRequest request, ModelMap model) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		DbcontextHolder.setDbType(Constants.dbSybase);
//		Object data = schedulingService.getGhTime();
//		DbcontextHolder.setDbType(Constants.dbmysql);
//		result.put(RESULT, true);
//		result.put(DATA, data);
//		return result;
//	}
//
//	/**
//	 * 修改挂号开始-截止时间
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/scheduling/editGhTime", method = RequestMethod.POST)
//	public Object editGhTime(HttpServletRequest request, ModelMap model, String c_code, String c_value) {
//		logger.debug(getUser() + "修改挂号时间==>c_code=" + c_code + "----c_value=" + c_value);
//		Map<String, Object> result = getResultMap();
//		try {
//			DbcontextHolder.setDbType(Constants.dbSybase);
//			if (schedulingService.editGhTime(c_code, c_value) > 0) {
//				result.put(RESULT, true);
//				result.put(MESSAGE, "修改成功！");
//			} else {
//				result.put(RESULT, false);
//				result.put(MESSAGE, "修改失败！");
//			}
//		} catch (Exception e) {
//			result.put(RESULT, false);
//			result.put(MESSAGE, "系统异常，操作失败！");
//			getLog(this).error(e.getMessage(), e);
//		}
//		DbcontextHolder.setDbType(Constants.dbmysql);
//		return result;
//	}
}
