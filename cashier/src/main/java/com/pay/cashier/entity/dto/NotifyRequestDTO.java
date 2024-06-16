package com.pay.cashier.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyRequestDTO {
    private String appid;            // 收银宝APPID
    private String outtrxid;         // 第三方交易号
    private String trxcode;          // 交易类型
    private String trxid;            // 收银宝交易单号
    private String initamt;          // 原始下单金额
    private long trxamt;             // 交易金额
    private String trxdate;          // 交易请求日期
    private String paytime;          // 交易完成时间
    private String chnltrxid;        // 渠道流水号
    private String trxstatus;        // 交易结果码
    private String cusid;            // 商户编号
    private String termno;           // 终端编号
    private String termbatchid;      // 终端批次号
    private String termtraceno;     // 终端流水号
    private String termauthno;       // 终端授权码
    private String termrefnum;       // 终端参考号
    private String trxreserved;      // 业务关联内容
    private String srctrxid;         // 原交易流水
    private String cusorderid;       // 业务流水
    private String acct;             // 交易账号
    private String fee;              // 手续费
    private String signtype;         // 签名类型
    private String cmid;             // 渠道子商户号
    private String chnlid;           // 渠道号
    private String chnldata;         // 渠道信息
    private String accttype;         // 借贷标识
    private String bankcode;         // 发卡行
    private String logonid;          // 支付宝买家账号
    private String sign;             // sign校验码
}
