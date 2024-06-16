package com.pay.cashier.controller;

import com.pay.cashier.utils.HttpConnectionUtil;
import com.pay.cashier.utils.SybConstants;
import com.pay.cashier.utils.SybUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@WebServlet(name = "NotifyServlet",urlPatterns = {"/notify"})
public class NotifyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public NotifyServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get method,no deal");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("接收到通知");
        request.setCharacterEncoding("UTF-8");//通知传输的编码为GBK
        response.setCharacterEncoding("UTF-8");
        TreeMap<String, String> params = getParams(request);//动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容
        try {
            String appkey = "";
            if ("RSA".equals(params.get("signtype"))) {
                appkey = SybConstants.SYB_RSATLPUBKEY;
            } else if ("SM2".equals(params.get("signtype"))) {
                appkey = SybConstants.SYB_SM2TLPUBKEY;
            } else {
                appkey = SybConstants.SYB_MD5_APPKEY;
            }
            boolean isSign = SybUtil.validSign(params, appkey, params.get("signtype"));// 接受到推送通知,首先验签
            if(isSign){
                // 业务逻辑处理
                System.out.println("success");

                String reqSn = params.get("cusorderid");
                int orderIdStart = reqSn.indexOf("_");
                int orderIdEnd = reqSn.lastIndexOf("_");

                // 取出订单id
                String orderId = reqSn.substring(orderIdStart + 1, orderIdEnd);

                // 取出订单类型
                String orderTypeIdx = reqSn.substring(0, orderIdStart);

                String orderType = getOrderType(orderTypeIdx);

                // 下面是模拟的远程请求调用，实际应用中应使用HTTP客户端如OkHttp、Apache HttpClient等
                // String resRes = sendHttpRequest(orderId, orderType, "已完成", reqSn);

                Map<String, String> postData = new HashMap<>();
                postData.put("order_id", orderId);
                postData.put("order_type", orderType);
                postData.put("order_status", "已完成");
                postData.put("pay_order", reqSn);

                /**
                 * 初始化HttpConnectionUtil实例，用于与指定URL建立连接并发送POST请求。
                 * 此段代码初始化了一个HttpConnectionUtil对象，设置了请求的URL，该URL是回调函数的地址，
                 * 并通过调用init方法初始化了HTTP连接，随后通过调用postParams方法发送了POST请求，携带了特定的POST数据。
                 */
                HttpConnectionUtil http = new HttpConnectionUtil("https://zion-app.functorz.com/rmkky19AQmK/zero/EQvM7ze16PP/callback/74263d09-cfd7-4f0a-ab17-ee432ea6199f");
                http.init();
                http.postParams(postData,true);
        }
            System.out.println("验签结果:" + isSign);
            //验签完毕进行业务处理
        } catch (Exception e) {//处理异常
            // TODO: handle exception
            e.printStackTrace();
        } finally {//收到通知,返回success
            response.getOutputStream().write("success".getBytes());
            response.flushBuffer();
        }
    }
    private String getOrderType(String orderTypeIdx) {
        switch (orderTypeIdx) {
            case "goods":
                return "商品购买";
            case "vip":
                return "超级会员";
            case "gvip":
                return "等级会员";
            case "cards":
                return "卡券购买";
            default:
                return "未知类型";
        }
    }
    /**
     * 动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容由于收银宝加字段而引起的签名异常
     *
     * @param request
     * @return
     */
    private TreeMap<String, String> getParams(HttpServletRequest request) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        Map reqMap = request.getParameterMap();
        for (Object key : reqMap.keySet()) {
            String value = ((String[]) reqMap.get(key))[0];
            System.out.println(key + ";" + value);
            map.put(key.toString(), value);
        }
        return map;
    }

}
