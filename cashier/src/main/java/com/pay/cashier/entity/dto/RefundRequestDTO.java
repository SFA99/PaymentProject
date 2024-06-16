package com.pay.cashier.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundRequestDTO {
    private String orgid;      // 集团/代理商商户号
    private String cusid;      // 商户号
    private String appid;      // 应用ID
    private String version;    // 版本号
    private String trxamt;     // 退款金额
    private String reqsn;      // 商户退款订单号
    private String oldreqsn;   // 原交易订单号
    private String oldtrxid;   // 原交易流水
    private String remark;     // 备注
    private String benefitdetail; // 优惠信息
    private String randomstr;  // 随机字符串
    private String signtype;   // 签名方式
    private String sign;       // 签名
}
