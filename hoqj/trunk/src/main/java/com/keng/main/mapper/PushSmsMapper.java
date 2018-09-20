package com.keng.main.mapper;

import java.util.List;
import java.util.Map;

import com.keng.main.entity.PushSms;
import com.keng.base.common.Pager;

public interface PushSmsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PushSms record);

    int insertSelective(PushSms record);

    PushSms selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PushSms record);

    int updateByPrimaryKey(PushSms record);

    /**
     * 修改短信状态和时间
     * @param map
     * @return
     */
    int updStimeAndStatus(Map<String, Object> map);

    /**
     * 根据订单号和类型查询短信发送信息
     * @param map
     * @return
     */
    List<PushSms> getPushsSmsByOrderNoAndType(Map<String, Object> map);

    /**
     * 分页获取短信详情
     * @param pager
     * @return
     */
    List<Map<String, Object>> selectSmsPager(Pager pager);

    /**
     * 条件查询短信信息
     * @param map
     * @return
     */
    PushSms selectPushSms(Map<String, Object> map);


}