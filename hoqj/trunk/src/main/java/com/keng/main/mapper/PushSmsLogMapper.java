package com.keng.main.mapper;

import java.util.List;
import java.util.Map;

import com.keng.main.entity.PushSmsLog;
import com.keng.base.common.Pager;

public interface PushSmsLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PushSmsLog record);

    int insertSelective(PushSmsLog record);

    PushSmsLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PushSmsLog record);

    int updateByPrimaryKey(PushSmsLog record);

    /**
     * 修改交易推送时间和状态
     * @param map
     * @return
     */
    int updDtimeAndStatus(Map<String, Object> map);

    /**
     * 修改转账推送时间和状态
     * @param map
     * @return
     */
    int updTtimeAndStatus(Map<String, Object> map);

    /**
     * 修改转账推送信息
     * @param map
     * @return
     */
    int updBillIdAndTstimeByOrderNo(Map<String, Object> map);

    /**
     * 根据转账id查询推送信息
     * @param map
     * @return
     */
    List<PushSmsLog> getPushSmsLog(Map<String, Object> map);

    /**
     * 根据订单号修改交易推送信息
     * @param map
     * @return
     */
    int updDealSmsStatus(Map<String, Object> map);

    /**
     * 根据订单号修改转账推送信息
     * @param map
     * @return
     */
    int updTSmsStatus(Map<String, Object> map);
    /**
     * 分页获取短信日志
     * @param pager
     * @return
     */
    List<Map<String, Object>> selectPager(Pager pager);
    /**
     * 根据订单id查询短信日志
     * @param map
     * @return
     */
    PushSmsLog selectSmsLogByOrderNo(Map<String, Object> map);

    /**
     * 查询支付时间小于当前2分钟之前的短信发送失败的订单
     * @param map
     * @return
     */
    List<PushSmsLog> selectSmsLogList(Map<String, Object> map);

    /**
     * 查询已转账短信发送失败的订单并按转账id分组
     * @return
     */
    List<PushSmsLog> selectPushSmsLogList();
    
}