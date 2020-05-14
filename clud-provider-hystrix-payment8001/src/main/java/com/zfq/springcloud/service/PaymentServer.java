package com.zfq.springcloud.service;

import org.springframework.stereotype.Service;

@Service
public interface PaymentServer {

    String paymentinfo_OK(Integer id);

    String paymentinfo_TimeOut(Integer id);
}
