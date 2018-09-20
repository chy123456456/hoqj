package com.keng.main.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keng.base.common.Pager;
import com.keng.main.entity.HisUpbgh;
import com.keng.main.entity.SysDept;
import com.keng.main.mapper.HisUpbghMapper;
import com.keng.main.service.SchedulingService;
@Service
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class SchedulingServiceImpl implements SchedulingService {
	@Autowired
    private HisUpbghMapper hisUpbghMapper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getList(Pager pager) {
		//查询上一页的截止
		Integer lastRowNum = (pager.getPageNo()-1)*pager.getPageSize();
		Map map = pager.getParamsMap();
		map.put("lastRowNum", lastRowNum);
		List<String> ids = hisUpbghMapper.getPageIds(map);
		map.put("lastId",(ids==null||ids.size()==0)?null:ids.get(ids.size()-1));
		pager.setResult(hisUpbghMapper.selectPager(pager.getParamsMap()));
	    pager.setTotalRecord(hisUpbghMapper.selectPagerCount(pager.getParamsMap()));
	}

	@Override
	public List<SysDept> getDeptsByTime(HisUpbgh gh) {
		List<SysDept> list = hisUpbghMapper.getDeptsByTime(gh);
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getDocsByDeptId(HisUpbgh gh) {
		List<Map> list = hisUpbghMapper.getDocsByDeptId(gh);
		return list;
	}

	@Override
	public Object addGh(String ghdeptId, String docId, Integer ghnum) {
		if(ghnum !=  null) {
			//如果超过5个，则先使用循环加5个号，这样可以减少和数据库的交互 sybase使用select调用存储过程加号时会出现错误，为了不影响其他操作，将错误吞掉
			for(int i = 0 ; i < ghnum/5 ; i ++) {
				try {
					hisUpbghMapper.addGh5(ghdeptId,docId);
				} catch (Exception e) {
				}
			}
			//加完5个号之后把加号数量对5取余，在循环每次加一个号
			for(int i =0; i < ghnum%5 ; i ++) {
				try {
					hisUpbghMapper.addGh(ghdeptId,docId);
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	@Override
	public Object getGhTime() {
		return hisUpbghMapper.getGhTime();
	}

	@Override
	public int editGhTime(String c_code, String c_value) {
		Map map = new HashMap<>();
		map.put("c_code", c_code);
		map.put("c_value", c_value);
		return hisUpbghMapper.editGhTime(map);
	}
	
}
