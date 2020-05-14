package com.zfq.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalance {


    private AtomicInteger atomicInteger = new AtomicInteger(0);
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances){
         int i = getAndIncrement()%serviceInstances.size();
        return serviceInstances.get(i);
    }

    public final int getAndIncrement(){
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current>= 2147483647?0:current+1;
        }while(!this.atomicInteger.compareAndSet(current,next));
        System.out.println("*******第几次next:" + next);
        return next;
    }
}
