/**
 * 
 */
package com.keng.main.service.impl;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.keng.base.common.Constants;
import com.keng.base.common.HttpUtils;
import com.keng.base.utils.JacksonBuilder;
import com.keng.base.utils.LogicException;
import com.keng.main.service.SmsService;

/**
 * @Version: $Id$
 * @Desc:
 */
@Service
public class SmsServiceImpl implements SmsService {
    private static Logger       logger         = LoggerFactory.getLogger(SmsServiceImpl.class);
    private static final String ENCODE_CHARSET = "UTF-16BE";
    private static final byte[] HEX_BYTE       = "0123456789abcdef".getBytes();

    /*
     * (non-Javadoc)
     * 
     * @see com.ym.tong.services.sms.SmsService#sendMessage(java.lang.String, java.lang.String)
     */
    @Override
    public void sendMessage(String phone, String content) {
        logger.debug("发送短信号码========" + phone + "   发送短信内容=====================" + content);
        String url = null;
        if (phone.startsWith("86") && phone.substring(2).length() == 11) {// 大陆手机
            phone = phone.substring(2);
            url = dlsendurl.replace("[phone]", phone).replace("[content]", content);
        } else if (phone.startsWith("+86") && phone.substring(3).length() == 11) {// 大陆手机
            phone = phone.substring(3);
            url = dlsendurl.replace("[phone]", phone).replace("[content]", content);
        } else {// 非大陆手机
            try {
                url = sendurl.replace("[phone]", phone).replace("[content]", encodeHex(content, ENCODE_CHARSET));
            } catch (UnsupportedEncodingException ignore) {
                /* never happens... */
            }
        }
        try {
            String sendResult = HttpUtils.getInstance().httpGet(url, new HashMap<String, String>(), false);
            logger.info("{url:" + url + "; sendResult:" + sendResult + "}");
        } catch (Exception e) {
            logger.error("给[" + phone + "]发送短信[" + content + "]异常", e);
            throw new LogicException("发送短信异常，请重试");
        }
    }

    public void sendEventMessage(String phone, String content) {
        logger.debug("发送短信内容=====================" + content);
        logger.debug("餐饮或者活动卡卷短信发送内容" + content + "手机号=" + phone);
        String url = null;
        if (phone.startsWith("86") && phone.substring(2).length() == 11) {// 大陆手机
            phone = phone.substring(2);
            url = dlsendurl.replace("[phone]", phone).replace("[content]", content).replace(" ", "%20");
        } else if (phone.startsWith("+86") && phone.substring(3).length() == 11) {// 大陆手机
            phone = phone.substring(3);
            url = dlsendurl.replace("[phone]", phone).replace("[content]", content).replace(" ", "%20");
        } else {// 非大陆手机
            try {
                if (phone.startsWith("0")) {
                    phone = phone.replaceFirst("0", "61").replace("-", "");
                }
                logger.debug("国外短信 0开始用61代替活动卡卷短信真实号码手机号=" + phone);
                url = sendurl.replace("[phone]", phone).replace("[content]", encodeHex(content, ENCODE_CHARSET));
            } catch (UnsupportedEncodingException ignore) {
                /* never happens... */
            }
        }
        try {
            String sendResult = HttpUtils.getInstance().httpGet(url, new HashMap<String, String>(), false);
            logger.info("{url:" + url + "; sendResult:" + sendResult + "}");
        } catch (Exception e) {
            logger.error("餐饮或者活动给商家给[" + phone + "]发送短信[" + content + "]异常", e);
        }
    }

    @Override
    public void sendBatchEventMessage(String phone, String phone2, String phone3, String phone4, String phone5, String content) {
        logger.debug("发送短信内容=====================" + content);
        logger.debug("活动卡卷和餐饮消费 给5个商家联系发送短信内容" + content + "手机号=" + phone + "=2=" + phone2 + "=3=" + phone3 + "=4=" + phone4 + "=5=" + phone5);
        String url = null;
        StringBuffer sb = new StringBuffer();
        String sendPhone = "";
        if (StringUtils.isNotEmpty(phone2) && phone2.startsWith("86") && phone2.substring(2).length() == 11) {// 大陆商家
            sb.append(phone2.substring(2));
            if (StringUtils.isNotEmpty(phone3) && phone3.startsWith("86") && phone3.substring(2).length() == 11) {
                sb.append(",").append(phone3.substring(2));
            }
            if (StringUtils.isNotEmpty(phone4) && phone4.startsWith("86") && phone4.substring(2).length() == 11) {
                sb.append(",").append(phone4.substring(2));
            }
            if (StringUtils.isNotEmpty(phone5) && phone5.startsWith("86") && phone5.substring(2).length() == 11) {
                sb.append(",").append(phone5.substring(2));
            }
            sendPhone = sb.toString();
            logger.debug("批量短信国内真实号码手机号=" + sendPhone);
            url = dlsendurl.replace("[phone]", sendPhone).replace("[content]", content).replace(" ", "%20");
            try {
                String sendResult = HttpUtils.getInstance().httpGet(url, new HashMap<String, String>(), false);
                logger.info("{url:" + url + "; sendResult:" + sendResult + "}");
            } catch (Exception e) {
                logger.error("批量给国内商家发送短信[" + sendPhone + "]发送短信[" + content + "]异常", e);
            }
        } else {// 澳洲商户电话
            // if(phone.startsWith("0")){
            // phone = phone.replaceFirst("0", "61").replace("-", "");
            // }
            // if(phone.startsWith("610")){//澳洲人习惯电话号码前加0，需去掉
            // phone = phone.replaceFirst("610", "61");
            // }
            // if(!phone.startsWith("61")){
            // phone="61"+phone;
            // }
            // sb.append(phone);
            if (StringUtils.isNotEmpty(phone2)) {// 联系人2
                if (phone2.startsWith("0")) {
                    phone2 = phone2.replaceFirst("0", "61").replace("-", "");
                }
                if (phone2.startsWith("610")) {// 澳洲人习惯电话号码前加0，需去掉
                    phone2 = phone2.replaceFirst("610", "61");
                }
                if (!phone2.startsWith("61")) {
                    phone2 = "61" + phone2;
                }
                sb.append(phone2);
                if (StringUtils.isNotEmpty(phone3)) {// 联系人3
                    if (phone3.startsWith("0")) {
                        phone3 = phone3.replaceFirst("0", "61").replace("-", "");
                    }
                    if (phone3.startsWith("610")) {// 澳洲人习惯电话号码前加0，需去掉
                        phone3 = phone3.replaceFirst("610", "61");
                    }
                    if (!phone3.startsWith("61")) {
                        phone3 = "61" + phone3;
                    }
                    sb.append(",").append(phone3);
                }
                if (StringUtils.isNotEmpty(phone4)) {// 联系人4
                    if (phone4.startsWith("0")) {
                        phone4 = phone4.replaceFirst("0", "61").replace("-", "");
                    }
                    if (phone4.startsWith("610")) {// 澳洲人习惯电话号码前加0，需去掉
                        phone4 = phone4.replaceFirst("610", "61");
                    }
                    if (!phone4.startsWith("61")) {
                        phone4 = "61" + phone4;
                    }
                    sb.append(",").append(phone4);
                }
                if (StringUtils.isNotEmpty(phone5)) {// 联系人5
                    if (phone5.startsWith("0")) {
                        phone5 = phone5.replaceFirst("0", "61").replace("-", "");
                    }
                    if (phone5.startsWith("610")) {// 澳洲人习惯电话号码前加0，需去掉
                        phone5 = phone5.replaceFirst("610", "61");
                    }
                    if (!phone5.startsWith("61")) {
                        phone5 = "61" + phone5;
                    }
                    sb.append(",").append(phone5);
                }
            }
            sendPhone = sb.toString();
            if (!"".equals(sendPhone)) {
                try {
                    logger.debug("批量短信国外短信 0开始用61代替短信真实号码手机号=" + sendPhone);
                    url = sendurl.replace("[phone]", sendPhone).replace("[content]", encodeHex(content, ENCODE_CHARSET));
                    String sendResult = HttpUtils.getInstance().httpGet(url, new HashMap<String, String>(), false);
                    logger.info("{url:" + url + "; sendResult:" + sendResult + "}");
                } catch (Exception e) {
                    logger.error("批量给国内商家发送短信[" + sendPhone + "]发送短信[" + content + "]异常", e);
                }
            } else {
                logger.debug("批量短信国外短信 0开始用61代替短信商户电话不存在，请去后台维护；联系电话=" + phone);
            }
        }
    }

    /**
     * 将字符串编码成指定字符集的hex byte字符串
     * 
     * @param s 源字符串
     * @param charsetName 字符集
     * @return 16进制字符串
     * @throws UnsupportedEncodingException
     */
    public static String encodeHex(String s, String charsetName) throws UnsupportedEncodingException {
        byte[] bytes = s.getBytes(charsetName);
        byte buf[] = new byte[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            buf[i * 2] = HEX_BYTE[(bytes[i] >> 4) & 0x0f];
            buf[i * 2 + 1] = HEX_BYTE[bytes[i] & 0x0f];
        }
        return new String(buf);
    }

    /**
     * 将hex byte字符串根据指定字符集解码
     * 
     * @param hex 16进制字符串
     * @param charsetName 字符集
     * @return 源字符串
     * @throws UnsupportedEncodingException
     */
    public static String decodeHex(String hex, String charsetName) throws UnsupportedEncodingException {
        char[] chars = hex.toCharArray();
        byte[] buf = new byte[chars.length / 2];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) (Integer.parseInt(new String(chars, i * 2, 2), 16));
        }
        return new String(buf, charsetName);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String encodeHex = encodeHex("你好，世界！", ENCODE_CHARSET);
        System.out.println(encodeHex);
        System.out.println(decodeHex(encodeHex, ENCODE_CHARSET));
        // new SmsServiceImpl().sendMessage("8613983287976", "fas");
    }

    /**
     * 谷歌短信推送入口 type{}
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean smspush(String phone, String content, int type) {
        // content = content.substring(0, 60);
        HttpUtils httpUtils = HttpUtils.getInstance();
        String url = "https://api.parse.com/1/push";
        List<Header> headers = new ArrayList<Header>();
        // 测试的key
        Header header1 = new BasicHeader("X-Parse-Application-Id", Constants.PUSH_APPLICATION_ID);
        Header header2 = new BasicHeader("X-Parse-REST-API-Key", Constants.PUSH_REST_API_KEY);
        // 正式的key
        // Header header1 = new BasicHeader("X-Parse-Application-Id", "MShvDdh1gJ3Eu63RRqpi8VfeKS6VUyYAsz0s8Drx");
        // Header header2 = new BasicHeader("X-Parse-REST-API-Key", "hpMBP2sHGIqKpk7c6UfZbs3TYUdV2O3ivLPZ6IvT");
        headers.add(header1);
        headers.add(header2);
        /*
         * {"where":{"deviceType": "android"},"data": {"action": "com.wyhd.sms.action", "orderId": "62014534532333",
         * "phone": "8618716664540|8618734432232", "message": "你有一笔消费￥49", "type": 1 }}
         */
        String json = "{\"where\":{\"deviceType\":\"android\"},\"data\":{\"action\":\"" + Constants.SMS_PUSH_ACTION + "\",\"orderId\":\"" + null
                + "\",\"phone\":\"" + phone + "\",\"message\":\"" + content + "\",\"type\":" + type + "}}";
        logger.debug("向app推送短信发送内容=========" + json);
        try {
            String result = httpUtils.httpPost(url, headers, json, false);
            Map<String, Object> map = JacksonBuilder.mapper().readValue(result, Map.class);
            logger.debug("返回json:" + result);
            return map.get("result").equals(true);
        } catch (Exception e) {
            logger.error("请求推送服务器异常", e);
            return false;
        }
    }
}
