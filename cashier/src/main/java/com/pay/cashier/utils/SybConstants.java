package com.pay.cashier.utils;

public class SybConstants {
    // 此部分常量用于配置与通联支付的接口连接信息
    // 正式环境测试参数
    public static final String SYB_ORGID = "";// 集团/机构模式下该参数不为空，且appid与key是与次参数对应

    public static final String SYB_CUSID = "56255105499D5ZX";

    public static final String SYB_APPID = "00259522";
    public static final String SYB_MD5_APPKEY = "a0ea3fa20dbd7bb4d5abf1d59d63bae8";
    public static final String SYB_APIURL = "https://vsp.allinpay.com/apiweb/unitorder";// 生产环境
    public static final String ACC = "oM4tN4x6Cdp51AYTZCYzPN2E8gLU";// 生产环境

    public static final String NOTIFY_URL = "https://zion-app.functorz.com/rmkky19AQmK/zero/EQvM7ze16PP/callback/74263d09-cfd7-4f0a-ab17-ee432ea6199f";
    // https://vsp.allinpay.com/apiweb/unitorder/pay

    // 签名类型，这里使用RSA算法
    public static final String SIGN_TYPE = "RSA";

    // RSA私钥，用于发送请求时的签名
    public static final String SYB_RSACUSPRIKEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDqSh8LnbVeMrmmd0GT9blKQ2+bPZfHVBsTSCQ+Y2BOPbvyblLJCUCN6yvQIlwwEoZ+MweMLzx6GGXSQzPX2gGgHNMcwPjco1GbfMhNWrOiu4+Ycnc42BQNH3IRRx036XH5JwgHh432tNxUGr9t/a4jWzsYA4QkdIIJ1Pbx5WOLvETmcRRaL+tkCVdUnbNe/prCLg/cBpW2wV1YRvRrkAs/CTX2EE3T3XeFq1d+7yRd5tMTR5/syZ+OOB2bJp0BMxeoCdxzJRuV35/0MDlRZW2BIukm1EEKWLut1hqvgjqOPazKIjPq7Ogm/KntgRtaO5m2Gp7P5As4PD5XxuIlHGbHAgMBAAECggEAcI0OD/sB45BTvmQhBU7w5fuydG3o6bF+Vkt8WdpMrBmdHhAgxgIjWGonAJRZHCDyKlMiZ5ZTo8XvuExfbe0FheEDDBeqFfiBXkRaR0rw7BbVm2M14gFv1IQMPL+gijkj7xDwmwlkSiXFiMPWvaS2ELclktoxk3/C7q03uqo7oabzUM2F4z5HfManUTT7HCxoJ8eilBvpGRXHofWUsxi849bizIvRvOXYUMsgrTjck3yl9B5q0eUVKXjv5Y/rogX7UnXCTF7J/dCpP5wDYRqEUipeb6CfFuXB/06I4mzK9oTLbsMbgM6rGtSpTzSVMA2U4V/fl+GAoNg7qMeIggjE3QKBgQD4W4wcuVsoozK7HFwIynz+R2KGehVJiPAcr7GogxPURdXWarlTRHrF3j8PdybMSbz4PcUvJzAvF3zkWg4ZNtA0TiJBF8MvSf3pEm8IBJa/XDYNITuep5Cv74nkNvE1xzStrHxVEuH3lEjBqBp4U9IgZ6dp3eBH36xjnmpgX31nNQKBgQDxf8B5BMAiTItMQyZjMt/TRkwiJ4tIBAxWI6sL2lM909PF565nUE+QP+3Q/ol8s/9dkOQZ74+66sWBtS3K7V2vcy8gjtDkJ/wuWh2BtX1kkCmfmDTF02NgvBHJygHwKpNi+q3foA2802nA2AIUHxR7ifCxRzR4GcUACtSuJ+KJiwKBgQDnByIbRkGKV+XFMOhcNSB42abJOZ+7u+rAhJ9bBjPX7u6weq7Syoz6q3c1x70LpDxpH+zhpC6qX8sKEMu0oMylG1m5+4TR5J6RxVgLuVkRwZF2PUfcmjt5rfYzmPhg8mIKfL1b1ijlyca0fs/AxxhclI+jZ4ZO1b7jHGYQKEPbUQKBgQDhmi2NIFH4+La8tJJc4z7TGP2wfm6QBTAdHE8C0J8oyAUzAT8cxpq9L+uTbOW1eFVb7CoazH/h5sZBaKbxxpLZf2oMKT6dlXjxOEAOVyRf2Ij5zW+nStWwnaCkeLEc0jlDotjhapzKxFkrzN8MOwiga/lNMy6h77joVDXgx+PKBwKBgQCgfirNKF/Okt70k/MbDcomeBKMZ+3fhEDuYtg4Sd+TyIH727EMgO3t2P51VXbpo+IZNTA0I9102BF11beQ16zM+9f1d1UzuD/soZTPE+dF3BRsRLyxRK/OZeTmqtfUjROH6OYw+DX89W0S3YKtWHDEqgfXifh+xc+Pg9M/uaK27A==";
    public static final String SYB_RSATLPUBKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCm9OV6zH5DYH/ZnAVYHscEELdCNfNTHGuBv1nYYEY9FrOzE0/4kLl9f7Y9dkWHlc2ocDwbrFSm0Vqz0q2rJPxXUYBCQl5yW3jzuKSXif7q1yOwkFVtJXvuhf5WRy+1X5FOFoMvS7538No0RpnLzmNi3ktmiqmhpcY/1pmt20FHQQIDAQAB";

    //	/**商户sm2私钥,用于向通联发起请求前进行签名**/
    public static final String SYB_SM2PPRIVATEKEY = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgjj4Rk+b0YjwO+UwXofnHf4bK+kaaY5Btkd8nMP2VimmgCgYIKoEcz1UBgi2hRANCAAQqlALW4qGC3bP1x3wo5QsKxaCMEZJ2ODTTwOQ+d8UGU7GoK/y/WMBQWf5upMnFU06p5FxGooXYYoBtldgm03hq";
    //	/**通联平台sm2公钥，用于请求返回或者通联通知的验签**/
    public static final String SYB_SM2TLPUBKEY = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEBQicgWm0KAMqhO3bdqMUEDrKQvYg8cCXHhdGwq7CGE6oJDzJ1P/94HpuVdBf1KidmPxr7HOH+0DAnpeCcx9TcQ==";

}
