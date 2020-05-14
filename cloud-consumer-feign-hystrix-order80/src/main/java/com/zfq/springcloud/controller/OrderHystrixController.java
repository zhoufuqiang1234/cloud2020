package com.zfq.springcloud.controller;

import com.zfq.springcloud.service.OrderHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/consumer")
public class OrderHystrixController {
    @Resource private OrderHystrixService orderHystrixService;

    @GetMapping("/payment/get/{id}")
    public String paymentinfo_OK(Integer id){
        return orderHystrixService.paymentInfo_OK(id);
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentinfo_TimeOut(Integer id){
        return orderHystrixService.paymentInfo_Timeout(id);
    }
}
