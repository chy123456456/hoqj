package com.keng.main.service;

import com.keng.base.common.Pager;

/**
 * <p>Title：PushSmsService</p>
 * <p>Desc：</p>
 * @date 2016-2-26 上午10:30:05
 */
public interface PushSmsService {

    /**
     * 短信推送
     * @param phone
     * @param orderNo
     * @param content
     * @param type
     * @return
     */
    boolean smspush(String phone, String orderNo, String content, int type);

    /**
     * 修改短信推送接收时间和状态
     * @param orderId
     * @param type
     * @param pushTime
     * @return
     */
    int updPushSmsLogTimeAndStatus(String orderId, int type, String pushTime);

    /**
     * 保存短信发送
     * @param orderId
     * @param phone
     * @param type
     * @param sendTime
     * @return
     */
    int addSendSms(String orderId, String phone, int type, String sendTime, int status);

    /**
     * 修改短信接收
     * @param orderId
     * @param phone
     * @param type
     * @param successTime
     * @return
     */
    int updSendSms(String orderId, String phone, int type, String successTime);

    /**
     * 分页获取短信日志信息
     * @param pager
     * @return
     */
    Pager queryPage(Pager pager);

    /**
     * 分页活动短信详情
     * @param pager
     * @return
     */
    Pager querySmsPager(Pager pager);

    /**
     * 每5分钟定时检查交易短信发送失败进行第三方平台发送===========
     */
    void pushDealOrdersTask();

    /**
     * 凌晨1点30分检查转账短信发送失败的进行第三方平台发送===========
     */
    void pushTransferOrdersTask();
    
}
