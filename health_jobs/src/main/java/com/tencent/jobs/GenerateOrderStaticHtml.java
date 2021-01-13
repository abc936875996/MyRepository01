package com.tencent.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author PR
 * @package com.tencent.jobs
 * @date 2021/1/11 19:59
 */
@Component("generateOrderStaticHtml")
public class GenerateOrderStaticHtml {

    @Scheduled(initialDelay = 3000,fixedDelay = 10000)
    public void generateOrderHtml(){

    }
}
