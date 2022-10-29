package com.xiong.springcloud.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Log4j2
public class OrderController {
    public static final String PAYMENT_SER_URL = "http://cloud-provider-payment";
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/zk")
    public String paymentzk() {
        return restTemplate.getForObject(PAYMENT_SER_URL + "/payment/zk", String.class);
    }
}
