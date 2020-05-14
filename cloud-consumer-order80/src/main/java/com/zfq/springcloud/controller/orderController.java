package com.zfq.springcloud.controller;

import com.netflix.discovery.EurekaClientConfig;
import com.sun.jndi.toolkit.url.Uri;
import com.zfq.springcloud.entities.CommonResult;
import com.zfq.springcloud.entities.Payment;
import com.zfq.springcloud.lb.LoadBalance;
import com.zfq.springcloud.lb.MyLB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class orderController {

    //public static final String PAYMENT_URL = "http://localhost:8001"; //单机版地址可以写死
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";//如果集群的话就要写成Eureka注册得服务名
        @Resource
        private RestTemplate restTemplate;
        @Resource
        private LoadBalance loadBalance;
        @Resource
        private DiscoveryClient discoveryClient;

        @GetMapping("/consumer/payment/create")
        public CommonResult<Payment> create(Payment payment){
            return  restTemplate.postForObject(PAYMENT_URL +"/payment/insert",payment,CommonResult.class);
        }

        @GetMapping("/consumer/payment/get/{id}")
        public CommonResult<Payment> getpayment(@PathVariable("id") Long id){
            log.info("order订单开始{}", id);
            return restTemplate.getForObject(PAYMENT_URL +"/payment/get/" + id, CommonResult.class);
        }

        @GetMapping("/consumer/getDisCovery")
        public Object getDisCovery(){
            List<String> services = discoveryClient.getServices();
            for (int i = 0; i <services.size() ; i++) {
                log.info("*********element：" +services.get(i) );
            }
            List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
            for (int i = 0; i <instances.size() ; i++) {
                log.info(instances.get(i).getServiceId()+"******host"+instances.get(i).getHost());
            }
            return this.discoveryClient;
        }

    @GetMapping(value = "/consumer/payment/lb")
    public  String getpaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances == null || instances.size() <= 0){
            return null;
        }
        ServiceInstance instances1 = loadBalance.instances(instances);
        URI uri = instances1.getUri();
        return restTemplate.getForObject(uri + "/payment/lb",String.class);
    }
}
