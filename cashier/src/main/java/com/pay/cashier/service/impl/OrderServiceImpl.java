package com.pay.cashier.service.impl;

import com.pay.cashier.entity.dto.*;
import com.pay.cashier.service.OrderService;
import com.pay.cashier.utils.HttpConnectionUtil;
import com.pay.cashier.utils.SybConstants;
import com.pay.cashier.utils.SybUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class OrderServiceImpl implements OrderService {


    @Override
    public Map<String, String> pay(PaymentRequestDTO paymentRequest) {
        // 初始化HTTP连接工具，并指定支付接口URL
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL + "/pay");
        try {
            http.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 创建一个有序参数集合，确保参数的顺序
        TreeMap<String, String> params = new TreeMap<String, String>();
        // 根据配置，添加必要的请求参数
        // 生成请求序列号，用于唯一标识此次支付请求
        String reqsn = String.valueOf(System.currentTimeMillis());
        if (!SybUtil.isEmpty(SybConstants.SYB_ORGID)) {
            params.put("orgid", SybConstants.SYB_ORGID);
            params.put("cusid", SybConstants.SYB_CUSID);
            params.put("appid", SybConstants.SYB_APPID);
            params.put("version", "11");
            params.put("trxamt", String.valueOf(paymentRequest.getTrxamt()));
            params.put("reqsn", reqsn);
            params.put("paytype", paymentRequest.getPaytype());
            params.put("randomstr", SybUtil.getValidatecode(8));
            params.put("body", paymentRequest.getBody());
            params.put("remark", paymentRequest.getRemark());
            params.put("validtime", paymentRequest.getValidtime());
            params.put("acct", paymentRequest.getAcct());
            params.put("notify_url", paymentRequest.getNotify_url());
            params.put("limit_pay", paymentRequest.getLimit_pay());
            params.put("sub_appid", paymentRequest.getSub_appid());
            params.put("goods_tag", paymentRequest.getGoods_tag());
            params.put("benefitdetail", paymentRequest.getBenefitdetail());
            params.put("chnlstoreid", paymentRequest.getChnlstoreid());
            params.put("subbranch", paymentRequest.getSubbranch());
            params.put("extendparams", paymentRequest.getExtendparams());
            params.put("cusip", paymentRequest.getCusip());
            params.put("fqnum", paymentRequest.getFqnum());
            params.put("idno", paymentRequest.getIdno());
            params.put("truename", paymentRequest.getTruename());
            params.put("asinfo", paymentRequest.getAsinfo());
            params.put("signtype", SybConstants.SIGN_TYPE);
        }
        // 根据签名类型选择合适的appkey进行签名
        String appkey = "";
        if (SybConstants.SIGN_TYPE.equals("RSA")) {
            appkey = SybConstants.SYB_RSACUSPRIKEY;
        } else if (SybConstants.SIGN_TYPE.equals("SM2")) {
            appkey = SybConstants.SYB_SM2PPRIVATEKEY;
        } else {
            appkey = SybConstants.SYB_MD5_APPKEY;
        }
        /* 将签名信息添加到参数集合中 */
        /* "sign"是待添加的参数键 */
        /* SybUtil.unionSign(params,appkey,SybConstants.SIGN_TYPE)是调用工具类SybUtil的静态方法unionSign， */
        /* 该方法用于生成签名值，参数包括params（参数集合）、appkey（应用密钥）和SIGN_TYPE（签名类型）， */
        /* 方法会根据这些信息计算出签名值，并将其添加到参数集合params中 */
        Map<String, String> map = null;
        try {
            params.put("sign", SybUtil.unionSign(params, appkey, SybConstants.SIGN_TYPE));
            // 发送POST请求，提交支付参数
            byte[] bys = new byte[0];
            bys = http.postParams(params, true);
            // 解析返回的支付结果
            String result = null;

            result = new String(bys, "UTF-8");

            map = handleResult(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    @Override
    public Map<String, String> scanPay(ScanPayRequestDTO scanPayRequest) {
        // TODO Auto-generated method stub
        /**
         * 初始化HttpConnectionUtil实例，用于后续的二维码支付请求。
         * 通过构造函数传入API的URL，确保请求地址的正确性。
         *
         * @param apiURL 二维码支付接口的URL，需拼接在基础URL后。
         */
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL + "/scanqrpay");

        try {
            http.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TreeMap<String, String> params = new TreeMap<String, String>();
        String reqsn = String.valueOf(System.currentTimeMillis());
        if (!SybUtil.isEmpty(SybConstants.SYB_ORGID)) {
            params.put("orgid", SybConstants.SYB_ORGID);
            params.put("cusid", SybConstants.SYB_CUSID);
            params.put("appid", SybConstants.SYB_APPID);
            params.put("version", "11");
            params.put("trxamt", String.valueOf(scanPayRequest.getTrxamt()));
            params.put("reqsn", reqsn);
            params.put("randomstr", SybUtil.getValidatecode(8));
            params.put("body", scanPayRequest.getBody());
            params.put("remark", scanPayRequest.getRemark());
            params.put("authcode", scanPayRequest.getAuthcode());
            params.put("limit_pay", scanPayRequest.getLimit_pay());
            params.put("asinfo", scanPayRequest.getAsinfo());
            params.put("signtype", SybConstants.SIGN_TYPE);
        }
        String appkey = "";
        if (SybConstants.SIGN_TYPE.equals("RSA")) {
            appkey = SybConstants.SYB_RSACUSPRIKEY;
        } else if (SybConstants.SIGN_TYPE.equals("SM2")) {
            appkey = SybConstants.SYB_SM2PPRIVATEKEY;
        } else {
            appkey = SybConstants.SYB_MD5_APPKEY;
        }
        Map<String, String> map = null;
        try {
            params.put("sign", SybUtil.unionSign(params, appkey, SybConstants.SIGN_TYPE));
            byte[] bys = http.postParams(params, true);
            String result = new String(bys, "UTF-8");
            map = handleResult(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    @Override
    public Map<String, String> cancel(CancelRequestDTO cancelRequest) {
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL + "/cancel");
        try {
            http.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TreeMap<String, String> params = new TreeMap<String, String>();
        String reqsn = String.valueOf(System.currentTimeMillis());
        if (!SybUtil.isEmpty(SybConstants.SYB_ORGID)) {
            params.put("orgid", SybConstants.SYB_ORGID);
            params.put("cusid", SybConstants.SYB_CUSID);
            params.put("appid", SybConstants.SYB_APPID);
            params.put("version", "11");
            params.put("trxamt", String.valueOf(cancelRequest.getTrxamt()));
            params.put("reqsn", reqsn);
            params.put("oldtrxid", cancelRequest.getOldtrxid());
            params.put("oldreqsn", cancelRequest.getOldreqsn());
            params.put("randomstr", SybUtil.getValidatecode(8));
            params.put("signtype", SybConstants.SIGN_TYPE);
        }
        String appkey = "";
        if (SybConstants.SIGN_TYPE.equals("RSA")) {
            appkey = SybConstants.SYB_RSACUSPRIKEY;
        } else if (SybConstants.SIGN_TYPE.equals("SM2")) {
            appkey = SybConstants.SYB_SM2PPRIVATEKEY;
        } else {
            appkey = SybConstants.SYB_MD5_APPKEY;
        }

        Map<String, String> map = null;
        try {
            params.put("sign", SybUtil.unionSign(params, appkey, SybConstants.SIGN_TYPE));
            byte[] bys = http.postParams(params, true);
            String result = new String(bys, "UTF-8");
            map = handleResult(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    @Override
    public Map<String, String> refund(RefundRequestDTO refundRequest) {
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL + "/refund");
        try {
            http.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TreeMap<String, String> params = new TreeMap<String, String>();
        String reqsn = String.valueOf(System.currentTimeMillis());
        if (!SybUtil.isEmpty(SybConstants.SYB_ORGID)) {
            params.put("orgid", SybConstants.SYB_ORGID);
            params.put("cusid", SybConstants.SYB_CUSID);
            params.put("appid", SybConstants.SYB_APPID);
            params.put("version", "11");
            params.put("trxamt", String.valueOf(refundRequest.getTrxamt()));
            params.put("reqsn", reqsn);
            params.put("oldreqsn", refundRequest.getOldreqsn());
            params.put("oldtrxid", refundRequest.getOldtrxid());
            params.put("randomstr", SybUtil.getValidatecode(8));
            params.put("signtype", SybConstants.SIGN_TYPE);
        }
        String appkey = "";
        if (SybConstants.SIGN_TYPE.equals("RSA")) {
            appkey = SybConstants.SYB_RSACUSPRIKEY;
        } else if (SybConstants.SIGN_TYPE.equals("SM2")) {
            appkey = SybConstants.SYB_SM2PPRIVATEKEY;
        } else {
            appkey = SybConstants.SYB_MD5_APPKEY;
        }
        Map<String, String> map = null;
        try {
            params.put("sign", SybUtil.unionSign(params, appkey, SybConstants.SIGN_TYPE));
            byte[] bys = http.postParams(params, true);
            String result = new String(bys, "UTF-8");
            map = handleResult(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    @Override
    public Map<String, String> query(QueryRequestDTO queryRequest) {
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL + "/query");
        try {
            http.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TreeMap<String, String> params = new TreeMap<String, String>();
        String reqsn = String.valueOf(System.currentTimeMillis());
        if (!SybUtil.isEmpty(SybConstants.SYB_ORGID)) {
            params.put("orgid", SybConstants.SYB_ORGID);
            params.put("cusid", SybConstants.SYB_CUSID);
            params.put("appid", SybConstants.SYB_APPID);
            params.put("version", "11");
            params.put("reqsn", reqsn);
            params.put("trxid", queryRequest.getTrxid());
            params.put("randomstr", SybUtil.getValidatecode(8));
            params.put("signtype", SybConstants.SIGN_TYPE);
        }
        String appkey = "";
        if (SybConstants.SIGN_TYPE.equals("RSA")) {
            appkey = SybConstants.SYB_RSACUSPRIKEY;
        } else if (SybConstants.SIGN_TYPE.equals("SM2")) {
            appkey = SybConstants.SYB_SM2PPRIVATEKEY;
        } else {
            appkey = SybConstants.SYB_MD5_APPKEY;
        }
        Map<String, String> map = null;
        try {
            params.put("sign", SybUtil.unionSign(params, appkey, SybConstants.SIGN_TYPE));
            byte[] bys = http.postParams(params, true);
            String result = new String(bys, "UTF-8");
            map = handleResult(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, String> handleResult(String result) throws Exception {
        System.out.println("ret:" + result);
        Map map = SybUtil.json2Obj(result, Map.class);
        if (map == null) {
            throw new Exception("返回数据错误");
        }
        if ("SUCCESS".equals(map.get("retcode"))) {
            TreeMap tmap = new TreeMap();
            tmap.putAll(map);
            String appkey = "";
            if (SybConstants.SIGN_TYPE.equals("RSA")) {
                appkey = SybConstants.SYB_RSATLPUBKEY;
            } else if (SybConstants.SIGN_TYPE.equals("SM2")) {
                appkey = SybConstants.SYB_SM2TLPUBKEY;
            } else {
                appkey = SybConstants.SYB_MD5_APPKEY;
            }
            if (SybUtil.validSign(tmap, appkey, SybConstants.SIGN_TYPE)) {
                System.out.println("签名成功");
                return map;
            } else {
                throw new Exception("验证签名失败");
            }

        } else {
            throw new Exception(map.get("retmsg").toString());
        }
    }
}
