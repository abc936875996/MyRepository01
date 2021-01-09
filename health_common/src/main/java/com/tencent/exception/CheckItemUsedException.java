package com.tencent.exception;

/**
 * @author PR
 * @package com.tencent.exception
 * @date 2021/1/7 16:35
 */
public class CheckItemUsedException extends RuntimeException {
    public CheckItemUsedException(String message){
        super(message);
    }
}
