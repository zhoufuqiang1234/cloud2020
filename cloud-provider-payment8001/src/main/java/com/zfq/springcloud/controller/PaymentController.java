package com.zfq.springcloud.controller;

import com.mysql.jdbc.TimeUtil;
import com.zfq.springcloud.entities.CommonResult;
import com.zfq.springcloud.entities.Payment;
import com.zfq.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;



    @PostMapping(value = "/payment/insert")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("*********插入结果： " + result);
        if (result > 0) {
            return new CommonResult(200, "插入数据成功serverPort:" +serverPort, result);
        } else {
            return new CommonResult(444, "插入数据失败", null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*********插入结果2： " + payment);
        if (payment != null) {
            return new CommonResult(200, "查询成功热部署测试 serverPort:" +serverPort, payment);
        } else {
            return new CommonResult(444, "没有对记录，查询id："+ id, null);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object getdiscovery(){
        List<String> services = discoveryClient.getServices();
        for (String element :services){
            log.info("**********" + element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance: instances){
            log.info(instance.getServiceId() + "\t" +instance.getHost() + "\t" + instance.getPort() + "\t" +instance.getUri());

        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public  String getpaymentLB(){
        return serverPort;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public  String paymentFeignTimeout(){
        try {
            //线程休眠时间
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  serverPort;
    }
}
