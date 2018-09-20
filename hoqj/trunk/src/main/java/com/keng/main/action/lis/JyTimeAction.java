package com.keng.main.action.lis;

import java.util.ArrayList;
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
import com.keng.main.service.HisZJcBillMService;
import com.keng.main.service.LisJyTimeService;

/**
 * 
 * @author Administrator
 *
 */
@Controller
public class JyTimeAction extends BaseAction {
	
	@Autowired
	private LisJyTimeService jyTimeService;
	
	@Autowired
	private HisZJcBillMService hisZJcBillMService;

	/**
	 * 初始化页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/menus/lis_biaobenFollow.html", method = RequestMethod.GET)
	public String listPage(ModelMap model) {
		logger.info("JyTimeAction[listPage]==>进入标本跟踪界面");
		return AD_HTML + "lis/biaobenFollow";
	}
	
	/**
	 * 列表页面
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/lis_biaobenFollow/list.html", method = RequestMethod.POST)
	public Object list(HttpServletRequest request, ModelMap model,String hzq,String zyhq,String sqdq) {
		logger.info("开始查询标本跟踪数据=>hzq:"+hzq+"==zyhq:"+zyhq+"==sqdq:"+sqdq);
	    Pager pager = createPager(request);
	    pager.addParam("hzq", StringUtil.isEmpty(hzq)?null:hzq);
	    pager.addParam("zyhq", StringUtil.isEmpty(zyhq)?null:zyhq);
	    pager.addParam("sqdq", StringUtil.isEmpty(sqdq)?null:sqdq);
	    DbcontextHolder.setDbType(Constants.dbSybase);
	    hisZJcBillMService.getJyList(pager);
	    DbcontextHolder.setDbType(Constants.dblissqlserver);
	    List<Map> list = (List<Map>) pager.getResult();
	    logger.info("HIS数据库返回标本集合=>"+list.toString());
	    List<String> ids = new ArrayList<>();
	    if(list!=null && !list.isEmpty()) {
	    	for(int i = 0 ; i < list.size(); i ++) {
		    	ids.add(((Map) list.get(i)).get("c_id").toString());
		    }
		    List<Map> jys =  jyTimeService.getListByIds(ids);
		    DbcontextHolder.setDbType(Constants.dbmysql);
		    if(jys!=null && !jys.isEmpty()) {
		    	for(int i = 0 ; i < jys.size() ; i ++) {
		    		for(int j = 0 ; j < list.size() ; j ++) {
		    			if(StringUtil.trim(jys.get(i).get("SerialNo").toString()).equals(StringUtil.trim(list.get(j).get("c_id").toString()))) {
		    				Map map = list.get(j);
		    				map.putAll(jys.get(i));
		    				list.set(j, map);
		    				break;
		    			}
		    		}
		    	}
		    }
	    }
	    pager.setResult(list);
	    return getGridData(pager);
	}

	
}
