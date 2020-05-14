package com.zfq.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zfq.springcloud.service.PaymentServer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @author fuqiang.zhou@hand-china.com
 * @description
 * @date2020/3/31 19:40
 * @params
 * @return
 */
@Service public class PaymentServerImpl implements PaymentServer {


    @Override public String paymentinfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "  paymentinfo_OK id: " + "id" + "/t" + "哈哈";
    }

    @HystrixCommand(fallbackMethod = "paymentinfo_Handle", commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")})
    @Override public String paymentinfo_TimeOut(Integer id) {
        int timeSleep = 3;
        try {
            TimeUnit.SECONDS.sleep(timeSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "  paymentinfo_TimeOut id: " + "id" + "/t" + "嘿嘿" + "耗时：  "
                        + timeSleep;
    }

    public String paymentinfo_Handle(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "  paymentinfo_Handle id: " + "id" + "/t" + "嘿嘿这是超时降级方案";
    }

    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),              //是否开启断路器
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),    //请求数达到后才计算
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //休眠时间窗
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),  //错误率达到多少跳闸
    }) public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("*********id 不能为负数");
        }
        //生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
        String simpleUUID = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功,流水号：" + simpleUUID;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id不能为负数，请稍后重试 id" + id;
    }
}
