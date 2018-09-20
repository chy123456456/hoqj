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
import com.keng.base.utils.StringUtil;
import com.keng.main.entity.Zdssrb;
import com.keng.main.mapper.CommonMapper;
import com.keng.main.service.MajorSurgicalPatUploadService;

/**
 * 重大手术患者日报
 * 
 * @author Administrator
 *
 */
@Controller
public class MajorSurgicalPatUploadAction extends BaseAction {
	@Autowired
	private MajorSurgicalPatUploadService majorSurgicalPatUploadService;
	@Autowired
	private CommonMapper commonMapper;

	private String getZDxxSQL(String zyh) {
		return "SELECT c_zyh,ks=(select c_name from p_deptemp where c_id=n_reg.c_dept_id),n_reg.c_dept_id,n_reg.c_br_id,n_reg.c_id,n_pat.c_name,n_pat.c_sex,n_pat.c_age, c_ry_zd = n_reg.c_ry_zd , c_out_flag = n_reg.c_out_flag, c_ysqz_flag = (select c_ysqz_flag from n_reg_ext where c_reg_id = n_reg.c_id ) ,c_bq = ( select c_bq from n_reg_ext where c_reg_id = n_reg.c_id ) , c_hljb = ( select c_hljb from n_reg_ext where c_reg_id = n_reg.c_id ) ,n_pat.c_age_unit,n_reg.c_yb_flag ,ssmc=(SELECT DISTINCT  ssrq FROM  n_ss_grjk WHERE c_reg_id=n_reg.c_id),n_reg.c_ry_date,c_brtype = '2'   FROM n_reg,n_pat  WHERE ( n_pat.c_id = n_reg.c_br_id ) and ( ( n_reg.c_zyh = '"
				+ zyh + "' ) AND  (n_reg.c_out_flag < 9 ))";
	}

	@RequestMapping(value = "menus/majorSurgicalPatUpload.html", method = RequestMethod.GET)
	public Object index() {
		return AD_HTML + "oldhis/majorSurgicalPatUpload_mgr";
	}

	/**
	 * 列表查询
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/majorSurgicalPatUpload/list", method = RequestMethod.POST)
	@ResponseBody
	public Object getList(ModelMap model, HttpServletRequest request) {
		logger.info("majorSurgicalPatUpload[list]==>重症与疑难患者日报列表查询");
		Pager pager = createPager(request);
		pager.addParam("eDate", getRequestParams(String.class, request, "eDate"));
		pager.addParam("bDate", getRequestParams(String.class, request, "bDate"));
		pager.addParam("deptName", getRequestParams(String.class, request, "deptName"));
		pager.addParam("docName", getRequestParams(String.class, request, "docName"));
		pager.addParam("zyh", getRequestParams(String.class, request, "zyh"));
		pager.addParam("xm", getRequestParams(String.class, request, "xm"));
		DbcontextHolder.setDbType(Constants.dbzdhris6sqlserver);
		majorSurgicalPatUploadService.query(pager);
		DbcontextHolder.setDbType(Constants.dbmysql);
		return getGridData(pager);
	}

	/**
	 * 根据住院号获取诊断信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/majorSurgicalPatUpload/getZDxxByZYH", method = RequestMethod.POST)
	@ResponseBody
	public Object getZDxxByZYH(ModelMap model, HttpServletRequest request, String zyh) {
		logger.info("majorSurgicalPatUpload[getZDxxByZYH]==>重大手术患者日报住院号查询手术信息  zyh=" + zyh);
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtil.isEmpty(zyh)) {
			result.put(RESULT, false);
			result.put(MESSAGE, "请输入住院号！");
			return result;
		}
		DbcontextHolder.setDbType(Constants.dbSybase);
		List<Map<String, Object>> r = commonMapper.selectByDynamicSql(getZDxxSQL(zyh));
		DbcontextHolder.setDbType(Constants.dbmysql);
		result.put(RESULT, true);
		result.put(DATA, r);
		return result;
	}

	/**
	 * 上报重大手术患者
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/majorSurgicalPatUpload/add", method = RequestMethod.POST)
	@ResponseBody
	public Object getZDxxByZYH(ModelMap model, HttpServletRequest request, Zdssrb zdssrb, String nlstr) {
		logger.info("majorSurgicalPatUpload[getZDxxByZYH]==>重大手术者日报住院号查询诊断信息  zdssrb=" + zdssrb + "  nlstr=" + nlstr);
		Map<String, Object> result = new HashMap<String, Object>();
		String nHZ = "[^\u4e00-\u9fa5]";// 非汉字匹配规则
		String HZ = "[\u4e00-\u9fa5]";// 汉字匹配规则
		zdssrb.setNl(Float.valueOf(nlstr.replaceAll(HZ, "")));
		zdssrb.setNldw(nlstr.replaceAll(nHZ, ""));
		zdssrb.setSbr(getUser().getName());
		zdssrb.setSbsj(new Date());
		try {
			DbcontextHolder.setDbType(Constants.dbzdhris6sqlserver);
			majorSurgicalPatUploadService.insert(zdssrb);
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
