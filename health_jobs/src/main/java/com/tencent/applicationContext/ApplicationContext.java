package com.tencent.applicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author PR
 * @package com.tencent.applicationContext
 * @date 2021/1/8 21:52
 */
public class ApplicationContext {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:spring-dubbo.xml");
        System.in.read();
    }
}
