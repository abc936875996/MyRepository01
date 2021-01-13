package com.tencent.exception;

/**
 * @author PR
 * @package com.tencent.exception
 * @date 2021/1/12 16:13
 */
public class CannotOrderException extends RuntimeException {
    public CannotOrderException(String message) {
        super(message);
    }
}
