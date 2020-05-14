package com.zfq.springcloud.controller;

import com.zfq.springcloud.service.PaymentServer;
import com.zfq.springcloud.service.impl.PaymentServerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private PaymentServerImpl paymentServer;

    @GetMapping("/payment/get/{id}")
    public String paymentinfo_OK(@PathVariable("id") Integer id){
        return paymentServer.paymentinfo_OK(id);
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentinfo_TimeOut(@PathVariable("id") Integer id){
        return paymentServer.paymentinfo_TimeOut(id);
    }
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        return paymentServer.paymentCircuitBreaker(id);
    }
}
