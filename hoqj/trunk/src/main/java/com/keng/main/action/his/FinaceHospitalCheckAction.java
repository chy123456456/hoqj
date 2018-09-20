
package com.keng.main.action.his;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.keng.base.common.BaseAction;
import com.keng.base.common.Constants;
import com.keng.base.common.DbcontextHolder;
import com.keng.base.utils.ExcelUtils;
import com.keng.main.mapper.HisFinaceHospitalCheckMapper;

@Controller
public class FinaceHospitalCheckAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(FinaceHospitalCheckAction.class);

	@Autowired
	private HisFinaceHospitalCheckMapper hisFinaceHospitalCheckMapper;

	/**
	 * 初始化页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/menus/his_finaceHospitalCheck.html", method = RequestMethod.GET)
	public String listPage(ModelMap model) {
		logger.info("FinaceHospitalCheckAction[listPage]==>进入医院调查表界面");
		return AD_HTML + "his/finaceHospitalCheck";
	}

	@RequestMapping(value = "/finaceHospitalCheck/mz_export.html", method = RequestMethod.GET)
	public void finaceHospitalCheck(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String bDate = getRequestParams(String.class, request, "bDate");
		String eDate = getRequestParams(String.class, request, "eDate");
		map.put("bDate", bDate);
		map.put("eDate", eDate);
		DbcontextHolder.setDbType(Constants.dbSybase);
		List<Map<String, Object>> lists = hisFinaceHospitalCheckMapper
				.getMz(map);
		DbcontextHolder.setDbType(Constants.dbmysql);
		exports(lists, request, response, bDate, eDate);
	}

	/**
	 * 导出医院调查表-门诊
	 * 
	 * @param lists
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	private void exports(List<Map<String, Object>> lists, HttpServletRequest request, HttpServletResponse response,
			String startDate, String endDate) {
		List<List<Comparable>> listData = new ArrayList<List<Comparable>>();
		NumberFormat nt = NumberFormat.getPercentInstance();
		// 设置百分数精确度1即保留两位小数
		nt.setMinimumFractionDigits(1);
		// 增加标题行
		listData.add(setHeader());
		for (int i = 0; i < lists.size(); i++) {
			Map<String, Object> map = lists.get(i);
			List<Comparable> list = new ArrayList<Comparable>();
			list.add(map.get("c_id").toString());
			list.add(map.get("c_br_id").toString());
			list.add(map.get("c_code").toString());
			list.add((map.get("c_age")==null?"":map.get("c_age").toString()));
			list.add((map.get("c_birthday")==null?"":map.get("c_birthday").toString()));
			list.add((map.get("c_sex")==null?"":map.get("c_sex").toString()));
			list.add((map.get("c_mz_zd_new")==null?"":map.get("c_mz_zd_new").toString()));
			list.add((map.get("icdbm")==null?"":map.get("icdbm").toString()));
			list.add((map.get("c_op_date")==null?"":map.get("c_op_date").toString()));
			list.add((map.get("c_name")==null?"":map.get("c_name").toString()));
			BigDecimal bg=new BigDecimal(map.get("mzzfy")==null?"0":map.get("mzzfy").toString());
			list.add(bg.doubleValue());
			
			bg=new BigDecimal(map.get("zlf")==null?"0":map.get("zlf").toString());
			list.add(bg.doubleValue());
			bg=new BigDecimal(map.get("ypf")==null?"0":map.get("ypf").toString());
			list.add(bg.doubleValue());
			bg=new BigDecimal(map.get("xy")==null?"0":map.get("xy").toString());
			list.add(bg.doubleValue());
			bg=new BigDecimal(map.get("zchengy")==null?"0":map.get("zchengy").toString());
			list.add(bg.doubleValue());
			bg=new BigDecimal(map.get("zcaoy")==null?"0":map.get("zcaoy").toString());
			list.add(bg.doubleValue());
			bg=new BigDecimal(map.get("ghf")==null?"0":map.get("ghf").toString());
			list.add(bg.doubleValue());
			bg=new BigDecimal(map.get("zcf")==null?"0":map.get("zcf").toString());
			list.add(bg.doubleValue());
			bg=new BigDecimal(map.get("jcf")==null?"0":map.get("jcf").toString());
			list.add(bg.doubleValue());
			bg=new BigDecimal(map.get("ssf")==null?"0":map.get("ssf").toString());
			list.add(bg.doubleValue());
			bg=new BigDecimal(map.get("hyf")==null?"0":map.get("hyf").toString());
			list.add(bg.doubleValue());
			bg=new BigDecimal(map.get("qtf")==null?"0":map.get("qtf").toString());
			list.add(bg.doubleValue());
			list.add((map.get("cblx")==null?"0":map.get("cblx").toString()));
			bg=new BigDecimal(map.get("bxtcjjzffy")==null?"0":map.get("bxtcjjzffy").toString());
			list.add(bg.doubleValue());
			bg=new BigDecimal(map.get("grzhzffy")==null?"0":map.get("grzhzffy").toString());
			list.add(bg.doubleValue());
			bg=new BigDecimal(map.get("hzzffy")==null?"0":map.get("hzzffy").toString());
			list.add(bg.doubleValue());
			list.add(((map.get("yljzfd")==null)?"0":map.get("yljzfd").toString()));
			listData.add(list);
		}
		String fileName = "";
		if (startDate.equals(endDate)) {
			fileName = "Orders_";
		} else {
			fileName = "Orders__";
		}
		ExcelUtils.createExcel(request, response, logger, listData, fileName);
	}

	/**
	 * 自定义标题栏
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<Comparable> setHeader() {
		List<Comparable> list = new ArrayList<Comparable>();
		list.add("c_id");
		list.add("c_br_id");
		list.add("就诊号");
		list.add("年龄");
		list.add("出生日期");
		list.add("性别");
		list.add("疾病名称");
		list.add("ICD-10编码");
		list.add("就诊日期");
		list.add("就诊科室");
		list.add("门诊总费用");
		list.add("治疗费");
		list.add("药品费");
		list.add("西药");
		list.add("中成药");
		list.add("中草药");
		list.add("挂号费");
		list.add("诊察费");
		list.add("检查费");
		list.add("手术费");
		list.add("化验费");
		list.add("其他费");
		list.add("参保类型");
		list.add("保险统筹基金支付费用");
		list.add("个人账户支付费用");
		list.add("患者自付费用");
		list.add("医疗救助负担");
		return list;
	}

}
