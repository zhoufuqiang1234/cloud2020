package com.zfq.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

public interface LoadBalance {

    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
