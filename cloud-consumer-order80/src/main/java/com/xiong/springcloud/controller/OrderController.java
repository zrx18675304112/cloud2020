package com.xiong.springcloud.controller;

import com.xiong.springcloud.entities.CommonResult;
import com.xiong.springcloud.entities.Payment;
import com.xiong.springcloud.lb.LoadBalancer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * @author 12796
 */
@RestController
@Log4j2
public class OrderController {
//    public static final String PAYMENT_SER_URL = "http://localhost:8001";

    public static final String PAYMENT_SER_URL = "http://CLOUD-PAYMENT-SERVICE";

    //可以获取注册中心上的服务列表
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private LoadBalancer loadBalancer;


    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResult create(Payment payment) {
        log.info(payment);
        return restTemplate.postForObject(PAYMENT_SER_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult getPayment(@PathVariable Long id) {
        log.info("consumer" + id);
        return restTemplate.getForObject(PAYMENT_SER_URL + "/payment/get/" + id, CommonResult.class, id);
    }


    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB() {
        List instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() == 0) {
            return null;
        }
        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }
}
