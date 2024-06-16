package com.pay.cashier.utils;

public class SybConstants {
    // 此部分常量用于配置与通联支付的接口连接信息
    // 正式环境测试参数
    public static final String SYB_ORGID = "";// 集团/机构模式下该参数不为空，且appid与key是与次参数对应
    public static final String SYB_CUSID = "55355105499VLTR";
    public static final String SYB_APPID = "00256400";
    public static final String SYB_MD5_APPKEY = "a0ea3fa20dbd7bb4d5abf1d59d63bae8";
    public static final String SYB_APIURL = "https://vsp.allinpay.com/apiweb/unitorder";// 生产环境
    // https://vsp.allinpay.com/apiweb/unitorder/pay

    // 签名类型，这里使用SM2算法
    public static final String SIGN_TYPE = "RSA";

    // RSA私钥，用于发送请求时的签名
    public static final String SYB_RSACUSPRIKEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDqSh8LnbVeMrmmd0GT9blKQ2+bPZfHVBsTSCQ+Y2BOPbvyblLJCUCN6yvQIlwwEoZ+MweMLzx6GGXSQzPX2gGgHNMcwPjco1GbfMhNWrOiu4+Ycnc42BQNH3IRRx036XH5JwgHh432tNxUGr9t/a4jWzsYA4QkdIIJ1Pbx5WOLvETmcRRaL+tkCVdUnbNe/prCLg/cBpW2wV1YRvRrkAs/CTX2EE3T3XeFq1d+7yRd5tMTR5/syZ+OOB2bJp0BMxeoCdxzJRuV35/0MDlRZW2BIukm1EEKWLut1hqvgjqOPazKIjPq7Ogm/KntgRtaO5m2Gp7P5As4PD5XxuIlHGbHAgMBAAECggEAcI0OD/sB45BTvmQhBU7w5fuydG3o6bF+Vkt8WdpMrBmdHhAgxgIjWGonAJRZHCDyKlMiZ5ZTo8XvuExfbe0FheEDDBeqFfiBXkRaR0rw7BbVm2M14gFv1IQMPL+gijkj7xDwmwlkSiXFiMPWvaS2ELclktoxk3/C7q03uqo7oabzUM2F4z5HfManUTT7HCxoJ8eilBvpGRXHofWUsxi849bizIvRvOXYUMsgrTjck3yl9B5q0eUVKXjv5Y/rogX7UnXCTF7J/dCpP5wDYRqEUipeb6CfFuXB/06I4mzK9oTLbsMbgM6rGtSpTzSVMA2U4V/fl+GAoNg7qMeIggjE3QKBgQD4W4wcuVsoozK7HFwIynz+R2KGehVJiPAcr7GogxPURdXWarlTRHrF3j8PdybMSbz4PcUvJzAvF3zkWg4ZNtA0TiJBF8MvSf3pEm8IBJa/XDYNITuep5Cv74nkNvE1xzStrHxVEuH3lEjBqBp4U9IgZ6dp3eBH36xjnmpgX31nNQKBgQDxf8B5BMAiTItMQyZjMt/TRkwiJ4tIBAxWI6sL2lM909PF565nUE+QP+3Q/ol8s/9dkOQZ74+66sWBtS3K7V2vcy8gjtDkJ/wuWh2BtX1kkCmfmDTF02NgvBHJygHwKpNi+q3foA2802nA2AIUHxR7ifCxRzR4GcUACtSuJ+KJiwKBgQDnByIbRkGKV+XFMOhcNSB42abJOZ+7u+rAhJ9bBjPX7u6weq7Syoz6q3c1x70LpDxpH+zhpC6qX8sKEMu0oMylG1m5+4TR5J6RxVgLuVkRwZ";

    // 通联平台的RSA公钥，用于验证通联返回数据的签名
    public static final String SYB_RSATLPUBKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6kofC521XjK5pndBk/W5SkNvmz2Xx1QbE0gkPmNgTj278m5SyQlAjesr0CJcMBKGfjMHjC88ehhl0kMz19oBoBzTHMD43KNRm3zITVqzoruPmHJ3ONgUDR9yEUcdN+lx+ScIB4eN9rTcVBq/bf2uI1s7GAOEJHSCCdT28eVji7xE5nEUWi/rZAlXVJ2zXv6awi4P3AaVtsFdWEb0a5ALPwk19hBN0913hatXfu8kXebTE0ef7MmfjjgdmyadATMXqAnccyUbld+f9DA5UWVtgSLpJtRBCli7rdYar4I6jj2syiIz6uzoJvyp7YEbWjuZthqez+QLODw+V8biJRxmxwIDAQAB";

    //	/**商户sm2私钥,用于向通联发起请求前进行签名**/
    public static final String SYB_SM2PPRIVATEKEY = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgjj4Rk+b0YjwO+UwXofnHf4bK+kaaY5Btkd8nMP2VimmgCgYIKoEcz1UBgi2hRANCAAQqlALW4qGC3bP1x3wo5QsKxaCMEZJ2ODTTwOQ+d8UGU7GoK/y/WMBQWf5upMnFU06p5FxGooXYYoBtldgm03hq";
    //	/**通联平台sm2公钥，用于请求返回或者通联通知的验签**/
    public static final String SYB_SM2TLPUBKEY = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEBQicgWm0KAMqhO3bdqMUEDrKQvYg8cCXHhdGwq7CGE6oJDzJ1P/94HpuVdBf1KidmPxr7HOH+0DAnpeCcx9TcQ==";
    //测试环境调试参数
    //	public static final String SYB_ORGID = "";
    //	public static final String SYB_CUSID = "990581007426001";
    //	public static final String SYB_APPID = "00000051";
    //	public static final String SYB_MD5_APPKEY = "allinpay888";
    //	public static final String SYB_APIURL = "https://test.allinpaygd.com/apiweb/unitorder";//生产环境
    //	public static final String SIGN_TYPE = "MD5";
        /**商户RSA私钥,用于向通联发起请求前进行签名**/
    //	public static final String SYB_RSACUSPRIKEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAK5aIo+E1eyWwEIgMB8ZEZRAaWjSAglmfKVhzy8N1eLjAlqPjJgOCqXGEYt/r61AyIjCCJiYVDTHzcqstmbBU7HKpYjTsquCLjRWcL/fhMwMGBSg2bP5mqw5locSOz1gtRujmd3kZo9OIJuWtfG2+wgPPdKUdGZS+5K8WtWCF4z1AgMBAAECgYAPvvqvkPzb9tpqrmsCJ/qvM6kBazP9Ytjfe8ehFYQLT1qrUJsPMXdzNMHpYhD82eSyeymZFGrIcIIMq4/2lD+pYOMQTMGGjoVb2wnQhZFqPdgjXgOQ90E43X69jD3p5F8CuKVNa13I4l3iyfzlVIL780JPdJdug7yKEFdSeOQZUQJBAONlFpIqz87pbnwzfgO5kRTbbI7DcyObb8OEeCK3VlGB3r9P4NoMEDaXm+HnIdv53gnFq+xgbREWUt2nFq9dSUUCQQDESOIdSvIBc3KQTYR+cnlQTH0SOvm0Tlx4KekBCLxTFAFyBqnOBLdVyQb6Z1wxGz855AjnNbHy1rFhUYQ6hPfxAkAIRZUcnBITJMqwGe9rk0SDzbeVOebmVLEsG5WDLcgmDuNbcjxrsiSk178D6LSCnARHtrkaUCenh3hcN8fLeUlBAkABNP2G9pYEYkRbFM7yxBtw3feK7Cfq7uxspL1VD0uxKxdTLy1OIgNKmMDdO1N6zdMWtQtE+LSObLmMgqbQgU7RAkBFX5kl4+B3k+/aCYB/ndqd1nQIr4SNAtLFJDtlW2xah9W2lQL/7KQDT4o4dUMY51m7Bu61SAmKtralv7Hf25yf";
    //	/**通联平台RSA公钥，用于请求返回或者通联通知的验签**/
    //	public static final String SYB_RSATLPUBKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYXfu4b7xgDSmEGQpQ8Sn3RzFgl5CE4gL4TbYrND4FtCYOrvbgLijkdFgIrVVWi2hUW4K0PwBsmlYhXcbR+JSmqv9zviVXZiym0lK3glJGVCN86r9EPvNTusZZPm40TOEKMVENSYaUjCxZ7JzeZDfQ4WCeQQr2xirqn6LdJjpZ5wIDAQAB";
    //
    //	/**商户sm2私钥,用于向通联发起请求前进行签名**/
    //	public static final String SYB_SM2PPRIVATEKEY = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgjj4Rk+b0YjwO+UwXofnHf4bK+kaaY5Btkd8nMP2VimmgCgYIKoEcz1UBgi2hRANCAAQqlALW4qGC3bP1x3wo5QsKxaCMEZJ2ODTTwOQ+d8UGU7GoK/y/WMBQWf5upMnFU06p5FxGooXYYoBtldgm03hq";
    //	/**通联平台sm2公钥，用于请求返回或者通联通知的验签**/
    //	public static final String SYB_SM2TLPUBKEY = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE/BnA8BawehBtH0ksPyayo4pmzL/u1FQ2sZcqwOp6bjVqQX4tjo930QAvHZPJ2eez8sCz/RYghcqv4LvMq+kloQ==";

}
