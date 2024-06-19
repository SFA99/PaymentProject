package com.pay.cashier.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerminalInfo {
    private String termno;           // 终端号
    private String devicetype;       // 设备类型
    private String termsn;           // 终端序列号
    private String encryptrandnum;   // 加密随机因子
    private String secrettext;       // 密文数据
    private String appversion;       // 终端程序版本号
    private String longitude;        // 经度
    private String latitude;         // 纬度
    private String deviceip;         // 终端IP
}
