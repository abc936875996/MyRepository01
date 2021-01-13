package com.tencent.controller;

import com.aliyuncs.exceptions.ClientException;
import com.tencent.constant.MessageConstant;
import com.tencent.constant.RedisMessageConstant;
import com.tencent.pojo.Result;
import com.tencent.utils.SMSUtils;
import com.tencent.utils.ValidateCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author PR
 * @package com.tencent.controller
 * @date 2021/1/12 17:46
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    private Logger logger = LoggerFactory.getLogger(ValidateCodeController.class);

    /**
     * 体检预约验证码
     */
    @GetMapping("/send4Order")
    public Result send4Order(String telephone) throws ClientException {
        //1.检查Redis中是否存在验证码
        Jedis jedis = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        if (jedis.exists(key)) {
            //1.1已存在
            return new Result(false, MessageConstant.SEND_VALIDATECODE_ALREADY);
        }
        //2.生成验证码
        String validateCode = ValidateCodeUtils.generateValidateCode4String(6);
        //3.将验证码存入Redis，设置有效期
        jedis.setex(key, 10 * 60, validateCode);
        jedis.close();
        //4.发送短信
//        SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode);

        logger.debug("======已发送验证码：" + validateCode + "======");

        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
