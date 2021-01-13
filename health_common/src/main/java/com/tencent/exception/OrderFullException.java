package com.tencent.exception;

/**
 * @author PR
 * @package com.tencent.constant
 * @date 2021/1/12 16:09
 */
public class OrderFullException extends RuntimeException {
    public OrderFullException(String message) {
        super(message);
    }
}
