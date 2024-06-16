package com.pay.cashier.service;

import com.pay.cashier.entity.dto.*;

import java.util.Map;

public interface OrderService {
    Map<String, String> pay(PaymentRequestDTO paymentRequest);

    Map<String, String> scanPay(ScanPayRequestDTO scanPayRequest);

    Map<String, String> cancel(CancelRequestDTO cancelRequest);

    Map<String, String> refund(RefundRequestDTO refundRequest);

    Map<String, String> query(QueryRequestDTO queryRequest);

}
