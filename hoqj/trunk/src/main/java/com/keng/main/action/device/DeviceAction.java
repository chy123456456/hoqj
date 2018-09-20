package com.keng.main.action.device;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.keng.base.utils.SystemUtil;
import com.keng.main.entity.Device;
import com.keng.main.entity.SysUsers;
import com.keng.main.mapper.CommonMapper;
import com.keng.main.service.DeviceService;

import net.sf.ehcache.search.expression.And;

/**
 * 设备管理
 * 
 * @author administrator
 *
 */
@Controller
public class DeviceAction extends BaseAction {
	@Autowired
	private DeviceService deviceService;

	@Autowired
	private CommonMapper commonMapper;
	private final String getDeptSql = "select * from p_deptemp where c_type=1 and c_status=1";

	/**
	 * 初始化页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/menus/device.html", method = RequestMethod.GET)
	public String listPage(ModelMap model,HttpServletRequest request) {
		logger.info("DeviceAction[listPage]==>进入设备管理界面");
		DbcontextHolder.setDbType(Constants.dbSybase);
		model.put("Depts", commonMapper.selectByDynamicSql(getDeptSql));
		DbcontextHolder.setDbType(Constants.dbmysql);
		return AD_HTML + "device/device_mgr";
	}

	/**
	 * 列表查询
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/device/list", method = RequestMethod.POST)
	@ResponseBody
	public Object getList(ModelMap model, HttpServletRequest request) {
		logger.info("DeviceAction[getList]==>设备管理列表查询");
		Pager pager = createPager(request);
		pager.addParam("deviceNo", getRequestParams(String.class, request, "deviceNo"));
		pager.addParam("deptName", getRequestParams(String.class, request, "deptName"));
		pager.addParam("ip", getRequestParams(String.class, request, "ip"));
		pager.addParam("mac", getRequestParams(String.class, request, "mac"));
		pager.addParam("type", getRequestParams(Integer.class, request, "type"));
		pager.addParam("status", getRequestParams(String.class, request, "status"));
		deviceService.query(pager);
		return getGridData(pager);
	}

	/**
	 * 新增
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/device/add.html", method = RequestMethod.POST)
	@ResponseBody
	public Object add(ModelMap model, HttpServletRequest request, Device device) {
		logger.info("新增设备==>" + device);
		Map<String, Object> result = getResultMap();
		try {
			if (StringUtil.isEmpty(device.getDeviceNo())) {
				result.put(RESULT, false);
				result.put(MESSAGE, "新增失败！设备编号不能为空");
				return result;
			}
			Date date = new Date();
			SysUsers u=getUser();
			device.setCreaterId(u.getId());
			device.setCreater(u.getName());
			device.setCreateTime(date);
			device.setUpdater(u.getName());
			device.setUpdaterId(u.getId());
			device.setUpdateTime(date);
			List list = deviceService.selectByDeviceNo(device.getDeviceNo());
			if (list!=null && list.size()>0) {
				result.put(RESULT, false);
				result.put(MESSAGE, "新增失败！该设备编号已存在");
			} else if (deviceService.add(device) > 0) {
				result.put(DATA, device);
				result.put(RESULT, true);
				result.put(MESSAGE, "新增成功！");
			} else {
				result.put(RESULT, false);
				result.put(MESSAGE, "新增失败！");
			}
		} catch (Exception e) {
			result.put(RESULT, false);
			result.put(MESSAGE, "系统异常，操作失败！");
			getLog(this).error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/device/edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object edit(ModelMap model, HttpServletRequest request, Device device) {
		logger.info("修改设备==>" + device);
		Map<String, Object> result = getResultMap();
		try {
			Date date = new Date();
			SysUsers u=getUser();
			device.setUpdater(u.getName());
			device.setUpdaterId(u.getId());
			device.setUpdateTime(date);
			if (device == null || device.getId() == null) {
				result.put(RESULT, false);
				result.put(MESSAGE, "修改失败，缺少主键");
			} else if (StringUtil.isEmpty(device.getDeviceNo())) {
				result.put(RESULT, false);
				result.put(MESSAGE, "修改失败！设备编号不能为空");
			} else if (deviceService.edit(device) > 0) {
				result.put(DATA, device);
				result.put(RESULT, true);
				result.put(MESSAGE, "修改成功！");
			} else {
				result.put(RESULT, false);
				result.put(MESSAGE, "修改失败！");
			}
		} catch (Exception e) {
			result.put(RESULT, false);
			result.put(MESSAGE, "系统异常，操作失败！");
			getLog(this).error(e.getMessage(), e);
		}
		return result;
	}

}
