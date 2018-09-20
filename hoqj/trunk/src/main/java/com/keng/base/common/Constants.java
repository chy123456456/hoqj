package com.keng.base.common;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    /** Session保存登录用户信息key */
    public final static String        SESSION_ADMIN_USER   = "_SESSION_ADMIN_USER_";
    /** session保存验证码信息key */
    public final static String        SESSION_KEY_AUTHCODE = "_SESSION_KEY_AUTHCODE_";
    /** 默认用户密码 */
    public final static String        DEFAULT_PASSWORD     = "111111";
    /** DEBUG模式 */
    public static boolean             DEBUG                = true;
    /** 是否压缩HTML代码 */
    public static boolean             HTML_IS_COMPRESS     = false;
    /** 文件上传路径 */
    public static String              UPLOAD_PATH_PARENT;
    /** 后台url */
    public static String              SYSTEM_URL;
    /** 接口url */
    public static String              SERVICE_URL;
    /** 超级管理员 */
    public static final int           POST_SUPER_ADMIN     = 1;
    /** 角色：普通商家 */
    public final static String        ROLE_NAME            = "普通商家";
    /** 角色：渠道商 */
    public final static String        ROLE_CHANNEL_NAME    = "渠道商";
    /** 渠道：渠道名称 */
    public final static String        CHANNEL_NAME         = "自主渠道";
    /** google parse短信推送接口 **/
    public static final String        SMS_PUSH_ACTION      = "com.wyhd.sms.action";
    /** 短信结尾 */
    public static final String        SMS_LAST_STR         = "【PAYBANG】";
    /** 支付宝优惠商家pid 和key 和 currency 活动支付使用该账号 */
    public static String       ALIPAY_ACTIVITY_PID;
    public static String       ALIPAY_ACTIVITY_KEY;
    public static String       ALIPAY_ACTIVITY_CURRENCY;
    public static String       ALIPAY_MD5_KEY;
    /** 支付宝私钥和公钥 */
    public static String       ALIPAY_MERCHANT_PARTNER;
    public static String       ALIPAY_PRIVATE_KEY;
    public static String       ALIPAY_PUBLIC_KEY;
    
    /** 推送应用ID和KEY */
    public static String       PUSH_APPLICATION_ID;
    public static String       PUSH_REST_API_KEY;
    
    /** 切换数据库使用的常量 */
    public static String dbSybase = "dataSource_sybase";
    public static String dbmysql = "dataSourceMysql";
    public static String dblissqlserver = "lis_dataSource_sqlserver";
    public static String dbdxptsqlserver = "dxpt_dataSource_sqlserver";
    public static String dbzdhris6sqlserver = "zdhris6_dataSource_sqlserver";
    public static Map<String, Object> POST_MAPPERS         = new HashMap<String, Object>();
    static {
        POST_MAPPERS.put("SUPER_ADMIN", POST_SUPER_ADMIN);
    }
    
    @Value("${path.upload}")
    public void setUPLOAD_PATH_PARENT(String upload_path_parent) {
        UPLOAD_PATH_PARENT = upload_path_parent;
    }

    @Value("${url.system}")
    public void setSYSTEM_URL(String sYSTEM_URL) {
        SYSTEM_URL = sYSTEM_URL;
    }

    @Value("${url.service}")
    public void setSERVICE_URL(String sERVICE_URL) {
        SERVICE_URL = sERVICE_URL;
    }
    
    @Value("${alipay.activity.pid}")
    public void setALIPAY_ACTIVITY_PID(String alipay_activity_pid) {
        ALIPAY_ACTIVITY_PID = alipay_activity_pid;
    }
    
    @Value("${alipay.activity.key}")
    public void setALIPAY_ACTIVITY_KEY(String alipay_activity_key) {
        ALIPAY_ACTIVITY_KEY = alipay_activity_key;
    }
    
    @Value("${alipay.activity.currency}")
    public void setALIPAY_ACTIVITY_CURRENCY(String alipay_activity_currency) {
        ALIPAY_ACTIVITY_CURRENCY = alipay_activity_currency;
    }
    
    @Value("${alipay.md5.key}")
    public void setALIPAY_MD5_KEY(String alipay_md5_key) {
        ALIPAY_MD5_KEY = alipay_md5_key;
    }
    
    @Value("${alipay.merchant.partner}")
    public void setALIPAY_MERCHANT_PARTNER(String alipay_merchant_partner) {
        ALIPAY_MERCHANT_PARTNER = alipay_merchant_partner;
    }
    
    @Value("${alipay.private.key}")
    public void setALIPAY_PRIVATE_KEY(String alipay_private_key) {
        ALIPAY_PRIVATE_KEY = alipay_private_key;
    }
    
    @Value("${alipay.public.key}")
    public void setALIPAY_PUBLIC_KEY_CURRENCY(String alipay_public_key) {
        ALIPAY_PUBLIC_KEY = alipay_public_key;
    }
    
    
    @Value("${application.id}")
    public void setPUSH_APPLICATION_ID(String push_application_id) {
        PUSH_APPLICATION_ID = push_application_id;
    }
    
    @Value("${rest.api.key}")
    public void setPUSH_REST_API_KEY_CURRENCY(String push_rest_api_key) {
        PUSH_REST_API_KEY = push_rest_api_key;
    }
    
}
