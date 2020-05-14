package com.zfq.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class OrderFallback implements OrderHystrixService{

    @Override
    public String paymentInfo_OK(Integer id){
        return "OrderFallback  paymentInfo_OK";
    }
    @Override
    public String paymentInfo_Timeout(Integer id){
        return "OrderFallback  paymentInfo_Timeout";
    }
}
