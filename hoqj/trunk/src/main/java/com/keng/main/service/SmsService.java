/**
 * 
 */
package com.keng.main.service;
/**
 * @Copyright: 禁止转发.
 * @Version: $Id$
 * @Desc:
 */
public interface SmsService {
    // http://sms.webnova.com.au/api/sendMessage.php?method=sendMessages&username=eSwap&password=2DEiJC1d&mobile=8618623500249&text=%E6%B5%8B%E8%AF%95%E7%9F%AD%E6%81%AF%E5%86%85%E5%AE%B9%E3%80%90%E6%BE%B3%E6%96%B0%E9%80%9A%E3%80%91&api_id=87
    /**
     * 非大陆手机短信接口地址
     */
    // public static final String sendurl =
    // "http://api.clickatell.com/http/sendmsg?user=eswipe&password=DNZEgdNTZMgfeC&api_id=3548144&to=[phone]&text=[content]&unicode=1";
    /**
     * 支付宝活动用
     */
    public static final String sendurl   = "http://api.clickatell.com/http/sendmsg?user=yfaustralia&password=CLQYIQTebEGJYO&api_id=3583529&to=[phone]&text=[content]&unicode=1";
    // String sendurl =
    // "http://sms.webnova.com.au/api/sendMessage.php?method=sendMessages&username=eSwap&password=2DEiJC1d&mobile=[phone]&text=[content]&api_id=87";
    /**
     * 大陆手机短信接口地址
     */
    public static final String dlsendurl = "http://sms.1xinxi.cn/asmx/smsservice.aspx?name=13301757350&pwd=A8DCC90FCD1A55BC3886FEFEE36A&content=[content]&mobile=[phone]&type=pt";

    // String dlsendurl =
    // "http://sms.1xinxi.cn/asmx/smsservice.aspx?name=13301757350&pwd=A8DCC90FCD1A55BC3886FEFEE36A&content=[content]&mobile=[phone]&type=pt";
    void sendMessage(String phone, String content);

    // 餐饮或者活动兑换卡卷的时候，不抛异常
    void sendEventMessage(String phone, String content);

    // 活动卡卷和餐饮消费 给5个商家联系发送短信
    void sendBatchEventMessage(String phone, String phone2, String phone3, String phone4, String phone5, String content);

    // parse短信推送接口
    public boolean smspush(String phone, String content, int type);
}
