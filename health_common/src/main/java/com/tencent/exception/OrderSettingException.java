package com.tencent.exception;

/**
 * @author PR
 * @package com.tencent.exception
 * @date 2021/1/9 17:43
 */
public class OrderSettingException extends RuntimeException {
    public OrderSettingException(String message){
        super(message);
    }
}
