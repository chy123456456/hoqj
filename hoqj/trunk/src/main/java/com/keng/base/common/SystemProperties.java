package com.keng.base.common;
/**
 * Title：SystemProperties Desc：系統常量定義
 * @date 2016-4-15 上午10:18:39
 */
public class SystemProperties {
    // 订单状态
    /** 代付款(未付款) */
    public static final Integer OS_INIT       = 1;
    /** 待分配(已付款) */
    public static final Integer OS_PAYED      = 2;
    /** 待收货(已分配) */
    public static final Integer OS_ALLOCATED  = 3;
    /** 已送达 */
    public static final Integer OS_DELIVERED  = 4;
    /** 交易完成(已收货) */
    public static final Integer OS_RECEIVED   = 5;
    /** 交易关闭 */
    public static final Integer OS_CLOSED     = 6;
    // 退款状态
    /** 已申请退款 */
    public static final Integer RS_REFOUNDING = 1;
    /** 审核通过 */
    public static final Integer RS_EGIS       = 2;
    /** 审核通过 */
    public static final Integer RS_REJECT     = 3;
    /** 已退款 */
    public static final Integer RS_REFOUNDED  = 4;
    // 配送员状态
    /** 注册 */
    public static final Integer SS_INIT       = 1;
    /** 已激活 */
    public static final Integer SS_ACTIVE     = 2;
    /** 禁用 */
    public static final Integer SS_DISABLE    = 3;
    // 设置表常用字段
    /** 城市查询cfgId */
    public static final String  AREA_ID       = "AREA";
    /** 商户类型查询cfgId */
    public static final String  SHOPTYPE_ID   = "SHOPTYPE";
}
