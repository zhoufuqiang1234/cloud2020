package com.zfq.springcloud.controller;

import com.zfq.springcloud.entities.CommonResult;
import com.zfq.springcloud.service.orderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping(value =  "/consumer")
public class orderController {

    @Resource
    private orderService orderServices;
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        return orderServices.getPaymentById(id);
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        //openfeign-ribbon 客户端一般默认等待时间1秒钟
        return orderServices.paymentFeignTimeout();
    }
}
