package com.pay.cashier.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanPayRequestDTO {
    private String orgid; // 集团/代理商商户号
    private String cusid; // 商户号
    private String appid; // 应用ID
    private String version; // 版本号
    private String randomstr; // 随机字符串
    private String trxamt; // 交易金额
    private String reqsn; // 商户交易单号
    private String body; // 订单标题
    private String remark; // 备注
    private String authcode; // 支付授权码
    private String limit_pay; // 支付限制
    private String goods_tag; // 订单优惠标记
    private String benefitdetail; // 优惠信息
    private String sub_appid; // 微信子appid
    private String chnlstoreid; // 渠道门店编号
    private String subbranch; // 门店号
    private String idno; // 证件号
    private String truename; // 付款人真实姓名
    private String extendparams; // 拓展参数
    private String asinfo; // 分账信息
    private String fqnum; // 分期
    private String notify_url; // 交易结果通知地址
    private String signtype; // 签名方式
    private String unpid; // 银联pid
    private String sign; // 签名
    private String terminfo; // 终端信息
    private String operatorid; // 收银员号


}
