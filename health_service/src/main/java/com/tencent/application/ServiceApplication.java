package com.tencent.application;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author PR
 * @package com.tencent.application
 * @date 2021/1/5 20:33
 */
public class ServiceApplication {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:spring-service.xml");
        System.in.read();
    }
}
