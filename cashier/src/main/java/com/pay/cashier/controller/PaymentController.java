package com.pay.cashier.controller;

import com.pay.cashier.entity.dto.*;
import com.pay.cashier.result.Result;
import com.pay.cashier.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
@CrossOrigin
public class PaymentController {

    @Autowired
    private OrderService orderService;

    //统一下单，异步类交易
    @PostMapping("/payment")
    public Result<Object> payment(@RequestBody PaymentRequestDTO paymentRequest) throws Exception{

        // 调用支付服务的pay方法，传入相关参数发起支付请求
        // 参数包括商户订单号、请求序列号、业务类型、订单标题、订单备注、商品描述等
        Map<String, String> map = orderService.pay(paymentRequest);
        return Result.success(map);
    }

    //统一扫码，被扫交易
    @PostMapping("/ScanPay")
    public Result<Object> scanPay(@RequestBody ScanPayRequestDTO scanPayRequest) throws Exception {
        // TODO Auto-generated method stub
        Map<String, String> map = orderService.scanPay(scanPayRequest);
        return Result.success(map);
//        return Result.success(1);
    }

    //撤销
    @PostMapping("/cancel")
    public Result<Object> Cancel(@RequestBody CancelRequestDTO cancelRequest) throws Exception{
        Map<String, String> map = orderService.cancel(cancelRequest);
        return Result.success(map);
    }

    //退款
    @PostMapping("/refund")
    public Result<Object> Refund(@RequestBody RefundRequestDTO refundRequest){
        Map<String, String> map = orderService.refund(refundRequest);
        return Result.success(map);
    }

    // 查询
    @PostMapping("/query")
    public Result<Object> Query(@RequestBody QueryRequestDTO queryRequest){
        Map<String, String> map = orderService.query(queryRequest);
        return Result.success(map);
    }
}
