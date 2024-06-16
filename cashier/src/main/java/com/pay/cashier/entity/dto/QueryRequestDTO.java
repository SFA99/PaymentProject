package com.pay.cashier.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryRequestDTO {
    private String orgid;      // 集团/代理商商户号
    private String cusid;      // 商户号
    private String appid;      // 应用ID
    private String version;    // 版本号
    private String reqsn;      // 商户订单号
    private String trxid;      // 平台交易流水
    private String randomstr;  // 随机字符串
    private String signtype;   // 签名类型
    private String sign;       // 签名
}
